// com/example/praktikum2/ui/ProfileScreen.kt

package com.example.praktikum2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.praktikum2.R // Pastikan import R ini benar

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Ganti dengan nama file gambar Anda
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Foto Profil",
            modifier = Modifier
                .size(180.dp)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp)
            ) {
                ProfileInfoRow(label = "Nama", value = "Muhammad Rafi Asytar")
                Spacer(modifier = Modifier.height(16.dp))
                ProfileInfoRow(label = "NIM", value = "2311522030")
                Spacer(modifier = Modifier.height(16.dp))
                ProfileInfoRow(label = "TTL", value = "Bukittinggi, 21 Desember 2004")
                Spacer(modifier = Modifier.height(16.dp))
                ProfileInfoRow(label = "Hobi", value = "Menggambar, mendesign")
                Spacer(modifier = Modifier.height(16.dp))
                ProfileInfoRow(label = "Peminatan", value = "Mobile Programming")
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            modifier = Modifier.width(100.dp), // Lebar tetap untuk label
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}