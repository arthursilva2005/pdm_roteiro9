package com.example.livraria0110

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(
    navigationController: NavHostController
) {

    val navBackStackEntry by
    navigationController.currentBackStackEntryAsState()

    val currentRoute =
        navBackStackEntry?.destination?.route

    val screens = listOf(

        Screen(
            route = "livros",
            title = "Livros",
            icon = R.drawable.baseline_menu_book_24
        ),

        Screen(
            route = "autores",
            title = "Autores",
            icon = R.drawable.baseline_person_24
        )
    )

    val routes = screens.map {
        it.route
    }

    // Barra aparece somente nas telas principais
    if (currentRoute in routes) {

        NavigationBar {

            screens.forEach { screen ->

                NavigationBarItem(

                    icon = {

                        Icon(
                            painter = painterResource(
                                screen.icon
                            ),
                            contentDescription = screen.title
                        )
                    },

                    label = {
                        Text(screen.title)
                    },

                    selected =
                        currentRoute == screen.route,

                    onClick = {

                        navigationController.navigate(
                            screen.route
                        ) {

                            launchSingleTop = true

                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}