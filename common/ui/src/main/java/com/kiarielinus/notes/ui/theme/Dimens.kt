package com.kiarielinus.notes.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import com.kiarielinus.notes.ui.R

@Composable
@ReadOnlyComposable
fun cutCornerSize() = dimensionResource(id = R.dimen.ui_cut_corner_size)

@Composable
@ReadOnlyComposable
fun cornerRadius() = dimensionResource(id = R.dimen.ui_corner_radius)