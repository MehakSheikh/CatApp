package com.example.catapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catapp.api.CatBreedsApi
import com.example.catapp.ui.screens.BreedListScreen
import com.example.catapp.ui.screens.ChoosenBreedScreen
import com.example.catapp.ui.theme.CatAppTheme
import com.example.catapp.viewmodel.BreedViewModel
import com.example.catapp.viewmodel.CatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
          //  CatApp()
            CatAppTheme {
             BreedListScreen()
            }
        }
    }
}

/*@Composable
fun CatApp(){
val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "breedlistscreen"){
        composable(route = "breedlistscreen"){
            BreedListScreen{
                navController.navigate("choosenbreedscreen")
            }
        }
        composable(route = "choosenbreedscreen"){
            ChoosenBreedScreen()
        }
    }
}*/






