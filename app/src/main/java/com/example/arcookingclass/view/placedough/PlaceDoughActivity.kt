package com.example.arcookingclass.view.placedough

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
import com.example.arcookingclass.databinding.ActivityPlaceDoughBinding
import com.example.arcookingclass.view.bakepajeon.BakePajeonActivity
import com.example.arcookingclass.view.turnongas.TurnOnGasActivity
import kotlinx.android.synthetic.main.activity_place_dough.*
import kotlinx.android.synthetic.main.activity_turn_on_gas.*

class PlaceDoughActivity  : AppCompatActivity(){
    private val TAG = PlaceDoughActivity::class.java.simpleName
    private lateinit var viewModel : PlaceDoughViewModel
    private lateinit var binding : ActivityPlaceDoughBinding
    private lateinit var video_uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_place_dough)
        viewModel = ViewModelProvider(this).get(PlaceDoughViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        video_uri = Uri.parse("android.resource://$packageName/raw/place_dough_video");
        place_dough_video.setMediaController(MediaController(this))
        place_dough_video.requestFocus()
        place_dough_video.setVideoURI(video_uri)

        initListener()
        observeLiveData()

        Log.d(TAG, "여기다")
    }

    private fun initListener(){
        place_dough_video.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp : MediaPlayer){

            }
        })

        place_dough_video.setOnCompletionListener(object : MediaPlayer.OnCompletionListener{
            override fun onCompletion(mp: MediaPlayer) {

            }
        })
    }

    private fun observeLiveData(){
        viewModel.navigateToNextActivity.observe(this, Observer {
            startActivity(
                    Intent(
                            this,
                            BakePajeonActivity::class.java
                    )
            )
        })

        viewModel.navigateToPrevActivity.observe(this, Observer {
            finish()
        })

        viewModel.startButtonClick.observe(this, Observer {
            if(it){
                //start video
                place_dough_video.seekTo(0)
                place_dough_video.start()
            }
        })

        viewModel.pauseButtonClick.observe(this, Observer {
            if(it){
                //pause video
                place_dough_video.pause()
            }
        })
    }
}