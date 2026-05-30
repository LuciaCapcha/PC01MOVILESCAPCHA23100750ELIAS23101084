package com.example.pc01capcha23100750elias23101084.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pc01capcha23100750elias23101084.screens.*

sealed class Screen(val route: String) {
    object MenuPrincipal : Screen("menu_principal")
    object Calculadora : Screen("calculadora")
    object Planificador : Screen("planificador")
    object Catalogo : Screen("catalogo")
    object Ubicacion : Screen("ubicacion")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.MenuPrincipal.route
    ) {
        composable(Screen.MenuPrincipal.route) {
            MenuPrincipalScreen(
                onNavigateToCalculadora = { navController.navigate(Screen.Calculadora.route) },
                onNavigateToPlanificador = { navController.navigate(Screen.Planificador.route) },
                onNavigateToCatalogo = { navController.navigate(Screen.Catalogo.route) },
                onNavigateToUbicacion = { navController.navigate(Screen.Ubicacion.route) }
            )
        }

        composable(Screen.Calculadora.route) {
            CalculadoraScreen(navController = navController)
        }

        composable(Screen.Planificador.route) {
            PlanificadorScreen(navController = navController)
        }

        composable(Screen.Catalogo.route) {
            CatalogoScreen()
        }
        composable(Screen.Ubicacion.route) {
            UbicacionScreen()
        }
    }
}