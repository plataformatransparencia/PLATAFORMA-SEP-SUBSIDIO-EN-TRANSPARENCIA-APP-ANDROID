package sep.dgesui.subsidioentransparencia.model

data class Compromisos(
    val compromisos: List<Compromiso>
)

data class Compromiso(
    val compromiso: String,
    val cumplimiento: String,
    val fecha: String,
    val observacion: String
)
