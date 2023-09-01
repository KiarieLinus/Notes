package com.kiarielinus.notes.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.kiarielinus.notes.presentation.MainViewModel
import com.kiarielinus.notes.settings.presentation.SettingsRoute

class SettingsRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val viewModel: MainViewModel = viewModel()

        BackHandler {
            viewModel.pop(backStack)
        }

        SettingsRoute(
            modifier = modifier,
            navigateBack = {
                viewModel.pop(backStack)
            },
        )
    }
}