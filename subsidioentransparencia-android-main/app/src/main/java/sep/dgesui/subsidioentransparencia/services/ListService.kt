package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sep.dgesui.subsidioentransparencia.model.Universidad


interface ListService {

    @GET("universidades2/{year}/{category}/{state}/{subsidio}")
    fun getUniversity(
        @Path("year") year: String,
        @Path("category") category: String,
        @Path("state") state: String,
        @Path("subsidio") subsidio: String
    ): Call<Universidad>

}