package com.example.arcookingclass.view.turnongas

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
import com.example.arcookingclass.data.Recipe
import com.example.arcookingclass.databinding.ActivityTurnOnGasBinding
import com.example.arcookingclass.view.addoil.AddOilActivity
import com.example.arcookingclass.view.main.MainActivity
import com.example.arcookingclass.view.makedough.MakeDoughActivity
import kotlinx.android.synthetic.main.activity_turn_on_gas.*

class TurnOnGasActivity : AppCompatActivity() {
    private val TAG = TurnOnGasActivity::class.java.simpleName
    private lateinit var viewModel: TurnOnGasViewModel
    private lateinit var binding : ActivityTurnOnGasBinding
    private lateinit var video_uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_turn_on_gas)
        viewModel = ViewModelProvider(this).get(TurnOnGasViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recipeItem = intent.getSerializableExtra(EXTRA_RECIPE_DATA) as Recipe

        video_uri = Uri.parse("android.resource://$packageName/raw/turn_on_gas_video");
        turn_on_gas_video.setMediaController(MediaController(this))
        turn_on_gas_video.requestFocus()
        turn_on_gas_video.setVideoURI(video_uri)

        initListener()
        observeLiveData()

        Log.d(TAG, "여기다")
    }

    private fun initListener(){
        turn_on_gas_video.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
            override fun onPrepared(mp : MediaPlayer){

            }
        })

        turn_on_gas_video.setOnCompletionListener(object : MediaPlayer.OnCompletionListener{
            override fun onCompletion(mp: MediaPlayer) {

            }
        })
    }

    private fun observeLiveData(){
        viewModel.navigateToNextActivity.observe(this, Observer {
            startActivity(
                    Intent(
                            this,
                            AddOilActivity::class.java
                    ).putExtra(AddOilActivity.EXTRA_RECIPE_DATA, binding.recipeItem)
            )
        })

        viewModel.navigateToPrevActivity.observe(this, Observer {
            finish()
        })

        viewModel.startButtonClick.observe(this, Observer {
            if(it){
                //start video
                turn_on_gas_video.seekTo(0)
                turn_on_gas_video.start()
            }
        })

        viewModel.pauseButtonClick.observe(this, Observer {
            if(it){
                //pause video
                turn_on_gas_video.pause()
            }
        })
    }

    companion object {
        const val EXTRA_RECIPE_DATA = "recipe_data"
    }
}