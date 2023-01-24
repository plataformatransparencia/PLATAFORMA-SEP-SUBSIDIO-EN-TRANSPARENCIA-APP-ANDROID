package sep.dgesui.subsidioentransparencia.engineadapter

import sep.dgesui.subsidioentransparencia.services.CommitmentService
import sep.dgesui.subsidioentransparencia.tableroext.presupuesto.tablero.TableroCumplimientoPresupuestoWrapper
import sep.dgesui.subsidioentransparencia.tableroext.profexce.tablero.TableroCumplimientoWrapper

class CumplimientoRepository {
    private val cumplimientos = TransparenciaRetrofit.serviceFactory(CommitmentService::class.java)

    fun tableroProfexce(idUniversidad: String, year: String): TableroCumplimientoWrapper? =
        cumplimientos.getTableroCumplimiento(year, idUniversidad)
            .execute()
            .body()

    fun tableroPresupuesto(idUniversidad: String, year: String): TableroCumplimientoPresupuestoWrapper? =
        cumplimientos.getTableroCumplimientoPresupuesto(year, idUniversidad)
            .execute()
            .body()
}