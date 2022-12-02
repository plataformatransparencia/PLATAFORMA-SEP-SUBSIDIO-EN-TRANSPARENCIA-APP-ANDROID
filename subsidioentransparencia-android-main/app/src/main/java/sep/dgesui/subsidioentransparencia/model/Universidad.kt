package sep.dgesui.subsidioentransparencia.model

data class Universidad(
        val universidades:ArrayList<Universidade>

)

data class Universidade(
        val id: Int,
        val clasificacion: Int,
        val estado: String,
        val nombre: String,
        val siglas: String,
        val latitud: Double,
        val longitud: Double,
        val logo: String
)
