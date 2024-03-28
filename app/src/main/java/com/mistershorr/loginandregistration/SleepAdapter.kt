package com.mistershorr.loginandregistration

import android.content.Intent
import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.time.ZoneOffset.ofHours
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class SleepAdapter (var dataSet: List<Sleep>) : RecyclerView.Adapter<SleepAdapter.ViewHolder>() {

    companion object {
        val PDT = 7
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewDate : TextView
        val textViewHours: TextView
        val textViewDuration: TextView
        val layout : ConstraintLayout
        val ratingBarQuality : RatingBar
        init {
            textViewDate = view.findViewById(R.id.textView_itemSleep_date)
            textViewDuration = view.findViewById(R.id.textView_itemSleep_duration)
            textViewHours = view.findViewById(R.id.textView_itemSleep_hours)
            layout = view.findViewById(R.id.layout_itemSleep)
            ratingBarQuality = view.findViewById(R.id.ratingBar_itemSleep_sleepQuality)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sleep, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: SleepAdapter.ViewHolder, position: Int) {
        val sleep = dataSet[position]
        val context = holder.layout.context


        val formatter = DateTimeFormatter.ofPattern("yyy-MM-dd")
//        holder.textViewDate.text = formatter.format(LocalDateTime.ofEpochSecond(sleep.sleepDate.time, 0, ofHours(PDT)))
        holder.textViewDate.text = formatter.format(sleep.sleepDate)
//        val sleepMillis = sleep.wakeTime.time - sleep.bedTime.time
        var minutes = ChronoUnit.MINUTES.between(sleep.bedTime, sleep.wakeTime);
        val hours = minutes / 60
        minutes = minutes % 60
//        val hours = sleepMillis / 1000 / 60 / 60
//        val minutes = sleepMillis / 1000 / 60 % 60
        holder.textViewDuration.text = String.format("2%d:2%d", hours.toInt(), minutes)
//        val bedTime = LocalDateTime.ofInstant(sleep.bedTime.toInstant(), ofHours(PDT))
//        val wakeTime = LocalDateTime.ofInstant(sleep.wakeTime.toInstant(), ofHours(PDT))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        holder.textViewHours.text = "${timeFormatter.format(sleep.bedTime)} - ${timeFormatter.format(sleep.wakeTime)}"
        holder.ratingBarQuality.rating = sleep.quality.toFloat()

        // requires desugaring
        // https://developer.android.com/studio/write/java8-support#library-desugaring
//        val formatter = DateTimeFormatter.ofPattern("MMM dd, YYYY, hh:mm:ss a")
//        val time = earthquake.properties.time
//        val utcTime = LocalDateTime.ofInstant(
//            Instant.ofEpochMilli(time),
//            TimeZone.getTimeZone("UTC").toZoneId())
//        holder.timeTextView.text = formatter.format(utcTime)
//        val magnitude = earthquake.properties.mag

        holder.layout.setOnClickListener {
            val hero = dataSet[position]
            val intent = Intent(context, SleepDetailActivity::class.java).apply {
                putExtra(SleepDetailActivity.EXTRA_SLEEP, dataSet[position])
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}