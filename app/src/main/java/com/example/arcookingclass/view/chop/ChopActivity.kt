package com.example.arcookingclass.view.chop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.arcookingclass.R
import com.example.arcookingclass.view.turnongas.TurnOnGasActivity
import com.google.ar.core.Anchor
import com.google.ar.core.Pose
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_chop.*

class ChopActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var arFragment : ArFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chop)

        complete_button.setOnClickListener(this)

        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment
        arFragment.setOnTapArPlaneListener(BaseArFragment.OnTapArPlaneListener { hitResult, plane, motionEvent ->
            val anchor : Anchor = hitResult.createAnchor()

            ModelRenderable.builder()
                    .setSource(this, R.raw.knife)
                    .build()
                    .thenAccept{ addModelToScence(anchor,it) }
                    .exceptionally {
                        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
                        builder.setMessage(it.localizedMessage)
                                .show()
                        return@exceptionally null
                    }
        })
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.complete_button -> onCompleteButtonClick()
        }
    }

    private fun addModelToScence(anchor: Anchor, it: ModelRenderable?) {
        var anchorNode = AnchorNode(anchor)
        val transform = TransformableNode(arFragment.transformationSystem)
        transform.scaleController.maxScale = 0.05f
        transform.scaleController.minScale = 0.04f
        transform.setParent(anchorNode)
        transform.renderable = it
        arFragment.arSceneView.scene.addChild(anchorNode)
        transform.select()

        for(i in 0..1){
            Handler(Looper.getMainLooper()).postDelayed({
                arFragment.arSceneView.scene.removeChild(anchorNode)
                val position = floatArrayOf(-0.3f, 0f, 0f)
                val rotation = floatArrayOf(0f, 0f, 0f, 0f)

                val session = arFragment.arSceneView.session
                val anchor = session?.createAnchor(Pose(position, rotation))

                anchorNode.localScale = Vector3(0.05f, 0.05f, 0.05f)
                anchorNode = AnchorNode(anchor)

                anchorNode.setParent(arFragment.arSceneView.scene)
                anchorNode.renderable = it
            }, 1000)
        }
    }

    private fun onCompleteButtonClick(){
        startActivity(
                Intent(this,
                TurnOnGasActivity::class.java)
        )
    }
}