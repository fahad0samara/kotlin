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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

          // State variable to track the selected category
          var selectedCategory by remember { mutableStateOf("Men's") }

          // Filtered clothing items based on the selected category
          val filteredClothingStoreState = clothingStoreState
            .filter { it.name == selectedCategory }
            .flatMap { it.products }

          Log.d("MainActivity", "clothingStoreState: $clothingStoreState")

          // Display the clickable category names and the products
          Column {
            CategoryFilterBar(
              categories = clothingStoreState.map { it.name },
              onCategorySelected = { category -> selectedCategory = category }
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
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
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
  LazyColumn {
    items(filteredClothingStoreState) { product ->
      ProductItem(product)
      Divider(color = Color.LightGray, thickness = 1.dp)
    }
  }
}


@Composable
fun ProductItem(product: Product) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
      .clip(MaterialTheme.shapes.medium)
      .shadow(4.dp),
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      // Display product details
      Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = "Price: $${product.price}", style = MaterialTheme.typography.labelLarge)
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = "Rating: ${product.rating ?: "N/A"}", style = MaterialTheme.typography.labelLarge)
      Spacer(modifier = Modifier.height(8.dp))
      Text(text = "In Stock: ${product.inStock}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Description: ${product.description}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Shipping Info: ${product.shippingInfo}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Sizes: ${product.sizes}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Tags: ${product.tags}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Discount: ${product.discount}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Discount Percentage: ${product.discountPercentage}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

      AsyncImageProfile(
        photoUrl = product.imageUrl,
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp),
        contentScale = ContentScale.Crop,
        loadingModifier = Modifier
          .fillMaxWidth()
            .height(20.dp),
        loadingSize = 48,


        loadingColor = MaterialTheme.colorScheme.primary
        )


        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Id: ${product.id}", style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Name: ${product.name}", style = MaterialTheme.typography.bodyMedium)
    Spacer(modifier = Modifier.height(8.dp))
      ShippingInfoItem(shippingInfo = product.shippingInfo)
        Spacer(modifier = Modifier.height(8.dp))




    }
  }
}
@Composable
fun ShippingInfoItem(shippingInfo: ShippingInfo) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp)
  ) {
    Text(
      text = "Estimated Delivery Days: ${shippingInfo.estimatedDeliveryDays}",
      style = MaterialTheme.typography.bodyMedium
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "Free Shipping Threshold: $${shippingInfo.freeShippingThreshold}",
      style = MaterialTheme.typography.bodyMedium
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = "Shipping Cost: $${shippingInfo.shippingCost}",
      style = MaterialTheme.typography.bodyMedium
    )
  }
}