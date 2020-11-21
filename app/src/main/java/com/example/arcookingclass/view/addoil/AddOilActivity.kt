package com.example.arcookingclass.view.addoil

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
import com.example.arcookingclass.databinding.ActivityAddOilBinding
import com.example.arcookingclass.view.placedough.PlaceDoughActivity
import com.example.arcookingclass.view.turnongas.TurnOnGasActivity
import kotlinx.android.synthetic.main.activity_add_oil.*

class AddOilActivity : AppCompatActivity() {
    private val TAG = AddOilActivity::class.java.simpleName
    private lateinit var viewModel: AddOilViewModel
    private lateinit var binding : ActivityAddOilBinding
    private lateinit var video_uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_oil)
        viewModel = ViewModelProvider(this).get(AddOilViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        video_uri = Uri.parse("android.resource://$packageName/raw/add_oil_video");
        add_oil_video.setMediaController(MediaController(this))
        add_oil_video.requestFocus()
        add_oil_video.setVideoURI(video_uri)

        initListener()
        observeLiveData()

        Log.d(TAG, "여기다")
    }

    private fun initListener(){
        add_oil_video.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp : MediaPlayer){

            }
        })

        add_oil_video.setOnCompletionListener(object : MediaPlayer.OnCompletionListener{
            override fun onCompletion(mp: MediaPlayer) {

            }
        })
    }

    private fun observeLiveData(){
        viewModel.navigateToNextActivity.observe(this, Observer {
            startActivity(
                    Intent(
                            this,
                            PlaceDoughActivity::class.java
                    )
            )
        })

        viewModel.navigateToPrevActivity.observe(this, Observer {
            finish()
        })

        viewModel.startButtonClick.observe(this, Observer {
            if(it){
                //start video
                add_oil_video.seekTo(0)
                add_oil_video.start()
            }
        })

        viewModel.pauseButtonClick.observe(this, Observer {
            if(it){
                //pause video
                add_oil_video.pause()
            }
        })
    }
}