package com.example.texttospeech

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var mTTs:TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTTs = TextToSpeech(this){status->
            if (status == TextToSpeech.SUCCESS){
                val result = mTTs.setLanguage(Locale.ENGLISH)
                if (result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Log.e("TTs","Language is not Supported")
                }else{
                    btnSpeak.isEnabled = true
                }
            }
            else{
                Log.e("TTs","Initialization failed")
            }
        }
        btnSpeak.setOnClickListener { speak() }
    }

    private fun speak() {
        val text = edTv.text.toString()
        var pitch = seekBar_pitch.progress.toFloat()/50
        if (pitch<0.1) pitch = 0.1f
        var speed = seekBar_speed.progress.toFloat()/50
        if (speed<0.1) speed = 0.1f
        mTTs.setPitch(pitch)
        mTTs.setSpeechRate(speed)
        mTTs.speak(text,TextToSpeech.QUEUE_FLUSH,null)
    }

    override fun onDestroy() {
        if (mTTs != null){
            mTTs.stop()
            mTTs.shutdown()
        }
        super.onDestroy()
    }

}