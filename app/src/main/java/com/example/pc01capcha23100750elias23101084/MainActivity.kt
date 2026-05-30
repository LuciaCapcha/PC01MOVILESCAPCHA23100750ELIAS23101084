package com.example.pc01capcha23100750elias23101084

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.pc01capcha23100750elias23101084.navigation.NavGraph
import com.example.pc01capcha23100750elias23101084.ui.theme.PC01CAPCHA23100750ELIAS23101084Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC01CAPCHA23100750ELIAS23101084Theme {
                NavGraph()
            }
        }
    }
}
