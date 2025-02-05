package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.databinding.FragmentMatriculaFedBinding
import sep.dgesui.subsidioentransparencia.getColorCumplimiento

class MatriculaFedFragment(
    val listMatricula: ArrayList<String>,
    val id: String,
    val uni: String,
    val year: String
) : Fragment() {

    private var _binding: FragmentMatriculaFedBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatriculaFedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.YearMatricula.text = year.substring(0, 4)
        binding.titleUniMat.text = uni
        binding.buttonBackMatricula.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val colorCumplimientoMatricula = getColorCumplimiento(listMatricula[1], context)
        binding.cardViewcumplimientoMatricula.setCardBackgroundColor(colorCumplimientoMatricula)

        when (listMatricula[1]) {
            "Cumplió" -> binding.textMatriculaTrimestralCumplio.text = "Cumplió"
            "No cumplió" -> binding.textMatriculaTrimestralCumplio.text = "No Cumplió"
            "En revisión" -> binding.textMatriculaTrimestralCumplio.text = "En Revisión"
            else -> {
                binding.textMatriculaTrimestralCumplio.text = "No Cumplió"
            }
        }

        binding.visibleObservacionesMatricula.isVisible = false
        if (listMatricula[2].isNotEmpty()) {
            binding.visibleObservacionesMatricula.isVisible = true
            binding.textObservacionMatricula.text = listMatricula[2]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}