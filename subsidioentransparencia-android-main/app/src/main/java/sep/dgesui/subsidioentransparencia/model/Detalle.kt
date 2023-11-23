package sep.dgesui.subsidioentransparencia.model

data class Detalle(
        val id: Int,
        val estado: String,
        val nombre: String,
        val siglas: String,
        val escudo: String,
        val webUrl: String,
        val transparencyUrl: String,
        val gobernador: String,
        val gobernadorCargo: String,
        val rector: String,
        val rectorCargo: String,
        val direccion: String,
        val montos: Montos,
        val anexoEjecucion: String?,
        val ModificacionCalendario: String,
        val oficioCalendario: String,
        val planAusteridad: String,
        val convenio: String?,
        val MarcoColaboracion: String?,
        val MarcoAnio: Int?,
        val numeralia: Numeralia
)

data class Montos(
        val montoFederal: Double,
        val montoEstatal: Double,
        val montoPublico: Double,
        val montoFederalLetra: String,
        val montoEstatalLetra: String,
        val montoPublicoLetra: String
)

data class Numeralia(
        val higherEducationEnrolment: Int,
        val highSchoolEnrolment: Int,
        val enrolmentTotal: Int,
        val fullTimeProfessorsTotal: Int,
        val desirableProfileProfessor: Int,
        val nationalSystemResearchersProfessor: Int,
        val studentAllowance: Double,
        val federationOwnershipPercentage: Double,
        val stateOwnershipPercentage: Double
)