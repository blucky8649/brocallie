package com.blucky8649.createcallie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import brocallie.shared.feature.createcallie.generated.resources.Res
import brocallie.shared.feature.createcallie.generated.resources.add_photo
import brocallie.shared.feature.createcallie.generated.resources.confirm
import brocallie.shared.feature.createcallie.generated.resources.create_callie
import brocallie.shared.feature.createcallie.generated.resources.error_message
import brocallie.shared.feature.createcallie.generated.resources.photo_ai
import brocallie.shared.feature.createcallie.generated.resources.tooltip_content
import brocallie.shared.feature.createcallie.generated.resources.tooltip_title
import coil3.compose.AsyncImage
import com.blucky8649.designsystem.BcText
import com.blucky8649.designsystem.BcTopAppBar
import com.blucky8649.room.BrocallieDatabase
import compose.icons.TablerIcons
import compose.icons.tablericons.Check
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

private val TooltipShape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCallieScreen(
    screenId: String,
    onBackButtonPressed: () -> Unit,
    onCreateClick: () -> Unit,
    languageCode: String = "en"
) {
    val dbInject = koinInject<BrocallieDatabase>()
    val viewModel: CreateCallieViewModel = viewModel(key = screenId) { CreateCallieViewModel(dbInject, languageCode) }

    Scaffold(
        topBar = {
            val title = stringResource(Res.string.create_callie)
            val errorMessage = stringResource(Res.string.error_message)
            BcTopAppBar(
                title = title,
                navigationIcon = Icons.AutoMirrored.Default.ArrowBack,
                onNavigationClick = onBackButtonPressed,
                actionIcon = TablerIcons.Check,
                onActionClick = {
                    viewModel.analyzeImage(errorMessage) {
                        onCreateClick()
                    }
                },
            )
        },
        bottomBar = {
            Box(modifier = Modifier.padding(10.dp)) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = TooltipShape,
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        BcText(
                            text = stringResource(Res.string.tooltip_title),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        BcText(
                            text = stringResource(Res.string.tooltip_content),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    ) { paddingValues: PaddingValues ->
        val uiState by viewModel.uiState.collectAsState()
        var showErrorDialog by remember { mutableStateOf(false) }
        val launchPhotoPicker = rememberImagePickerLauncher(viewModel::setImage)

        LaunchedEffect(uiState.errorMessage) {
            showErrorDialog = uiState.errorMessage != null
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.analyzeImage(null) },
                text = { Text(text = uiState.errorMessage ?: "Unexpected Error Occurred") },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text(stringResource(Res.string.confirm))
                    }
                },
            )
        }

        LaunchedEffect(uiState.errorMessage) {
            if (uiState.errorMessage != null) {

            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            val alignCenter = Modifier.align(Alignment.Center)
            val circleModifier = Modifier
                .size(300.dp)
                .then(alignCenter)
                .clip(CircleShape)
                .clickable { launchPhotoPicker() }

            Column(
                modifier = circleModifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.photo_ai),
                    contentDescription = "add photo",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(150.dp)
                )
                Text(
                    text = stringResource(Res.string.add_photo),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(5.dp)
                )
            }

            uiState.image.byteArray?.also {
                AsyncImage(
                    model = it,
                    modifier = circleModifier,
                    contentDescription = "selected image",
                    contentScale = ContentScale.Crop
                )
            }
            if (uiState.isLoading) { CircularProgressIndicator(modifier = alignCenter) }
        }
    }
}

@Composable
expect fun rememberImagePickerLauncher(
    onImagePicked: (ByteArray) -> Unit
): () -> Unit

@Composable
@Preview
fun CreateCallieScreenPreview() {
    CreateCallieScreen(
        screenId = "",
        onBackButtonPressed = {},
        onCreateClick = {}
    )
}
