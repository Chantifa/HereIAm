package ch.ffhs.esa.hereiam.util

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.hide() {
    this.visibility = View.GONE
}

fun ProgressBar.show() {
    this.alpha = 0f
    this.visibility = View.VISIBLE
    this.animate().setDuration(200).alpha(0.4f)
}