package ch.ffhs.esa.hereiam.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.appcompat.app.AlertDialog
import ch.ffhs.esa.hereiam.MainActivity
import ch.ffhs.esa.hereiam.R

class Connection(private val activity: MainActivity) : ConnectivityManager.NetworkCallback() {
    private val connectivityManager =
        activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, this)
        if (!isConnected()) {
            showHint()
        }
    }

    override fun onLost(network: Network) {
        showHint()
    }

    private fun showHint() {
        val dialog = AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.missing_connection_title))
            .setMessage(activity.getString(R.string.missing_connection_body_text))
            .setCancelable(false)
            .setPositiveButton(activity.getString(R.string.ok), null)
            .show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (isConnected()) {
                dialog.dismiss()
            }
        }
    }

    private fun isConnected() =
        connectivityManager.allNetworks.any {
            connectivityManager.getNetworkCapabilities(it)
                ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        }
}