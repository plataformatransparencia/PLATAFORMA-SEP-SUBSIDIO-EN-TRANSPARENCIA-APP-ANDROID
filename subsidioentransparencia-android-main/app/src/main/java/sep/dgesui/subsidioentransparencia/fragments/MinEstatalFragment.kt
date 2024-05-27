package sep.dgesui.subsidioentransparencia.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_min_federal.*
import kotlinx.android.synthetic.main.fragment_programs.*
import sep.dgesui.subsidioentransparencia.*
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.components.TrimestreComponent
import sep.dgesui.subsidioentransparencia.tablero.estado.MinEstatal
import sep.dgesui.subsidioentransparencia.tablero.estado.estatalAMapa


class MinEstatalFragment(

    private val informacion: InformacionGeneralWrapper,
    private val ministracionEstatal: MinEstatal

) : Fragment() {

    private val mapa = estatalAMapa(ministracionEstatal.estatal)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_min_federal, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resumenMinistracionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext(),
        )

        resumenMinistracionTitulo.text = context?.getString(R.string.tablero_estado_universidad)

        resumenMinistracionBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        detalleMinistracionTrimestre1.trimestre = 1
        detalleMinistracionTrimestre2.trimestre = 2
        detalleMinistracionTrimestre3.trimestre = 3
        detalleMinistracionTrimestre4.trimestre = 4

        botonesMinistraicionMensual()

        habilitarLinksMeses()

        pintarSemaforos()

        desactivarBotonesInformes()

        cargarGrafica()

        agregarDatosGenerales()

        referenciaMinistracion.text = getReferencia(informacion.year)
        notaMinistracion.visibility = View.VISIBLE

    }

    private fun desactivarBotonesInformes() {
        detalleMinistracionTrimestre1.hideLinks()
        detalleMinistracionTrimestre2.hideLinks()
        detalleMinistracionTrimestre3.hideLinks()
        detalleMinistracionTrimestre4.hideLinks()
    }

    private fun agregarDatosGenerales() {

        val montoTotalField = montoTotal

        labelMontoTotal.text = context?.getString(R.string.monto_total_calendarizado_estado)

        labelMontoCalendarizado.visibility = View.VISIBLE
        montoCalendarizado.visibility = View.VISIBLE

        ministracionEstatal.totales_adeudos.apply {

            montoTotalField.text = currencyFormatter.format(montoTotalEstado)

            montoCalendarizado.text = currencyFormatter.format(montoTotal)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                WRAP_CONTENT
            )
            adeudos.setBackgroundColor(Color.parseColor("#D4C19C"))
            val texto = TextView(context)
            texto.text = String.format(
                requireContext().getString(R.string.adeudo_estatal_corte),
                adeudoEstatal.fecha
            )
            texto.layoutParams = params
            texto.gravity = Gravity.CENTER
            texto.textSize = 15F
            texto.setTextColor(Color.BLACK)
            texto.setPadding(10,40,10,20)
            adeudos.addView(texto)

            val adeudoTotal = TextView(context)
            adeudoTotal.text = currencyFormatter.format(adeudoEstatal.adeudo)
            adeudoTotal.layoutParams = params
            adeudoTotal.gravity = Gravity.CENTER
            adeudoTotal.textSize = 20F
            adeudoTotal.setTextColor(Color.BLACK)
            adeudoTotal.setPadding(10,40,10,20)
            adeudos.addView(adeudoTotal)
        }
    }


    private fun cargarGrafica() {
        val parMontos =
            mapOf(*months.map { mapa[it]!!.monto to mapa[it]!!.montoRecibido }
                .toTypedArray())

        detalleMinistracionBarChart.setData(parMontos.keys.toList(), parMontos.values.toList())
    }

    private fun pintarSemaforos() {

        val semaforosPrimerTrimestre =
            mesesTrimestre1
                .map { mapa[it] }
                .map { it!!.estado_universidad.cumplimiento }

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

        val semaforosSegundoTrimestre =
            mesesTrimestre2
                .map { mapa[it] }
                .map { it!!.estado_universidad.cumplimiento }

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


        val semaforosTercerTrimestre =
            mesesTrimestre3
                .map { mapa[it] }
                .map { it!!.estado_universidad.cumplimiento }

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


        val semaforosCuartoTrimestre =
            mesesTrimestre4
                .map { mapa[it] }
                .map { it!!.estado_universidad.cumplimiento }

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

    private fun habilitarLinksMeses() {
        val targetsMeses = months.map {
            loadFragment(
                FragmentDetalleMinistracionEstatal(
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

    private fun botonesMinistraicionMensual() {
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

    private fun getReferencia(year: String): String =
        when (year) {
            "2019", "2020" -> String.format(
                requireContext().getString(R.string.referencia_ordinario_estatal_variable),
                year
            )
            else -> requireContext().getString(R.string.referencia_ordinario_estatal_comun)
        }

}