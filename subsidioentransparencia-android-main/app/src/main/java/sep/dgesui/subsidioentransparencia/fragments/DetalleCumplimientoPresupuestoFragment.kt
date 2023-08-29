package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.component_semaforo_smple.view.*
import kotlinx.android.synthetic.main.fragment_detalle_cumplimiento_profexce.*
import kotlinx.android.synthetic.main.fragment_detalle_cumplimiento_profexce.detalleAccionBack
import kotlinx.android.synthetic.main.fragment_detalle_cumplimiento_profexce.detalleAccionHeader
import kotlinx.android.synthetic.main.fragment_detalle_cumplimiento_profexce.detalleAccionTitle
import kotlinx.android.synthetic.main.item_compromisos_list.view.*
import kotlinx.android.synthetic.main.layout_compromiso_card.view.*
import kotlinx.android.synthetic.main.layout_cumplimiento_card.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper

open class DetalleCumplimientoPresupuestoFragment(
    private val informacion: InformacionGeneralWrapper,
    private val item: ItemPresupuesto,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detalle_cumplimiento_profexce, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        detalleAccionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        detalleAccionBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        accionesDescripcion.text = item.descripcion
        fechaLimite.text = item.fechaCompromiso
        cumplimientoProfexceSemaforo.cardTitleFechaEjecucion.text = item.tipo
        cumplimientoProfexceSemaforo.bextemp.visibility = View.VISIBLE
        cumplimientoProfexceSemaforo.textemp.visibility = View.VISIBLE
        cumplimientoProfexceSemaforo.setValues(item.cumplimiento, item.fechaEjecucion )
        if (item.observacion.isNotEmpty()) {
            observaciones.text = item.observacion
        } else {
            observaciones.visibility = View.GONE
            labelObservaciones.visibility = View.GONE
        }


    }
}


class DetalleAccionCumplimientoPresupuestoFragment(informacion: InformacionGeneralWrapper, item: ItemPresupuesto) :
    DetalleCumplimientoPresupuestoFragment(informacion, item) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lblAccion = requireContext().getString(R.string.accion)

        detalleAccionTitle.text = lblAccion
        subtitulo.text = lblAccion

    }
}
