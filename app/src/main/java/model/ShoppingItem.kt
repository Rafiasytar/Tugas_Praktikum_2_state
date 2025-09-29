package com.example.praktikum2.model

import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import java.util.UUID

data class ShoppingItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isChecked: Boolean = false
)

/**
 * Saver untuk menyimpan dan memulihkan SnapshotStateList<ShoppingItem>.
 * Ini mengubah setiap ShoppingItem menjadi List<Any> untuk disimpan,
 * dan memulihkannya kembali saat aplikasi dibuat ulang.
 */
val ShoppingItemListSaver = listSaver<SnapshotStateList<ShoppingItem>, Any>(
    save = { shoppingItems ->
        // Mengubah list of ShoppingItem menjadi list of lists
        shoppingItems.map { item -> listOf(item.id, item.name, item.isChecked) }
    },
    restore = { savedList ->
        // Memulihkan list of ShoppingItem dari saved list
        savedList.map { itemData ->
            val data = itemData as List<*>
            ShoppingItem(data[0] as String, data[1] as String, data[2] as Boolean)
        }.toMutableStateList()
    }
)