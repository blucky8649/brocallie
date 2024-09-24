package com.blucky8649.contacts.navigation

import com.blucky8649.contacts.ContactsScreen
import com.blucky8649.decompose_navhost.navigation.NavController
import com.blucky8649.decompose_navhost.navigation.NavGraphBuilder
import com.blucky8649.room.model.CallieEntity

const val ROUTE_CONTACTS = "contacts"

fun NavGraphBuilder.contactsRoute(
    onContactClick: (CallieEntity) -> Unit,
    onAddButtonClick: () -> Unit
) {
    composable(route = ROUTE_CONTACTS) {
        ContactsScreen(
            onContactClick = onContactClick,
            onAddButtonClick = onAddButtonClick
        )
    }
}

fun NavController.navigateToContacts() {
    navigate(ROUTE_CONTACTS)
}