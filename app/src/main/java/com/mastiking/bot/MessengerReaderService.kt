package com.mastiking.bot

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class MessengerReaderService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        val pkg = event.packageName?.toString() ?: return
        if (pkg != "com.facebook.orca") return

        val pieces = mutableListOf<String>()
        event.text?.forEach { if (!it.isNullOrBlank()) pieces.add(it.toString()) }
        val source = event.source
        try {
            val desc = source?.contentDescription?.toString()
            if (!desc.isNullOrBlank()) pieces.add(desc)
            val text = source?.text?.toString()
            if (!text.isNullOrBlank()) pieces.add(text)
        } finally {
            source?.recycle()
        }
        val candidate = pieces.joinToString(" ").trim().take(500)
        if (candidate.isNotBlank()) {
            getSharedPreferences("masti", MODE_PRIVATE).edit()
                .putString("last_message", candidate).apply()
        }
    }

    override fun onInterrupt() {}
}
