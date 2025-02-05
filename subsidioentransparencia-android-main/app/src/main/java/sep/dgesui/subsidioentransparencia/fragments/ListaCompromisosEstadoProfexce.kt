package sep.dgesui.subsidioentransparencia.fragments

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
        binding.listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.listTitle.text = requireContext().getString(R.string.compromisos_estado)

        binding.listBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.listItemsRecycler.adapter =
            ItemListCardRecyclerAdapter(
                compromisos,
                requireActivity(),
                targetFactory
            )
    }
}
