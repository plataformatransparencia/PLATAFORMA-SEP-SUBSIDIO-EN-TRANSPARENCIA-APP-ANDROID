package sep.dgesui.subsidioentransparencia.tableroext.presupuesto.tablero

data class TableroCumplimientoPresupuestoWrapper(
    val tablero_cumplimiento: TableroCumplimientoPresupuesto,
)

data class DetalleCumplimientoPresupuesto(
    val descripcion: String = "",
    val fechaLimite: String = "",
    val fechaEntrega: String = "",
    val cumplimiento: String = "",
    val observacion: String = "",
)

data class Entrega(
    val mes: String = "",
    val fechaLimite: String = "",
    val fechaEntrega: String = "",
    val cumplimiento: String = "",
    val observacion: String = "",
)

data class TESOFE(
    val descripcion: String = "",
    val fechaLimite: String = "",
    val fechaReintegro: String = "",
    val cumplimiento: String = "",
    val observacion: String = "",
)

data class Cierre(
    val accion: String = "",
    val fechaLimite: String = "",
    val fechaComprobacion: String = "",
    val cumplimiento: String = "",
    val observacion: String = "",
)

data class TableroCumplimientoPresupuesto(
    val Informes: List<DetalleCumplimientoPresupuesto>,
    val Seguimiento_de_entrega: List<Entrega>,
    val Reintegro_TESOFE: List<TESOFE>,
    val Cierre_productiva:Cierre
)
