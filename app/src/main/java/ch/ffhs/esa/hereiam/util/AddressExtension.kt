package ch.ffhs.esa.hereiam.util

import android.location.Address
import timber.log.Timber

fun Address?.getShortAddress(): String? {
    Timber.e("$this")
    if (this == null) return null

    var string = ""
    this.thoroughfare?.let { street ->
        string += street
        this.featureName?.let { streetNum ->
            string += " $streetNum"
        }
        string += ", "
    }
    string += this.locality
    return string
}
