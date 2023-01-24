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

val detalleVacio = DetalleCumplimientoPresupuesto()

val detalleVacioTESOFE = TESOFE()

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

data class Informe(
    val Primer_trimestre_academico: DetalleCumplimientoPresupuesto = detalleVacio,
    val Primer_trimestre_publicacion: DetalleCumplimientoPresupuesto = detalleVacio,
    val Segundo_trimestre_academico: DetalleCumplimientoPresupuesto = detalleVacio,
    val Segundo_trimestre_publicacion: DetalleCumplimientoPresupuesto = detalleVacio,
    val Final_academico: DetalleCumplimientoPresupuesto = detalleVacio,
    val Oficio: DetalleCumplimientoPresupuesto = detalleVacio,
    val Recursos_financieros: DetalleCumplimientoPresupuesto = detalleVacio,
)

data class Reintegro(
    val No_comprobado: TESOFE = detalleVacioTESOFE,
    val Primera_revision_generado: TESOFE = detalleVacioTESOFE,
    val Comprometidos: TESOFE = detalleVacioTESOFE,
    val Segunda_revision_generado: TESOFE = detalleVacioTESOFE,
)

data class TableroCumplimientoPresupuesto(
    val Informes: Informe,
    val Seguimiento_de_entrega: List<Entrega>,
    val Reintegro_TESOFE: Reintegro,
    val Cierre_productiva:Cierre

)
