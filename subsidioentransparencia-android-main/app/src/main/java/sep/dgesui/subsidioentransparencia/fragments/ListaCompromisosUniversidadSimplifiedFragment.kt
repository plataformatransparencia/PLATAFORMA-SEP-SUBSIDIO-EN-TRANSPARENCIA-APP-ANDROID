package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_item_list.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper

open class ListaCompromisosUniversidadSimplifiedFragment(
    protected val informacion: InformacionGeneralWrapper,
    protected val compromisos: List<Item>,
) : Fragment() {

    private val targetFactory =
        { item: Item -> DetalleCompromisoUniversidadFragment(informacion, item) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_item_list, container, false)

    open fun loadData() {

        listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        listTitle.text = requireContext().getString(R.string.compromisos)

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
    }
}