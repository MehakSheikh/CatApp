package com.example.catapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.catapp.ui.screens.BreedListScreen
import com.example.catapp.ui.screens.ChosenBreedScreen
import com.example.catapp.ui.theme.CatAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Cat World")
                        },
                            navigationIcon = {
                                IconButton(onClick = {

                                  //  navController?.popBackStack()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Localized description"
                                    )
                                }
                            },)

//                            Modifier.background(Color.White))

                    }
                ) {
                    Box(modifier = Modifier.padding(it)){
                        CatApp()
                    }
                }

            }
        }
    }

}

@Composable
fun CatApp(){
val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "breedlist"){
        composable(route = "breedlist"){
            BreedListScreen {
                navController.navigate("detail/${it}")
            }
        }

        composable(route = "detail/{breedId}",
        arguments = listOf(
            navArgument("breedId"){
                type = NavType.StringType
            }
        )
        )
        {
            ChosenBreedScreen()
        }


    }
}






