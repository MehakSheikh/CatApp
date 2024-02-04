package com.example.catapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.catapp.R
import kotlinx.coroutines.delay


@Composable
fun mainSplash(navController: NavController){
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()


        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))
            val logoAnimationState =
                animateLottieCompositionAsState(composition = composition)
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
                verticalArrangement = Arrangement.Center, // Center the elements vertically
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                LottieAnimation(
                    composition = composition,
                    progress = { logoAnimationState.progress }
                )
                Text("CATLOPEDIA" , color = MaterialTheme.colorScheme.primaryContainer, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            }



            if (logoAnimationState.isAtEnd && logoAnimationState.isPlaying) {
                navController.navigate("breedlist")
            }
        }
        LaunchedEffect(Unit) {
            delay(3000)  // the delay of 3 seconds

        }

    }
}