package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleReporteBinding

class DetalleReporteFragment(
    private val informacion: InformacionGeneralWrapper,
    private val titulo:String,
    private val item: Item
) : Fragment() {
    private var _binding: FragmentDetalleReporteBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleReporteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detalleReporteTitulo.text = titulo

        binding.detalleReporteBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.detalleReporteSemaforo.showData(item.cumplimiento, informacion.year)


        if (item.observacion.isNotEmpty()) {
            binding.detalleReporteObservaciones.text = item.observacion
        } else {
            binding.detalleReporteObservaciones.visibility = View.GONE
            binding.detalleReporteTituloObservaciones.visibility = View.GONE
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}