package ru.anlyashenko.atmosphereapp.feature.setting_notification

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.ui.components.BaseVerticalWheelPicker
import ru.anlyashenko.atmosphereapp.core.design_system.ui.components.rememberWheelPickerState
import ru.anlyashenko.atmosphereapp.feature.settings.ui.SettingsEffect
import ru.anlyashenko.atmosphereapp.feature.settings.ui.SettingsEvent
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationPermissionManager

//todo: Сломанная анимация перехода на этот экран
//todo: Если выключить уведомления через свитч, выбрать время и нажать Готово, то уведомление всё равно придёт
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsRoute(
    viewModel: NotificationSettingsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionManager = remember {
        NotificationPermissionManager(context)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.setEvent(NotificationSettingsEvent.OnPermissionResult(isGranted))
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.setEvent(
                    NotificationSettingsEvent.OnPermissionResult(permissionManager.checkPermission())
                )
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                NotificationSettingsEffect.NavigateBack -> onBackClick()
                NotificationSettingsEffect.RequestNotificationPermission -> {
                    val activity = context as? Activity ?: return@collectLatest
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (permissionManager.shouldShowRationale(activity)) {
                            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            viewModel.setEvent(NotificationSettingsEvent.OnShouldOpenSettings)
                        }
                    } else {
                        viewModel.setEvent(NotificationSettingsEvent.OnPermissionResult(true))
                    }
                }
                NotificationSettingsEffect.OpenAppSettings -> {
                    val activity = context as? Activity ?: return@collectLatest
                    permissionManager.openAppSettings(activity)
                }
            }
        }
    }

    if (!state.isLoading) {
        NotificationSettingsScreen(
            initialEnabled = state.isNotificationsEnabled,
            initialHour = state.notificationHour,
            initialMinute = state.notificationMinute,
            onBackClick = { viewModel.setEvent(NotificationSettingsEvent.OnBackClick) },
            onToggleNotifications = { isEnabled ->
                viewModel.setEvent(NotificationSettingsEvent.ToggleNotifications(isEnabled))
            },
            onSaveRequest = { hour, minute, isEnabled ->
                viewModel.setEvent(
                    NotificationSettingsEvent.SaveNotificationSettings(
                        hour,
                        minute,
                        isEnabled
                    )
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(
    initialEnabled: Boolean,
    initialHour: Int,
    initialMinute: Int,
    onBackClick: () -> Unit,
    onToggleNotifications: (Boolean) -> Unit,
    onSaveRequest: (hour: Int, minute: Int, isEnabled: Boolean) -> Unit,
) {

    var isEnabled by rememberSaveable { mutableStateOf(initialEnabled) }
    var hour by rememberSaveable { mutableIntStateOf(initialHour) }
    var minute by rememberSaveable { mutableIntStateOf(initialMinute) }

    LaunchedEffect(initialEnabled) {
        isEnabled = initialEnabled
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 23.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 34.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = stringResource(R.string.cd_settings_back),
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .clickable { onBackClick() },
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.notification_settings_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = stringResource(R.string.notification_settings_description),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        )

        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.notification_settings_enable),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Switch(
                checked = isEnabled,
                onCheckedChange = { newValue ->
                    isEnabled = newValue
                    onToggleNotifications(newValue)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }

        Spacer(Modifier.height(50.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val hours = remember { (0..23).toList() }
            val minutes = remember { (0..59).toList() }

            val hourState = rememberWheelPickerState(initialIndex = hour)
            val minuteState = rememberWheelPickerState(initialIndex = minute)

            LaunchedEffect(hourState) {
                snapshotFlow { hourState.currentIndexSnapshot }
                    .collect { index -> if (index >= 0) hour = hours[index] }
            }

            LaunchedEffect(minuteState) {
                snapshotFlow { minuteState.currentIndexSnapshot }
                    .collect { index -> if (index >= 0) minute = minutes[index] }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BaseVerticalWheelPicker(
                    modifier = Modifier.width(128.dp),
                    items = hours,
                    state = hourState,
                    unfocusedCount = 1,
                    itemHeight = 96.dp,
                    focus = { },
                    content = { index ->
                        Text(
                            text = hours[index].toString().padStart(2, '0'),
                            fontSize = 96.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )

                Text(
                    text = ":",
                    fontSize = 96.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = MaterialTheme.colorScheme.onSurface
                )

                BaseVerticalWheelPicker(
                    modifier = Modifier.width(128.dp),
                    items = minutes,
                    state = minuteState,
                    unfocusedCount = 1,
                    itemHeight = 96.dp,
                    focus = { },
                    content = { index ->
                        Text(
                            text = minutes[index].toString().padStart(2, '0'),
                            fontSize = 96.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )
            }
        }

        Spacer(Modifier.weight(1f))
        Button(
            onClick = {
                onSaveRequest(hour, minute, isEnabled)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = stringResource(R.string.text_confirm_button),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(Modifier.height(34.dp))
    }
}
