package com.blucky8649.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BcTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    ),
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String? = null,
    actionIcon: ImageVector? = null,
    actionIconContentDescription: String? = null,
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            navigationIcon ?: return@CenterAlignedTopAppBar
            IconButton(onClick = onNavigationClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationIconContentDescription,
                    tint = colors.navigationIconContentColor,
                )
            }
        },
        actions = {
            actionIcon ?: return@CenterAlignedTopAppBar
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionIconContentDescription,
                    tint = colors.actionIconContentColor
                )
            }
        },
        colors = colors,
        modifier = modifier.testTag("BcTopAppBar")
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BcTopAppBarPreview() {
    MaterialTheme {
        BcTopAppBar(
            title = "TopAppBar",
            navigationIcon = Icons.AutoMirrored.Default.ArrowBack,
            navigationIconContentDescription = "Back",
            actionIcon = Icons.Default.MoreVert,
            actionIconContentDescription = "More",
            onNavigationClick = {},
            onActionClick = {},
        )
    }
}