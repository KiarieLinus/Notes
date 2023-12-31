package com.kiarielinus.notes.domain

import com.kiarielinus.notes.navigation.Navigation
import com.kiarielinus.notes.notes.domain.NoteScreen
import com.kiarielinus.notes.presentation.DrawerItem

internal val Navigation.Route.drawer: DrawerItem?
    get() = when (this) {
            is Navigation.Route.Notes -> when (screen) {
                NoteScreen.Archive -> DrawerItem.Archive
                NoteScreen.Notes -> DrawerItem.Notes
                NoteScreen.Trash -> DrawerItem.Trash
            }

            else -> null
        }

internal val DrawerItem.route: Navigation.Route
    get() = when(this){
        DrawerItem.Notes -> Navigation.Route.Notes(NoteScreen.Notes)
        DrawerItem.Archive -> Navigation.Route.Notes(NoteScreen.Archive)
        DrawerItem.Trash -> Navigation.Route.Notes(NoteScreen.Trash)
        DrawerItem.Settings -> Navigation.Route.Settings
        DrawerItem.Logout -> Navigation.Route.Login
    }