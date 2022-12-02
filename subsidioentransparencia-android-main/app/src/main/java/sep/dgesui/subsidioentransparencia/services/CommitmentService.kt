package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sep.dgesui.subsidioentransparencia.model.Compromisos
import sep.dgesui.subsidioentransparencia.tableroext.compromisos.CompromisosExt
import sep.dgesui.subsidioentransparencia.tableroext.profexce.compromisos.CompromisoEstado
import sep.dgesui.subsidioentransparencia.tableroext.profexce.compromisos.CompromisoUniversidad
import sep.dgesui.subsidioentransparencia.tableroext.profexce.tablero.TableroCumplimientoWrapper


private const val serviceSection = "compromisos2"

interface CommitmentService {

    @GET("$serviceSection/{year}/{id}/subsidio_ordinario")
    fun getCommitmentOrdinario(
        @Path("year") year: String,
        @Path("id") idUniversidad: String
    ): Call<Compromisos>

    @GET("$serviceSection/2018/{id}/subsidio_extraordinario")
    fun getCommitmentExt(@Path("id") idUniversidad: String): Call<CompromisosExt>

    @GET("$serviceSection/{year}/{id}/subsidio_profexce/estado")
    fun getCompromisoEstadoProfexce(
        @Path("year") year: String,
        @Path("id") idUniversidad: String
    ): Call<CompromisoEstado>

    @GET("$serviceSection/{year}/{id}/subsidio_profexce/universidad")
    fun getCompromisoUniversidadProfexce(
        @Path("year") year: String,
        @Path("id") idUniversidad: String
    ): Call<CompromisoUniversidad>

    @GET("$serviceSection/{year}/{id}/subsidio_profexce/tablero")
    fun getTableroCumplimiento(
        @Path("year") year: String,
        @Path("id") idUniversidad: String
    ): Call<TableroCumplimientoWrapper>
}