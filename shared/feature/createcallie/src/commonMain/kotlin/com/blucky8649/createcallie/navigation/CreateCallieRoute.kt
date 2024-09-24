package com.blucky8649.createcallie.navigation

import com.blucky8649.createcallie.CreateCallieScreen
import com.blucky8649.createcallie.defaultLocale
import com.blucky8649.decompose_navhost.navigation.NavController
import com.blucky8649.decompose_navhost.navigation.NavGraphBuilder

const val ROUTE_CREATE_CALLIE = "route_create_callie"

fun NavGraphBuilder.createCallieRoute(
    onBackPressed: () -> Unit,
    onCreateCallieClick: () -> Unit
) {
    composable(ROUTE_CREATE_CALLIE) {
        val languageCode = defaultLocale
        CreateCallieScreen(
            onBackButtonPressed = onBackPressed,
            onCreateClick = onCreateCallieClick,
            languageCode = languageCode
        )
    }
}

fun NavController.navigateToCreateCallie() {
    navigate(ROUTE_CREATE_CALLIE)
}