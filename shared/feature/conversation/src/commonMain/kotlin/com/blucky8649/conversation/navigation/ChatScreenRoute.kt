package com.blucky8649.conversation.navigation

import com.blucky8649.conversation.ChatScreen
import com.blucky8649.decompose_navhost.navigation.NavController
import com.blucky8649.decompose_navhost.navigation.NavGraphBuilder
import com.blucky8649.room.model.CallieEntity
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.thauvin.erik.urlencoder.UrlEncoderUtil

const val ROUTE_CHAT = "route_chat"
const val KEY_CALLIE = "key_callie"
const val KEY_IMAGE = "key_image"

fun NavGraphBuilder.chatScreenRoute(
    onBackPressed: () -> Unit,
    onImageClick: (id: String) -> Unit
) {
    composable(
        "$ROUTE_CHAT?$KEY_CALLIE={$KEY_CALLIE}&$KEY_IMAGE={$KEY_IMAGE}",
    ) {
        val callieJson = it.arguments.getString(KEY_CALLIE)
        val image = it.arguments.getString(KEY_IMAGE)

        println("callieJson = $callieJson")
        val callie = callieJson
            ?.let { Json.decodeFromString<CallieEntity>(it) }
            ?.copy(image = image ?: "")
            ?: CallieEntity()

        ChatScreen(
            callie = callie,
            onBackPressed = onBackPressed,
            onImageClick = onImageClick
        )
    }
}


fun NavController.navigateToChat(callie: CallieEntity) {
    val image = UrlEncoderUtil.encode(callie.image)
    val callieWithoutImage = callie.copy(image = "")
    val callieJson = Json.encodeToString(callieWithoutImage)
    println("callie22 = $callieJson")
    navigate("$ROUTE_CHAT?$KEY_CALLIE=$callieJson&$KEY_IMAGE=$image")
}