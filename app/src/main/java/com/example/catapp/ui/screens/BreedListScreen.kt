package com.example.catapp.ui.screens

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.MarqueeAnimationMode
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.catapp.common.AppState
import com.example.catapp.domain.BreedsListDomain
import com.example.catapp.viewmodel.CatViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BreedListScreen(onClick: (breedId: String) -> Unit) {
    val catViewModel: CatViewModel = hiltViewModel()
    val cats: State<AppState<List<BreedsListDomain>>> = catViewModel.catListState().collectAsState()

    if (cats.value.isLoading) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxSize(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.basicMarquee(
                    animationMode = MarqueeAnimationMode.Immediately,
                    delayMillis = 5
                ),
                text = "CATLOPEDIA",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.primaryContainer,
                fontWeight = FontWeight.Bold
            )
        }
    } else if (cats.value.isIdle) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
        ) {
            items(
                cats.value.data ?: emptyList()
            ) {
                //  AnimatedItem(index = it)
                CatItem(cats = it, onClick)
            }

        }
    }
}

@Composable
fun AnimatedItem(index: BreedsListDomain) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = index) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { -40 }) + fadeIn() + scaleIn(),
        exit = slideOutVertically(targetOffsetY = { -40 }) + fadeOut() + scaleOut()

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp)
                .background(Color.Gray)
        ) {
            Text(
                text = "Item $index",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun CatItem(cats: BreedsListDomain, onClick: (breedId: String) -> Unit) {
    var isSelected by remember { mutableStateOf(false) }

    /*    Card(
            modifier = Modifier
                .clickable {
                    onClick(cats.breeds.first().id)
                }
                .padding(8.dp)
                .background(Color.Black),
            elevation = CardDefaults.cardElevation(16.dp)
        ) */

    Row(
        modifier = Modifier
            .clickable {
                onClick(cats.breeds.first().id)
            }
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = cats.url,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .padding(8.dp),
            // colorFilter = ColorFilter.colorMatrix(matrix)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = cats.breeds[0].name,
//                        text = "Abc",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            )
            //  Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Life Span: " + cats.breeds[0].lifeSpan,
//                        text = "Life Span: 12 14" ,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            )
            //  Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Origin: " + cats.breeds[0].origin,
//                        text = "Origin: United States" ,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}
