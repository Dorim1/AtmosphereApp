package ru.anlyashenko.atmosphereapp.core.designsystem.elements

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity

object StopPostScrollNestedScrollConnection : NestedScrollConnection {
    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ) = if (available.y < 0) available.copy(x = 0f) else Offset.Zero

    override suspend fun onPostFling(
        consumed: Velocity,
        available: Velocity
    ) = if (available.y < 0) available.copy(x = 0f) else Velocity.Zero
}