package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_item_list.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper

class ListaAccionesUADECExtraordinario2019(
    private val tituloLista: String,
    private val informacion: InformacionGeneralWrapper,
    private val acciones: List<Item>
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_item_list, container, false)

    private val targetFactory = { item: Item ->
        DetalleAccionUniversidadFragment(informacion, item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        listTitle.text = tituloLista

        listBackButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        listItemsRecycler.adapter =
            ItemListCardRecyclerAdapter(acciones, requireActivity(), targetFactory)
    }
}