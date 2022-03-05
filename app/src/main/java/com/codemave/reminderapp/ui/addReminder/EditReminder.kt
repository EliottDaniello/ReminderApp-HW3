package com.codemave.reminderapp.ui.addReminder

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_EDIT
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codemave.reminderapp.Data.Entity.Reminder
import com.codemave.reminderapp.util.viewModelProviderFactoryOf
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditReminder(
    onBackPress: () -> Unit,
    reminderId: Long
) {
    val viewModel: EditReminderViewModel = viewModel(
        key = "reminder_id_" + reminderId.toString(),
        factory = viewModelProviderFactoryOf { EditReminderViewModel(reminderId) }
    )
    val coroutineScope = rememberCoroutineScope()
    var thisReminder: Reminder?

    val name = rememberSaveable { mutableStateOf("") }
    val location_x = rememberSaveable { mutableStateOf("") }
    val location_y = rememberSaveable { mutableStateOf("") }
    var reminder_time: Date
    var rt: String
    val day = rememberSaveable { mutableStateOf("") }
    val month = rememberSaveable { mutableStateOf("") }
    val year = rememberSaveable { mutableStateOf("") }
    val hour = rememberSaveable { mutableStateOf("") }
    val minute = rememberSaveable { mutableStateOf("") }
    val creation_time = rememberSaveable { mutableStateOf("") }
    val creator_id = rememberSaveable { mutableStateOf("") }
    val reminder_seen = rememberSaveable { mutableStateOf("") }
    val iconSelected = rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    coroutineScope.launch {
        thisReminder = viewModel.getReminder(reminderId)
        name.value = thisReminder?.message.toString()
        iconSelected.value = thisReminder?.icon.toString()
        location_x.value = thisReminder?.location_x.toString()
        location_y.value = thisReminder?.location_y.toString()
        reminder_time = SimpleDateFormat("dd MM yyyy 'at' HH:mm").parse(thisReminder?.reminder_time.toString())
        rt = thisReminder?.reminder_time.toString()
        day.value = (rt[0].toString()+rt[1].toString()).toInt().toString()
        month.value = (reminder_time.month+1).toString()
        year.value = (reminder_time.year+1900).toString()
        hour.value = reminder_time.hours.toString()
        minute.value = reminder_time.minutes.toString()
        creation_time.value = thisReminder?.creation_time.toString()
        creator_id.value = thisReminder?.creator_id.toString()
        reminder_seen.value = thisReminder?.reminder_seen.toString()
    }

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
                Text(text = "Edit a reminder")
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
                    Spacer(modifier = Modifier.width(10.dp))
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
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(30.dp))
                Text("Creation date : "+creation_time.value)
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
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        if(year.value != "" && month.value != "" && day.value != "" && hour.value != "" && minute.value != ""){
                            coroutineScope.launch {
                                viewModel.editReminder(
                                    Reminder(
                                        reminderId = reminderId,
                                        icon = iconSelected.value.toInt(),
                                        message = name.value,
                                        location_x = location_x.value,
                                        location_y = location_y.value,
                                        reminder_time = Date(year.value.toInt()-1900, month.value.toInt()-1, day.value.toInt(), hour.value.toInt(), minute.value.toInt()).formatToString(),
                                        creation_time = creation_time.value,
                                        creator_id = creator_id.value.toInt(),
                                        reminder_seen = reminder_seen.value.toLong()
                                    )
                                )
                            }
                            onBackPress()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Save reminder")
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        coroutineScope.launch { viewModel.deleteReminder(reminderId) }
                        onBackPress()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Delete reminder")
                }
                Spacer(modifier = Modifier.height(60.dp))
                Button(
                    onClick = {
                        addCalendarEvent(context,name.value,Date(year.value.toInt()-1900, month.value.toInt()-1, day.value.toInt(), hour.value.toInt(), minute.value.toInt()))},
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Add to calendar")
                }
            }
        }
    }
}

private fun Date.formatToString(): String {
    return SimpleDateFormat("dd MM yyyy 'at' HH:mm", Locale.ENGLISH).format(this)
}

private fun addCalendarEvent(context: Context, title: String, reminderTime: Date) {
    val calendarEvent: Calendar = Calendar.getInstance()
    val intent = Intent(ACTION_EDIT)
    calendarEvent.setTime(reminderTime)
    intent.type = "vnd.android.cursor.item/event"
    intent.putExtra("beginTime", calendarEvent.timeInMillis)
    intent.putExtra("endTime", calendarEvent.timeInMillis + 3600000)
    intent.putExtra("title", title)
    context.startActivity(intent)
}
