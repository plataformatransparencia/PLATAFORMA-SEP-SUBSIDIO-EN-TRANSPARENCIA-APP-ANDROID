package sep.dgesui.subsidioentransparencia.fragments

import retrofit2.Call
import timber.log.Timber

val errorHandler = { call: Call<*>, throwable: Throwable ->
    Timber.e("Error en ${call.request().url}")
    Timber.e(throwable)
}
