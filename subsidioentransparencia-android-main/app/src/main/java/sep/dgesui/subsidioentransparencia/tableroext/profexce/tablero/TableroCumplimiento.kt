package sep.dgesui.subsidioentransparencia.tableroext.profexce.tablero

data class TableroCumplimientoWrapper(
    val tablero_cumplimiento: TableroCumplimiento,
)

data class DetalleCumplimiento(
    val fechaLimite: String = "",
    val fechaEntrega: String = "",
    val cumplimiento: String = "",
    val observacion: String = "",
)

val detalleVacio = DetalleCumplimiento()

data class Informe(
    val primer_trimestre: DetalleCumplimiento = detalleVacio,
    val segundo_trimestre: DetalleCumplimiento = detalleVacio,
    val tercero_trimestre: DetalleCumplimiento = detalleVacio,
    val cuarto_trimestre: DetalleCumplimiento = detalleVacio,
)

data class TableroCumplimiento(
    val informe_academico: Informe,
    val informe_financiero: Informe,
    val informe_publicacion: Informe,
    val Entrega_del_reporte: DetalleCumplimiento = detalleVacio,
    val Comprobacion_de_recurso: DetalleCumplimiento = detalleVacio,
    val Finiquito: DetalleCumplimiento = detalleVacio,
    val Resultado_general: ResultadoGeneral,
)
