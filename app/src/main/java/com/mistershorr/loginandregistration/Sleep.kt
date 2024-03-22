package com.mistershorr.loginandregistration

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Sleep(
    var wakeTime: Date = Date(),
    var bedTime: Date = Date(),
    var sleepDate: Date = Date(),
    var quality: Int = 5,
    var notes: String? = null
) : Parcelable

