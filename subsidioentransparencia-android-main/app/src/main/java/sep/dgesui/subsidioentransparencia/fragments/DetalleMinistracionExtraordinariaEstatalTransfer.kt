package sep.dgesui.subsidioentransparencia.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleMinistracionExtraordinariaBinding
import sep.dgesui.subsidioentransparencia.tableroext.minest.Ministracion


class DetalleMinistracionExtraordinariaEstatalTransfer(

    private val informacin: InformacionGeneralWrapper,
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

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detalleMinistracionExtraordinariaHeader.setValues(
            informacin.nombreUniversidad,
            informacin.subsidio,
            informacin.year,
            requireContext()
        )

        binding.detalleMinistracionExtraordinariaBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.detalleMinistracionExtraordinariaTitle.text = currencyFormatter.format(ministracion.monto)
         ministracion.apply {

             binding.detalleMinistracionExtraordinariaTitle.text = currencyFormatter.format(monto)

             binding.detalleMinistracionExtraordinariaEstadoUniversidad.text =
                currencyFormatter.format(estado_universidad)

             binding.detalleMinistracionExtraordinariaEstadoUniversidadFecha.text = fechaEjecucionEstado

             binding.detalleMinistracionExtraordinariaObservacion.text = observacion
        }


    }
}

