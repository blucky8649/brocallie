package com.blucky8649.createcallie

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberImagePickerLauncher(
    onImagePicked: (ByteArray) -> Unit
): () -> Unit {
    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        val bytes = uri?.readBytes(context) ?: return@rememberLauncherForActivityResult
        onImagePicked(bytes)
    }

    return remember(photoPicker, onImagePicked) {
        {
            photoPicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }
}

private fun Uri.readBytes(context: Context): ByteArray? = runCatching {
    context.contentResolver.openInputStream(this)?.use { it.readBytes() }
}.getOrNull()
