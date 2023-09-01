package com.kiarielinus.notes.navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.kiarielinus.notes.notes.domain.NoteScreen
import com.kiarielinus.notes.presentation.MainViewModel
import kotlinx.parcelize.Parcelize

class Navigation(
    buildContext: BuildContext,
    startingRoute: Route,
    viewModel: MainViewModel,
    private val backStack: BackStack<Route> = BackStack(
        initialElement = startingRoute,
        savedStateMap = buildContext.savedStateMap,
    ).also {
        viewModel.initNavigation(it)
    },

    ) : ParentNode<Navigation.Route>(
    buildContext = buildContext,
    navModel = backStack
) {

    @Composable
    override fun View(modifier: Modifier) {

    }

    override fun resolve(navTarget: Route, buildContext: BuildContext): Node =
        when (navTarget) {
            is Route.EditNote -> TODO()
            Route.Login -> TODO()
            is Route.Notes -> TODO()
            Route.Settings -> TODO()
        }

    sealed class Route : Parcelable {

        @Parcelize
        object Login : Route()

        @Parcelize
        data class Notes(val screen: NoteScreen) : Route()

        @Parcelize
        data class EditNote(val id: Long?) : Route()

        @Parcelize
        object Settings : Route()
    }


}