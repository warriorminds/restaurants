package com.warriorminds.restaurants.utils

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.warriorminds.restaurants.R


fun TextView.manageTextAndVisibilityForView(string: String?) {
    if (string.isNullOrEmpty()) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
        text = string
    }
}

fun Uri.openUri(context: Context) {
    val builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()
    builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
    customTabsIntent.launchUrl(context, this)
}