package com.example.catapp.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.example.catapp.R
import com.example.catapp.common.AppState
import com.example.catapp.data.local.CatDataEntity
import com.example.catapp.model.BreedsListDomain
import com.example.catapp.model.BreedsListDto
import com.example.catapp.viewmodel.CatViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BreedListScreen(onClick: (breedId: String) -> Unit) {
    val catViewModel: CatViewModel = hiltViewModel()

    val cats by catViewModel.cats.collectAsState()
    val isLoading by catViewModel.isLoading.collectAsState()

 /*   Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Cat World", style = MaterialTheme.typography.headlineMedium)
                },
                modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)
            )
        }
    ) {*/
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
        ) {
            items(cats) { cat ->
                CatItem(cat, onClick = onClick)
            }

            // Load more button or trigger loading when reaching the end
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.padding(8.dp)
                            ,color = MaterialTheme.colorScheme.primaryContainer)
                    } else {
                        Row {
                            Button(onClick = { catViewModel.loadCats() }) {
                                Text("Load More")
                            }
                        }
                    }
                }
            }
        }
  //  }
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
fun CatItem(cats: BreedsListDto, onClick: (breedId: String) -> Unit) {

    Row(
        modifier = Modifier
            .clickable {
                onClick(cats.breeds!![0].id!!)
            }
            .height(IntrinsicSize.Max)
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedImage(
            model = cats.url ?: "",
            contentDescription = "",
            // contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(190.dp)
                .clip(CircleShape)
                .padding(8.dp),
            placeholder = R.drawable.catimage
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .weight(3f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = cats.breeds!![0].name ?: "",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            )
            //  Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Life Span: ${cats.breeds[0].life_span ?: ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            )
            //  Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Origin: ${cats.breeds[0].origin ?: ""}",
//                        text = "Origin: United States" ,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxSize(1f),
        contentAlignment = Alignment.Center
    ) {
        AnimatedText(
            text = "CATLOPEDIA",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primaryContainer,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AnimatedText(
    text: String,
    style: TextStyle,
    color: Color,
    fontWeight: FontWeight
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(text) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Text(
            text = text,
            style = style,
            color = color,
            fontWeight = fontWeight
        )
    }
}

@Composable
fun AnimatedImage(
    model: String,
    contentDescription: String,
    // contentScale: String,
    modifier: Modifier = Modifier,
    placeholder: Int
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(model) {
        isVisible = true
    }

    Box(
        modifier = modifier
    ) {
        if (isVisible) {
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(
                    initialAlpha = 0.3f,
                    animationSpec = tween(durationMillis = 500)
                ),
                exit = fadeOut(
                    targetAlpha = 0.3f,
                    animationSpec = tween(durationMillis = 500)
                )
            ) {
                AsyncImage(
                    model = model,
                    contentDescription = contentDescription,
                    modifier = modifier
                )
            }
        } else {
            Image(
                painter = painterResource(id = placeholder),
                contentDescription = contentDescription,
                modifier = modifier
            )
        }
    }
}
