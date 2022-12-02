package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sep.dgesui.subsidioentransparencia.modelreferencias.Referencias

interface ReferenciasService {

    @GET("referencias2/{year}")
    fun getReferencias(@Path("year") year: String): Call<Referencias>

}