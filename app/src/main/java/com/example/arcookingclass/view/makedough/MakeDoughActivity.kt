package com.example.arcookingclass.view.makedough

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.arcookingclass.R
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.databinding.ActivityMakeDoughBinding
import com.example.arcookingclass.view.turnongas.TurnOnGasActivity
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Pose
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_make_dough.*
import kotlin.math.pow
import kotlin.math.sqrt
import com.google.ar.sceneform.rendering.Color as arColor

class MakeDoughActivity : AppCompatActivity(), Scene.OnUpdateListener {
    private lateinit var viewModel : MakeDoughViewModel
    private lateinit var binding : ActivityMakeDoughBinding

    private var arFragment: ArFragment? = null
    private val placedAnchors = ArrayList<Anchor>()
    private val placedAnchorNodes = ArrayList<AnchorNode>()
    private val midAnchors: MutableMap<String, Anchor> = mutableMapOf()
    private val midAnchorNodes: MutableMap<String, AnchorNode> = mutableMapOf()
    private val fromGroundNodes = ArrayList<List<Node>>()

    private var cubeRenderable: ModelRenderable? = null
    private var distanceCardViewRenderable: ViewRenderable? = null

    private val multipleDistances = Array(
            Constants.maxNumMultiplePoints,
            {Array<TextView?>(Constants.maxNumMultiplePoints){null} })
    private lateinit var initCM: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_make_dough)
        viewModel = ViewModelProvider(this).get(MakeDoughViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recipeItem = intent.getSerializableExtra(EXTRA_RECIPE_DATA) as Recipe

        observeLiveData()

        clearButton()
        initRenderable()

        initCM = resources.getString(R.string.initCM)
        arFragment = makeDoughArFragment as ArFragment
        arFragment!!.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if(distanceCardViewRenderable == null || cubeRenderable == null) return@setOnTapArPlaneListener
            tapDistanceOf2Points(hitResult)
        }
    }

    private fun observeLiveData(){
        viewModel.navigateToNextActivity.observe(this, Observer {
            startActivity(
                Intent(
                    this,
                    TurnOnGasActivity::class.java
                ).putExtra(TurnOnGasActivity.EXTRA_RECIPE_DATA, binding.recipeItem)
            )
        })

        viewModel.navigateToPrevActivity.observe(this, Observer {
            finish()
        })
    }

    private fun initRenderable(){
        MaterialFactory.makeTransparentWithColor(
                this,
                arColor(Color.RED))
                .thenAccept { material: Material? ->
                    cubeRenderable = ShapeFactory.makeSphere(
                            0.02f,
                            Vector3.zero(),
                            material)
                    cubeRenderable!!.setShadowCaster(false)
                    cubeRenderable!!.setShadowReceiver(false)
                }
                .exceptionally {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(it.message).setTitle("Error")
                    val dialog = builder.create()
                    dialog.show()
                    return@exceptionally null
                }

        ViewRenderable
                .builder()
                .setView(this, R.layout.distance_text_layout)
                .build()
                .thenAccept{
                    distanceCardViewRenderable = it
                    distanceCardViewRenderable!!.isShadowCaster = false
                    distanceCardViewRenderable!!.isShadowReceiver = false
                }
                .exceptionally {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(it.message).setTitle("Error")
                    val dialog = builder.create()
                    dialog.show()
                    return@exceptionally null
                }
    }

    private fun tapDistanceOf2Points(hitResult: HitResult){
        if (placedAnchorNodes.size == 0){
            placeAnchor(hitResult, cubeRenderable!!)
        }
        else if (placedAnchorNodes.size == 1){
            placeAnchor(hitResult, cubeRenderable!!)

            val midPosition = floatArrayOf(
                    (placedAnchorNodes[0].worldPosition.x + placedAnchorNodes[1].worldPosition.x) / 2,
                    (placedAnchorNodes[0].worldPosition.y + placedAnchorNodes[1].worldPosition.y) / 2,
                    (placedAnchorNodes[0].worldPosition.z + placedAnchorNodes[1].worldPosition.z) / 2)
            val quaternion = floatArrayOf(0.0f,0.0f,0.0f,0.0f)
            val pose = Pose(midPosition, quaternion)

            placeMidAnchor(pose, distanceCardViewRenderable!!)
        }
        else {
            clearAllAnchors()
            placeAnchor(hitResult, cubeRenderable!!)
        }
    }

    private fun clearAllAnchors(){
        placedAnchors.clear()
        for (anchorNode in placedAnchorNodes){
            arFragment!!.arSceneView.scene.removeChild(anchorNode)
            anchorNode.isEnabled = false
            anchorNode.anchor!!.detach()
            anchorNode.setParent(null)
        }
        placedAnchorNodes.clear()
        midAnchors.clear()
        for ((k,anchorNode) in midAnchorNodes){
            arFragment!!.arSceneView.scene.removeChild(anchorNode)
            anchorNode.isEnabled = false
            anchorNode.anchor!!.detach()
            anchorNode.setParent(null)
        }
        midAnchorNodes.clear()

        for (i in 0 until Constants.maxNumMultiplePoints){
            for (j in 0 until Constants.maxNumMultiplePoints){
                if (multipleDistances[i][j] != null){
                    multipleDistances[i][j]!!.setText(if(i==j) "-" else initCM)
                }
            }
        }
        fromGroundNodes.clear()
    }

    private fun placeMidAnchor(pose: Pose, renderable: Renderable, between: Array<Int> = arrayOf(0,1)){
        val midKey = "${between[0]}_${between[1]}"
        val anchor = arFragment!!.arSceneView.session!!.createAnchor(pose)
        midAnchors.put(midKey, anchor)

        val anchorNode = AnchorNode(anchor).apply {
            isSmoothed = true
            setParent(arFragment!!.arSceneView.scene)
        }
        midAnchorNodes.put(midKey, anchorNode)

        val node = TransformableNode(arFragment!!.transformationSystem)
                .apply{
                    this.rotationController.isEnabled = false
                    this.scaleController.isEnabled = false
                    this.translationController.isEnabled = true
                    this.renderable = renderable
                    setParent(anchorNode)
                }
        arFragment!!.arSceneView.scene.addOnUpdateListener(this)
        arFragment!!.arSceneView.scene.addChild(anchorNode)
    }

    private fun placeAnchor(hitResult: HitResult, renderable: Renderable){
        val anchor = hitResult.createAnchor()
        placedAnchors.add(anchor)

        val anchorNode = AnchorNode(anchor).apply {
            isSmoothed = true
            setParent(arFragment!!.arSceneView.scene)
        }
        placedAnchorNodes.add(anchorNode)

        val node = TransformableNode(arFragment!!.transformationSystem)
                .apply{
                    this.rotationController.isEnabled = false
                    this.scaleController.isEnabled = false
                    this.translationController.isEnabled = true
                    this.renderable = renderable
                    setParent(anchorNode)
                }

        arFragment!!.arSceneView.scene.addOnUpdateListener(this)
        arFragment!!.arSceneView.scene.addChild(anchorNode)
        node.select()
    }

    private fun clearButton(){
        clear_btn.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                clearAllAnchors()
            }
        })
    }

    private fun measureDistanceOf2Points() {
        if (placedAnchorNodes.size == 2) {
            val distanceMeter = calculateDistance(
                    placedAnchorNodes[0].worldPosition,
                    placedAnchorNodes[1].worldPosition)
            measureDistanceOf2Points(distanceMeter)
        }
    }

    private fun measureDistanceOf2Points(distanceMeter: Float){
        val distanceCM = makeDistanceTextWithCM(distanceMeter)

        val textView = (distanceCardViewRenderable!!.view as LinearLayout).findViewById<TextView>(R.id.distanceCard)
        textView.text = "${distanceCM}cm"
        _distanceCard.text = "${distanceCM}cm"

        if (distanceCM > 20.0f){
            _distanceCard.text = "물의 양을 줄여주세요. "
        } else if (distanceCM > 15.0f && distanceMeter <= 20.0f){
            _distanceCard.text = "물의 양이 적당해요!"
        } else {
            _distanceCard.text = "물을 더 넣어 주세요"
        }
    }

    private fun makeDistanceTextWithCM(distanceMeter: Float): Float{
        val distanceCM = changeUnit(distanceMeter, "cm")
        val distanceCMFloor = "%.2f".format(distanceCM)
        //return "${distanceCMFloor} cm"
        return distanceCM
    }

    private fun calculateDistance(x: Float, y: Float, z: Float): Float{
        return sqrt(x.pow(2) + y.pow(2) + z.pow(2))
    }

    private fun calculateDistance(objectPose0: Vector3, objectPose1: Vector3): Float{
        return calculateDistance(
                objectPose0.x - objectPose1.x,
                objectPose0.y - objectPose1.y,
                objectPose0.z - objectPose1.z
        )
    }

    private fun changeUnit(distanceMeter: Float, unit: String): Float{
        return when(unit){
            "cm" -> distanceMeter * 100
            "mm" -> distanceMeter * 1000
            else -> distanceMeter
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onUpdate(frameTime: FrameTime) {
        measureDistanceOf2Points()
    }

    companion object {
        const val EXTRA_RECIPE_DATA = "recipe_data"
    }
}