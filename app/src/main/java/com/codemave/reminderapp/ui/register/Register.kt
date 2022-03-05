package com.codemave.reminderapp.ui.register

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun Register(
    navController: NavController,
    sharedPreferences: SharedPreferences,
    onBackPress: () -> Unit
) {
    val usernameInput = rememberSaveable { mutableStateOf("") }
    val passwordInput = rememberSaveable { mutableStateOf("") }
    var userID: Int
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
                Text(text = "Register")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = usernameInput.value,
                    onValueChange = { input -> usernameInput.value = input },
                    label = { Text(text = "Username")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = passwordInput.value,
                    onValueChange = { input -> passwordInput.value = input },
                    label = { Text(text = "Password")},
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {userID = saveUser(sharedPreferences, usernameInput, passwordInput)
                        navController.navigate(route = "home/"+userID)},
                    modifier = Modifier.fillMaxWidth().size(55.dp)
                ) {
                    Text("Add new user")
                }
            }
        }
    }
}

fun saveUser(
    sharedPreferences: SharedPreferences,
    username: MutableState<String>,
    password: MutableState<String>
): Int{
    val newUsername: String = username.value
    val newPassword: String = password.value
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    var userID: Int = 0

    while(sharedPreferences.getString(userID.toString(),"") != ""){
        userID += 1
    }
    editor.putString(userID.toString(), newUsername)
    editor.putString(userID.toString()+"p", newPassword)
    editor.commit()
    return userID

}
