package com.blucky8649.conversation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.blucky8649.conversation.component.TextMessage
import com.blucky8649.conversation.component.UserInputField
import com.blucky8649.designsystem.BcTopAppBar
import com.blucky8649.designsystem.loading.LoadingProgressView
import com.blucky8649.room.BrocallieDatabase
import com.blucky8649.room.model.CallieEntity
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

const val ConversationTestTag = "ConversationTestTag"

/**
 * Composable function that displays a chat screen.
 * @param title The title to be displayed at the top of the screen.
 * @param viewModel The ViewModel that manages the state of the chat screen.
 * @param onBackPressed Callback function to be invoked when the back button is pressed.
 * @param onImageClick Callback function to be invoked when an image is clicked, taking the author ID as an argument.
 * @param modifier Modifier to be applied to the composable.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    callie: CallieEntity,
    onBackPressed: () -> Unit,
    onImageClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dbInject = koinInject<BrocallieDatabase>()
    val viewModel: ChatViewModel = viewModel(key = callie.id.toString()) { ChatViewModel(dbInject, callie) }
    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)
    val chatUiState by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
          BcTopAppBar(
              title = callie.name,
              navigationIcon = Icons.AutoMirrored.Default.ArrowBack,
              onNavigationClick = onBackPressed,
              scrollBehavior = scrollBehavior
          )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            UserInputField(
                onMessageSent = { viewModel.sendMessage(Message(AUTHOR_ME, it, Clock.System.now().toEpochMilliseconds().toString())) },
                resetScroll = { scope.launch { scrollState.animateScrollToItem(0) } },
                modifier = Modifier.navigationBarsPadding().imePadding()
            )
        },
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        val interactionSource = remember { MutableInteractionSource() }

        LaunchedEffect(chatUiState.errorMessage) {
            if (chatUiState.errorMessage.isNotEmpty()) {
                snackbarHostState.showSnackbar(message = chatUiState.errorMessage)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    keyboardController?.hide()
                    focusManager.clearFocus(true)
                }
                .padding(paddingValues)
        ) {
            /**
             * The reason for setting reverseLayout to true is
             * simply to handle cases where the IME (Input Method Editor, or keyboard)
             * covers a portion of the items in the LazyColumn.
             */
            LazyColumn(
                reverseLayout = true,
                state = scrollState,
                modifier = Modifier
                    .testTag(ConversationTestTag),
                contentPadding = PaddingValues(bottom = 5.dp)
            ) {
                if (chatUiState.isLoading) {
                    item {
                        TextMessage(
                            author = Author(callie.id.toString(), callie.name, callie.image),
                            isUserMe = false,
                            isAuthorRepeated = false,
                            onImageClick = {},
                        ) {
                            LoadingProgressView(modifier = Modifier.padding(16.dp))
                        }
                    }
                }
                items(chatUiState.messages.size) {
                    val currentMessage = chatUiState.messages[it]
                    val previousMessage = chatUiState.messages.getOrNull(it + 1)

                    TextMessage(
                        author = currentMessage.author,
                        message = currentMessage.content,
                        isUserMe = currentMessage.author.name == AUTHOR_ME.name,
                        isAuthorRepeated = currentMessage.author == previousMessage?.author,
                        onImageClick = onImageClick
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ChatScreenPreView() {
    ChatScreen(
        callie = CallieEntity(name = "Callie Preview"),
        onBackPressed = {},
        onImageClick = {}
    )
}