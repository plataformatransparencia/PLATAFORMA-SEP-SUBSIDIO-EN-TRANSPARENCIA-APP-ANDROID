package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detail_entrega_compromiso.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.tableroext.compromisos.Entrega
import timber.log.Timber

class DetailEntregaCompromisoFragment(
    private val universidad: String,
    private val year: String,
    private val entrega: Entrega
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_entrega_compromiso, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonBackEntregaCompromiso.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }


    override fun onResume() {
        super.onResume()

        encabezado.text =
            String.format(resources.getString(R.string.upe_deficit_financiero), year)

        nombreUniversidad.text = universidad

        descripcionEntrega.text = entrega.entrega

        Timber.d("Setting adapter for ${entrega.cumplimientos.size} cumplimientos")

        //TODO Validar que esto no se usa ya
//        linksEntregas.adapter = EntregaCompromisoAdapter(entrega.cumplimientos)

    }
}
