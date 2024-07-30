package sep.dgesui.subsidioentransparencia.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_contact.textCorreo
import kotlinx.android.synthetic.main.fragment_platform.buttonBackPlataforma
import kotlinx.android.synthetic.main.fragment_politicas.buttonBackPoliticas
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.engineadapter.Filter

class PoliticaFragment : Fragment(){
    var filter = Filter.filter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_politicas, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        buttonBackPoliticas.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        textCorreo.setOnClickListener {
            val intenEmail = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("subsidiotransparencia@nube.sep.gob.mx"))
            }
            startActivity(intenEmail)
        }
    }

}
