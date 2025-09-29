package com.example.praktikum2

// PERBAIKAN: Import yang diperlukan
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.praktikum2.components.ItemInput
import com.example.praktikum2.components.SearchInput
import com.example.praktikum2.components.ShoppingList
import com.example.praktikum2.components.Title
import com.example.praktikum2.model.ShoppingItem
import com.example.praktikum2.model.ShoppingItemListSaver // Menggunakan saver yang sudah diperbaiki
import com.example.praktikum2.ui.theme.Praktikum2Theme

@Composable
fun ShoppingListScreen() {
    var newItemText by rememberSaveable { mutableStateOf("") }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    // PERBAIKAN: Menggunakan ShoppingItemListSaver yang sudah dikustom
    val shoppingItems = rememberSaveable(saver = ShoppingItemListSaver) {
        // Inisialisasi daftar
        mutableStateListOf<ShoppingItem>()
    } as SnapshotStateList<ShoppingItem> // Casting diperlukan karena saver mengembalikan list yang dapat diubah

    // Daftar item yang difilter
    val filteredItems: List<ShoppingItem> by remember(searchQuery, shoppingItems) {
        derivedStateOf {
            if (searchQuery.isBlank()) {
                shoppingItems
            } else {
                // Fungsi .filter tersedia
                shoppingItems.filter { it.name.contains(searchQuery, ignoreCase = true) }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {
        Title()
        ItemInput(
            text = newItemText,
            onTextChange = { newItemText = it },
            onAddItem = {
                if (newItemText.isNotBlank()) {
                    shoppingItems.add(ShoppingItem(name = newItemText))
                    newItemText = ""
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SearchInput(
            query = searchQuery,
            onQueryChange = { searchQuery = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        ShoppingList(
            items = filteredItems,
            onCheckChange = { checkedItem ->
                val index = shoppingItems.indexOfFirst { it.id == checkedItem.id }
                if (index != -1) {
                    val currentItem = shoppingItems[index]
                    // Perbarui item di indeks tersebut
                    shoppingItems[index] = currentItem.copy(isChecked = !currentItem.isChecked)
                }
            },
            onRemoveItem = { itemToRemove ->
                shoppingItems.remove(itemToRemove)
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
    Praktikum2Theme {
        ShoppingListScreen()
    }
}
