package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.databinding.FragmentEstCumplimientoBinding
import sep.dgesui.subsidioentransparencia.getColorCumplimiento


class EstCumplimientoFragment(
    val listEstCumplimiento: ArrayList<String>,
    val id: String,
    val year: String,
    val nombre: String
) : Fragment() {

    private var _binding: FragmentEstCumplimientoBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEstCumplimientoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.YearMinEstCumpl.text = year.substring(0, 4)
        binding.titleUni.text = nombre
        binding.titleMesEst.text = listEstCumplimiento[0]

        binding.buttonBackMesEst.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val colorCumplimientoMesEst = getColorCumplimiento(listEstCumplimiento[1], context)
        binding.cardViewcumplimientoMesEst.setCardBackgroundColor(colorCumplimientoMesEst)

        binding.fechaCumplimientoMesEst.text = listEstCumplimiento[2]

        if (listEstCumplimiento[3].isNotEmpty()) {
            binding.visibleObservacionesMesEst.isVisible = true
            binding.textObservacionMesEst.text = listEstCumplimiento[3]
        } else {
            binding.visibleObservacionesMesEst.isVisible = false
            binding.visibleObservacionesMesEst.isVisible = false
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}