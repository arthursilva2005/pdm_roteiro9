package com.example.livraria0110

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import java.util.UUID

@Composable
fun Navigation(
    navigationController: NavHostController,
    modifier: Modifier = Modifier
) {

    val livrosViewModel =
        viewModel<LivrosViewModel>()

    val autoresViewModel =
        viewModel<AutoresViewModel>()

    NavHost(
        navController = navigationController,
        startDestination = "livros",
        modifier = modifier
    ) {

        // LISTA DE LIVROS
        composable(
            route = "livros"
        ) {

            LivroList(
                livrosViewModel = livrosViewModel,

                onNavigateToForm = { id ->

                    navigationController.navigate(
                        "livroForm?livroId=$id"
                    )
                }
            )
        }

        // FORMULÁRIO DE LIVRO
        composable(
            route = "livroForm?livroId={livroId}",

            arguments = listOf(

                navArgument("livroId") {

                    type = NavType.StringType

                    nullable = true

                    defaultValue = null
                }
            )
        ) { backStackEntry ->

            val livroId =
                backStackEntry.arguments
                    ?.getString("livroId")

            LivroForm(
                id = livroId?.let {
                    UUID.fromString(it)
                },

                livrosViewModel =
                    livrosViewModel,

                autoresViewModel =
                    autoresViewModel,

                onBack = {
                    navigationController.popBackStack()
                }
            )
        }

        // LISTA DE AUTORES
        composable(
            route = "autores"
        ) {

            AutorList(
                autoresViewModel = autoresViewModel,

                onNavigateToForm = { id ->

                    navigationController.navigate(
                        "autorForm?autorId=$id"
                    )
                }
            )
        }

        // FORMULÁRIO DE AUTOR
        composable(
            route = "autorForm?autorId={autorId}",

            arguments = listOf(

                navArgument("autorId") {

                    type = NavType.StringType

                    nullable = true

                    defaultValue = null
                }
            )
        ) { backStackEntry ->

            val autorId =
                backStackEntry.arguments
                    ?.getString("autorId")

            AutorForm(
                id = autorId?.let {
                    UUID.fromString(it)
                },

                autoresViewModel =
                    autoresViewModel,

                onBack = {
                    navigationController.popBackStack()
                }
            )
        }
    }
}