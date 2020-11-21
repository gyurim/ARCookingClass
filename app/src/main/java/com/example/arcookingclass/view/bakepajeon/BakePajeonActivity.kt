package com.example.arcookingclass.view.bakepajeon

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.arcookingclass.R
import com.example.arcookingclass.databinding.ActivityBakePajeonBinding
import com.example.arcookingclass.view.addoil.AddOilActivity
import com.example.arcookingclass.view.completecooking.CompleteCookingActivity
import com.example.arcookingclass.view.turnongas.TurnOnGasActivity
import com.example.arcookingclass.view.turnongas.TurnOnGasViewModel
import kotlinx.android.synthetic.main.activity_bake_pajeon.*
import kotlinx.android.synthetic.main.activity_turn_on_gas.*

class BakePajeonActivity : AppCompatActivity() {
    private val TAG = BakePajeonActivity::class.java.simpleName
    private lateinit var viewModel: BakePajeonViewModel
    private lateinit var binding : ActivityBakePajeonBinding
    private lateinit var video_uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bake_pajeon)
        viewModel = ViewModelProvider(this).get(BakePajeonViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        video_uri = Uri.parse("android.resource://$packageName/raw/bake_pajeon_video");
        bake_pajeon_video.setMediaController(MediaController(this))
        bake_pajeon_video.requestFocus()
        bake_pajeon_video.setVideoURI(video_uri)

        initListener()
        observeLiveData()

        Log.d(TAG, "여기다")
    }

    private fun initListener(){
        bake_pajeon_video.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp : MediaPlayer){

            }
        })

        bake_pajeon_video.setOnCompletionListener(object : MediaPlayer.OnCompletionListener{
            override fun onCompletion(mp: MediaPlayer) {

            }
        })
    }

    private fun observeLiveData(){
        viewModel.navigateToNextActivity.observe(this, Observer {
            startActivity(
                    Intent(
                            this,
                            CompleteCookingActivity::class.java
                    )
            )
        })

        viewModel.navigateToPrevActivity.observe(this, Observer {
            finish()
        })

        viewModel.startButtonClick.observe(this, Observer {
            if(it){
                //start video
                bake_pajeon_video.seekTo(0)
                bake_pajeon_video.start()
            }
        })

        viewModel.pauseButtonClick.observe(this, Observer {
            if(it){
                //pause video
                bake_pajeon_video.pause()
            }
        })
    }
}