package com.codemave.reminderapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.codemave.reminderapp.ui.theme.MobileComputingTheme

class MainActivity : ComponentActivity()
{
    lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileComputingTheme {
                Surface(color = MaterialTheme.colors.background) {
                   ReminderApp(sharedPreferences)
                }

            }
        }
        sharedPreferences = getSharedPreferences("ReminderApp", Context.MODE_PRIVATE)
    }
}

