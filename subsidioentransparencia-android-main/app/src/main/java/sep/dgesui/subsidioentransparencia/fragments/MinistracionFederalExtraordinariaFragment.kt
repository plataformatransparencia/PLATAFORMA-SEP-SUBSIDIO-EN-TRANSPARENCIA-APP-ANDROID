package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.databinding.FragmentExtMinEstatalBinding
import sep.dgesui.subsidioentransparencia.tableroext.minfed.MinFederalExt
import sep.dgesui.subsidioentransparencia.tableroext.minfed.Ministracion

class MinistracionFederalExtraordinariaFragment(
    private val informacion: InformacionGeneralWrapper,
    private val ministracionFederal: MinFederalExt?,
    private val ministracionFederalPres: Ministracion?
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
            informacion.nombreUniversidad, informacion.subsidio, informacion.year, requireContext()
        )

        binding.ministracionExtraordinariaBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.ministracionExtraordinariaTitle.text =
            context?.getString(R.string.tablero_sep_estado_universidad)

        binding.ministracionExtraordinariaMontos.visibility = View.GONE
        binding.ministracionExtraordinariaFederalLink.visibility = View.VISIBLE
        binding.ministracionExtraordinarioTituloMontoTotalMinistrado.visibility = View.GONE
        binding.ministracionExtraordinarioMontoTotalMinistrado.visibility = View.GONE

        if(ministracionFederal != null){
            ministracionFederal.federal.apply {
                binding.ministracionExtraordinariaFederalLink.setLabel(currencyFormatter.format(ministracion.monto))
                binding.ministracionExtraordinariaFederalLink.setTarget(
                    loadFragment(
                        DetalleMinistracionFederalExtraordinaria(informacion, ministracion),
                        requireActivity()
                    )
                )

            }

            ministracionFederal.totales_adeudos.apply {

                binding.ministracionExtraordinarioAdeudoFederacion.visibility = View.VISIBLE
                binding.ministracionExtraordinarioTituloAdeudoFederacion.visibility = View.VISIBLE
                binding.ministracionExtraordinarioTituloAdeudoFederacion.text =
                    String.format(
                        requireContext().getString(R.string.adeudo_federal_corte),
                        adeudoFederal?.fecha ?: ""
                    )

                binding.ministracionExtraordinarioAdeudoFederacion.isVisible = true
                binding.ministracionExtraordinarioMontoFederacion.text =
                    currencyFormatter.format(montoTotalSEP)

                binding.ministracionExtraordinarioAdeudoFederacion.text =
                    currencyFormatter.format(adeudoFederal?.adeudo ?: 0)

            }
        }

        if(ministracionFederalPres != null){

            ministracionFederalPres.apply {
                binding.ministracionExtraordinariaFederalLink.setLabel(currencyFormatter.format(monto))
                binding.ministracionExtraordinariaFederalLink.setTarget(
                    loadFragment(
                        DetalleMinistracionFederalExtraordinaria(informacion, ministracionFederalPres),
                        requireActivity()
                    )
                )
                binding.ministracionExtraordinarioMontoFederacion.text = currencyFormatter.format(estado_universidad)
            }

            binding.ministracionExtraordinarioAdeudoFederacion.visibility = View.GONE
            binding.ministracionExtraordinarioTituloAdeudoFederacion.visibility = View.GONE


        }
    }
}