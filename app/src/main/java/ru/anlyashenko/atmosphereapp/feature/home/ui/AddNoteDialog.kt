package ru.anlyashenko.atmosphereapp.feature.home.ui

// todo: ----
/*
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.anlyashenko.atmosphereapp.R

@Composable
fun AddNoteDialog(
    initialText: String = "",
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    var noteText by remember { mutableStateOf(initialText) }
    val maxCharLimit = 250
    val isLimitReached = noteText.length == maxCharLimit

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

   */
/* val animatedBorderColor by animateColorAsState(
        targetValue = if (isLimitReached) MaterialTheme.colorScheme.error.copy(alpha = 0.5f)
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        animationSpec = tween(durationMillis = 300),
        label = "BorderColorAnimation"
    )*//*


    val animatedCounterColor by animateColorAsState(
        targetValue = if (isLimitReached) MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
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
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.add_note_to_day_title),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(24.dp))

                OutlinedTextField(
                    value = noteText,
                    onValueChange = {
                        if (it.length <= maxCharLimit) noteText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
//                        .focusRequester(focusRequester),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = if (isLimitReached)
                            MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                        else
                            MaterialTheme.colorScheme.primary,

                        unfocusedBorderColor = if (isLimitReached)
                            MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                        else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),

                        cursorColor = MaterialTheme.colorScheme.onSurface
                    ),
                    label = {
                        Text(
                            text = stringResource(R.string.note_dialog_placeholder),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    },
                    supportingText = {
                        Text(
                            text = "${noteText.length} / $maxCharLimit",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            color = animatedCounterColor,
                            fontWeight = if (isLimitReached) FontWeight.Medium else FontWeight.Normal
                        )
                    },
                )


                Spacer(Modifier.height(28.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.05f
                            ),
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(30.dp),
                        elevation = null
                    ) {
                        Text(
                            text = stringResource(R.string.text_cancel_button),
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
                            .height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(30.dp),
                        elevation = null
                    ) {
                        Text(
                            text = stringResource(R.string.text_save_button),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}*/
