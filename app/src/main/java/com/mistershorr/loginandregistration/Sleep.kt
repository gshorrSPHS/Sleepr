package com.mistershorr.loginandregistration

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Parcelize
data class Sleep(
    var wakeTime: Date = Date(),
    var bedTime: Date = Date(),
    var sleepDate: Date = Date(),
    var wakeMillis: Long = Date().time,
    var bedMillis: Long = Date().time,
    var sleepDateMillis: Long = Date().time,
    var quality: Int = 5,
    var notes: String? = null,
    var ownerId: String? = null,
    var objectId: String? = null
) : Parcelable

