package sep.dgesui.subsidioentransparencia.fragments

import kotlinx.android.synthetic.main.fragment_item_list.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper

class ListaCompromisosEstadoProfexce(
    informacion: InformacionGeneralWrapper,
    compromisos: List<Item>
) :
    ListaCompromisosUniversidadSimplifiedFragment(informacion, compromisos) {

    private val targetFactory = { item: Item ->
        DetalleCompromisoEstadoProfexceFragment(informacion, item)
    }

    override fun loadData() {
        listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        listTitle.text = requireContext().getString(R.string.compromisos_estado)

        listBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        listItemsRecycler.adapter =
            ItemListCardRecyclerAdapter(
                compromisos,
                requireActivity(),
                targetFactory
            )
    }
}
