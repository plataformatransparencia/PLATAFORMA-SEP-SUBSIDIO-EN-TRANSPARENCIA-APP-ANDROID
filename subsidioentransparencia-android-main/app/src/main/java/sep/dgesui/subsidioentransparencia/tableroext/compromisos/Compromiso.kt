package sep.dgesui.subsidioentransparencia.tableroext.compromisos

data class Compromiso(
    val compromiso: String,
    val cumplimiento: String?,
    val fechaEjecucion: String?,
    val fechaEstipulada: String,
    val observacion: String?
)