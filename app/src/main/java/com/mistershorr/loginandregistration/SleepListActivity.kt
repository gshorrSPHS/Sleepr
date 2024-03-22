package com.mistershorr.loginandregistration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.mistershorr.loginandregistration.databinding.ActivitySleepDetailBinding
import com.mistershorr.loginandregistration.databinding.ActivitySleepListBinding

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

    private fun loadDataFromBackendless() {
        Backendless.Data.of(Sleep::class.java).find(object: AsyncCallback<List<Sleep>> {
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