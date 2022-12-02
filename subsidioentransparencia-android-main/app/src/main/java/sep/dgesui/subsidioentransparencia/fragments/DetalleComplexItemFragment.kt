package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detalle_acciones.*
import kotlinx.android.synthetic.main.fragment_detalle_acciones.accionesDescripcion
import kotlinx.android.synthetic.main.fragment_detalle_acciones.detalleAccionBack
import kotlinx.android.synthetic.main.fragment_detalle_acciones.detalleAccionHeader
import kotlinx.android.synthetic.main.fragment_detalle_acciones.detalleAccionTitle
import kotlinx.android.synthetic.main.fragment_detalle_acciones.subtitulo
import kotlinx.android.synthetic.main.fragment_detalle_cumplimiento_profexce.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.engineadapter.EntregaCompromisoAdapter

class DetalleComplexItemFragment(
    private val informacion: InformacionGeneralWrapper,
    private val titulo: String,
    private val item: ComplexItem,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detalle_acciones, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detalleAccionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )
        detalleAccionBack.setOnClickListener { requireActivity().onBackPressed() }

        detalleAccionTitle.text = titulo
        subtitulo.text = titulo


        accionesDescripcion.text = item.descripcion

        detalleCumplimientos.adapter = EntregaCompromisoAdapter(item.cumplimientos)
    }
}

class DetalleTableroCumplimientoProfexceFragment(
    private val informacion: InformacionGeneralWrapper,
    private val item: Item,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detalle_cumplimiento_profexce, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detalleAccionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )
        detalleAccionBack.setOnClickListener { requireActivity().onBackPressed() }

        accionesDescripcion.text = item.descripcion
        fechaLimite.text = item.fechaCompromiso

        cumplimientoProfexceSemaforo.setValues(item.cumplimiento, item.fechaEjecucion)

        if (item.observacion.isNotEmpty()) {
            observaciones.text = item.observacion
        } else {
            observaciones.visibility = View.GONE
            labelObservaciones.visibility = View.GONE
        }
    }
}
