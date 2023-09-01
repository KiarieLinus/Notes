package com.kiarielinus.notes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.newRoot
import com.kiarielinus.notes.login.presentation.LoginRoute
import com.kiarielinus.notes.notes.domain.NoteScreen
import com.kiarielinus.notes.presentation.MainViewModel

class LoginRoute(
    buildContext: BuildContext,
    private val backStack: BackStack<Navigation.Route>,
) : Node(buildContext) {

    @Composable
    override fun View(modifier: Modifier) {
        val viewModel: MainViewModel = viewModel()

        LoginRoute(
            modifier = modifier,
            googleAuthClient = viewModel.googleAuthClient,
            onLogin = {
                val route = Navigation.Route.Notes(NoteScreen.Notes)
                viewModel.updateScreen(route)
                backStack.newRoot(route)
            }
        )
    }
}