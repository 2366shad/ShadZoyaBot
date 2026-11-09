package com.mastiking.bot

import android.app.Activity
import android.content.ClipboardManager
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var messageView: TextView
    private lateinit var replyView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (18 * resources.displayMetrics.density).toInt()
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad, pad, pad)
            gravity = Gravity.CENTER_HORIZONTAL
        }
        val title = TextView(this).apply {
            text = "🤖 MASTI KING"
            textSize = 28f
            gravity = Gravity.CENTER
        }
        messageView = TextView(this).apply {
            text = "Messenger par dikhne wala message yahan aayega."
            textSize = 18f
            setPadding(0, pad, 0, pad)
        }
        replyView = TextView(this).apply {
            text = "Funny reply yahan banega 😎"
            textSize = 20f
            setPadding(0, pad, 0, pad)
        }
        layout.addView(title)
        layout.addView(messageView)
        layout.addView(replyView)
        layout.addView(makeButton("Floating Masti Button chalu karo") { enableOverlay() })
        layout.addView(makeButton("Accessibility permission kholo") {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        })
        layout.addView(makeButton("Funny reply banao") { refreshReply() })
        layout.addView(makeButton("Reply copy karo") {
            val clip = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clip.setPrimaryClip(ClipData.newPlainText("Masti reply", replyView.text))
        })
        layout.addView(makeButton("Messenger kholo") {
            val launch = packageManager.getLaunchIntentForPackage("com.facebook.orca")
            if (launch != null) startActivity(launch)
            else messageView.text = "Messenger app nahi mili. Pehle Messenger install karo."
        })
        setContentView(layout)
        refreshReply()
    }

    private fun makeButton(label: String, action: () -> Unit): Button =
        Button(this).apply { text = label; setOnClickListener { action() } }

    private fun enableOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")))
        } else {
            startService(Intent(this, FloatingService::class.java))
        }
    }

    private fun refreshReply() {
        val prefs = getSharedPreferences("masti", MODE_PRIVATE)
        val msg = prefs.getString("last_message", "").orEmpty()
        messageView.text = if (msg.isBlank()) "Abhi koi message capture nahi hua." else "Message: $msg"
        replyView.text = ReplyMaker.reply(msg)
    }

    override fun onResume() {
        super.onResume()
        if (::messageView.isInitialized) refreshReply()
    }
}
