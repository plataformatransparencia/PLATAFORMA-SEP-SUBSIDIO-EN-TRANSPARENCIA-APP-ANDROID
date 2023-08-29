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
import sep.dgesui.subsidioentransparencia.tableroext.minfed.Ministracion

class DetalleMinistracionFederalExtraordinaria(
    private val informacion: InformacionGeneralWrapper,
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
            informacion.nombreUniversidad, informacion.subsidio, informacion.year, requireContext()
        )

        detalleMinistracionExtraordinariaBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        detalleMinistracionExtraordinariaTitle.text = currencyFormatter.format(ministracion.monto)

        detalleMinistracionExtraordinariaTitleSepEstado.visibility = View.VISIBLE
        detalleMinistracionExtraordinariaSepEstado.visibility = View.VISIBLE
        detalleMinistracionExtraordinariaTitleSepEstadoFecha.visibility = View.VISIBLE
        detalleMinistracionExtraordinariaSepEstadoFecha.visibility = View.VISIBLE

        detalleMinistracionExtraordinariaSepEstado.text =
            currencyFormatter.format(ministracion.sep_estado)
        detalleMinistracionExtraordinariaSepEstadoFecha.text = ministracion.fechaEjecucionEstado
        detalleMinistracionExtraordinariaEstadoUniversidad.text =
            currencyFormatter.format(ministracion.estado_universidad)
        detalleMinistracionExtraordinariaEstadoUniversidadFecha.text =
            ministracion.fechaEjecucionUniversidad
        detalleMinistracionExtraordinariaObservacion.text = ministracion.observacion


    }
}