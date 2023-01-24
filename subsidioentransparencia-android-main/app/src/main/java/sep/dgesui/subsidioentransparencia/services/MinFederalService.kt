package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sep.dgesui.subsidioentransparencia.tablero.MinFederal
import sep.dgesui.subsidioentransparencia.tableroext.minfed.MinFederalExt
import sep.dgesui.subsidioentransparencia.tableroext.presupuesto.tablero.MinFederalPres

private const val serviceSection = "ministracionfederal2"

interface MinFederalService {
    @GET("$serviceSection/{year}/{id}/subsidio_ordinario")
    fun getMinFederal(
        @Path("year") year: String,
        @Path("id") idUniversodad: String
    ): Call<MinFederal>

    @GET("$serviceSection/{year}/{id}/subsidio_extraordinario")
    fun getMinFederalExt(
        @Path("year") year: String,
        @Path("id") idUniversodad: String
    ): Call<MinFederalExt>

    @GET("$serviceSection/{year}/{id}/subsidio_presupuesto")
    fun getMinFederalPresupuesto(
        @Path("year") year: String,
        @Path("id") idUniversodad: String
    ): Call<MinFederalPres>
}