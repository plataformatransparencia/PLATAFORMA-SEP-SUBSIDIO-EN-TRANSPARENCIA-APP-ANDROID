package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_ext_min_estatal.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.engineadapter.MontosEstatalAdapter
import sep.dgesui.subsidioentransparencia.tableroext.minest.MinEstatalExt


class MinistracionEstatalExtraordinariaFragment(
    private val informacion: InformacionGeneralWrapper,
    private val ministracion: MinEstatalExt,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
 ): View? = inflater.inflate(R.layout.fragment_ext_min_estatal, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ministracionExtraordinariaHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        ministracionExtraordinariaBack.setOnClickListener { requireActivity().onBackPressed() }

        ministracionExtraordinarioTituloMontoTotalMinistrado.visibility = View.VISIBLE
        ministracionExtraordinarioMontoTotalMinistrado.visibility = View.VISIBLE


        ministracion.apply {
            //enviar a nueva pantalla con montos
            ministracionExtraordinariaMontos.adapter = MontosEstatalAdapter(estatal.ministracion, informacion, requireActivity())
            ministracionExtraordinarioMontoFederacion.text =
                currencyFormatter.format(totales_adeudos.montoTotalSEP)


            ministracionExtraordinarioTituloAdeudoFederacion.text = String.format(
                requireContext().getString(R.string.adeudo_estatal_corte),
                totales_adeudos.adeudoEstatal?.fecha ?: ""
            )

            ministracionExtraordinarioAdeudoFederacion.text =
                currencyFormatter.format(totales_adeudos.adeudoEstatal?.adeudo ?: 0.0)

            ministracionExtraordinarioMontoTotalMinistrado.text =
                currencyFormatter.format(totales_adeudos.montoTotal)
        }


    }
}

