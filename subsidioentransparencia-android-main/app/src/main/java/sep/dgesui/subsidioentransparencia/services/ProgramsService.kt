package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import sep.dgesui.subsidioentransparencia.model.Programas


interface ProgramsService {

    @GET("programas")
    fun getProgramas(): Call<Programas>

}