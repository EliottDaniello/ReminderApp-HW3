package com.codemave.reminderapp.ui.addReminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.codemave.reminderapp.Data.Entity.Reminder
import com.codemave.reminderapp.Data.Repository.ReminderRepository
import com.codemave.reminderapp.Graph
import com.codemave.reminderapp.Graph.reminderRepository
import com.codemave.reminderapp.R
import com.codemave.reminderapp.ui.MainActivity
import com.codemave.reminderapp.util.NotificationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AddReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
): ViewModel() {
    private val _state = MutableStateFlow(ReminderViewState())

    suspend fun saveReminder(reminder: Reminder, notif: Boolean, multinotif: Boolean, timemultinotif: String){
        val id = reminderRepository.addReminder(reminder)
        NewNotif(id, reminder, notif, Graph.appContext)
        if(multinotif){
            if(timemultinotif != "")
                earlierNotif(id, reminder, notif, Graph.appContext, timemultinotif.toLong())
        }
    }

    init {
        createNotificationChannel(context = Graph.appContext)
        viewModelScope.launch {
            reminderRepository.reminders().collect { reminders ->
                _state.value = ReminderViewState(reminders)
            }
        }
    }
}

private fun createNotificationChannel(context: Context) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "NotificationChannelName"
        val descriptionText = "NotificationChannelDescriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        // register the channel with the system
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

private fun NewNotif(id: Long, reminder: Reminder, notif: Boolean, context: Context) {
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val remindercalendar: Calendar = Calendar.getInstance()
    val reminderdate: Date = SimpleDateFormat("dd MM yyyy 'at' HH:mm").parse(reminder.reminder_time)
    remindercalendar.setTime(reminderdate)
    var timer: Long
    var notifAllow = notif

    if((remindercalendar.timeInMillis - System.currentTimeMillis())>0){
        timer = remindercalendar.timeInMillis - System.currentTimeMillis()
    }
    else{
        timer = 0
        notifAllow = false
    }

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(timer, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .build()

    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                if(notifAllow) {
                    createReminderNotification(id, reminder, context)
                }
                CoroutineScope(Dispatchers.Default).launch {
                    reminderRepository.setSeen(id)
                }
            }
        }
}

private fun createReminderNotification(id: Long, reminder: Reminder, context: Context) {
    val notificationId: Int = id.toInt()
    val contentIntent = Intent(Graph.appContext, MainActivity()::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        context.applicationContext,
        notificationId,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("New Reminder !")
        .setContentText("${reminder.message} the ${reminder.reminder_time}")
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }
}

private fun earlierNotif(id: Long, reminder: Reminder, notif: Boolean, context: Context, timemultinotif: Long) {
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val remindercalendar: Calendar = Calendar.getInstance()
    val reminderdate: Date = SimpleDateFormat("dd MM yyyy 'at' HH:mm").parse(reminder.reminder_time)
    remindercalendar.setTime(reminderdate)
    var timer: Long
    var notifAllow = notif

    if((remindercalendar.timeInMillis - System.currentTimeMillis() - timemultinotif*60000)>0){
        timer = remindercalendar.timeInMillis - System.currentTimeMillis() - timemultinotif*60000
    }
    else{
        timer = 0
        notifAllow = false
    }

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(timer, TimeUnit.MILLISECONDS)
        .setConstraints(constraints)
        .build()

    workManager.enqueue(notificationWorker)

    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever { workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                if(notifAllow) {
                    createEarlierReminderNotification(id, reminder, context, timemultinotif)
                }
            }
        }
}

private fun createEarlierReminderNotification(id: Long, reminder: Reminder, context: Context, timemultinotif: Long) {
    val notificationId: Int = id.toInt()
    val contentIntent = Intent(Graph.appContext, MainActivity()::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        context.applicationContext,
        notificationId,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    val builder = NotificationCompat.Builder(Graph.appContext, "CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentTitle("Reminder in ${timemultinotif} min !")
        .setContentText("${reminder.message} the ${reminder.reminder_time}")
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    with(NotificationManagerCompat.from(Graph.appContext)) {
        notify(notificationId, builder.build())
    }
}

data class ReminderViewState(
    val reminders: List<Reminder> = emptyList()
)