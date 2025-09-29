package com.example.praktikum2.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.praktikum2.model.ShoppingItem

@Composable
fun ShoppingList(
    items: List<ShoppingItem>,
    onCheckChange: (ShoppingItem) -> Unit,
    onDeleteItem: (ShoppingItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(items, key = { it.id }) { item ->
            ShoppingListItem(
                item = item,
                onCheckChange = onCheckChange,
                onDeleteItem = onDeleteItem
            )
        }
    }
}