package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.databinding.FragmentFedCumplimientoBinding
import sep.dgesui.subsidioentransparencia.getColorCumplimiento


class FedCumplimientoFragment(
    val listCompromisos: ArrayList<String>,
    val id: String,
    val year: String,
    val nombre: String
) : Fragment() {
    private var _binding: FragmentFedCumplimientoBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFedCumplimientoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.YearMinFedCumpl.text = year.substring(0, 4)
        binding.titleUni.text = nombre
        binding.titleMesFed.text = listCompromisos[6]
        binding.buttonBackTablero.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val colorSepFed = getColorCumplimiento(listCompromisos[0], view.context)

        binding.cardViewcumplimientoSEPFed.setCardBackgroundColor(colorSepFed)



        binding.fechaCumplimientoSEPFed.text = listCompromisos[1]
        binding.visibleObservacionesSEPFed.isVisible = false
        if (listCompromisos[2].isNotEmpty()) {
            binding.visibleObservacionesSEPFed.isVisible = true
            binding.textObservacionSEPFed.text = listCompromisos[2]
        }

        val colorCumplimientoEstFed = getColorCumplimiento(listCompromisos[3], view.context)
        binding.cardViewcumplimientoEstFed.setCardBackgroundColor(colorCumplimientoEstFed)

        binding.fechaCumplimientoEstFed.text = listCompromisos[4]

        binding.visibleObservacionesEstFed.isVisible = false
        if (listCompromisos[5].isNotEmpty()) {
            binding.visibleObservacionesEstFed.isVisible = true
            binding.textObservacionEstFed.text = listCompromisos[5]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}