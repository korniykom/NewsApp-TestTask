package com.korniykom.newsapp.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun String.toRelativeTimeText(): String {
    return try {
        val publishedInstant = Instant.parse(this)
        val now = Instant.now()
        val minutes = ChronoUnit.MINUTES.between(publishedInstant, now)
        val hours = ChronoUnit.HOURS.between(publishedInstant, now)
        val days = ChronoUnit.DAYS.between(publishedInstant, now)

        when {
            minutes < 1 -> "Just now"
            minutes < 60 -> "${minutes}m ago"
            hours < 24 -> "${hours}h ago"
            days < 7 -> "${days}d ago"
            else -> {
                val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
                    .withZone(ZoneId.systemDefault())
                formatter.format(publishedInstant)
            }
        }
    } catch (e: Exception) {
        this
    }
}