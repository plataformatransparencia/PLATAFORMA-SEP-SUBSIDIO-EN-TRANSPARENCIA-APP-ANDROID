package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleMinistracionExtraordinariaBinding
import sep.dgesui.subsidioentransparencia.tableroext.minfed.Ministracion

class DetalleMinistracionFederalExtraordinaria(
    private val informacion: InformacionGeneralWrapper,
    private val ministracion: Ministracion
) : Fragment() {
    private var _binding: FragmentDetalleMinistracionExtraordinariaBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleMinistracionExtraordinariaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detalleMinistracionExtraordinariaHeader.setValues(
            informacion.nombreUniversidad, informacion.subsidio, informacion.year, requireContext()
        )

        binding.detalleMinistracionExtraordinariaBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.detalleMinistracionExtraordinariaTitle.text = currencyFormatter.format(ministracion.monto)

        binding.detalleMinistracionExtraordinariaTitleSepEstado.visibility = View.VISIBLE
        binding.detalleMinistracionExtraordinariaSepEstado.visibility = View.VISIBLE
        binding.detalleMinistracionExtraordinariaTitleSepEstadoFecha.visibility = View.VISIBLE
        binding.detalleMinistracionExtraordinariaSepEstadoFecha.visibility = View.VISIBLE

        binding.detalleMinistracionExtraordinariaSepEstado.text =
            currencyFormatter.format(ministracion.sep_estado)
        binding.detalleMinistracionExtraordinariaSepEstadoFecha.text = ministracion.fechaEjecucionEstado
        binding.detalleMinistracionExtraordinariaEstadoUniversidad.text =
            currencyFormatter.format(ministracion.estado_universidad)
        binding.detalleMinistracionExtraordinariaEstadoUniversidadFecha.text =
            ministracion.fechaEjecucionUniversidad
        binding.detalleMinistracionExtraordinariaObservacion.text = ministracion.observacion


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}