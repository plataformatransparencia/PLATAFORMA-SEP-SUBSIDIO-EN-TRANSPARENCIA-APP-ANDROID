package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.databinding.FragmentInfRendicionFedBinding
import sep.dgesui.subsidioentransparencia.getColorCumplimiento


class InfRendicionFedFragment(
    val listRendicion: ArrayList<String>,
    val id: String,
    val year: String,
    val uni: String
) : Fragment() {
    private var _binding: FragmentInfRendicionFedBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfRendicionFedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.YearRend.text = year.substring(0, 4)
        binding.titleUni.text = uni
        binding.buttonBackTableroTR.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val colorCumplimientoTrimestreR = getColorCumplimiento(listRendicion[1], context)
        binding.cardViewcumplimientoTrimestreR.setCardBackgroundColor(colorCumplimientoTrimestreR)

        when (listRendicion[1]) {
            "Cumplió" -> binding.textInfRendicionTrimestralCumplio.text = "Cumplió"
            "No cumplió" -> binding.textInfRendicionTrimestralCumplio.text = "No Cumplió"
            "En revisión" -> binding.textInfRendicionTrimestralCumplio.text = "En Revisión"
            else -> {
                binding.textInfRendicionTrimestralCumplio.text = "No Cumplió"
            }
        }

        binding.visibleObservacionesTrimestreR.isVisible = false
        if (listRendicion[2] != "") {
            binding.visibleObservacionesTrimestreR.isVisible = true
            binding.textObservacionTrimestreR.text = listRendicion[2]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}