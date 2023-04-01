package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.MotionEvent
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dixitpatel.movieratingdemo.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


fun Activity.playYouTubeVideo(videoKey: String) {
    ContextCompat.startActivity(this,Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoKey")),null)
}

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.parcelableArrayList(key: String): ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableArrayListExtra(key, T::class.java)
    else -> getParcelableArrayListExtra(key)
}

@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelableExtra(key, T::class.java)
    else -> getParcelableExtra(key) as? T
}
@Suppress("DEPRECATION")
inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getParcelable(key, T::class.java)
    else -> getParcelable(key) as? T
}

fun geErrorImageDrawable(context: Context,mediaType: SelectedMediaType?): Drawable {
    val errorImage = AppCompatResources.getDrawable(
        context,
        when (mediaType) {
            SelectedMediaType.MOVIE -> R.drawable.ic_baseline_movie_24
            SelectedMediaType.TV -> R.drawable.ic_baseline_live_tv_24
            else -> R.drawable.ic_baseline_image_24
        }
    )
    return errorImage!!
}


fun RecyclerView.interceptTouch() {
    addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            return if (canScrollHorizontally(RecyclerView.FOCUS_FORWARD)) {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                false
            } else {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(false)
                }
                removeOnItemTouchListener(this)
                true
            }
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
           // no needed
        }
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            // no needed
        }
    })
}

fun isDarkMode(context: Context): Boolean {
    val darkModeFlag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return darkModeFlag == Configuration.UI_MODE_NIGHT_YES
}

fun setTintColor(context: Context, reverse: Boolean = false) = if (reverse) {
    if (isDarkMode(context)) Color.BLACK else Color.WHITE
} else {
    if (isDarkMode(context)) Color.WHITE else Color.BLACK
}

fun Int?.formatTime(context: Context) = this?.let {
    when {
        it == 0 -> return null
        it >= 60 -> {
            val hours = it / 60
            val minutes = it % 60
            "${hours}${context.getString(R.string.hour_short)} ${if (minutes == 0) "" else "$minutes${context.getString(R.string.minute_short)}"}"
        }
        else -> "${it}${context.getString(R.string.minute_short)}"
    }
}

fun Long.thousandsSeparator(context: Context): String {
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = context.getString(R.string.thousand_separator).toCharArray()[0]
    return DecimalFormat("#,###", symbols).format(this)
}

fun Double.round() = (this * 10.0).roundToInt() / 10.0

fun String?.formatDate(): String {
    val outputFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return if (!this.isNullOrEmpty()) inputFormat.parse(this)?.let { outputFormat.format(it) } ?: run { "" } else ""
}