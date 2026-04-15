package ru.anlyashenko.atmosphereapp.feature.settings.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui.ThemeMode

@Preview
@Composable
private fun SettingsScreenPreview() {
    AtmosphereAppTheme() {
        SettingsScreen(
            onNavigateToAppearance = {},
            onNavigateToEditMoods = {},
            onBackClick = {},
        )
    }

}

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onNavigateToAppearance: () -> Unit,
    onNavigateToEditMoods: () -> Unit
) {
    val currentTheme = ThemeMode.SYSTEM

    var showLanguageDialog by remember { mutableStateOf(false) }

    var showNotificationSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
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
                    .clickable(onClick = onBackClick),
            )

            Spacer(Modifier.width(12.dp))
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
                subtitle = stringResource(R.string.settings_notifications_subtitle),
                painter = painterResource(R.drawable.ic_setting_notifications),
                modifier = Modifier.weight(1f),
                onClick = { showNotificationSheet = true }
            )
            SettingsItemCard(
                title = stringResource(R.string.settings_appearance_title),
                subtitle = stringResource(R.string.settings_appearance_system),
                painter = painterResource(R.drawable.ic_setting_theme),
                modifier = Modifier.weight(1f),
                onClick = { onNavigateToAppearance() }
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
                onClick = { onNavigateToEditMoods() }
            )

            SettingsItemCard(
                title = stringResource(R.string.settings_language_title),
                subtitle = stringResource(R.string.settings_language_subtitle),
                painter = painterResource(R.drawable.ic_setting_language),
                modifier = Modifier.weight(1f),
                onClick = { showLanguageDialog = true }
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
                onClick = {  }
            )
            Spacer(Modifier.weight(1f))
        }
        Spacer(Modifier.height(48.dp))

    }

    if (showLanguageDialog) {
        LanguageSelectionDialog(
            initialLanguage = AppLanguage.RUSSIAN,
            onDismissRequest = { showLanguageDialog = false },
            onSaveClick = { selectedLanguage ->
                showLanguageDialog = false
            }
        )
    }

    if (showNotificationSheet) {
        NotificationSettingsBottomSheet(
            onDismissRequest = { showNotificationSheet = false },
            onSaveRequest = { hour, minute, isEnabled ->
                showNotificationSheet = false
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
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
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
