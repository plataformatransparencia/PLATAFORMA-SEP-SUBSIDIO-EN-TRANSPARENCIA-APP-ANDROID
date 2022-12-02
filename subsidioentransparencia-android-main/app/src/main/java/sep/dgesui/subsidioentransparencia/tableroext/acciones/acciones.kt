package sep.dgesui.subsidioentransparencia.tableroext.acciones

data class AccionesAltoNivel(
    val Acciones: AccionesWrapper?,
    val referencias: Map<String, String>? = null,
)

data class AccionesWrapper(
    val Acciones: List<Accion>,
    val Materiales_Suministros_Acciones: List<Accion>? = null,
    val Servicios_Generales_Acciones: List<Accion>? = null,
    val Asignaciones_Subsidios_otras_Ayudas: List<Accion>? = null,
    val Bienes_muebles_inmuebles: List<Accion>? = null,
    val Obras_remodelaciones: List<Accion>? = null,
)

data class Accion(
    var accion: String,
    val imagen: String? = null,
    val cumplimiento: String,
    val fechaEjecucion: String,
    val fechaEstipulada: String? = null,
    val observacion: String,
    val subacciones: List<Subaccione>? = null,
)