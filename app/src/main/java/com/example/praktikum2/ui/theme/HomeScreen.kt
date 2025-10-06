// com/example/praktikum2/ui/HomeScreen.kt

package com.example.praktikum2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.praktikum2.components.ItemInput
import com.example.praktikum2.components.SearchInput
import com.example.praktikum2.components.ShoppingList
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    var newItemText by rememberSaveable { mutableStateOf("") }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val shoppingItems = remember { mutableStateListOf<String>() }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarJob by remember { mutableStateOf<Job?>(null) }

    val filteredItems by remember(searchQuery, shoppingItems) {
        derivedStateOf {
            if (searchQuery.isBlank()) shoppingItems
            else shoppingItems.filter { it.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp) // Beri sedikit padding atas
    ) {
        // Title() dihapus dari sini
        ItemInput(
            text = newItemText,
            onTextChange = { newItemText = it },
            onAddItem = {
                val newItem = newItemText.trim()
                if (newItem.isNotBlank()) {
                    if (shoppingItems.contains(newItem)) {
                        snackbarJob?.cancel()
                        snackbarJob = coroutineScope.launch {
                            snackbarHostState.currentSnackbarData?.dismiss()
                            snackbarHostState.showSnackbar(
                                message = "Item '$newItem' sudah ada!",
                                duration = SnackbarDuration.Short
                            )
                        }
                    } else {
                        shoppingItems.add(newItem)
                        newItemText = ""
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        SearchInput(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SnackbarHost(hostState = snackbarHostState)
        ShoppingList(items = filteredItems)
    }
}