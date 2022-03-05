package com.codemave.reminderapp.ui

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codemave.reminderapp.ui.addReminder.AddReminder
import com.codemave.reminderapp.ui.addReminder.EditReminder
import com.codemave.reminderapp.ui.home.Home
import com.codemave.reminderapp.ui.login.Login
import com.codemave.reminderapp.ui.register.Register
import com.codemave.reminderapp.ui.profil.Profil

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReminderApp(
    sharedPreferences: SharedPreferences,
    appState: ReminderAppState = rememberReminderAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            Login(navController = appState.navController, sharedPreferences)
        }
        composable(route = "register") {
            Register(navController = appState.navController, sharedPreferences, onBackPress = appState::navigateBack)
        }
        composable(route = "home/{userID}") {
            route->Home(navController = appState.navController,(route.arguments?.getString("userID")?:"").toInt() )
        }
        composable(route = "addReminder/{userID}") {
            route->AddReminder(onBackPress = appState::navigateBack, (route.arguments?.getString("userID")?:"").toInt() )
        }
        composable(route = "profil/{userID}") {
            route->Profil(navController = appState.navController, sharedPreferences, onBackPress = appState::navigateBack, (route.arguments?.getString("userID")?:"").toInt() )
        }
        composable(route = "editReminder/{reminderID}") {
            route->EditReminder(onBackPress = appState::navigateBack, (route.arguments?.getString("reminderID")?:"").toLong() )
        }
    }
}