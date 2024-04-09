package com.mistershorr.loginandregistration

import android.content.Intent
import android.icu.util.Calendar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset.UTC
import java.time.ZoneOffset.ofHours
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class SleepAdapter (var dataSet: List<Sleep>) : RecyclerView.Adapter<SleepAdapter.ViewHolder>() {

    companion object {
        val PDT = 7
        val TAG = "SleepAdapter"
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
        val sleepDate = LocalDateTime.ofEpochSecond(sleep.sleepDateMillis/1000, 0,
            ZoneId.systemDefault().rules.getOffset(Instant.now()))
//        Log.d(TAG, "onBindViewHolder: ${sleepDate.year}/${sleepDate.month}/${sleepDate.dayOfMonth} ${sleepDate.hour}:${sleepDate.minute}")
        holder.textViewDate.text = formatter.format(sleepDate)
        val deltaMillis = sleep.wakeMillis - sleep.bedMillis


        val hours = deltaMillis / (1000 * 60 * 60)
        val minutes = deltaMillis / (1000 * 60) % 60

        holder.textViewDuration.text = String.format("%02d:%02d", hours, minutes)
        val bedTime = LocalDateTime.ofEpochSecond(sleep.bedMillis/1000, 0,
            ZoneId.systemDefault().rules.getOffset(Instant.now()))
        val wakeTime = LocalDateTime.ofEpochSecond(sleep.wakeMillis/1000, 0,
            ZoneId.systemDefault().rules.getOffset(Instant.now()))
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        holder.textViewHours.text = "${timeFormatter.format(bedTime)} - ${timeFormatter.format(wakeTime)}"
        holder.ratingBarQuality.rating = sleep.quality.toFloat()



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