package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sep.dgesui.subsidioentransparencia.tablero.estado.MinEstatal
import sep.dgesui.subsidioentransparencia.tableroext.minest.MinEstatalExt

private const val serviceSection = "ministracionestatal2"

interface MinEstatalService {
    @GET("$serviceSection/{year}/{id}/subsidio_ordinario")
    fun getMinEstatal(
        @Path("year") year: String,
        @Path("id") idUniversidad: String
    ): Call<MinEstatal>

    @GET("$serviceSection/{year}/{id}/subsidio_extraordinario")
    fun getMinEstatalExt(
        @Path("year") year: String,
        @Path("id") idUniversidad: String
    ): Call<MinEstatalExt>
}