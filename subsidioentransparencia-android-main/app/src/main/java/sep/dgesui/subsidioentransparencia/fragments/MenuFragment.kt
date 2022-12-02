package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_menu.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.engineadapter.Filter


class MenuFragment : Fragment(){
    var filter = Filter.filter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?

    ): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)

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


        buttonProgramas.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, ProgramsFragment()).addToBackStack(null).commit()
        }
    }



}