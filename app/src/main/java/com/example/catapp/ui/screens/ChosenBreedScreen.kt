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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.example.catapp.model.BreedsListDto
import com.example.catapp.ui.shimmerBrush
import com.example.catapp.viewmodel.BreedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChosenBreedScreen(navController: NavController) {

    val breedViewModel: BreedViewModel = hiltViewModel()
    val breeds: State<List<BreedsListDto>> = breedViewModel.breedsData.collectAsState()
    val isLoading by breedViewModel.isLoading.collectAsState()

    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val breedId = navBackStackEntry?.arguments?.getString("breedId")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        // TopAppBar with a back button
        TopAppBar(
            title = { Text(text = "") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)

        )
        // Rest of the content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 72.dp)
        ) {
            if (breeds.value.isEmpty()) {
                // Display loading spinner when data is being loaded
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                        //   .fillMaxHeight()
                        //   .padding(bottom = 16.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }
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
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier

                            // Expand to fill the entire height
                            // .padding(bottom = 160.dp)  // Add padding to the bottom
                        ){
                        Row(
                            modifier = Modifier.fillMaxSize(),
                        ) {

                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(50.dp)
                                    , color = MaterialTheme.colorScheme.primaryContainer
                                )
                                //  CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                            } else {

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Button(onClick = { breedViewModel.loadBreedSpecificCats() }) {
                                        Text("Load More")
                                    }
                                }
                            }

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
    val showShimmer = remember { mutableStateOf(true) }

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
            contentDescription = "Cat Image",
            modifier = Modifier
                .background(shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value))
                .size(190.dp)
                .clip(CircleShape)
                .padding(12.dp),
            onSuccess = { showShimmer.value = false },
            contentScale = ContentScale.Crop
        )
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