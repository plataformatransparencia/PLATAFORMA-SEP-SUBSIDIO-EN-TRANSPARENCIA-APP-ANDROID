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
        ministracionExtraordinariaMontos.visibility = View.GONE
        montoTotalEstadoExtraordinario.visibility = View.VISIBLE
        LinkTranferEstatalExtraordinario.visibility = View.VISIBLE
        ministracionExtraordinarioMontoFederacion.visibility = View.GONE
        ministracion.apply {
            //ministracionExtraordinariaMontos.adapter = MontosEstatalAdapter(estatal.ministracion, informacion, requireActivity())
            montoTotalEstadoExtraordinario.text = currencyFormatter.format(totales_adeudos.montoTotalSEP)

//            ministracionExtraordinarioMontoFederacion.text =
//                currencyFormatter.format(totales_adeudos.montoTotalEstado)
            LinkTranferEstatalExtraordinario.text = currencyFormatter.format(totales_adeudos.montoTotalEstado)
            LinkTranferEstatalExtraordinario.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, DetalleMinistracionExtraordinariaEstatal(informacion,ministracion.estatal.ministracion))
                    .addToBackStack(null).commit()

            }
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

