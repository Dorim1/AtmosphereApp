package ru.anlyashenko.atmosphereapp.feature.onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.design_system.theme.SecondaryGrayLight

@Composable
@Preview
private fun InroScreenPreview() {
    AtmosphereAppTheme {
        IntroScreen(
            onGetInClick = {}
        )
    }
}

@Composable
fun IntroScreen(onGetInClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryGrayLight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection()
        Spacer(Modifier.weight(1f, fill = false))
        FooterSection(onGetInClick)
    }
}


@Composable
fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.background_photo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(4f/ 4f)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.intro_title),
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.intro_subtitle),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 30.dp)
        )
    }
}

@Composable
fun FooterSection(onGetInClick: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(vertical = 150.dp)
            .size(200.dp, 50.dp),
        onClick = onGetInClick,
        shape = RoundedCornerShape(50.dp),
    ) {
        Text(
            text = stringResource(R.string.intro_button),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}


