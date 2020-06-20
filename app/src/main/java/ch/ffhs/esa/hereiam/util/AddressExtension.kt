package ch.ffhs.esa.hereiam.util

import android.location.Address

fun Address?.getShortAddress(): String? {
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
