package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sep.dgesui.subsidioentransparencia.tableroext.notas.Notas


interface NotasService {
    @GET("notas/{year}/{id}/{subsidio}")
    fun getNotas(
        @Path("year") year: String,
        @Path("id") idUniversidad: String,
        @Path("subsidio") subsidio: String
    ): Call<Notas>
}