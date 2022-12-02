package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_item_list.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.engineadapter.ReferenciasAdapter

class ListaAccionesUniversidadExtraordinario2020(
    private val informacion: InformacionGeneralWrapper,
    private val referencias: Map<String, String>?,
    private val acciones: List<Item>?,
    private val materialesSuministros: List<Item>? = null,
    private val serviciosGenerales: List<Item>? = null,
    private val asignacionesSubsidios: List<Item>? = null,
    private val bienesMueblesInmuebles: List<Item>? = null,
    private val obrasRemodelaciones: List<Item>? = null,
) : Fragment() {

    private val targetFactory = { item: Item ->
        DetalleAccionUniversidadFragment(informacion, item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_item_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        listTitle.text = requireContext().getString(R.string.acciones_por_emprender_universidad)

        listBackButton.setOnClickListener { requireActivity().onBackPressed() }

        listItemsRecycler.adapter =
            ItemListCardRecyclerAdapter(
                acciones ?: emptyList(),
                requireActivity(),
                targetFactory
            )

        if (materialesSuministros != null)
            buttonMateriales.apply {
                visibility = View.VISIBLE

                setTarget(
                    loadFragment(
                        ListaAccionesUADECExtraordinario2019(
                            requireContext().getString(R.string.materiales_suministros),
                            informacion,
                            materialesSuministros
                        ), requireActivity()
                    )
                )
            }

        if (serviciosGenerales != null)
            buttonServiciosGenerales.apply {
                visibility = View.VISIBLE

                setTarget(
                    loadFragment(
                        ListaAccionesUADECExtraordinario2019(
                            requireContext().getString(R.string.servicios_generales),
                            informacion,
                            serviciosGenerales
                        ), requireActivity()
                    )
                )
            }

        if (asignacionesSubsidios != null)
            buttonAsignacionesSubsidios.apply {
                visibility = View.VISIBLE

                setTarget(
                    loadFragment(
                        ListaAccionesUADECExtraordinario2019(
                            requireContext().getString(R.string.asignaciones_subsidios),
                            informacion,
                            asignacionesSubsidios
                        ), requireActivity()
                    )
                )
            }

        if (bienesMueblesInmuebles != null)
            buttonBienesMueblesInmuebles.apply {
                visibility = View.VISIBLE

                setTarget(
                    loadFragment(
                        ListaAccionesUADECExtraordinario2019(
                            requireContext().getString(R.string.bienes_muebles_inmuebles),
                            informacion,
                            bienesMueblesInmuebles
                        ), requireActivity()
                    )
                )
            }

        if (obrasRemodelaciones != null)
            buttonObrasRemodelaciones.apply {
                visibility = View.VISIBLE

                setTarget(
                    loadFragment(
                        ListaAccionesUADECExtraordinario2019(
                            requireContext().getString(R.string.obras_remodelaciones),
                            informacion,
                            obrasRemodelaciones
                        ), requireActivity()
                    )
                )
            }

        if (referencias != null) {
            title_referencias.visibility = View.VISIBLE
            listReferenciasRecycler.visibility = View.VISIBLE
            listReferenciasRecycler.adapter = ReferenciasAdapter(referencias)
        }
    }

}