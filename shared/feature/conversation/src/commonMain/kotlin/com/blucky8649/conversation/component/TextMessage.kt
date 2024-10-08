package com.blucky8649.conversation.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.blucky8649.conversation.AUTHOR_KIM
import com.blucky8649.conversation.AUTHOR_ME
import com.blucky8649.conversation.Author
import com.blucky8649.designsystem.BcText
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextMessage(
    author: Author,
    message: String,
    modifier: Modifier = Modifier,
    isUserMe: Boolean = false,
    isAuthorRepeated: Boolean,
    onImageClick: (id: String) -> Unit
) {
    TextMessage(
        author,
        modifier,
        isUserMe,
        isAuthorRepeated,
        onImageClick
    ) {
        ChatBubble(
            modifier = Modifier
                .align(if (isUserMe) Alignment.End else Alignment.Start)
                .widthIn(0.dp, 250.dp),
            isUserMe = isUserMe,
            messageText = message,
            isAuthorRepeated = isAuthorRepeated
        )
    }
}

@Composable
fun TextMessage(
    author: Author,
    modifier: Modifier = Modifier,
    isUserMe: Boolean = false,
    isAuthorRepeated: Boolean,
    onImageClick: (id: String) -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {

    val spaceBetween = if (isAuthorRepeated)
        Modifier else Modifier.padding(top = 8.dp)

    Row(modifier = spaceBetween.then(modifier)) {
        when {
            isUserMe -> {}
            isAuthorRepeated -> {
                Spacer(modifier = Modifier.width(58.dp))
            }
            else -> ChatCircleImage(author = author, onClick = onImageClick)
        }
        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
        ) {
            when {
                (isAuthorRepeated || isUserMe) -> Spacer(modifier = Modifier.height(4.dp))
                else -> AuthorName(author.name)
            }

            ChatBubble(
                modifier = Modifier
                    .align(if (isUserMe) Alignment.End else Alignment.Start)
                    .widthIn(0.dp, 250.dp)
                ,
                isUserMe = isUserMe,
                isAuthorRepeated = isAuthorRepeated,
                content = { content() }
            )
        }
    }
}

@Composable
private fun AuthorName(
    name: String
) {
    Row {
        BcText(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .alignBy(LastBaseline)
                .paddingFrom(LastBaseline, after = 8.dp)
        )      
    }
}

@Composable
private fun ChatCircleImage(
    author: Author,
    onClick: (id: String) -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = ""
) {
    val borderColor = MaterialTheme.colorScheme.tertiary
    AsyncImage(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .size(42.dp)
            .border(1.5.dp, borderColor, CircleShape)
            .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
            .clip(CircleShape)
            .clickable(onClick = { onClick(author.id) })
            .then(modifier),
        model = author.image,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop
    )
}

@Composable
@Preview
fun TextMessagePreview() {
    Column {
        TextMessage(
            author = AUTHOR_ME,
            message = "Hi, nice to meet you.",
            isUserMe = true,
            isAuthorRepeated = false,
        ) {}
        
        TextMessage(
            author = AUTHOR_ME,
            message = "How are you?",
            isUserMe = true,
            isAuthorRepeated = true,
        ) {}
        
        TextMessage(
            author = AUTHOR_KIM,
            message = "Hi!",
            isUserMe = false,
            isAuthorRepeated = false,
        ) {}
    }
}