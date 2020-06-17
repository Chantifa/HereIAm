package ch.ffhs.esa.hereiam.services
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(".")
    fun getFeeds(): Call<String>
}