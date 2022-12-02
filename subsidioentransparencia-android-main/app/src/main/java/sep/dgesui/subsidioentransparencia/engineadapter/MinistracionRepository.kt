package sep.dgesui.subsidioentransparencia.engineadapter

import sep.dgesui.subsidioentransparencia.services.MinEstatalService
import sep.dgesui.subsidioentransparencia.services.MinFederalService
import sep.dgesui.subsidioentransparencia.tablero.MinFederal
import sep.dgesui.subsidioentransparencia.tablero.estado.MinEstatal
import sep.dgesui.subsidioentransparencia.tableroext.minest.MinEstatalExt
import sep.dgesui.subsidioentransparencia.tableroext.minfed.MinFederalExt

class MinistracionRepository {
    private val federal = TransparenciaRetrofit.serviceFactory(MinFederalService::class.java)
    private val estatal = TransparenciaRetrofit.serviceFactory(MinEstatalService::class.java)

    fun ministracionFederalOrdinaro(id: String, year: String): MinFederal? =
        federal.getMinFederal(year, id)
            .execute()
            .body()

    fun ministracionEstatalOrdinario(id: String, year: String): MinEstatal? =
        estatal.getMinEstatal(year, id)
            .execute()
            .body()

    fun ministracionEstatalExtraordinario(id: String, year: String): MinEstatalExt? =
        try {
            estatal.getMinEstatalExt(year, id)
                .execute()
                .body()
        } catch (e: Throwable) {
            null
        }

    fun ministracionFederalExtraordinario(id: String, year: String): MinFederalExt? =
        federal.getMinFederalExt(year, id)
            .execute()
            .body()
}