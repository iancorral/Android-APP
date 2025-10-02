package com.jorgeromo.androidClassMp1

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "From: ${remoteMessage.from}")

        // Ahora, creamos y mostramos la notificación desde aquí
        remoteMessage.notification?.let { notification ->
            Log.d("FCM", "Message Notification Title: ${notification.title}")
            Log.d("FCM", "Message Notification Body: ${notification.body}")

            // Construimos la notificación
            val builder = NotificationCompat.Builder(this, "fcm_channel") // Usa el ID del canal que creamos
                .setSmallIcon(R.drawable.ic_launcher_foreground) // ¡IMPORTANTE: Añade un ícono!
                .setContentTitle(notification.title)
                .setContentText(notification.body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            // Mostramos la notificación
            with(NotificationManagerCompat.from(this)) {
                // Verificamos el permiso antes de notificar (buena práctica)
                if (ActivityCompat.checkSelfPermission(
                        this@MyFirebaseMessagingService,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Si no tenemos permiso, no podemos hacer nada.
                    // El permiso ya se debió pedir en la MainActivity.
                    Log.e("FCM", "Notification permission not granted.")
                    return
                }
                // El ID de la notificación debe ser único si quieres mostrar varias
                notify(1, builder.build())
            }
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
    }
}