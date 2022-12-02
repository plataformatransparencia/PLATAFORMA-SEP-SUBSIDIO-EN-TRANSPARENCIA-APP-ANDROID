package sep.dgesui.subsidioentransparencia.tablero.estado

data class MinEstatal(
    val estatal: MesesData,
    val totales_adeudos: TotalesAdeudos
)

data class MesesData(
    val Enero: DatosMes,
    val Febrero: DatosMes,
    val Marzo: DatosMes,
    val Abril: DatosMes,
    val Mayo: DatosMes,
    val Junio: DatosMes,
    val Julio: DatosMes,
    val Agosto: DatosMes,
    val Septiembre: DatosMes,
    val Octubre: DatosMes,
    val Noviembre: DatosMes,
    val Diciembre: DatosMes
)

fun estatalAMapa(estatal: MesesData): Map<String, DatosMes> =
    estatal.let {
        mapOf(
            "Enero" to it.Enero,
            "Febrero" to it.Febrero,
            "Marzo" to it.Marzo,
            "Abril" to it.Abril,
            "Mayo" to it.Mayo,
            "Junio" to it.Junio,
            "Julio" to it.Julio,
            "Agosto" to it.Agosto,
            "Septiembre" to it.Septiembre,
            "Octubre" to it.Octubre,
            "Noviembre" to it.Noviembre,
            "Diciembre" to it.Diciembre,
        )
    }

data class DatosMes(
    val monto: Double,
    val montoRecibido: Double,
    val estado_universidad: EstadoUniversidad,
    val ejecucion_estado: EjecucionEstado?
)

data class EstadoUniversidad(
    val cumplimiento: String,
    val fecha: String,
    val observacion: String
)

data class EjecucionEstado(
    val fecha: String,
    val observacion: String
)


data class TotalesAdeudos(
    val montoTotalSEP: Double,
    val montoTotalEstado: Double,
    val adeudoEstatal: AdeudoEstatal,
    val montoTotal: Double
)

data class AdeudoEstatal(
    val adeudo: Double,
    val fecha: String
)