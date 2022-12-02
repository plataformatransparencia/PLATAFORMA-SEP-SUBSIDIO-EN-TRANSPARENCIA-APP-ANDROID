package sep.dgesui.subsidioentransparencia.modelplataforma

data class InformacionPlataforma(
    val UI: UI,
    val UPE: UPE,
    val UPEAS: UPEAS,
    val complemento: List<String>,
    val descripcion: List<String>,
    val ley_universitaria: LeyUniversitaria,
    val titulo: String,
    val video: String
)