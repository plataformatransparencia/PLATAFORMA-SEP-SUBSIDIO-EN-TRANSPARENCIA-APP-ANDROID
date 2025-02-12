package sep.dgesui.subsidioentransparencia.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.databinding.FragmentPoliticasBinding
import sep.dgesui.subsidioentransparencia.engineadapter.Filter

class PoliticaFragment : Fragment(){
    var filter = Filter.filter

    private var _binding: FragmentPoliticasBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPoliticasBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonBackPoliticas.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.textCorreo.setOnClickListener {
            val intenEmail = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("subsidiotransparencia@nube.sep.gob.mx"))
            }
            startActivity(intenEmail)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
