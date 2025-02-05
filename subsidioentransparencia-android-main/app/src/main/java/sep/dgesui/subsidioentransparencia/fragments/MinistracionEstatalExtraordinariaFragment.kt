package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.databinding.FragmentExtMinEstatalBinding
import sep.dgesui.subsidioentransparencia.tableroext.minest.MinEstatalExt


class MinistracionEstatalExtraordinariaFragment(
    private val informacion: InformacionGeneralWrapper,
    private val ministracion: MinEstatalExt,
) : Fragment() {
    private var _binding: FragmentExtMinEstatalBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
 ): View? {
        _binding = FragmentExtMinEstatalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ministracionExtraordinariaHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.ministracionExtraordinariaBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.ministracionExtraordinarioTituloMontoTotalMinistrado.visibility = View.VISIBLE
        binding.ministracionExtraordinarioMontoTotalMinistrado.visibility = View.VISIBLE
        binding.ministracionExtraordinariaMontos.visibility = View.GONE
        binding.montoTotalEstadoExtraordinario.visibility = View.VISIBLE
        binding.LinkTranferEstatalExtraordinario.visibility = View.VISIBLE
        binding.ministracionExtraordinarioMontoFederacion.visibility = View.GONE
        ministracion.apply {
            //ministracionExtraordinariaMontos.adapter = MontosEstatalAdapter(estatal.ministracion, informacion, requireActivity())
            binding.montoTotalEstadoExtraordinario.text = currencyFormatter.format(totales_adeudos.montoTotalSEP)

//            ministracionExtraordinarioMontoFederacion.text =
//                currencyFormatter.format(totales_adeudos.montoTotalEstado)
            binding.LinkTranferEstatalExtraordinario.text = currencyFormatter.format(totales_adeudos.montoTotalEstado)
            binding.LinkTranferEstatalExtraordinario.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, DetalleMinistracionExtraordinariaEstatal(informacion,ministracion.estatal.ministracion))
                    .addToBackStack(null).commit()

            }
            binding.ministracionExtraordinarioTituloAdeudoFederacion.text = String.format(
                requireContext().getString(R.string.adeudo_estatal_corte),
                totales_adeudos.adeudoEstatal?.fecha ?: ""
            )

            binding.ministracionExtraordinarioAdeudoFederacion.text =
                currencyFormatter.format(totales_adeudos.adeudoEstatal?.adeudo ?: 0.0)

            binding.ministracionExtraordinarioMontoTotalMinistrado.text =
                currencyFormatter.format(totales_adeudos.montoTotal)
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

