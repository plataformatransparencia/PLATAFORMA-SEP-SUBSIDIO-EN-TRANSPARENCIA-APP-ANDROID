package sep.dgesui.subsidioentransparencia.tableroext.presupuesto

import sep.dgesui.subsidioentransparencia.tableroext.compromisos.Compromiso


data class CompromisoUniversidad(
    val vertienteA : List<Compromiso>,
    val vertienteB : List<Compromiso>,
    val vertienteC : List<Compromiso>
)
