package ru.anlyashenko.atmosphereapp.feature.calendar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.anlyashenko.atmosphereapp.R

@Composable
fun DeleteConfirmationDialog(
    onDismiss:() -> Unit,
    onConfirm:() -> Unit
) {
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
                modifier = Modifier.padding(23.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_warning),
                    contentDescription = "Внимание",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(82.dp)
                )

                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Удаление записи",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Отменить это действие будет невозможно",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )

                Spacer(Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                            text = "Нет, оставить",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center

                        )
                    }
                    Button(
                        onClick = {
                            onConfirm()
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        Text(
                            text = "Да, удалить",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}