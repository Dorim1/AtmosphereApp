package ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.anlyashenko.atmosphereapp.R

@Composable
fun AppearanceScreen(
    onBackClick: () -> Unit,
    viewModel: AppearanceViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                AppearanceEffect.NavigateToBack -> onBackClick()
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
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = stringResource(R.string.cd_settings_back),
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .clickable { viewModel.setEvent(AppearanceEvent.OnBackClick) },
            )

            Spacer(Modifier.width(18.dp))
            Text(
                text = stringResource(R.string.appearance_screen_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = stringResource(R.string.appearance_theme_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.settings_appearance_desc),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        )

        Spacer(Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ThemeOptionCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.settings_appearance_system),
                painter = painterResource(R.drawable.ic_setting_theme),
                isSelected = state.theme == ThemeMode.SYSTEM,
                onClick = { viewModel.setEvent(AppearanceEvent.OnThemeSelected(ThemeMode.SYSTEM)) }
            )

            ThemeOptionCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.settings_appearance_light),
                painter = painterResource(R.drawable.ic_light_mode),
                isSelected = state.theme == ThemeMode.LIGHT,
                onClick = { viewModel.setEvent(AppearanceEvent.OnThemeSelected(ThemeMode.LIGHT)) }
            )

            ThemeOptionCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.settings_appearance_dark),
                painter = painterResource(R.drawable.ic_dark_mode),
                isSelected = state.theme == ThemeMode.DARK,
                onClick = { viewModel.setEvent(AppearanceEvent.OnThemeSelected(ThemeMode.DARK)) }
            )
        }

        Spacer(Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.appearance_cards_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.appearance_cards_description),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        )

        Spacer(Modifier.height(28.dp))
        Column(verticalArrangement = Arrangement.spacedBy(11.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                CornerRadiusCard(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.appearance_corner_small),
                    subtitle = "12.dp",
                    cornerRadius = 12.dp,
                    isSelected = state.cornerRadius == CornerRadiusMode.SMALL,
                    onClick = {
                        viewModel.setEvent(
                            AppearanceEvent.OnCornerRadiusSelected(
                                CornerRadiusMode.SMALL
                            )
                        )
                    }
                )
                CornerRadiusCard(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.appearance_corner_medium),
                    subtitle = "20.dp",
                    cornerRadius = 20.dp,
                    isSelected = state.cornerRadius == CornerRadiusMode.MODERATE,
                    onClick = {
                        viewModel.setEvent(
                            AppearanceEvent.OnCornerRadiusSelected(
                                CornerRadiusMode.MODERATE
                            )
                        )
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CornerRadiusCard(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.appearance_corner_large),
                    subtitle = "30.dp",
                    cornerRadius = 30.dp,
                    isSelected = state.cornerRadius == CornerRadiusMode.BIG,
                    onClick = {
                        viewModel.setEvent(
                            AppearanceEvent.OnCornerRadiusSelected(
                                CornerRadiusMode.BIG
                            )
                        )
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(Modifier.height(40.dp))

    }
}

@Composable
fun ThemeOptionCard(
    modifier: Modifier = Modifier,
    title: String,
    painter: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val contentColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.4f
        )
    val borderColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
            alpha = 0.2f
        )
    val borderWidth = if (isSelected) 2.dp else 1.dp

    Surface(
        modifier = modifier.aspectRatio(0.9f),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(borderWidth, borderColor),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painter,
                contentDescription = title,
                tint = contentColor,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.height(12.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                color = contentColor,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun CornerRadiusCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    cornerRadius: Dp,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    val cardColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f)
    val borderWidth = if (isSelected) 2.dp else 1.dp

    Surface(
        modifier = modifier.aspectRatio(0.75f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(borderWidth, borderColor),
        color = Color.Transparent,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column() {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 14.sp
                    )
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        lineHeight = 12.sp
                    )
                }

                RadioButton(
                    selected = isSelected,
                    onClick = onClick,
                    modifier = Modifier.size(24.dp),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    )
                )
            }

            Spacer(Modifier.height(14.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(cardColor, RoundedCornerShape(cornerRadius))
                    )
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(cardColor, RoundedCornerShape(cornerRadius))
                    )
                }
            }
        }
    }
}