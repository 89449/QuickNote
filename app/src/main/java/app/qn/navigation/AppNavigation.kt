package app.qn.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

import app.qn.ui.Home
import app.qn.ui.Detail
import app.qn.viewmodel.NoteViewModel

@Composable
fun AppNavigation(viewModel: NoteViewModel) {
	val navController = rememberNavController()

	NavHost(
		startDestination = "home",
		navController = navController,
		enterTransition = { EnterTransition.None },
    	exitTransition = { ExitTransition.None }
	) {
		composable(
			route = "home"
		) {
			Home(
				viewModel = viewModel,
				onTitleClick = { noteId ->
					navController.navigate("detail/$noteId")
				},
				onFabClick = {
					navController.navigate("detail/0")
				}
			)
		}
		composable(
			route = "detail/{noteId}",
			arguments = listOf( navArgument("noteId") { type = NavType.IntType })
		) {
			val noteId = it.arguments?.getInt("noteId") ?: 0
			Detail(
				viewModel = viewModel,
				noteId = noteId,
				onBack = {
					navController.popBackStack()
				}
			)
		}
	}
}
