package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detalle_ministracion_extraordinaria.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.tableroext.minest.Ministracion

class DetalleMinistracionExtraordinariaEstatal(
    private val informacin: InformacionGeneralWrapper,
    private val ministracion: Ministracion
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_detalle_ministracion_extraordinaria, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detalleMinistracionExtraordinariaHeader.setValues(
            informacin.nombreUniversidad,
            informacin.subsidio,
            informacin.year,
            requireContext()
        )

        detalleMinistracionExtraordinariaBack.setOnClickListener { requireActivity().onBackPressed() }

        ministracion.apply {

            detalleMinistracionExtraordinariaTitle.text = currencyFormatter.format(monto)

            detalleMinistracionExtraordinariaEstadoUniversidad.text =
                currencyFormatter.format(estado_universidad)

            detalleMinistracionExtraordinariaEstadoUniversidadFecha.text = fechaEjecucionEstado

            detalleMinistracionExtraordinariaObservacion.text = observacion
        }


    }
}