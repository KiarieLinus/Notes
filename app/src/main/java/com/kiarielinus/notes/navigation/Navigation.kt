package com.kiarielinus.notes.navigation

import android.os.Parcelable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.operation.replace
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackSlider
import com.kiarielinus.notes.domain.drawer
import com.kiarielinus.notes.domain.route
import com.kiarielinus.notes.notes.domain.NoteScreen
import com.kiarielinus.notes.notes_api.NoteId
import com.kiarielinus.notes.presentation.AppDrawer
import com.kiarielinus.notes.presentation.DrawerItem
import com.kiarielinus.notes.presentation.MainViewModel
import kotlinx.coroutines.launch
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
    private lateinit var scaffoldState: ScaffoldState

    @Composable
    override fun View(modifier: Modifier) {
        scaffoldState = rememberScaffoldState()
        val viewModel: MainViewModel = viewModel()
        val screen by viewModel.screen.collectAsState()
        val scope = rememberCoroutineScope()
        var drawerGesturesEnabled by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(
            key1 = screen,
            block = {
                drawerGesturesEnabled = when (screen) {
                    is Route.Notes -> true
                    else -> false
                }
            }
        )

        Scaffold(
            modifier = modifier,
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = drawerGesturesEnabled,
            drawerContent = {
                screen.drawer?.let { drawerItem ->
                    AppDrawer(
                        selected = drawerItem,
                        itemSelected = { item ->
                            val route = item.route
                            if (item == DrawerItem.Logout) {
                                viewModel.logout()
                            }
                            when (route) {
                                Route.Settings, is Route.EditNote -> backStack.push(route)
                                else -> backStack.replace(route)
                            }
                            viewModel.updateScreen(route)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    )
                }
            },
            drawerShape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp),
            drawerBackgroundColor = MaterialTheme.colors.background,
            content = { paddingValues ->
                Children(
                    modifier = modifier.padding(paddingValues),
                    navModel = backStack,
                    transitionHandler = rememberBackstackSlider()
                )
            }
        )
    }

    override fun resolve(navTarget: Route, buildContext: BuildContext): Node =
        when (navTarget) {
            is Route.EditNote -> EditNoteRoute(
                noteId = navTarget.id?.let { NoteId(it) },
                buildContext = buildContext,
                backStack = backStack
            )

            Route.Login -> LoginRoute(
                buildContext = buildContext,
                backStack = backStack
            )

            is Route.Notes -> NotesRoute(
                buildContext = buildContext,
                backStack = backStack,
                screen = navTarget.screen,
                openDrawer = {
                    scaffoldState.drawerState.open()
                }
            )

            Route.Settings -> SettingsRoute(
                buildContext = buildContext,
                backStack = backStack
            )
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