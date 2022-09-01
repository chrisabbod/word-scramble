package com.chrisabbod.wordscramble

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chrisabbod.wordscramble.ui.game.GameViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}