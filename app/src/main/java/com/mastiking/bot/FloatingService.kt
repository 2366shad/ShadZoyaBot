package com.mastiking.bot

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.provider.Settings
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView

class FloatingService : Service() {
    private var windowManager: WindowManager? = null
    private var bubble: TextView? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!Settings.canDrawOverlays(this) || bubble != null) return START_NOT_STICKY
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        bubble = TextView(this).apply {
            text = "😂 Masti"
            textSize = 16f
            setPadding(22, 14, 22, 14)
            setBackgroundColor(0xFF6A1B9A.toInt())
            setTextColor(0xFFFFFFFF.toInt())
            setOnClickListener {
                val open = Intent(this@FloatingService, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(open)
            }
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply { gravity = Gravity.END or Gravity.CENTER_VERTICAL; x = 12; y = 0 }
        windowManager?.addView(bubble, params)
        return START_STICKY
    }

    override fun onDestroy() {
        bubble?.let { windowManager?.removeView(it) }
        bubble = null
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): IBinder? = null
}
