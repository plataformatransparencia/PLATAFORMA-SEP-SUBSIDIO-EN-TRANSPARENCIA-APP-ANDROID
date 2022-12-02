package sep.dgesui.subsidioentransparencia.services

import retrofit2.Call
import retrofit2.http.GET
import sep.dgesui.subsidioentransparencia.model.Contact

interface ContactService {

    @GET("contacto")
    fun getContact(): Call<Contact>
}