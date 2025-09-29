package com.example.praktikum2.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled. BrightnessLow
import androidx.compose.material.icons.filled.BrightnessHigh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.praktikum2.ui.theme.ThemeMode

@Composable
fun ThemeSwitcher(
    currentMode: ThemeMode,
    onModeChange: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            onClick = { expanded = true },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp)
        ) {
            AnimatedContent(targetState = currentMode, label = "themeIcon") { mode ->
                val icon = when (mode) {
                    ThemeMode.SYSTEM -> Icons.Filled.Settings
                    ThemeMode.LIGHT -> Icons.Filled.BrightnessHigh
                    ThemeMode.DARK -> Icons.Filled.BrightnessLow
                }
                Icon(
                    imageVector = icon,
                    contentDescription = "Theme Icon"
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("System") },
                onClick = {
                    onModeChange(ThemeMode.SYSTEM)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Light") },
                onClick = {
                    onModeChange(ThemeMode.LIGHT)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Dark") },
                onClick = {
                    onModeChange(ThemeMode.DARK)
                    expanded = false
                }
            )
        }
    }
}
