package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleAccionesBinding
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleCumplimientoProfexceBinding
import sep.dgesui.subsidioentransparencia.engineadapter.EntregaCompromisoAdapter

class DetalleComplexItemFragment(
    private val informacion: InformacionGeneralWrapper,
    private val titulo: String,
    private val item: ComplexItem,
) : Fragment() {

    private var _binding: FragmentDetalleAccionesBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleAccionesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detalleAccionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )
        binding.detalleAccionBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.detalleAccionTitle.text = titulo
        binding.subtitulo.text = titulo


        binding.accionesDescripcion.text = item.descripcion

        binding.detalleCumplimientos.adapter = EntregaCompromisoAdapter(item.cumplimientos)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class DetalleTableroCumplimientoProfexceFragment(
    private val informacion: InformacionGeneralWrapper,
    private val item: Item,
) : Fragment() {

    private var _binding: FragmentDetalleCumplimientoProfexceBinding? = null
    private val binding get() = _binding!!

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
        super.onViewCreated(view, savedInstanceState)
        binding.detalleAccionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )
        binding.detalleAccionBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.accionesDescripcion.text = item.descripcion
        binding.fechaLimite.text = item.fechaCompromiso

        binding.cumplimientoProfexceSemaforo.setValues(item.cumplimiento, item.fechaEjecucion)

        if (item.observacion.isNotEmpty()) {
            binding.observaciones.text = item.observacion
        } else {
            binding.observaciones.visibility = View.GONE
            binding.labelObservaciones.visibility = View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
