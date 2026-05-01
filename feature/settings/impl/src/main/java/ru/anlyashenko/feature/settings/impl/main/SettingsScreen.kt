package ru.anlyashenko.feature.settings.impl.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.presentation.language.LanguageManager

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNavigateToAppearance: () -> Unit,
    onNavigateToEditMoods: () -> Unit,
    onNavigateToNotificationSettings: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val currentLanguage = remember { LanguageManager.getCurrentLanguage() }

    val uriHandler = LocalUriHandler.current

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                SettingsEffect.NavigateBack -> onBackClick()
                SettingsEffect.NavigateToAppearance -> onNavigateToAppearance()
                SettingsEffect.NavigateToEditMoods -> onNavigateToEditMoods()
                SettingsEffect.NavigateToNotificationSettings -> onNavigateToNotificationSettings()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 23.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 34.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = stringResource(R.string.cd_settings_back),
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .clickable(onClick = onBackClick),
            )

            Spacer(Modifier.width(18.dp))
            Text(
                text = stringResource(R.string.settings_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingsItemCard(
                title = stringResource(R.string.settings_notifications_title),
                subtitle = state.notificationSubtitle.asString(),
                painter = painterResource(R.drawable.ic_setting_notifications),
                modifier = Modifier.weight(1f),
                onClick = { viewModel.setEvent(SettingsEvent.OnNotificationClick) }
            )
            SettingsItemCard(
                title = stringResource(R.string.settings_appearance_title),
                subtitle = state.themeSubtitle.asString(),
                painter = painterResource(R.drawable.ic_setting_theme),
                modifier = Modifier.weight(1f),
                onClick = { viewModel.setEvent(SettingsEvent.OnAppearanceClick) }
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingsItemCard(
                title = stringResource(R.string.settings_moods_title),
                subtitle = stringResource(R.string.settings_moods_subtitle),
                painter = painterResource(R.drawable.ic_setting_palette),
                modifier = Modifier.weight(1f),
                onClick = { viewModel.setEvent(SettingsEvent.OnEditMoodsClick) }
            )

            SettingsItemCard(
                title = stringResource(R.string.settings_language_title),
                subtitle = stringResource(R.string.settings_language_subtitle),
                painter = painterResource(R.drawable.ic_setting_language),
                modifier = Modifier.weight(1f),
                onClick = { viewModel.setEvent(SettingsEvent.OpenLanguageDialog) }
            )

        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingsItemCard(
                title = stringResource(R.string.settings_about_title),
                subtitle = stringResource(R.string.settings_about_subtitle),
                painter = painterResource(R.drawable.ic_setting_info),
                modifier = Modifier.weight(1f),
                onClick = {
                    uriHandler.openUri("https://dorim1.github.io/app-privacy/")
                }
            )
            Spacer(Modifier.weight(1f))
        }
        Spacer(Modifier.height(48.dp))

    }

    if (state.showLanguageDialog) {
        LanguageSelectionDialog(
            initialLanguage = currentLanguage,
            onDismissRequest = { viewModel.setEvent(SettingsEvent.DismissDialogs) },
            onSaveClick = { selectedLanguage ->
                viewModel.setEvent(SettingsEvent.DismissDialogs)
                LanguageManager.setLanguage(selectedLanguage)
            }
        )
    }
}

@Composable
fun SettingsItemCard(
    title: String,
    subtitle: String,
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier.height(148.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        lineHeight = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_forward),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }

            Icon(
                painter = painter,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(38.dp)
            )
        }
    }
}