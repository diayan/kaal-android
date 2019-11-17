package com.diayan.kaal.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent

object IntentUtil {

    fun start(context: Context?, cls: Class<out Any>) {
        val intent = Intent(context, cls)
        context?.startActivity(intent)
    }

    tailrec fun Context.activity(): Activity? = when {
        this is Activity -> this
        else -> (this as? ContextWrapper)?.baseContext?.activity()
    }
}