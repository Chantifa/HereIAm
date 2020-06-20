package ch.ffhs.esa.hereiam

import android.location.Address
import android.location.Location
import ch.ffhs.esa.hereiam.screens.home.HomeViewModel
import ch.ffhs.esa.hereiam.services.LocationService
import com.google.android.gms.maps.model.LatLng
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun init() {
        homeViewModel = HomeViewModel()
        homeViewModel.initLocationService(LocationServiceMock())
    }

    @Test(expected = Exception::class)
    fun changeMapBasedOnUserInputTest() {
        homeViewModel.changeMapBasedOnUserInput("wrong")
    }
}

class LocationServiceMock : LocationService {
    override fun getCoordinatesFromLocationName(query: String): LatLng? {
        return when (query) {
            "correct" -> LatLng(0.0, 0.0)
            else -> null
        }
    }

    override fun getAddressFromCoordinates(lat: Double, long: Double): Address? {
        return when (lat) {
            0.0 -> null
            else -> Address(Locale.GERMAN)
        }
    }

    override suspend fun getCurrentLocation(): Location? {
        return null
    }
}

