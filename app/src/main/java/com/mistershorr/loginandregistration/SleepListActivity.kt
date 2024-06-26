package com.mistershorr.loginandregistration

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.mistershorr.loginandregistration.databinding.ActivitySleepListBinding
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class SleepListActivity : AppCompatActivity() {

    companion object {
        val TAG = "SleepListActivity"
    }

    private lateinit var binding : ActivitySleepListBinding
    private lateinit var adapter: SleepAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadDataFromBackendless()

    }

    fun saveToBackendless() {
        // the real use case will be to read from all the editText
        // fields in the detail activity and then use that info
        // to make the object

        // here, we'll just make up an object
        val sleep = Sleep(
            Date().time, Date().time, Date().time,
            10, "finally a restful night"
        )
        sleep.ownerId = Backendless.UserService.CurrentUser().userId
        // if i do not set the objectId, it will make a new object
        // if I do set the objectId to an existing object Id from data table
        // on backendless, it will update the object.

        // include the async callback to save the object here
    }

    private fun loadDataFromBackendless() {
        val userId = Backendless.UserService.CurrentUser().userId
        // need the ownerId to match the objectId of the user
        val whereClause = "ownerId = '$userId'"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause
        // include the queryBuilder in the find function

        Backendless.Data.of(Sleep::class.java).find(queryBuilder, object: AsyncCallback<List<Sleep>> {
            override fun handleResponse(response: List<Sleep>?) {
                Log.d(TAG, "handleResponse: $response")

                adapter = SleepAdapter(response ?: listOf())
                binding.recyclerViewSleepList.adapter = adapter
                binding.recyclerViewSleepList.layoutManager = LinearLayoutManager(this@SleepListActivity)
            }

            override fun handleFault(fault: BackendlessFault?) {
                Log.d(TAG, "handleFault: ${fault?.message}")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_sleeplist, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            else-> super.onOptionsItemSelected(item)
        }
    }

}