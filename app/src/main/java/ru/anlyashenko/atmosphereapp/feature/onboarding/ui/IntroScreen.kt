package ru.anlyashenko.atmosphereapp.feature.onboarding.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.design_system.theme.OnPrimaryLight
import ru.anlyashenko.atmosphereapp.core.design_system.theme.PrimaryLight
import ru.anlyashenko.atmosphereapp.core.design_system.theme.SecondaryGrayLight

@Composable
fun IntroScreen(
    viewModel: IntroViewModel = hiltViewModel(),
    onGetInClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                IntroEffect.NavigateToHome -> onGetInClick()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SecondaryGrayLight)
            .statusBarsPadding()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderSection()
        Spacer(Modifier.weight(1f))
        FooterSection(
            onGetInClick = { isGranted ->
                viewModel.setEvent(IntroEvent.CompleteOnboarding(isGranted))
            }
        )
        Spacer(Modifier.weight(1f))
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
                .aspectRatio(4f / 4f)
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
fun FooterSection(onGetInClick: (Boolean) -> Unit) {

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        onGetInClick(isGranted)
    }

    Button(
        onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                onGetInClick(true)
            }

        },
        modifier = Modifier
            .width(200.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryLight,
            contentColor = OnPrimaryLight
        ),
        shape = RoundedCornerShape(50.dp),
    ) {
        Text(
            text = stringResource(R.string.intro_button),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}


