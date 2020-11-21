package com.example.arcookingclass.view.chop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.arcookingclass.R
import com.example.arcookingclass.view.makedough.MakeDoughActivity
import com.example.arcookingclass.view.turnongas.TurnOnGasActivity
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.core.Pose
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.BaseArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlinx.android.synthetic.main.activity_chop.*

class ChopActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var arFragment : ArFragment
    var positionX = 0f
    var positionY = 0f
    val positionZ = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chop)

        chop_next_btn.setOnClickListener(this)
        chop_prev_btn.setOnClickListener(this)

        arFragment = supportFragmentManager.findFragmentById(R.id.arFragment) as ArFragment
        arFragment.setOnTapArPlaneListener(BaseArFragment.OnTapArPlaneListener { hitResult, plane, motionEvent ->
            val anchor = hitResult.createAnchor()

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
            R.id.chop_next_btn -> onNextButtonClick()
            R.id.chop_prev_btn -> onPrevButtonClick()
        }
    }

    private fun addModelToScence(anchor: Anchor, it: ModelRenderable?) {
        val anchorNode = AnchorNode(anchor)

        it.let {
            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(positionX, positionY, positionZ)
                localScale = Vector3(0.1f, 0.1f, 0.1f)
            }
            arFragment.arSceneView.scene.addChild(anchorNode)

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(positionX, positionY, positionZ)
                localScale = Vector3(0.1f, 0.1f, 0.1f)
            }
            arFragment.arSceneView.scene.addChild(anchorNode)

            Node().apply {
                setParent(anchorNode)
                renderable = it
                localPosition = Vector3(positionX, positionY, positionZ)
                localScale = Vector3(0.1f, 0.1f, 0.1f)
            }
            arFragment.arSceneView.scene.addChild(anchorNode)
        }

//        positionX -= 3f
    }

    private fun onNextButtonClick(){
        startActivity(
                Intent(this,
                MakeDoughActivity::class.java)
        )
    }

    private fun onPrevButtonClick(){
        finish()
    }
}