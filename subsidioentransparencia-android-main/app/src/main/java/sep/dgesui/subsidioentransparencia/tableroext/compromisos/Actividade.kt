package sep.dgesui.subsidioentransparencia.tableroext.compromisos

data class Actividade(
    val actividad: String,
    val cumplimientos: List<Cumplimiento>
)