package sep.dgesui.subsidioentransparencia.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_min_federal.*
import sep.dgesui.subsidioentransparencia.*
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.components.TrimestreComponent
import sep.dgesui.subsidioentransparencia.tablero.MinFederal
import sep.dgesui.subsidioentransparencia.tablero.federalAMapa


class MinFederalFragment(
    private val informacion: InformacionGeneralWrapper,
    private val ministracionFederal: MinFederal,
) : Fragment() {

    private val mapa = federalAMapa(ministracionFederal.federal)
    private lateinit var titulReporteOrdinario: String
    private lateinit var titulReporteRendicionCuentas: String
    private lateinit var titulReporteMatricula: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_min_federal, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        titulReporteOrdinario = requireContext().getString(R.string.informeSubsidioOrdinario)
        titulReporteRendicionCuentas = requireContext().getString(R.string.informeRendicionCuentas)
        titulReporteMatricula = requireContext().getString(R.string.informeMatricula)


        resumenMinistracionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        resumenMinistracionTitulo.text = context?.getString(R.string.tablero_sep_estado_universidad)

        resumenMinistracionBack.setOnClickListener { requireActivity().onBackPressed() }

        detalleMinistracionTrimestre1.trimestre = 1
        detalleMinistracionTrimestre2.trimestre = 2
        detalleMinistracionTrimestre3.trimestre = 3
        detalleMinistracionTrimestre4.trimestre = 4

        botonesMinistracionMensual()

        habilitarLinksMeses()

        pintarSemaforos()

        habilitarLinksReportes(detalleMinistracionTrimestre1, 1)
        habilitarLinksReportes(detalleMinistracionTrimestre2, 2)
        habilitarLinksReportes(detalleMinistracionTrimestre3, 3)
        habilitarLinksReportes(detalleMinistracionTrimestre4, 4)

        cargarGrafica()

        agregarDatosGenerales()

        referenciaMinistracion.text = context?.getString(R.string.referencia_ordinario_federal)


    }

    private fun habilitarLinksReportes(trimestreComponent: TrimestreComponent, trimestre: Int) {

        val informesTrimestrales = when (trimestre) {
            1 -> ministracionFederal.federal.informes.subsidio_ordinario.trimestre1 to ministracionFederal.federal.informes.rendicion_cuentas.trimestre1
            2 -> ministracionFederal.federal.informes.subsidio_ordinario.trimestre2 to ministracionFederal.federal.informes.rendicion_cuentas.trimestre1
            3 -> ministracionFederal.federal.informes.subsidio_ordinario.trimestre3 to ministracionFederal.federal.informes.rendicion_cuentas.trimestre1
            4 -> ministracionFederal.federal.informes.subsidio_ordinario.trimestre4 to ministracionFederal.federal.informes.rendicion_cuentas.trimestre1
            else -> throw RuntimeException("El trimestre debe tener un valor entero en el intervalo cerrado [1,4]")
        }.let {
            Item(cumplimiento = it.first.cumplimiento, observacion = it.first.observacion,porcentajeIncremento =null) to
                    Item(cumplimiento = it.second.cumplimiento, observacion = it.second.observacion,porcentajeIncremento =null)
        }.let {
            DetalleReporteFragment(
                informacion,
                titulReporteOrdinario,
                it.first
            ) to DetalleReporteFragment(informacion, titulReporteRendicionCuentas, it.second)
        }

        val informeMatricula = when (trimestre) {
            2 -> ministracionFederal.federal.informes.matricula.semestre1
            4 -> ministracionFederal.federal.informes.matricula.semestre2
            else -> null
        }?.let {
            Item(cumplimiento = it.cumplimiento, observacion = it.observacion,porcentajeIncremento =null)
        }?.let {
            DetalleReporteFragment(informacion, titulReporteMatricula, it)
        }

        trimestreComponent.setTargets(
            targetSubsidioOrdinario = informesTrimestrales.first,
            targetRendicionCuentas = informesTrimestrales.second,
            targetReporteMatricula = informeMatricula,
            activity = requireActivity()
        )

    }

    private fun pintarSemaforos() {

        val semaforosPrimerTrimestre = obtenerSemaforosMutuos(mesesTrimestre1)

        detalleMinistracionTrimestre1.semaforo(
            TrimestreComponent.Mes.PRIMERO,
            semaforosPrimerTrimestre[0]
        )
        detalleMinistracionTrimestre1.semaforo(
            TrimestreComponent.Mes.SEGUNDO,
            semaforosPrimerTrimestre[1]
        )
        detalleMinistracionTrimestre1.semaforo(
            TrimestreComponent.Mes.TERCERO,
            semaforosPrimerTrimestre[2]
        )

        val semaforosSegundoTrimestre = obtenerSemaforosMutuos(mesesTrimestre2)

        detalleMinistracionTrimestre2.semaforo(
            TrimestreComponent.Mes.PRIMERO,
            semaforosSegundoTrimestre[0]
        )
        detalleMinistracionTrimestre2.semaforo(
            TrimestreComponent.Mes.SEGUNDO,
            semaforosSegundoTrimestre[1]
        )
        detalleMinistracionTrimestre2.semaforo(
            TrimestreComponent.Mes.TERCERO,
            semaforosSegundoTrimestre[2]
        )

        val semaforosTercerTrimestre = obtenerSemaforosMutuos(mesesTrimestre3)

        detalleMinistracionTrimestre3.semaforo(
            TrimestreComponent.Mes.PRIMERO,
            semaforosTercerTrimestre[0]
        )
        detalleMinistracionTrimestre3.semaforo(
            TrimestreComponent.Mes.SEGUNDO,
            semaforosTercerTrimestre[1]
        )
        detalleMinistracionTrimestre3.semaforo(
            TrimestreComponent.Mes.TERCERO,
            semaforosTercerTrimestre[2]
        )

        val semaforosCuartoTrimestre = obtenerSemaforosMutuos(mesesTrimestre4)

        detalleMinistracionTrimestre4.semaforo(
            TrimestreComponent.Mes.PRIMERO,
            semaforosCuartoTrimestre[0]
        )
        detalleMinistracionTrimestre4.semaforo(
            TrimestreComponent.Mes.SEGUNDO,
            semaforosCuartoTrimestre[1]
        )
        detalleMinistracionTrimestre4.semaforo(
            TrimestreComponent.Mes.TERCERO,
            semaforosCuartoTrimestre[2]
        )
    }

    private fun obtenerSemaforosMutuos(meses: List<String>): List<String> =
        meses.map { mapa[it]!! }
            .map { it.sep_estado.cumplimiento to it.estado_Universidad.cumplimiento }
            .map { it.first }

    private fun botonesMinistracionMensual() {

        detalleMinistracionTrimestre1.montos =
            mesesTrimestre1.map { it to currencyFormatter.format(mapa[it]?.monto) }
                .let { TrimestreComponent.TrimestreInfo(it[0], it[1], it[2]) }


        detalleMinistracionTrimestre2.montos =
            mesesTrimestre2.map { it to currencyFormatter.format(mapa[it]?.monto) }
                .let { TrimestreComponent.TrimestreInfo(it[0], it[1], it[2]) }


        detalleMinistracionTrimestre3.montos =
            mesesTrimestre3.map { it to currencyFormatter.format(mapa[it]?.monto) }
                .let { TrimestreComponent.TrimestreInfo(it[0], it[1], it[2]) }

        detalleMinistracionTrimestre4.montos =
            mesesTrimestre4.map { it to currencyFormatter.format(mapa[it]?.monto) }
                .let { TrimestreComponent.TrimestreInfo(it[0], it[1], it[2]) }
    }

    private fun cargarGrafica() {
        val parMontos =
            mapOf(*months.map { mapa[it]!!.monto to mapa[it]!!.montoRecibido }
                .toTypedArray())

        detalleMinistracionBarChart.setData(parMontos.keys.toList(), parMontos.values.toList())
    }

    private fun agregarDatosGenerales() =
        ministracionFederal.totales_adeudos.apply {

            montoTotal.text = currencyFormatter.format(montoTotalSEP)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            adeudos.setBackgroundColor(Color.parseColor("#D4C19C"))
            val texto = TextView(context)
            texto.text = String.format(
                requireContext().getString(R.string.adeudo_federal_corte),
                adeudoFederal.fecha
            )
            texto.layoutParams = params
            texto.gravity = Gravity.CENTER
            texto.textSize = 15F
            texto.setTextColor(Color.BLACK)
            texto.setPadding(10,40,10,20)
            adeudos.addView(texto)

            val adeudoTotal = TextView(context)
            adeudoTotal.text = currencyFormatter.format(adeudoFederal.adeudo)
            adeudoTotal.layoutParams = params
            adeudoTotal.gravity = Gravity.CENTER
            adeudoTotal.textSize = 20F
            adeudoTotal.setTextColor(Color.BLACK)
            adeudoTotal.setPadding(10,40,10,20)
            adeudos.addView(adeudoTotal)

//            labelAdeudoTotalEstatalCorteA.text = String.format(
//                requireContext().getString(R.string.adeudo_federal_corte),
//                adeudoFederal.fecha
//            )

//            adeudoTotalEstatal.text = currencyFormatter.format(adeudoFederal.adeudo)
        }


    private fun habilitarLinksMeses() {
        val targetsMeses = months.map {
            loadFragment(
                FragmentDetalleMinistracionFederal(
                    informacion,
                    it,
                    mapa[it]!!
                ), requireActivity()
            )
        }

        detalleMinistracionTrimestre1.setMesesTargets(
            targetsMeses[0],
            targetsMeses[1],
            targetsMeses[2],
        )

        detalleMinistracionTrimestre2.setMesesTargets(
            targetsMeses[3],
            targetsMeses[4],
            targetsMeses[5],
        )

        detalleMinistracionTrimestre3.setMesesTargets(
            targetsMeses[6],
            targetsMeses[7],
            targetsMeses[8],
        )

        detalleMinistracionTrimestre4.setMesesTargets(
            targetsMeses[9],
            targetsMeses[10],
            targetsMeses[11],
        )
    }


}

