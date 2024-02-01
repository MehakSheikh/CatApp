package com.example.catapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.catapp.model.BreedsListItem
import com.example.catapp.viewmodel.BreedViewModel


@Composable
fun ChoosenBreedScreen() {

    val breedViewModel: BreedViewModel = viewModel()
    val breeds: State<List<BreedsListItem>> = breedViewModel._breedsData.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        items(breeds.value) {
            BreedItems(breed = it)
        }
    }
}

@Composable
fun BreedItems(breed: BreedsListItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),  // Add padding here to create margins
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(4.dp), // Add padding here to create space between items
            horizontalArrangement = Arrangement.Center, // Center horizontally
            verticalAlignment = Alignment.CenterVertically // Center vertically
        ) {
            AsyncImage(
                model = breed.url,
                contentDescription = "Image of Cat",
                modifier = Modifier
                    .size(100.dp)
                   // .padding(4.dp) // Add margin here to create space around the image
                    .clip(CircleShape)
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
            )
           // Spacer(modifier = Modifier.width(16.dp))
            // Rest of your code
        }
    }
}

