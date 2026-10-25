package com.memowave.timer

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.memowave.timer.ui.MainScreenRoute
import com.memowave.timer.ui.TimerViewModel

sealed class Screen(val route: String) {
    object MainScreen : Screen("main")
}

@Composable
fun NavGraph(navController: NavHostController, timerVM: TimerViewModel) {

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreenRoute(navController = navController, timerVM)
        }
    }
}