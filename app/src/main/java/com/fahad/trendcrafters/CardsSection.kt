package com.fahad.trendcrafters
import CategoryList
import Product
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue

import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import coil.compose.AsyncImage

@Composable
fun CardsSection1(
  clothingStoreViewModel: ClothingStoreViewModel,
  navController: NavController
) {
  val clothingStoreState by clothingStoreViewModel.clothingStoreState.collectAsState()

  Column(
    modifier = Modifier
      .fillMaxSize() // Fill the available space
      .padding(10.dp)
  ) {
    Text(
      text = "Trending Now",
      modifier = Modifier
        .fillMaxWidth(),
      fontSize = MaterialTheme.typography.titleMedium.fontSize,
      fontWeight = FontWeight.Bold,
      fontStyle = Italic,
      color = MaterialTheme.colorScheme.primary
    )

    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp)
    ) {
      items(clothingStoreState) { category ->
        category.products.forEach { product ->
          CardItem(product, navController)
        }
      }
    }
  }
}

@Composable
fun CardItem(
  product: Product,
  navController: NavController
) {
  Box(
    modifier = Modifier
      .clickable {
        navController.navigate("itemDetails/${product.name}")
      }
      .clip(RoundedCornerShape(25.dp))
      .background(
        color = colorResource(id = R.color.black).copy(alpha = 0.7f)
      )
      .fillMaxWidth()
      .height(170.dp)
      .width(300.dp)
      .padding(8.dp),
    contentAlignment = Alignment.BottomCenter,
  ) {
    AsyncImage(
      model = product.imageUrl,
      contentDescription = null,
      placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
      error = painterResource(id = R.drawable.ic_launcher_foreground),
      contentScale = ContentScale.FillBounds,
      modifier = Modifier.fillMaxSize(),
    )

    Box(
      modifier = Modifier
        .padding(8.dp)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color.Transparent,
              Color.Black.copy(alpha = 0.5f),
              Color.Black.copy(alpha = 0.7f),
              Color.Black.copy(alpha = 0.9f),
              Color.Black
            ),
            startY = 0f,
            endY = 50f
          )
        ),
    ) {
      Text(
        text = product.name,
        color = Color.White,
        fontSize = MaterialTheme.typography.titleMedium.fontSize,
        textAlign = TextAlign.Center,
      )
    }
  }
}

@Composable
fun CardsSection(navController: NavController) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Text(
      text = "Trending Now",
      modifier = Modifier
        .fillMaxWidth(),
      fontSize = MaterialTheme.typography.titleMedium.fontSize,
      fontWeight = FontWeight.Bold,
      fontStyle =Italic,
      color = MaterialTheme.colorScheme.primary
    )

    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp) // Add space between items
    ) {
      items(imagesWithTitles) { imageResId ->
        CardItem(imageResId.first, imageResId.second, navController )
      }
    }
  }
}

@Composable
fun CardItem(imageResId: Int, title: String, navController: NavController) {
  Box(
    modifier = Modifier
      .clickable {
        // Handle navigation or any other action here
        // For example: navController.navigate("itemDetails/$imageResId")
      }
      .clip(RoundedCornerShape(25.dp))
      .background(
        color = colorResource(id = R.color.black).copy(alpha = 0.7f)
      )
      .fillMaxWidth()
      .height(180.dp)
      .width(250.dp)
      .padding(horizontal = 5.dp, vertical = 5.dp), // Add padding here
    contentAlignment = Alignment.BottomCenter,
  ) {
    Image(
      painter = painterResource(id = imageResId),
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(25.dp))
    )

    Box(
      modifier = Modifier
        .padding(8.dp)
        .background(
          Brush.verticalGradient(
            colors = listOf(
              Color.Transparent,
              Color.Black.copy(alpha = 0.5f),
              Color.Black.copy(alpha = 0.7f),
              Color.Black.copy(alpha = 0.9f),
              Color.Black
            ),
            startY = 0f,
            endY = 50f
          )
        ),
    ) {
      // You can customize this part as needed
      Text(
        text = title,
        color = Color.White,
        fontSize = MaterialTheme.typography.titleMedium.fontSize,
        textAlign = TextAlign.Center,
      )
    }
  }
}

val imagesWithTitles = listOf(
  Pair(R.drawable.image, "Stylish Denim Jacket"),
  Pair(R.drawable.image0, "Casual Summer Dress"),
  Pair(R.drawable.image1, "Formal Men's Suit"),
  Pair(R.drawable.image2, "Sporty Running Shoes")
)
