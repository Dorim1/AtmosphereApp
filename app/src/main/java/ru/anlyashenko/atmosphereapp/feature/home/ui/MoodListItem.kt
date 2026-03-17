package ru.anlyashenko.atmosphereapp.feature.home.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.SentimentNeutral
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.MainTitleColorLight
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.SecondaryGrayLight
import ru.anlyashenko.atmosphereapp.data.model.MoodUI

@Composable
@Preview
private fun MoodListItemPreview() {
    AtmosphereAppTheme {
        MoodListItem(
            mood = MoodUI(
                id = 1,
                title = "Хорошо",
                backgroundColor = Color.Green,
                icon = Icons.Rounded.SentimentNeutral
            ),
            false,
            onClick = {},
            onEdit = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodListItem(
    mood: MoodUI,
    isSelected: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = { totalDistance -> totalDistance * 0.4f },
        confirmValueChange = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onEdit()
            }
            false
        }
    )

    val offsetY by animateDpAsState(
        targetValue = if (isSelected) (-4).dp else 0.dp,
        label = "offsetY"
    )

    val elevation by animateDpAsState(
        targetValue = if (isSelected) 2.dp else 0.dp,
        label = "elevation"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) mood.backgroundColor else Color.White, // todo: Поменять на Material Theme
        label = "bdColor"
    )

    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.Black else SecondaryGrayLight
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            /*val iconAlpha by animateFloatAsState(
                targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) 1f else 0.4f,
                label = "iconAlpha"
            )*/

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)) //
                    .background(Color.Transparent)
                    .padding(horizontal = 42.dp, vertical = 23.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary, //.copy(alpha = iconAlpha),
                    modifier = Modifier.size(36.dp)
                )
            }
        },
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = offsetY)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = onClick
                    ),
                shape = RoundedCornerShape(16.dp),
                color = backgroundColor,
                shadowElevation = elevation,
                border = if (isSelected) null else BorderStroke(2.dp, SecondaryGrayLight),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 19.dp, vertical = 23.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = mood.title,
                        fontSize = 20.sp,
                        color = contentColor
                    )
                    Icon(
                        imageVector = mood.icon,
                        contentDescription = mood.title,
                        tint = contentColor,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    )
}