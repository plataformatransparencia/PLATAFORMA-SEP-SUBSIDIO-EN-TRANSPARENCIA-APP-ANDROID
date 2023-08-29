package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_ext_min_estatal.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.tableroext.minfed.MinFederalExt
import sep.dgesui.subsidioentransparencia.tableroext.minfed.Ministracion
import sep.dgesui.subsidioentransparencia.tableroext.presupuesto.tablero.MinFederalPres

class MinistracionFederalExtraordinariaFragment(
    private val informacion: InformacionGeneralWrapper,
    private val ministracionFederal: MinFederalExt?,
    private val ministracionFederalPres: Ministracion?
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

        ministracionExtraordinariaBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        ministracionExtraordinariaTitle.text =
            context?.getString(R.string.tablero_sep_estado_universidad)

        ministracionExtraordinariaMontos.visibility = View.GONE
        ministracionExtraordinariaFederalLink.visibility = View.VISIBLE
        ministracionExtraordinarioTituloMontoTotalMinistrado.visibility = View.GONE
        ministracionExtraordinarioMontoTotalMinistrado.visibility = View.GONE

        if(ministracionFederal != null){
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

                ministracionExtraordinarioAdeudoFederacion.visibility = View.VISIBLE
                ministracionExtraordinarioTituloAdeudoFederacion.visibility = View.VISIBLE
                ministracionExtraordinarioTituloAdeudoFederacion.text =
                    String.format(
                        requireContext().getString(R.string.adeudo_federal_corte),
                        adeudoFederal?.fecha ?: ""
                    )

                ministracionExtraordinarioAdeudoFederacion.isVisible = true
                ministracionExtraordinarioMontoFederacion.text =
                    currencyFormatter.format(montoTotalSEP)

                ministracionExtraordinarioAdeudoFederacion.text =
                    currencyFormatter.format(adeudoFederal?.adeudo ?: 0)

            }
        }

        if(ministracionFederalPres != null){

            ministracionFederalPres.apply {
                ministracionExtraordinariaFederalLink.setLabel(currencyFormatter.format(monto))
                ministracionExtraordinariaFederalLink.setTarget(
                    loadFragment(
                        DetalleMinistracionFederalExtraordinaria(informacion, ministracionFederalPres),
                        requireActivity()
                    )
                )
                ministracionExtraordinarioMontoFederacion.text = currencyFormatter.format(estado_universidad)
            }

            ministracionExtraordinarioAdeudoFederacion.visibility = View.GONE
            ministracionExtraordinarioTituloAdeudoFederacion.visibility = View.GONE


        }
    }
}