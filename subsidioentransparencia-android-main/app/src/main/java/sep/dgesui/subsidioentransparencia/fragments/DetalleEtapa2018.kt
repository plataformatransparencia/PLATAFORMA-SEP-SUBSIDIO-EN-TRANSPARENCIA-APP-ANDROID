package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleEntapaBinding

class DetalleEtapa2018(
    private val informacion: InformacionGeneralWrapper,
    private val item: Item,
) : Fragment() {
    private var _binding: FragmentDetalleEntapaBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleEntapaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.etapaHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.etapaBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.etapaFechaEstipulada.text = item.fechaCompromiso

        binding.etapaDescripcion.text = item.descripcion
        binding.compromisoCard.setValues(item.cumplimiento, item.fechaEjecucion)

        binding.etapaObservaciones.text = item.observacion

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}