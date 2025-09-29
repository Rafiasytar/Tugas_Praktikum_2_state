package com.example.praktikum2.components

// --- IMPORT YANG DIPERLUKAN ---
import androidx.compose.animation.*
import androidx.compose.animation.core.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktikum2.model.ShoppingItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListItem(
    item: ShoppingItem,
    onCheckChange: (ShoppingItem) -> Unit,
    onDeleteItem: (ShoppingItem) -> Unit,
    modifier: Modifier = Modifier
) {
    // Menggunakan rememberDismissState dari Material 3
    val dismissState = rememberDismissState(
        confirmValueChange = { dismissValue ->
            // Hanya proses penghapusan jika item belum dicentang dan digeser ke kiri
            if (!item.isChecked && dismissValue == DismissValue.DismissedToStart) {
                onDeleteItem(item)
                return@rememberDismissState true // Konfirmasi perubahan state
            }
            false // Tolak perubahan state
        }
    )

    // Menggunakan SwipeToDismiss yang baru dari Material 3
    SwipeToDismiss(
        state = dismissState,
        modifier = modifier.padding(vertical = 4.dp),
        // Arah geser hanya dari kanan ke kiri
        directions = setOf(DismissDirection.EndToStart),
        background = {
            // Latar belakang yang muncul saat item digeser
            val color = when (dismissState.targetValue) {
                DismissValue.DismissedToStart -> MaterialTheme.colorScheme.errorContainer
                else -> Color.Transparent
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        },
        dismissContent = {
            // Konten utama yang akan ditampilkan
            ShoppingListItemContent(item, onCheckChange)
        }
    )
}

@Composable
private fun ShoppingListItemContent(
    item: ShoppingItem,
    onCheckChange: (ShoppingItem) -> Unit
) {
    val elevation by animateDpAsState(if (item.isChecked) 6.dp else 2.dp, label = "elevation")
    val cardColor by animateColorAsState(
        if (item.isChecked) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        label = "cardColor"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        onClick = { onCheckChange(item) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = if (item.isChecked) 1f else 0.4f),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = item.name.firstOrNull()?.toString()?.uppercase() ?: "",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textDecoration = if (item.isChecked) TextDecoration.LineThrough else null
                    )
                }
                AnimatedVisibility(
                    visible = item.isChecked,
                    enter = scaleIn(animationSpec = tween(200)) + fadeIn(),
                    exit = scaleOut(animationSpec = tween(200)) + fadeOut()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Checked",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            AnimatedVisibility(visible = item.isChecked) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Selesai!",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}