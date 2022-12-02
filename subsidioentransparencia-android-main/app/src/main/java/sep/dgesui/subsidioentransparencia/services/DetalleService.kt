package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sep.dgesui.subsidioentransparencia.model.Detalle

interface DetalleService {

    @GET("ficha2/{year}/{id}/{subsidio}")
    fun getDetalle(
        @Path("year") year: String,
        @Path("id") idUniversidad: String,
        @Path("subsidio") subsidio: String
    ): Call<Detalle>
}