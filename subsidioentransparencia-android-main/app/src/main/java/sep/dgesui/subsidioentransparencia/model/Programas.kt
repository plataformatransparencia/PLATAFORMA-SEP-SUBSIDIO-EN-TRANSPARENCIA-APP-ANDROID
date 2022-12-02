package sep.dgesui.subsidioentransparencia.model

data class Programas(
    val programa: List<Programa>
)

data class Programa(
        val descripcion: List<String>,
        val titulo: String
)