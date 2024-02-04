package com.example.catapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.catapp.model.BreedsListDto
import com.example.catapp.viewmodel.BreedViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChosenBreedScreen(navController: NavController) {

    val breedViewModel: BreedViewModel = hiltViewModel()
    val breeds: State<List<BreedsListDto>> = breedViewModel.breedsData.collectAsState()
    val isLoading by breedViewModel.isLoading.collectAsState()



    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Row(modifier = Modifier.height(100.dp).background(Color.Green)) {


        }

        if (breeds.value.isEmpty()) {
            // Display loading spinner when data is being loaded
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
                , color = Color.White
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary),
            ) {
                items(breeds.value) {
                    BreedItems(breed = it)
                }

                item {
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        if (isLoading) {
                            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                        }else {

                            Row( modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically) {
                                Button(onClick = { breedViewModel.loadBreedSpecificCats() }) {
                                    Text("Load More")
                                }
//                        Button(onClick = { catViewModel.decrementCatData() }) {
//                            Text("Previous Data")
//                        }

                            }

                        }
                    }
                }
            }
        }
    }
}
@Composable
fun BreedItems(breed: BreedsListDto) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        Alert(name = breed.breeds?.get(0)?.name ?: "",
            detail = "Temperament: ${breed.breeds?.first()?.name} " +
                    "\n Health Issues: ${breed.breeds?.first()?.health_issues}" +
                    "\n Shedding Level: ${breed.breeds?.first()?.shedding_level}",
            showDialog = showDialog.value,
            onDismiss = { showDialog.value = false })
    }

    Row(
        modifier = Modifier
            .clickable(onClick = { showDialog.value = true })
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            //  .padding(4.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.Center, // Center horizontally
        verticalAlignment = Alignment.CenterVertically // Center vertically
    ) {
        AsyncImage(
            model = breed.url,
            contentDescription = "Image of Cat",
            contentScale = ContentScale.Crop, // or ContentScale.FillBounds based on your preference
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .padding(8.dp)
        )
        // Spacer(modifier = Modifier.width(16.dp))

    }
}


@Composable
fun Alert(
    name: String,
    detail: String,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            title = {
                Text(text = name)
            },
            text = {
                Text(text = detail)
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            },
            dismissButton = {}
        )
    }
}
/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChosenBreedScreen(navController: NavController) {

    val breedViewModel: BreedViewModel = hiltViewModel()
    val breeds: State<List<BreedsListDto>> = breedViewModel._breedsData.collectAsState()

  *//*  Button(onClick = { navController.popBackStack() }) {
        Text("Go Back to Screen 1")
    }*//*
    Row(
        modifier = Modifier
          //  .clickable(onClick = { showDialog.value = true })
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            //  .padding(4.dp)
            .background(color = Color.White),
        horizontalArrangement = Arrangement.Center, // Center horizontally
        verticalAlignment = Alignment.CenterVertically // Center vertically
    ){}
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary),
    ) {
        items(breeds.value) {
            BreedItems(breed = it)
        }
    }
}


@Composable
fun BreedItems(breed: BreedsListDto) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        Alert(name = breed.breeds?.get(0)?.name ?: "",
            detail = "Temperament: ${breed.breeds?.first()?.name} " +
                    "\n Health Issues: ${breed.breeds?.first()?.health_issues}" +
                    "\n Shedding Level: ${breed.breeds?.first()?.shedding_level}",
            showDialog = showDialog.value,
            onDismiss = { showDialog.value = false })
    }

    Row(
        modifier = Modifier
            .clickable(onClick = { showDialog.value = true })
             .fillMaxWidth()
             .height(IntrinsicSize.Max)
            //  .padding(4.dp)
            .background(color = MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.Center, // Center horizontally
        verticalAlignment = Alignment.CenterVertically // Center vertically
    ) {
        AsyncImage(
            model = breed.url,
            contentDescription = "Image of Cat",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .padding(8.dp)
            // .padding(4.dp) // Add margin here to create space around the image
            //  .clip(CircleShape)
            // .fillMaxWidth()
//                    .padding(0.dp, 1.dp)
            // .height(IntrinsicSize.Max),
        )//
        // Spacer(modifier = Modifier.width(16.dp))

    }
}


@Composable
fun Alert(
    name: String,
    detail: String,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            title = {
                Text(text = name)
            },
            text = {
                Text(text = detail)
            },
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            },
            dismissButton = {}
        )
    }
}*/
