package ch.ffhs.esa.hereiam

import android.Manifest.permission.ACCESS_NETWORK_STATE
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ch.ffhs.esa.hereiam.services.ApiService
import ch.ffhs.esa.hereiam.services.AuthenticationService
import ch.ffhs.esa.hereiam.util.RecyclerAdapter
import ch.ffhs.esa.hereiam.util.logout
import kotlinx.android.synthetic.main.activity_connection.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber

class Connection : AppCompatActivity() {

        private val arrayList = ArrayList<String>()
        private var adapter = RecyclerAdapter()
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.reddit.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())}
            setContentView(R.layout.activity_connection)
            setupRecyclerView()
        }

        override fun onStart() {
            super.onStart()
            registerReceiver(
                broadcastReceiver,
                IntentFilter(ACCESS_NETWORK_STATE)
            )
        }

        override fun onStop() {
            super.onStop()
            unregisterReceiver(broadcastReceiver)
        }

        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            super.onCreateOptionsMenu(menu)
            menuInflater.inflate(R.menu.option_menu, menu)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem?): Boolean {
            item?.let {
                super.onOptionsItemSelected(item)
            }
            if (item?.itemId == R.id.action_logout) {

                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.logout_question))
                    setPositiveButton(getString(R.string.logout_yes)) { _, _ ->

                        AuthenticationService.signOut()
                        this.context.logout()

                    }
                    setNegativeButton(getString(R.string.logout_abort)) { _, _ ->
                    }
                }.create().show()

            }
            return true
        }

        private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val notConnected = intent.getBooleanExtra(
                    ConnectivityManager
                    .EXTRA_NO_CONNECTIVITY, false)
                if (notConnected) {
                    disconnected()
                } else {
                    connected()
                }
            }
        }

        private fun setupRecyclerView() {
            with(recyclerView){
                val layoutManager = LinearLayoutManager(this@Connection)
                adapter = this@Connection.adapter
            }
        }

        private fun disconnected() {
            recyclerView.visibility = View.INVISIBLE
            noSignal.visibility = View.VISIBLE
        }

        private fun connected() {
            recyclerView.visibility = View.VISIBLE
            noSignal.visibility = View.INVISIBLE
            fetchFeeds()
        }

        private fun fetchFeeds() {
            retrofit.create(ApiService::class.java)
                .getFeeds()
                .enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e("MainActivityTag", t.message)
                    }

                    override fun onResponse(call: Call<String>?, response: Response<String>) {
                        addTitleToList(response.body()!!)
                    }

                })
        }

        private fun addTitleToList(response: String) {
            val jsonObject = JSONObject(response).getJSONObject("data")
            val children = jsonObject.getJSONArray("children")

            for (i in 0..(children.length()-1)) {
                val item = children.getJSONObject(i).getJSONObject("data").getString("title")
                arrayList.add(item)
                adapter.setItems(arrayList)
            }
        }
}