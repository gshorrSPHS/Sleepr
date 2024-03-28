package com.mistershorr.loginandregistration

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Parcelize
data class Sleep(
    var wakeTime: LocalDateTime = LocalDateTime.now(),
    var bedTime: LocalDateTime = LocalDateTime.now(),
    var sleepDate: LocalDateTime = LocalDateTime.now(),
    var quality: Int = 5,
    var notes: String? = null,
    var ownerId: String? = null,
    var objectId: String? = null
) : Parcelable

