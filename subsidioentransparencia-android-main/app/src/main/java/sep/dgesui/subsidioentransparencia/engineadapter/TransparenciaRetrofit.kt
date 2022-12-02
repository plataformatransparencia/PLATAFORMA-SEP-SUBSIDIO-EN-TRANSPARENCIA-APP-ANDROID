package sep.dgesui.subsidioentransparencia.engineadapter

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sep.dgesui.subsidioentransparencia.BuildConfig


class TransparenciaRetrofit {
    companion object {
        private const val isLoggingActive = true

        private val loggingInterceptor = HttpLoggingInterceptor()

        private val client = if (isLoggingActive && BuildConfig.DEBUG)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        else
            OkHttpClient()

        private val transparenciaRetrofit = Retrofit.Builder()
            .baseUrl(transparenciaBaseUrlRefactor)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun <S> serviceFactory(serviceClass: Class<S>): S =
            transparenciaRetrofit.create(serviceClass)

    }

}



