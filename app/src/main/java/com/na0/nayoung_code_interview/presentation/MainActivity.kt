package com.na0.nayoung_code_interview.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.na0.nayoung_code_interview.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
