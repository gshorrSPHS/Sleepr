package com.mistershorr.loginandregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.mistershorr.loginandregistration.databinding.ActivitySleepDetailBinding
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class SleepDetailActivity : AppCompatActivity() {

    companion object {
        val TAG = "SleepDetailActivity"
        val EXTRA_SLEEP = "sleepytime"
    }

    private lateinit var binding: ActivitySleepDetailBinding
    lateinit var bedTime: LocalDateTime
    lateinit var wakeTime: LocalDateTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bedTime = LocalDateTime.now()
        val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
        binding.buttonSleepDetailBedTime.text = timeFormatter.format(bedTime)
        wakeTime = bedTime.plusHours(8)
        binding.buttonSleepDetailWakeTime.text = timeFormatter.format(wakeTime)
        val dateFormatter = DateTimeFormatter.ofPattern("EEEE MMM dd, yyyy")
        binding.buttonSleepDetailDate.text = dateFormatter.format(bedTime)

        binding.buttonSleepDetailBedTime.setOnClickListener {
           setTime(bedTime, timeFormatter, binding.buttonSleepDetailBedTime)
        }

        binding.buttonSleepDetailWakeTime.setOnClickListener {
            setTime(wakeTime, timeFormatter, binding.buttonSleepDetailWakeTime)
        }

        binding.buttonSleepDetailDate.setOnClickListener {
            var selection = bedTime.toEpochSecond(ZoneOffset.UTC)
            var selected = LocalDateTime.ofEpochSecond(selection, 0, ZoneOffset.UTC)
            Log.d(TAG, "onCreate: Selection: ${dateFormatter.format(selected)}")
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("Select a Date")
                .build()

            Log.d(TAG, "onCreate: after build: ${LocalDateTime.ofEpochSecond(datePicker.selection?: 0L, 0, ZoneOffset.UTC)}")
            datePicker.addOnPositiveButtonClickListener { millis ->
                val selectedLocalDate = Instant.ofEpochMilli(millis).atOffset(ZoneOffset.UTC).toLocalDateTime()
                Toast.makeText(this, "Date is: ${dateFormatter.format(selectedLocalDate)}", Toast.LENGTH_SHORT).show()
                bedTime = LocalDateTime.of(selectedLocalDate.year, selectedLocalDate.month, selectedLocalDate.dayOfMonth, bedTime.hour, bedTime.minute)
                wakeTime = LocalDateTime.of(selectedLocalDate.year, selectedLocalDate.month, selectedLocalDate.dayOfMonth, wakeTime.hour, wakeTime.minute)
                binding.buttonSleepDetailDate.text = dateFormatter.format(bedTime)
                Log.d(TAG, "onCreate: $bedTime")
            }


            datePicker.show(supportFragmentManager, "datepicker")
        }

    }

    fun setTime(time: LocalDateTime, timeFormatter: DateTimeFormatter, button: Button) {
        val timePickerDialog = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(time.hour)
            .setMinute(time.minute)
            .build()

        timePickerDialog.show(supportFragmentManager, "bedtime")
        timePickerDialog.addOnPositiveButtonClickListener {
            val selectedTime = LocalDateTime.of(time.year, time.month, time.dayOfMonth, timePickerDialog.hour, timePickerDialog.minute)
            val newDateTime = time.with(selectedTime)
            Toast.makeText(this, "Time is: ${timeFormatter.format(newDateTime)}", Toast.LENGTH_SHORT).show()
            button.text = timeFormatter.format(selectedTime)
            when(button.id) {
                binding.buttonSleepDetailBedTime.id -> {
                    bedTime = selectedTime
                    Log.d(TAG, "setTime: $bedTime")
                }
                binding.buttonSleepDetailWakeTime.id -> wakeTime = selectedTime
            }
        }
    }
}