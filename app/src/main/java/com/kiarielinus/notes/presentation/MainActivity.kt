package com.kiarielinus.notes.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.kiarielinus.notes.navigation.Navigation
import com.kiarielinus.notes.settings_api.Theme
import com.kiarielinus.notes.ui.theme.NotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : NodeActivity() {


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val theme by viewModel.theme.collectAsState()
            val screen by viewModel.startScreen.collectAsState()

            NotesTheme(
                darkTheme = when (theme) {
                    Theme.System -> isSystemInDarkTheme()
                    Theme.Light -> false
                    Theme.Dark -> true
                },
                content = {
                    screen?.let {
                        NodeHost(
                            integrationPoint = appyxIntegrationPoint,
                            factory = {context ->
                                Navigation(
                                    buildContext = context,
                                    startingRoute = it,
                                    viewModel = viewModel
                                )
                            }
                        )
                    }
                }
            )
        }
    }
}