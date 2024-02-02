package com.example.catapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.catapp.domain.BreedsListDto
import com.example.catapp.viewmodel.BreedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChosenBreedScreen(navController: NavController) {

    val breedViewModel: BreedViewModel = hiltViewModel()
    val breeds: State<List<BreedsListDto>> = breedViewModel._breedsData.collectAsState()

    Button(onClick = { navController.popBackStack() }) {
        Text("Go Back to Screen 1")
    }

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

    /*  Card(
          modifier = Modifier
              .clickable(onClick = { showDialog.value = true })
              .fillMaxWidth()
              .padding(8.dp) // Add padding here to create margins
              .background(Color.Black),
          elevation = CardDefaults.cardElevation(16.dp)
      ) {*/
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
}
