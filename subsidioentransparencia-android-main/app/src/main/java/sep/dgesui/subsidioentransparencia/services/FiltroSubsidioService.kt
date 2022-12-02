package sep.dgesui.subsidioentransparencia.services

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface FiltroSubsidioService {

    @GET("filtros2")
    fun getFilterSubsidio(): Call<ResponseBody>

}
