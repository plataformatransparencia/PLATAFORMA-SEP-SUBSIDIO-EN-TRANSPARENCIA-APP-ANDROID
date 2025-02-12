package sep.dgesui.subsidioentransparencia.engineadapter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sep.dgesui.subsidioentransparencia.currentYear
import sep.dgesui.subsidioentransparencia.fragments.errorHandler
import sep.dgesui.subsidioentransparencia.model.Contact
import sep.dgesui.subsidioentransparencia.model.Universidad
import sep.dgesui.subsidioentransparencia.model.Universidade
import sep.dgesui.subsidioentransparencia.services.ContactService
import sep.dgesui.subsidioentransparencia.services.ListService

class Filter private constructor() {
    private val defaultState = "0"
    private val defaultCategory = "0"
    private val defaultSubsidio = "subsidio_ordinario"


    var content = MutableLiveData<List<Universidade>>()
    var contentContacto = MutableLiveData<Contact>()
    var filtered: Boolean = false
    var year: String = currentYear()
    var subsidio: String = defaultSubsidio

    var selectState: Boolean = false
    var selectDetalle: Boolean = true
    var selectDeficitFinanciero: Boolean = false
    var btnFilter: Boolean = false
    lateinit var response: Call<Universidad>
    lateinit var responseContacto: Call<Contact>
    var contact: Boolean = false


    init {
        filtrar()
    }

    companion object {
        val filter = Filter()
    }

    fun reset() {
        filtrar()
        subsidio = defaultSubsidio
    }


    fun getCoordinates() = if (filtered)
        Coordinates(
            content.value?.first()?.latitud ?: 23.634501,
            content.value?.first()?.longitud ?: -102.552784
        )
    else
        Coordinates(23.634501, -102.552784)


    fun filtrar(
        year: String = currentYear(),
        category: String = defaultCategory,
        state: String = defaultState,
        subsidio: String = defaultSubsidio
    ): MutableLiveData<List<Universidade>> {

        this.year = year
        this.subsidio = subsidio

        val uniService: ListService = TransparenciaRetrofit.serviceFactory(ListService::class.java)
        response = uniService.getUniversity(year, category, state, subsidio)
        response.enqueue(object : Callback<Universidad> {

            override fun onResponse(call: Call<Universidad>, response: Response<Universidad>) {
                content.value = response.body()?.universidades?.sortedBy { it.siglas }
                    ?: emptyList()
            }

            override fun onFailure(call: Call<Universidad>, t: Throwable) = errorHandler(call, t)
        })

        filtered =
            year != currentYear() || category != defaultCategory || state != defaultState || subsidio != defaultSubsidio

        return content
    }

    fun getContacto(): MutableLiveData<Contact> {
        CoroutineScope(Job() + Dispatchers.Default).launch {
            val contantoService = TransparenciaRetrofit.serviceFactory(ContactService::class.java)
            responseContacto = contantoService.getContact()
            responseContacto.enqueue(object : Callback<Contact> {
                override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                    contentContacto.postValue(response.body())

                }

                override fun onFailure(call: Call<Contact>, t: Throwable) = errorHandler(call, t)

            })
        }
        return contentContacto
    }


}

data class Coordinates(val lat: Double, val long: Double)