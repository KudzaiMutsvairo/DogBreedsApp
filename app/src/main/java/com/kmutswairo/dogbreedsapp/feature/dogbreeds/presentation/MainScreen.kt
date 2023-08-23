package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.navigation.Navigation
import com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.navigation.Screen
import com.kmutswairo.dogbreedsapp.ui.theme.DogBreedsAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    DogBreedsAppTheme {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomBar(navController = navController)
            },
        ) { paddingValues ->
            Navigation(
                navController = navController,
                paddingValues,
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        Screen.DogBreedsList,
        Screen.FavouriteDogBreeds,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val excludedRoute = Screen.ViewDogBreed.route + "/{name}"

    if (currentRoute == null || currentRoute == excludedRoute) {
        return
    }

    BottomAppBar {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentRoute = currentRoute,
                navController = navController,
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Screen,
    currentRoute: String?,
    navController: NavHostController,
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            screen.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = "${screen.title} Icon",
                )
            }
        },
        selected = currentRoute == screen.route,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
    )
}
