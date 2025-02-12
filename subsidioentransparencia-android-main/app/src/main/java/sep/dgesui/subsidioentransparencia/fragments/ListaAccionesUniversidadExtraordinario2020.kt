package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentItemListBinding
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

    private var _binding: FragmentItemListBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.listTitle.text = requireContext().getString(R.string.acciones_por_emprender_universidad)

        binding.listBackButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.listItemsRecycler.adapter =
            ItemListCardRecyclerAdapter(
                acciones ?: emptyList(),
                requireActivity(),
                targetFactory
            )

        if (materialesSuministros != null)
            binding.buttonMateriales.apply {
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
            binding.buttonServiciosGenerales.apply {
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
            binding.buttonAsignacionesSubsidios.apply {
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
            binding.buttonBienesMueblesInmuebles.apply {
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
            binding.buttonObrasRemodelaciones.apply {
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
            binding.titleReferencias.visibility = View.VISIBLE
            binding.listReferenciasRecycler.visibility = View.VISIBLE
            binding.listReferenciasRecycler.adapter = ReferenciasAdapter(referencias)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}