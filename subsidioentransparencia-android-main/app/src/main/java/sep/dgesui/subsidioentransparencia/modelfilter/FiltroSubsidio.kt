package sep.dgesui.subsidioentransparencia.modelfilter

import android.content.Context
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.engineadapter.TransparenciaRetrofit
import sep.dgesui.subsidioentransparencia.fragments.errorHandler
import sep.dgesui.subsidioentransparencia.services.FiltroSubsidioService
import timber.log.Timber

class FilterValuesCache {
    companion object {
        private var filters = MutableLiveData<FilterValues>(null)
        private var inicializacion = MutableLiveData<String>(null)

        fun getFilterValues(
            contexto: Context,
            dispatcher: CoroutineDispatcher = Dispatchers.IO
        ): MutableLiveData<FilterValues> =
            runBlocking {
                if (filters.value == null)
                    launch(dispatcher) {

                        TransparenciaRetrofit
                            .serviceFactory(FiltroSubsidioService::class.java)
                            .getFilterSubsidio()
                            .enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    filters.value =
                                        FilterValues.fromRaw(response.body()!!.string(), contexto)
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) =
                                    errorHandler(call, t)
                            })

                    }
                filters
            }
        fun getFilterValuesIni(
            dispatcher: CoroutineDispatcher = Dispatchers.IO
        ): MutableLiveData<String> =
            runBlocking {
                if (inicializacion.value == null)
                    launch(dispatcher) {
                        TransparenciaRetrofit
                            .serviceFactory(FiltroSubsidioService::class.java)
                            .getFilterSubsidio()
                            .enqueue(object : Callback<ResponseBody> {
                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    var resultado = JSONObject(response.body()!!.string())
                                    val xsub = resultado.getJSONObject("subsidios")
                                    val years: List<String> = xsub.keys().asSequence().toList().reversed()
                                    inicializacion.value = years.first().toString()
                                }

                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) =
                                    errorHandler(call, t)
                            })

                    }
                inicializacion
            }
    }
}

class FilterValues private constructor(
    val subsidios: SubsidiosObj,
    val clasificacion: ClasificacionObj,
    val categorias: CategoriaEstadoObj
) {
    companion object {
        fun fromRaw(rawFilters: String, contexto: Context): FilterValues =
            JSONObject(rawFilters).let {
                FilterValues(
                    SubsidiosObj(it.getJSONObject("subsidios"), contexto),
                    ClasificacionObj(it.getJSONObject("clasificacion"), contexto),
                    CategoriaEstadoObj(it.getJSONObject("categoria_estado"), contexto)
                )
            }

    }
}

class SubsidiosObj(private val raw: JSONObject, contexto: Context) {
    val years: List<String> = raw.keys().asSequence().toList().reversed()
    val todos = contexto.getString(R.string.todos)

    val ordinario = contexto.getString(R.string.subsidioOrdinario)
    val extraordinario = contexto.getString(R.string.subsidioExtrdinario)
    val key_subsidio_ordinario = "subsidio_ordinario"


    fun subsidiosForYear(year: String, tipo: String): List<String> =
        subsidiosWrapperForYear(year).let { listaCompleta ->
            when (tipo) {
                ordinario -> listaCompleta.filter { it.llave == key_subsidio_ordinario }
                extraordinario -> listaCompleta.filter { it.llave != key_subsidio_ordinario }
                else -> listaCompleta
            }
        }.map { it.etiqueta }


    fun tiposSubsidioForYear(year: String): List<String> {

        val list = mutableListOf<String>()

        val subsidios = subsidiosWrapperForYear(year).map { it.llave }

        if (subsidios.contains(key_subsidio_ordinario))
            list.add(ordinario)

        if (subsidios.any { it != key_subsidio_ordinario })
            list.add(extraordinario)

        return list

    }

    private fun subsidiosWrapperForYear(year: String): List<TipoSubsidioWrapper> =
        raw.getJSONObject(year).let { subsidiosForYear ->
            subsidiosForYear.keys()
                .asSequence()
                .toList()
                .map { clave ->
                    TipoSubsidioWrapper(clave, subsidiosForYear.getString(clave))
                }
        }


    fun llaveDeValor(year: String, valor: String): String =
        if (valor == todos)
            "0"
        else
            subsidiosWrapperForYear(year).first { it.etiqueta == valor }.llave

}

data class TipoSubsidioWrapper(val llave: String, val etiqueta: String)

class ClasificacionObj(private val raw: JSONObject, contexto: Context) {

    val todos = contexto.getString(R.string.todos)

    fun clasificacionesForSubsidio(subsidio: String): List<String> =
        agregarTodos(
            todos,
            clasificacionesWrapperForSubsidio(subsidio).map { it.clasificacion })


    private fun clasificacionesWrapperForSubsidio(subsidio: String): List<ClasificacionWrapper> {
        val originalList =
            raw.getJSONObject(subsidio).let { clasificacoinesDeSubsidio ->
                clasificacoinesDeSubsidio
                    .keys()
                    .asSequence()
                    .toList()
                    .map { clave ->
                        ClasificacionWrapper(
                            clave,
                            clasificacoinesDeSubsidio.getString(clave)
                        )
                    }
            }
        return originalList
    }

    fun llaveDeValor(subsidio: String, valor: String): String {

        Timber.d("Recovering value for s: $subsidio, v: $valor")

        return if (valor == todos || valor.isBlank())
            "0"
        else
            clasificacionesWrapperForSubsidio(subsidio).first { it.clasificacion == valor }.llave
    }
}

data class ClasificacionWrapper(val llave: String, val clasificacion: String)

fun <T> agregarTodos(element: T, list: List<T>): List<T> =
    if (list.size < 2)
        list
    else {
        val modifiedList = list.toMutableList()
        modifiedList.add(0, element)
        modifiedList
    }


class CategoriaEstadoObj(private val raw: JSONObject, contexto: Context) {
    val todos = contexto.getString(R.string.todos)

    fun forSubsidioAndYearAndClasificacion(
        subsidio: String,
        year: String,
        clasificacion: String
    ): List<String> =
        forWrapperSubsidioAndYearAndClasificacion(
            subsidio,
            year,
            clasificacion
        ).map { it.nombre }

    private fun forWrapperSubsidioAndYearAndClasificacion(
        subsidio: String,
        year: String,
        clasificacion: String
    ): List<EstadoWrapper> {

        Timber.d("Subsidio: $subsidio, Año: $year Clasificación: $clasificacion")

        val originalList =
            raw.getJSONObject(subsidio).getJSONObject(year).getJSONObject(clasificacion)
                .let { estados ->
                    estados.keys()
                        .asSequence()
                        .toList()
                        .map { clave ->
                            Timber.d("Cargando configuración para $clave")
                            EstadoWrapper(clave, estados.getString(clave))
                        }
                }



        return agregarTodos(EstadoWrapper("00", "Todos"), originalList)

    }

    fun llaveDeValor(
        nombreEstado: String,
        subsidio: String,
        year: String,
        categoria: String,
    ): String =
        if (nombreEstado == todos || nombreEstado.isBlank())
            "0"
        else
            forWrapperSubsidioAndYearAndClasificacion(
                subsidio,
                year,
                categoria
            ).first { it.nombre == nombreEstado }.numero
}

data class EstadoWrapper(val numero: String, val nombre: String)
