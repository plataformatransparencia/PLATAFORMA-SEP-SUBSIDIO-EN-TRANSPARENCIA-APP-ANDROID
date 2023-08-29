package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detalle_ministracion.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.tablero.DatosFederal
import sep.dgesui.subsidioentransparencia.tablero.estado.DatosMes

class FragmentDetalleMinistracionFederal(
    private val informacion: InformacionGeneralWrapper,
    private val mes: String,
    private val datos: DatosFederal
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detalle_ministracion, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detalleMinistracionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        detalleMinistracionBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        detalleMinistracionTitulo.text = mes

        datos.sep_estado.apply {
            semaforoSEPEstado.setValues(cumplimiento, fecha)
            observacionesSEPEstado.text = observacion
        }

        datos.estado_Universidad.apply {
            semaforoEstadoUniversidad.setValues(cumplimiento, fecha)
            observacionesEstadoUniversidad.text = observacion
        }

        if (informacion.year.toInt() >= yearWithExtraKeys) {
            semaforoSEPEstado.showExtra()
            semaforoEstadoUniversidad.showExtra()
        }


    }
}

class FragmentDetalleMinistracionEstatal(
    private val informacion: InformacionGeneralWrapper,
    private val mes: String,
    private val datos: DatosMes
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detalle_ministracion, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detalleMinistracionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        detalleMinistracionBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        detalleMinistracionTitulo.text = mes

        semaforoSEPEstado.visibility = View.GONE
        tituloSemaforoSEPEstado.visibility = View.GONE
        tituloObservacionesSemaforoSEPEstado.visibility = View.GONE

        tituloSemaforoEstadoUniversidad.text =
            context?.getString(R.string.transferencia_estado_universidad_estatal)

        datos.estado_universidad.apply {
            semaforoEstadoUniversidad.setValues(cumplimiento, fecha)
            observacionesEstadoUniversidad.text = observacion
        }

        if (informacion.year.toInt() >= yearWithExtraKeys)
            semaforoEstadoUniversidad.showExtra()

    }
}