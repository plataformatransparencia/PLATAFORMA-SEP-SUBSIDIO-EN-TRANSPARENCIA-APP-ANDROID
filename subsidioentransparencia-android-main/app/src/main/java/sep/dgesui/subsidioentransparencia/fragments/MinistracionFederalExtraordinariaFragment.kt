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
import sep.dgesui.subsidioentransparencia.tableroext.minfed.MinFederalExt

class MinistracionFederalExtraordinariaFragment(
    private val informacion: InformacionGeneralWrapper,
    private val ministracionFederal: MinFederalExt
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_ext_min_estatal, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ministracionExtraordinariaHeader.setValues(
            informacion.nombreUniversidad, informacion.subsidio, informacion.year, requireContext()
        )

        ministracionExtraordinariaBack.setOnClickListener { requireActivity().onBackPressed() }

        ministracionExtraordinariaTitle.text =
            context?.getString(R.string.tablero_sep_estado_universidad)

        ministracionExtraordinariaMontos.visibility = View.GONE
        ministracionExtraordinariaFederalLink.visibility = View.VISIBLE
        ministracionExtraordinarioTituloMontoTotalMinistrado.visibility = View.GONE
        ministracionExtraordinarioMontoTotalMinistrado.visibility = View.GONE

        ministracionFederal.federal.apply {
            ministracionExtraordinariaFederalLink.setLabel(currencyFormatter.format(ministracion.monto))
            ministracionExtraordinariaFederalLink.setTarget(
                loadFragment(
                    DetalleMinistracionFederalExtraordinaria(informacion, ministracion),
                    requireActivity()
                )
            )

        }


        ministracionFederal.totales_adeudos.apply {

            ministracionExtraordinarioTituloAdeudoFederacion.text =
                String.format(
                    requireContext().getString(R.string.adeudo_federal_corte),
                    adeudoFederal?.fecha ?: ""
                )

            ministracionExtraordinarioMontoFederacion.text =
                currencyFormatter.format(montoTotalSEP)

            ministracionExtraordinarioAdeudoFederacion.text =
                currencyFormatter.format(adeudoFederal?.adeudo ?: 0)


        }


    }
}