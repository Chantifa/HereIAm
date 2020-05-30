package ch.ffhs.esa.hereiam.screens.profile

import androidx.lifecycle.ViewModel
import ch.ffhs.esa.hereiam.services.AuthenticationService

class ProfileViewModel : ViewModel() {

    val currentUser = AuthenticationService.currentUser
}