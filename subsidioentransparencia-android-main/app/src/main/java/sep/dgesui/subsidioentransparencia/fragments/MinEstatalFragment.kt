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
import sep.dgesui.subsidioentransparencia.*
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.components.TrimestreComponent
import sep.dgesui.subsidioentransparencia.databinding.FragmentMinFederalBinding
import sep.dgesui.subsidioentransparencia.tablero.estado.MinEstatal
import sep.dgesui.subsidioentransparencia.tablero.estado.estatalAMapa


class MinEstatalFragment(

    private val informacion: InformacionGeneralWrapper,
    private val ministracionEstatal: MinEstatal

) : Fragment() {

    private val mapa = estatalAMapa(ministracionEstatal.estatal)
    private var _binding: FragmentMinFederalBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMinFederalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.resumenMinistracionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext(),
        )

        binding.resumenMinistracionTitulo.text = context?.getString(R.string.tablero_estado_universidad)

        binding.resumenMinistracionBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.detalleMinistracionTrimestre1.trimestre = 1
        binding.detalleMinistracionTrimestre2.trimestre = 2
        binding.detalleMinistracionTrimestre3.trimestre = 3
        binding.detalleMinistracionTrimestre4.trimestre = 4

        botonesMinistraicionMensual()

        habilitarLinksMeses()

        pintarSemaforos()

        desactivarBotonesInformes()

        cargarGrafica()

        agregarDatosGenerales()

        binding.referenciaMinistracion.text = getReferencia(informacion.year)
        binding.notaMinistracion.visibility = View.VISIBLE

    }

    private fun desactivarBotonesInformes() {
        binding.detalleMinistracionTrimestre1.hideLinks()
        binding.detalleMinistracionTrimestre2.hideLinks()
        binding.detalleMinistracionTrimestre3.hideLinks()
        binding.detalleMinistracionTrimestre4.hideLinks()
    }

    private fun agregarDatosGenerales() {

        val montoTotalField = binding.montoTotal

        binding.labelMontoTotal.text = context?.getString(R.string.monto_total_calendarizado_estado)

        binding.labelMontoCalendarizado.visibility = View.VISIBLE
        binding.montoCalendarizado.visibility = View.VISIBLE

        ministracionEstatal.totales_adeudos.apply {

            montoTotalField.text = currencyFormatter.format(montoTotalEstado)

            binding.montoCalendarizado.text = currencyFormatter.format(montoTotal)

            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                WRAP_CONTENT
            )
            binding.adeudos.setBackgroundColor(Color.parseColor("#D4C19C"))
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
            binding.adeudos.addView(texto)

            val adeudoTotal = TextView(context)
            adeudoTotal.text = currencyFormatter.format(adeudoEstatal.adeudo)
            adeudoTotal.layoutParams = params
            adeudoTotal.gravity = Gravity.CENTER
            adeudoTotal.textSize = 20F
            adeudoTotal.setTextColor(Color.BLACK)
            adeudoTotal.setPadding(10,40,10,20)
            binding.adeudos.addView(adeudoTotal)
        }
    }


    private fun cargarGrafica() {
        val parMontos =
            mapOf(*months.map { mapa[it]!!.monto to mapa[it]!!.montoRecibido }
                .toTypedArray())

        binding.detalleMinistracionBarChart.setData(parMontos.keys.toList(), parMontos.values.toList())
    }

    private fun pintarSemaforos() {

        val semaforosPrimerTrimestre =
            mesesTrimestre1
                .map { mapa[it] }
                .map { it!!.estado_universidad.cumplimiento }

        binding.detalleMinistracionTrimestre1.semaforo(
            TrimestreComponent.Mes.PRIMERO,
            semaforosPrimerTrimestre[0]
        )
        binding.detalleMinistracionTrimestre1.semaforo(
            TrimestreComponent.Mes.SEGUNDO,
            semaforosPrimerTrimestre[1]
        )
        binding.detalleMinistracionTrimestre1.semaforo(
            TrimestreComponent.Mes.TERCERO,
            semaforosPrimerTrimestre[2]
        )

        val semaforosSegundoTrimestre =
            mesesTrimestre2
                .map { mapa[it] }
                .map { it!!.estado_universidad.cumplimiento }

        binding.detalleMinistracionTrimestre2.semaforo(
            TrimestreComponent.Mes.PRIMERO,
            semaforosSegundoTrimestre[0]
        )
        binding.detalleMinistracionTrimestre2.semaforo(
            TrimestreComponent.Mes.SEGUNDO,
            semaforosSegundoTrimestre[1]
        )
        binding.detalleMinistracionTrimestre2.semaforo(
            TrimestreComponent.Mes.TERCERO,
            semaforosSegundoTrimestre[2]
        )


        val semaforosTercerTrimestre =
            mesesTrimestre3
                .map { mapa[it] }
                .map { it!!.estado_universidad.cumplimiento }

        binding.detalleMinistracionTrimestre3.semaforo(
            TrimestreComponent.Mes.PRIMERO,
            semaforosTercerTrimestre[0]
        )
        binding.detalleMinistracionTrimestre3.semaforo(
            TrimestreComponent.Mes.SEGUNDO,
            semaforosTercerTrimestre[1]
        )
        binding.detalleMinistracionTrimestre3.semaforo(
            TrimestreComponent.Mes.TERCERO,
            semaforosTercerTrimestre[2]
        )


        val semaforosCuartoTrimestre =
            mesesTrimestre4
                .map { mapa[it] }
                .map { it!!.estado_universidad.cumplimiento }

        binding.detalleMinistracionTrimestre4.semaforo(
            TrimestreComponent.Mes.PRIMERO,
            semaforosCuartoTrimestre[0]
        )
        binding.detalleMinistracionTrimestre4.semaforo(
            TrimestreComponent.Mes.SEGUNDO,
            semaforosCuartoTrimestre[1]
        )
        binding.detalleMinistracionTrimestre4.semaforo(
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

        binding.detalleMinistracionTrimestre1.setMesesTargets(
            targetsMeses[0],
            targetsMeses[1],
            targetsMeses[2],
        )

        binding.detalleMinistracionTrimestre2.setMesesTargets(
            targetsMeses[3],
            targetsMeses[4],
            targetsMeses[5],
        )

        binding.detalleMinistracionTrimestre3.setMesesTargets(
            targetsMeses[6],
            targetsMeses[7],
            targetsMeses[8],
        )

        binding.detalleMinistracionTrimestre4.setMesesTargets(
            targetsMeses[9],
            targetsMeses[10],
            targetsMeses[11],
        )
    }

    private fun botonesMinistraicionMensual() {
        binding.detalleMinistracionTrimestre1.montos =
            mesesTrimestre1.map { it to currencyFormatter.format(mapa[it]?.monto) }
                .let { TrimestreComponent.TrimestreInfo(it[0], it[1], it[2]) }


        binding.detalleMinistracionTrimestre2.montos =
            mesesTrimestre2.map { it to currencyFormatter.format(mapa[it]?.monto) }
                .let { TrimestreComponent.TrimestreInfo(it[0], it[1], it[2]) }


        binding.detalleMinistracionTrimestre3.montos =
            mesesTrimestre3.map { it to currencyFormatter.format(mapa[it]?.monto) }
                .let { TrimestreComponent.TrimestreInfo(it[0], it[1], it[2]) }

        binding.detalleMinistracionTrimestre4.montos =
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}