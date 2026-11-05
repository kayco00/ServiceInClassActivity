package edu.temple.basictimer

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val broadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
        }

    }
    val displayTextView : TextView by lazy {
        findViewById<TextView>(R.id.textView)
    }
    var timerBinder : TimerService.TimerBinder? = null
    val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            timerBinder = service as TimerService.TimerBinder
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            timerBinder = null
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(
            Intent(this, TimerService::class.java),
            serviceConnection,
            BIND_AUTO_CREATE
        )

        findViewById<Button>(R.id.startButton).setOnClickListener {
          timerBinder?.start(100)
            if(timerBinder?.isRunning == true){

            }
        }
        
        findViewById<Button>(R.id.stopButton).setOnClickListener {
        timerBinder?.stop()
        }
    }

    override fun onDestroy(){
        super.onDestroy()
        unbindService(serviceConnection)
    }
}