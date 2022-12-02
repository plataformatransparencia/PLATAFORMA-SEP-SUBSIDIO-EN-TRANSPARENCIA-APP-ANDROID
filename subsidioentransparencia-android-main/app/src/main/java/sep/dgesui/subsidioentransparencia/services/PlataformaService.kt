package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import sep.dgesui.subsidioentransparencia.modelplataforma.Plataforma

interface PlataformaService {
    @GET("plataforma")
    fun getPlataforma(): Call<Plataforma>
}