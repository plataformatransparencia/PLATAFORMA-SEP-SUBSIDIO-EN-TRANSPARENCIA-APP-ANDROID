package sep.dgesui.subsidioentransparencia.engineadapter

import sep.dgesui.subsidioentransparencia.model.Detalle
import sep.dgesui.subsidioentransparencia.modelreferencias.Referencias
import sep.dgesui.subsidioentransparencia.services.DetalleService
import sep.dgesui.subsidioentransparencia.services.NotasService
import sep.dgesui.subsidioentransparencia.services.ReferenciasService
import sep.dgesui.subsidioentransparencia.tableroext.notas.Notas

class FichaRepository {
    private val referenciasSource =
        TransparenciaRetrofit.serviceFactory(ReferenciasService::class.java)

    private val fichaSource = TransparenciaRetrofit.serviceFactory(DetalleService::class.java)

    fun referencias(year: String): Referencias? =
        referenciasSource.getReferencias(year)
            .execute()
            .body()

    fun ficha(year: String, idUniversidad: String, subsidio: String): Detalle? =
        fichaSource.getDetalle(year, idUniversidad, subsidio)
            .execute()
            .body()


}

class NotaRepository {
    private val notas = TransparenciaRetrofit.serviceFactory(NotasService::class.java)

    fun notas(year: String, idUniversidad: String, subsidio: String): Notas? =
        notas.getNotas(year, idUniversidad, subsidio)
            .execute()
            .body()
}