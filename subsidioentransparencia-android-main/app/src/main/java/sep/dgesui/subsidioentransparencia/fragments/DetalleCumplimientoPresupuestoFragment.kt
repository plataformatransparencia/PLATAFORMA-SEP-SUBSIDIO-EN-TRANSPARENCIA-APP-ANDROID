package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleCumplimientoProfexceBinding
import sep.dgesui.subsidioentransparencia.databinding.LayoutCumplimientoCardBinding

open class DetalleCumplimientoPresupuestoFragment(
    private val informacion: InformacionGeneralWrapper,
    private val item: ItemPresupuesto,
) : Fragment() {

    private var _binding: FragmentDetalleCumplimientoProfexceBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleCumplimientoProfexceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        binding.detalleAccionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.detalleAccionBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val bnc = LayoutCumplimientoCardBinding.bind(view)
        binding.accionesDescripcion.text = item.descripcion
        binding.fechaLimite.text = item.fechaCompromiso
        bnc.cardTitleFechaEjecucion.text = item.tipo
        bnc.bextemp.visibility = View.VISIBLE
        bnc.textemp.visibility = View.VISIBLE
        binding.cumplimientoProfexceSemaforo.setValues(item.cumplimiento, item.fechaEjecucion )
        if (item.observacion.isNotEmpty()) {
            binding.observaciones.text = item.observacion
        } else {
            binding.observaciones.visibility = View.GONE
            binding.labelObservaciones.visibility = View.GONE
        }


    }
}


class DetalleAccionCumplimientoPresupuestoFragment(informacion: InformacionGeneralWrapper, item: ItemPresupuesto) :
    DetalleCumplimientoPresupuestoFragment(informacion, item) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lblAccion = requireContext().getString(R.string.accion)

        binding.detalleAccionTitle.text = lblAccion
        binding.subtitulo.text = lblAccion

    }
}
