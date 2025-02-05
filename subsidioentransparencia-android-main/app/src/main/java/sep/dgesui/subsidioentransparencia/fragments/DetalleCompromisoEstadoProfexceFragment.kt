package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleCompromisoEstadoProfexceBinding

open class DetalleCompromisoEstadoProfexceFragment(
    private val informacion: InformacionGeneralWrapper,
    private val item: Item
) : Fragment() {
    private var _binding: FragmentDetalleCompromisoEstadoProfexceBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleCompromisoEstadoProfexceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.detalleEstadoProfexceHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.detalleEstadoProfexceBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.detalleEstadoProfexceDescripcion.text = item.descripcion
        binding.detalleEstadoProfexceFechaEntrega.text = item.fechaCompromiso
        binding.detalleEstadoProfexceObservaciones.text = item.observacion
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}