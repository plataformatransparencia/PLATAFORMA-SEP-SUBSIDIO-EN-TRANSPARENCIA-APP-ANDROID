package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetailEntregaCompromisoBinding
import sep.dgesui.subsidioentransparencia.tableroext.compromisos.Entrega

class DetailEntregaCompromisoFragment(
    private val universidad: String,
    private val year: String,
    private val entrega: Entrega
) : Fragment() {

    private var _binding: FragmentDetailEntregaCompromisoBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailEntregaCompromisoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBackEntregaCompromiso.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }


    override fun onResume() {
        super.onResume()

        binding.encabezado.text =
            String.format(resources.getString(R.string.upe_deficit_financiero), year)

        binding.nombreUniversidad.text = universidad

        binding.descripcionEntrega.text = entrega.entrega

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
