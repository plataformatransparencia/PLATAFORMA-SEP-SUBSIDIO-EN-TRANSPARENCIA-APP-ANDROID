package sep.dgesui.subsidioentransparencia.tablero


data class MinFederal(
    val federal: Federal,
    val totales_adeudos: TotalesFederalAdeudo,
)

data class Federal(
    val Enero: DatosFederal,
    val Febrero: DatosFederal,
    val Marzo: DatosFederal,
    val Abril: DatosFederal,
    val Mayo: DatosFederal,
    val Junio: DatosFederal,
    val Julio: DatosFederal,
    val Agosto: DatosFederal,
    val Septiembre: DatosFederal,
    val Octubre: DatosFederal,
    val Noviembre: DatosFederal,
    val Diciembre: DatosFederal,
    val informes: Informes,
)

fun federalAMapa(federal: Federal): Map<String, DatosFederal> =
    federal.let {
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

data class DatosFederal(
    val monto: Double,
    val montoRecibido: Double,
    val sep_estado: SepEstadoFederal,
    val ejecucion_estado: EjecucionEstadoFederal?,
    val estado_Universidad: EstadoUniversidadFederal
)

data class SepEstadoFederal(
    val cumplimiento: String,
    val fecha: String,
    val observacion: String
)

data class EjecucionEstadoFederal(
    val cumplimiento: String,
    val fecha: String,
    val observacion: String
)

data class EstadoUniversidadFederal(
    val cumplimiento: String,
    val fecha: String,
    val observacion: String
)

data class Informes(
    val subsidio_ordinario: SubsidioOrdinario,
    val rendicion_cuentas: RendicionCuentas,
    val matricula: Matricula
)

data class SubsidioOrdinario(
    val trimestre1: Trimestre,
    val trimestre2: Trimestre,
    val trimestre3: Trimestre,
    val trimestre4: Trimestre
)

data class RendicionCuentas(
    val trimestre1: Trimestre,
    val trimestre2: Trimestre,
    val trimestre3: Trimestre,
    val trimestre4: Trimestre
)

data class Trimestre(
    val cumplimiento: String,
    val observacion: String
)

data class Matricula(
    val semestre1: Semestre,
    val semestre2: Semestre
)

data class Semestre(
    val cumplimiento: String,
    val observacion: String
)

data class TotalesFederalAdeudo(
    val montoTotalSEP: Double,
    val adeudoFederal: FederalAdeudoFederal
)

data class FederalAdeudoFederal(
    val adeudo: Double,
    val fecha: String
)