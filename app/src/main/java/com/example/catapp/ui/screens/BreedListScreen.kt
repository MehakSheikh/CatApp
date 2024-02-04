package com.example.catapp.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalContext
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BreedListScreen(onClick: (breedId: String) -> Unit) {
    val catViewModel: CatViewModel = hiltViewModel()

    val cats by catViewModel.cats.collectAsState()
    val roomCat by catViewModel.catDataFromRoom.collectAsState()
    val isLoading by catViewModel.isLoading.collectAsState()

    var isInternetConnected by remember { mutableStateOf(false) }
    val context = LocalContext.current


    DisposableEffect(context) {
        isInternetConnected = checkInternetConnection(context)

        onDispose { /* cleanup */ }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        if (isInternetConnected) {
            items(cats) { cat ->
                CatItem(
                    cat.breeds!![0].id!!,
                    cat.url!!, cat.breeds[0].name!!,
                    cat.breeds[0].life_span!!, cat.breeds[0].origin!!, onClick = onClick
                )

            }
        } else {
            items(roomCat) { cat ->
                cat.imageUrl?.let {
                    CatItem(
                        cat.id, it, cat.name!!, cat.lifeSpan!!, cat.origin!!, onClick = onClick
                    )
                }

            }
        }

        // Load more button or trigger loading when reaching the end
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                } else {
                    Row {
                        Button(onClick = {
                            if (isInternetConnected) catViewModel.loadCats() else {
                                showToast(context = context, "Please connect to Internet")
                                Log.d("Mehak internet check on load more", "")
                            }
                        }) {
                            Text("Load More")
                        }
                    }

                }
            }

        }
    }

}


@Composable
fun CatItem(
    breedId: String,
    asynchImag: String,
    breedName: String,
    breedLifespan: String,
    breedOrigin: String,
    onClick: (breedId: String) -> Unit
) {
    var isInternetConnected by remember { mutableStateOf(false) }
    val context = LocalContext.current


    DisposableEffect(context) {
        isInternetConnected = checkInternetConnection(context)

        onDispose { /* cleanup */ }
    }
    Log.d("Mehak checking", "inside the function")
    Row(
        modifier = Modifier
            .clickable {
                if (isInternetConnected)
                    onClick(breedId)
                else {
                    showToast(context = context, "Please connect to Internet")

                    //Log.d("NO INTERNER", breedId)
                }
            }
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedImage(
            model = asynchImag ?: "",
            contentDescription = "",
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
                text = breedName ?: "",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Life Span: ${breedLifespan ?: ""}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Origin: ${breedOrigin ?: ""}",
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun AnimatedImage(
    model: String,
    contentDescription: String,
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

fun checkInternetConnection(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
