package com.fahad.trendcrafters

import AsyncImageProfile
import CategoryList
import Product
import ShippingInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.fahad.trendcrafters.ui.theme.TrendCraftersTheme

class MainActivity : ComponentActivity() {
  private val viewModel: ClothingStoreViewModel by viewModels()



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      TrendCraftersTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          // Observe the clothing store state
          val clothingStoreState by viewModel.clothingStoreState.collectAsState()
          val navController = rememberNavController()



          // State variable to track the selected category
          var selectedCategory by remember { mutableStateOf("Men's") }

          // Filtered clothing items based on the selected category
          val filteredClothingStoreState = clothingStoreState
            .filter { it.name == selectedCategory }
            .flatMap { it.products }



          // Display the clickable category names and the products
          Column {


         CategoryFilterBar(
              categories = clothingStoreState.map { it.name },
                onCategorySelected = { category ->
                    selectedCategory = category
                }
            )
           ClothingStoreScreen(filteredClothingStoreState)

         }
        }
      }
    }
  }
}

@Composable
fun CategoryFilterBar(categories: List<String>, onCategorySelected: (String) -> Unit) {
  LazyRow(
    contentPadding = PaddingValues(horizontal = 6 .dp, vertical = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    items(categories) { category ->
      ClickableText(
        text = AnnotatedString(category),
        onClick = { offset ->
          onCategorySelected(category)
        },
        style = TextStyle(
          color = Color.White,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp
        ),
        modifier = Modifier.padding(end = 8.dp)
      )
    }
  }
}


@Composable
fun ClothingStoreScreen(filteredClothingStoreState: List<Product>) {
  LazyVerticalGrid(
    GridCells.Fixed(2),
    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
    items(filteredClothingStoreState) { product ->
      GridOfImages(product)


    }
  }
}




@Composable
fun GridOfImages(product: Product) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clickable { /* Handle click */ }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      Box(
        modifier = Modifier
          .size(150.dp, 150.dp)
          .background(
            color = MaterialTheme.colorScheme.background, // Adjust background color
            shape = RoundedCornerShape(16.dp)
          )
          .clip(shape = RoundedCornerShape(16.dp))
          .clickable { /* Handle click */ }
      ) {
        AsyncImageProfile(
          modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .align(Alignment.BottomCenter),
          photoUrl = product.imageUrl,
          contentScale = ContentScale.Crop,
        )

        Image(
          painter = painterResource(id = R.drawable.ic_heart),
          contentDescription = null,
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(end = 8.dp, top = 8.dp)
            .clickable { /* Handle click */ }
        )
      }

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(8.dp)
      ) {
        Column {
          Text(
            text = product.name,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold

          )



          Row(
            modifier = Modifier
              .fillMaxWidth()
              .background(
                color = MaterialTheme.colorScheme.surface, // Adjust background color
                shape = RoundedCornerShape(16.dp)
              )
              .padding(8.dp)
              .clip(shape = RoundedCornerShape(16.dp)),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(18.dp)
              )

              Spacer(modifier = Modifier.size(4.dp))

              Text(
                text = product.rating.toString(),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
              )
            }

            Spacer(modifier = Modifier.size(8.dp))

            Text(
              text = "$${product.price}",
              style = MaterialTheme.typography.titleMedium
            )
          }
        }
      }
    }
  }
}





