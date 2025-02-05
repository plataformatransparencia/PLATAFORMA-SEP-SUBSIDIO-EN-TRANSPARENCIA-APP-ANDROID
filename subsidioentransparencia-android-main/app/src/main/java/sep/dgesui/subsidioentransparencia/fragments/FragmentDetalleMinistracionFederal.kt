package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleMinistracionBinding
import sep.dgesui.subsidioentransparencia.tablero.DatosFederal
import sep.dgesui.subsidioentransparencia.tablero.estado.DatosMes

class FragmentDetalleMinistracionFederal(
    private val informacion: InformacionGeneralWrapper,
    private val mes: String,
    private val datos: DatosFederal
) : Fragment() {
    private var _binding: FragmentDetalleMinistracionBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleMinistracionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detalleMinistracionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.detalleMinistracionBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.detalleMinistracionTitulo.text = mes

        datos.sep_estado.apply {
            binding.semaforoSEPEstado.setValues(cumplimiento, fecha)
            binding.observacionesSEPEstado.text = observacion
        }

        datos.estado_Universidad.apply {
            binding.semaforoEstadoUniversidad.setValues(cumplimiento, fecha)
            binding.observacionesEstadoUniversidad.text = observacion
        }

        if (informacion.year.toInt() >= yearWithExtraKeys) {
            binding.semaforoSEPEstado.showExtra()
            binding.semaforoEstadoUniversidad.showExtra()
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class FragmentDetalleMinistracionEstatal(
    private val informacion: InformacionGeneralWrapper,
    private val mes: String,
    private val datos: DatosMes
) : Fragment() {
    private var _binding: FragmentDetalleMinistracionBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleMinistracionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detalleMinistracionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.detalleMinistracionBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.detalleMinistracionTitulo.text = mes

        binding.semaforoSEPEstado.visibility = View.GONE
        binding.tituloSemaforoSEPEstado.visibility = View.GONE
        binding.tituloObservacionesSemaforoSEPEstado.visibility = View.GONE

        binding.tituloSemaforoEstadoUniversidad.text =
            context?.getString(R.string.transferencia_estado_universidad_estatal)

        datos.estado_universidad.apply {
            binding.semaforoEstadoUniversidad.setValues(cumplimiento, fecha)
            binding.observacionesEstadoUniversidad.text = observacion
        }

        if (informacion.year.toInt() >= yearWithExtraKeys) {
            binding.semaforoEstadoUniversidad.showExtra()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}