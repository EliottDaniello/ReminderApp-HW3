package com.codemave.reminderapp.ui.addReminder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codemave.reminderapp.Data.Entity.Reminder
import java.util.*
import java.text.SimpleDateFormat

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddReminder(
    onBackPress: () -> Unit,
    userID: Int
    ) {
    val viewModelAdd: AddReminderViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    val name = rememberSaveable { mutableStateOf("") }
    val day = rememberSaveable { mutableStateOf("") }
    val month = rememberSaveable { mutableStateOf("") }
    val year = rememberSaveable { mutableStateOf("") }
    val hour = rememberSaveable { mutableStateOf("") }
    val minute = rememberSaveable { mutableStateOf("") }
    val iconSelected = rememberSaveable { mutableStateOf("1") }
    val notif = remember { mutableStateOf(true)}
    val mulitNotif = remember { mutableStateOf(false)}
    val timemultinotif = rememberSaveable { mutableStateOf("") }
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
                Text(text = "Create a new reminder")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = {name.value = it},
                    label = { Text(text = "Reminder name")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row{
                    OutlinedTextField(
                        value = day.value,
                        onValueChange = {day.value = it},
                        label = { Text(text = "Day")},
                        modifier = Modifier.size(80.dp,65.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = month.value,
                        onValueChange = {month.value = it},
                        label = { Text(text = "Month")},
                        modifier = Modifier.size(80.dp,65.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = year.value,
                        onValueChange = {year.value = it},
                        label = { Text(text = "Year")},
                        modifier = Modifier.size(80.dp,65.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row{
                    OutlinedTextField(
                        value = hour.value,
                        onValueChange = {hour.value = it},
                        label = { Text(text = "Hour")},
                        modifier = Modifier.size(80.dp,65.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedTextField(
                        value = minute.value,
                        onValueChange = {minute.value = it},
                        label = { Text(text = "Minute")},
                        modifier = Modifier.size(80.dp,65.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row{
                    Button(
                        onClick = { iconSelected.value = "1" },
                        modifier = Modifier.size(65.dp,65.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessAlarm,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = { iconSelected.value = "2" },
                        modifier = Modifier.size(65.dp,65.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBalance,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = { iconSelected.value = "3" },
                        modifier = Modifier.size(65.dp,65.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountTree,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = { iconSelected.value = "4" },
                        modifier = Modifier.size(65.dp,65.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = null
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = { iconSelected.value = "5" },
                        modifier = Modifier.size(65.dp,65.dp),

                    ) {
                        Icon(
                            imageVector = Icons.Default.Assistant,
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row{
                    Text("Icon selected : ")
                    when (iconSelected.value){
                        "1" ->  Icon(
                                    imageVector = Icons.Default.AccessAlarm,
                                    contentDescription = null
                                )
                        "2" ->  Icon(
                                    imageVector = Icons.Default.AccountBalance,
                                    contentDescription = null
                                )
                        "3" ->  Icon(
                                    imageVector = Icons.Default.AccountTree,
                                    contentDescription = null
                                )
                        "4" ->  Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = null
                                )
                        "5" ->  Icon(
                                    imageVector = Icons.Default.Assistant,
                                    contentDescription = null
                                )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Text(
                        text = "Notification"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(
                        checked = notif.value,
                        onCheckedChange = { notif.value = it }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Text(
                        text = "Multiple Notification"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Checkbox(
                        checked = mulitNotif.value,
                        onCheckedChange = { mulitNotif.value = it }
                    )
                }
                if(mulitNotif.value) {
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = timemultinotif.value,
                        onValueChange = {timemultinotif.value = it},
                        label = { Text(text = "Time before the main notification (in min)")},
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        if(year.value != "" && month.value != "" && day.value != "" && hour.value != "" && minute.value != ""){
                            coroutineScope.launch {
                                    viewModelAdd.saveReminder(
                                        Reminder(
                                            message = name.value,
                                            icon = iconSelected.value.toInt(),
                                            location_x = "",
                                            location_y = "",
                                            reminder_time = Date(year.value.toInt()-1900, month.value.toInt()-1, day.value.toInt(), hour.value.toInt(), minute.value.toInt()).formatToString(),
                                            creation_time = Date().formatToString(),
                                            creator_id = userID,
                                            reminder_seen = 0
                                        ),
                                        notif = notif.value,
                                        multinotif = mulitNotif.value,
                                        timemultinotif = timemultinotif.value
                                    )
                            }
                            onBackPress()
                        }
                    },
                    modifier = Modifier.fillMaxWidth().size(55.dp)
                ) {
                    Text("Add reminder")
                }
            }
        }
    }
}

private fun Date.formatToString(): String {
    return SimpleDateFormat("dd MM yyyy 'at' HH:mm", Locale.ENGLISH).format(this)
}