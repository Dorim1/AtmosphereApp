package ru.anlyashenko.atmosphereapp.feature.home.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onSave: (String) -> Unit
) {
    var noteText by remember() { mutableStateOf("") }
    val maxChar = 250

    val isLimitReached = noteText.length == maxChar

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isLimitReached) MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        animationSpec = tween(durationMillis = 300),
        label = "BorderColorAnimation"
    )

    val animatedCounterColor by animateColorAsState(
        targetValue = if (isLimitReached) MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        animationSpec = tween(durationMillis = 300),
        label = "CounterColorAnimation"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 19.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(228.dp)
                        .border(
                            width = 1.dp,
                            color = animatedBorderColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(
                            Color.White,
                            RoundedCornerShape(8.dp)
                        ) // TODO: Изменить под тему
                        .padding(13.dp)
                ) {
                    BasicTextField(
                        value = noteText,
                        onValueChange = {
                            if (it.length <= maxChar) noteText = it
                        },
                        modifier = Modifier.fillMaxSize(),
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
                        decorationBox = { innerTextField ->
                            Box(modifier = Modifier.fillMaxSize()) {
                                if (noteText.isEmpty()) {
                                    Text(
                                        text = "Напишите как прошёл ваш день...",
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        fontSize = 18.sp
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                    Text(
                        text = "${noteText.length} / $maxChar",
                        color = animatedCounterColor,
                        fontSize = 10.sp,
                        fontWeight = if (isLimitReached) FontWeight.Medium else FontWeight.Normal,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
                Spacer(Modifier.height(22.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "Отмена",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Button(
                        onClick = {
                            onSave(noteText)
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "Сохранить",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}