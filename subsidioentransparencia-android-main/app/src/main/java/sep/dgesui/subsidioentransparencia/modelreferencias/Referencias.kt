package sep.dgesui.subsidioentransparencia.modelreferencias

data class Referencias(
    val numeralia: Map<String, String> = emptyMap(),
    val subsidio_extraordinario: Map<String, String>?,
    val subsidio_ordinario: SubsidioOrdinario?,
    val subsidio_profexce: SubsidioProfexce?,
)