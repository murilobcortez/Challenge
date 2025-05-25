package com.challenge.shopping.fruits.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.challenge.shopping.app.Route
import com.challenge.shopping.fruits.presentation.fruitsdetail.FruitsDetailAction
import com.challenge.shopping.fruits.presentation.fruitsdetail.FruitsDetailScreenRoot
import com.challenge.shopping.fruits.presentation.fruitsdetail.FruitsDetailViewModel
import com.challenge.shopping.fruits.presentation.fruitslist.FruitsListScreenRoot
import com.challenge.shopping.fruits.presentation.fruitslist.FruitsListViewModel
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinAndroidContext {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.FruitsGraph
        ) {
            navigation<Route.FruitsGraph>(
                startDestination = Route.FruitsList,
            ) {
                composable<Route.FruitsList>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = {
                        slideInHorizontally()
                    }
                ) {
                    val viewModel = koinViewModel<FruitsListViewModel>()
                    val selectedFruitSharedViewModel =
                        it.sharedKoinViewModel<SelectedFruitSharedViewModel>(
                            navController
                        )

                    LaunchedEffect(true) {
                        selectedFruitSharedViewModel.onSelectFruit(null)
                    }

                    FruitsListScreenRoot(
                        viewModel = viewModel,
                        onFruitClick = { fruit ->
                            selectedFruitSharedViewModel.onSelectFruit(fruit)
                            navController.navigate(
                                Route.FruitsDetail(fruit.id)
                            )
                        }
                    )
                }
                composable<Route.FruitsDetail>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    val selectedFruitSharedViewModel =
                        it.sharedKoinViewModel<SelectedFruitSharedViewModel>(navController)
                    val viewModel = koinViewModel<FruitsDetailViewModel>()
                    val selectedFruit by selectedFruitSharedViewModel.selectedFruit.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedFruit){
                        selectedFruit?.let {
                            viewModel.onAction(FruitsDetailAction.OnCartFruitsChange(it))
                        }
                    }

                    FruitsDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private inline fun <reified  T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}
