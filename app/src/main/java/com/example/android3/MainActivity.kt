package com.example.android3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.TextView
import java.lang.ref.WeakReference
import java.util.*

class MainActivity : AppCompatActivity() {

    internal val names = intArrayOf(R.id.view01,R.id.view02,R.id.view03,R.id.view04,R.id.view05,R.id.view06)
    private var views = arrayOfNulls<TextView>(names.size)


    class MyHandler(private val activity:WeakReference<MainActivity>):Handler(){

        private var currentColor = 0

        internal val colors = intArrayOf(R.color.color1,R.color.color2,R.color.color3,R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent)

        override fun handleMessage(msg: Message) {

       
            if (msg.what==0x123)  {
                for ( i in activity.get()?.names?.indices!!){
                    activity.get()?.views
                    activity.get()?.views!![i]?.setBackgroundResource(
                        colors[(i+currentColor) % colors.size]   )

                }
                currentColor++
            }
          super.handleMessage(msg)
        }

    }


    private var handler:Handler =   MyHandler(WeakReference(this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (i in names.indices) {
            views[i] = findViewById(names[i])
        }
        Timer().schedule(object: TimerTask(){

            override fun run() {
                handler.sendEmptyMessage(0x123)
            }
            
        },0,200)
    }
}
