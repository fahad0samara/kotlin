package com.fahad.trendcrafters

import CategoryList
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class ClothingStoreViewModel : ViewModel() {

  // State for holding the data
  private val _clothingStoreState = MutableStateFlow<List<CategoryList>>(emptyList())
  val clothingStoreState: StateFlow<List<CategoryList>> = _clothingStoreState.asStateFlow()

  // Fetch data directly in the ViewModel
  init {
    fetchData()
  }

  fun fetchData() {
    viewModelScope.launch {
      try {
        val client = HttpClient(CIO)
        val response: HttpResponse = client.get("https://trendcrafters-6586b-default-rtdb.firebaseio.com/data.json")
        val responseBody = response.bodyAsText()
        Log.d("CoffeeViewModel", "Response body: $responseBody")

        if (response.status.isSuccess()) {
          val coffeeDrinkList: List<CategoryList> = Json.decodeFromString(responseBody)
          Log.d("CoffeeViewModel", "Coffee list: $coffeeDrinkList")
            _clothingStoreState.value = coffeeDrinkList








        } else {
          // Handle non-successful response (e.g., show an error message)
          Log.e("ViewModele", "Error: ${response.status.value} - ${response.status.description}")
        }
      } catch (e: Exception) {
        Log.e("Coffeele", "Error: ${e.message}", e)
      }
    }
  }
}









