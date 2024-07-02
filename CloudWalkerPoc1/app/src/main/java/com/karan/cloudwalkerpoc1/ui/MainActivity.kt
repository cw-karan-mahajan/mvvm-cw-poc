package com.karan.cloudwalkerpoc1.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.karan.cloudwalkerpoc1.ui.navigation.SetupNavGraph
import com.karan.cloudwalkerpoc1.ui.screens.MobileNumberScreen
import com.karan.cloudwalkerpoc1.ui.theme.CloudWalkerPoc1Theme
import com.karan.cloudwalkerpoc1.ui.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloudWalkerPoc1Theme {
                SetupNavGraph(rememberNavController(), viewModel)
                /*Surface {
                    MobileNumberScreen(viewModel, rememberNavController())
                }*/
            }
        }

    }

    /*override fun onBackPressed() {
        if (viewModel.isSuccessScreen) {
            finish()
        }
    }*/

}
