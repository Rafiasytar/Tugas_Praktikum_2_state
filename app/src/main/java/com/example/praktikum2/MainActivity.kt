package com.example.praktikum2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikum2.components.ItemInput
import com.example.praktikum2.components.SearchInput
import com.example.praktikum2.components.ShoppingList
import com.example.praktikum2.components.Title
import com.example.praktikum2.components.ThemeSwitcher
import com.example.praktikum2.ui.theme.Praktikum2Theme
import com.example.praktikum2.ui.theme.ThemeMode
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var themeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }

            Praktikum2Theme(themeMode = themeMode) {
                Box(modifier = Modifier.fillMaxSize()) {
                    ShoppingListApp(modifier = Modifier.fillMaxSize())

                    ThemeSwitcher(
                        currentMode = themeMode,
                        onModeChange = { themeMode = it },
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingListApp(modifier: Modifier = Modifier) {
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

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Title()

            SnackbarHost(hostState = snackbarHostState)

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

            Spacer(modifier = Modifier.height(16.dp))

            SearchInput(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ShoppingList(items = filteredItems)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingListAppPreview() {
    Praktikum2Theme {
        Box(modifier = Modifier.fillMaxSize()) {
            ShoppingListApp(modifier = Modifier.fillMaxSize())
            ThemeSwitcher(
                currentMode = ThemeMode.SYSTEM,
                onModeChange = {},
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }
    }
}
