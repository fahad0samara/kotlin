package com.fahad.trendcrafters

import Product
import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(
    ExperimentalFoundationApi::class
)

@Composable
fun MediaCarousel(
  clothingStoreViewModel: ClothingStoreViewModel,
  totalItemsToShow: Int = 10,
  carouselLabel: String = "",
) {
  val CAROUSEL_AUTO_SCROLL_TIMER: Long = 3000L
  val autoScrollDuration: Long = CAROUSEL_AUTO_SCROLL_TIMER
  val pagerState: PagerState = rememberPagerState(pageCount = { clothingStoreViewModel.clothingStoreState.value.size })
  val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

  if (isDragged.not()) {
    with(pagerState) {
      if (pageCount > 0) {
        var currentPageKey by remember { mutableStateOf(0) }
        LaunchedEffect(key1 = currentPageKey) {
          launch {
            delay(timeMillis = autoScrollDuration)
            val nextPage = (currentPage + 1).mod(pageCount)
            animateScrollToPage(
              page = nextPage,
              animationSpec = tween(
                durationMillis = 300,
              )
            )
            currentPageKey = nextPage
          }
        }
      }
    }
  }

  Column(


    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(200.dp).
          width(200.dp).
        padding( 8.dp)

    ) {
      HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(
          horizontal = 32.dp,
        ),
        pageSpacing = 16.dp
      ) { page ->
        // Extract relevant products from the CategoryList
        val categoryList = clothingStoreViewModel.clothingStoreState.value[page]
        val products = categoryList.products.take(totalItemsToShow)
        // Display the CarouselBox for each product
        products.forEach { product ->
          CarouselBox(item = product)
        }
      }
    }

    if (carouselLabel.isNotBlank()) {
      Text(
        text = carouselLabel,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(top = 8.dp)
      )
    }
  }
}

@Composable
fun CarouselBox(item: Product) {
  Box(
    modifier = Modifier
      .height(160.dp)
      .fillMaxWidth()
  ) {
    AsyncImage(
      model = item.imageUrl,
      contentDescription = null,
      placeholder = painterResource(id = R.drawable.trendcrafters),
      error = painterResource(id = R.drawable.ic_launcher_foreground),
      contentScale = ContentScale.FillBounds,
      modifier = Modifier.fillMaxSize()
    )
    val gradient = remember {
      Brush.verticalGradient(
        listOf(
          Color.Transparent,
          Color.Black
        )
      )
    }

    Text(
      text = item.name,
      color = Color.White,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
      style = MaterialTheme.typography.labelLarge,
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter)
        .background(gradient)
        .padding(
          horizontal = 16.dp,
          vertical = 4.dp
        )
    )
  }
}




