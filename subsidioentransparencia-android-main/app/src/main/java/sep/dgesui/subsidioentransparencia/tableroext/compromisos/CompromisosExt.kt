package sep.dgesui.subsidioentransparencia.tableroext.compromisos

data class CompromisosExt(
    val actividades: List<Actividade>,
    val compromisos: List<Compromiso>?,
    val entregas: List<Entrega>?,
    val referencias: Map<String, String>?,
    val nota_compromisos: String?,
)