package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sep.dgesui.subsidioentransparencia.tableroext.acciones.AccionesAltoNivel


private const val serviceSection = "acciones"

interface AccionesEmprenderService {

    @GET("$serviceSection/{year}/{id}/universidad")
    fun getPorEmprenderUniversidad(
        @Path("year") year: String,
        @Path("id") idUniversidad: String
    ): Call<AccionesAltoNivel>

    @GET("$serviceSection/{year}/{id}/estado")
    fun getPorEmprenderEstado(
        @Path("year") year: String,
        @Path("id") idUniversidad: String
    ): Call<AccionesAltoNivel>

    @GET("$serviceSection/2018/{id}/emprendidas")
    fun getPorEmprender2018Emprendidas(
        @Path("id") idUniversidad: String
    ): Call<AccionesAltoNivel>

    @GET("$serviceSection/2018/{id}/universidad")
    fun getPorEmprender2018Universidad(
        @Path("id") idUniversidad: String
    ): Call<AccionesAltoNivel>

}