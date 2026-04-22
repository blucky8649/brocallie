package com.blucky8649.createcallie

import androidx.compose.runtime.Composable

@Composable
actual fun rememberImagePickerLauncher(
    onImagePicked: (ByteArray) -> Unit
): () -> Unit = {}
