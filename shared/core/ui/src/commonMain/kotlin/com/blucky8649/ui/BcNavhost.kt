package com.blucky8649.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.blucky8649.contacts.navigation.ROUTE_CONTACTS
import com.blucky8649.contacts.navigation.contactsRoute
import com.blucky8649.conversation.navigation.chatScreenRoute
import com.blucky8649.conversation.navigation.navigateToChat
import com.blucky8649.createcallie.navigation.createCallieRoute
import com.blucky8649.createcallie.navigation.navigateToCreateCallie
import com.blucky8649.decompose_navhost.navigation.NavController
import com.blucky8649.decompose_navhost.navigation.NavHost

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun BcNavHost(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ROUTE_CONTACTS
    ) {
        contactsRoute(
            onAddButtonClick = navController::navigateToCreateCallie,
            onContactClick = navController::navigateToChat
        )
        chatScreenRoute(
            onBackPressed = navController::popBackStack,
            onImageClick = { /** TODO: Implement image click */ }
        )
        createCallieRoute(
            onBackPressed =  navController::popBackStack,
            onCreateCallieClick = navController::popBackStack
        )
    }
}