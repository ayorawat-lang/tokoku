package com.example.tokoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tokoku.ui.screens.MainScaffold
import com.example.tokoku.ui.theme.TokokuTheme
import com.example.tokoku.viewmodel.MarketplaceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MarketplaceViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            TokokuTheme(darkTheme = isDarkMode) {
                MainScaffold(viewModel = viewModel)
            }
        }
    }
}
