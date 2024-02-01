package com.example.catapp.ui.screens

import android.content.res.Resources.Theme
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.catapp.model.BreedsListItem
import com.example.catapp.viewmodel.BreedViewModel
import com.example.catapp.viewmodel.CatViewModel

@Composable
fun BreedListScreen() {
    val catViewModel: CatViewModel = viewModel()
    val cats: State<List<BreedsListItem>> = catViewModel._catData.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        items(cats.value) {
            CatItem(cats = it)
        }
    }
}
/*@Composable
fun CatItem(cats: BreedsListItem) {


        Text(
            text = "Breed: " + cats.breeds.get(0).name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(16.dp)
        )
    }*/



@Composable
fun CatItem(cats: BreedsListItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(4.dp)
        ) {
           /* AsyncImage(
                model = cats.url,
                contentDescription = "Image of Cat",
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .clip(CircleShape)
            )*/
            Spacer(modifier = Modifier.height(16.dp))
            AsyncImage(
                model = cats.url,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape),
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
                    text = cats.breeds.get(0).name,
//                        text = "Abc",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                  //  Spacer(modifier = Modifier.height(4.dp))
                    Text(
                    text = "Life Span: " + cats.breeds.get(0).life_span,
//                        text = "Life Span: 12 14" ,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                  //  Spacer(modifier = Modifier.height(4.dp))
                    Text(
                    text = "Origin: " + cats.breeds.get(0).origin,
//                        text = "Origin: United States" ,
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                }
        }
    }
 /*   Card(
        modifier = Modifier.padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Breed Name: " + cats.breeds.get(0).name,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Life Span: " + cats.breeds.get(0).life_span,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }*/
}

@Composable
fun Testing(){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White),
        elevation = CardDefaults.cardElevation(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(4.dp),
        ) {
            AsyncImage(
//                model = cats.url,
                model = "https://cdn2.thecatapi.com/images/ebv.jpg",
                contentDescription = "Image of Cat",
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .clip(CircleShape)
                    .padding(0.dp,8.dp,0.dp,8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
//                    text = "Breed: " + cats.breeds.get(0).name,
                    text = "Abc",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
//                    text = "Life Span: " + cats.breeds.get(0).life_span,
                    text = "Life Span: 12 14" ,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
//                    text = "Origin: " + cats.breeds.get(0).origin,
                      text = "Origin: United States" ,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth()
                )

            }
        }
    }
}

@Preview
@Composable
fun PreviewFunction(){
    Testing()
}
