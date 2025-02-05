package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.databinding.FragmentInfTrimestralFedBinding
import sep.dgesui.subsidioentransparencia.getColorCumplimiento


class InfTrimestralFedFragment(
    val listSO: ArrayList<String>,
    val id: String,
    val year: String,
    val uni: String
) : Fragment() {
    private var _binding: FragmentInfTrimestralFedBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfTrimestralFedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.YearInfTrimestral.text = year.substring(0, 4)
        binding.titleUni.text = uni
        binding.buttonBackTableroTO.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val colorTrimestreO = getColorCumplimiento(listSO[1], context)
        binding.cardViewcumplimientoTrimestreO.setCardBackgroundColor(colorTrimestreO)


        when (listSO[1]) {
            "Cumplió" -> binding.textInfTrimestralCumplio.text = "Cumplió"
            "No cumplió" -> binding.textInfTrimestralCumplio.text = "No Cumplió"
            "En revisión" -> binding.textInfTrimestralCumplio.text = "En Revisión"
            else -> {
                binding.textInfTrimestralCumplio.text = "No Cumplió"
            }
        }

        binding.visibleObservacionesTrimestreO.isVisible = false
        if (listSO[2] != "") {
            binding.visibleObservacionesTrimestreO.isVisible = true
            binding.textObservacionTrimestreO.text = listSO[2]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}