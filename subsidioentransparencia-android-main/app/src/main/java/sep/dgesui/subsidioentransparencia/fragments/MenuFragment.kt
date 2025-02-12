package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.FragmentMenuBinding
import sep.dgesui.subsidioentransparencia.engineadapter.Filter


class MenuFragment : Fragment(){
    var filter = Filter.filter

    private var _binding: FragmentMenuBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.main_fragment_container, MapsFragment()).commit()
                }
            }
        })

        binding.buttonProgramas.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, ProgramsFragment()).addToBackStack(null).commit()
        }
        binding.buttonPolitica.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container,PoliticaFragment()).addToBackStack(null).commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}