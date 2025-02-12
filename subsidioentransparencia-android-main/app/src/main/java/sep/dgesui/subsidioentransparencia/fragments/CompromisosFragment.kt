package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentCompromisosBinding
import sep.dgesui.subsidioentransparencia.databinding.LayoutCumplimientoCardBinding
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.fragments.DetalleFragment.ChartNotaDecider
import sep.dgesui.subsidioentransparencia.model.Universidade


class CompromisosFragment(
    private val id: String,
    private val year: String,
    private val nombre: String,
    private val subsidio: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO):
    BottomSheetDialogFragment() {

    private lateinit var notaMontoDecider: ChartNotaDecider

    val informacion = InformacionGeneralWrapper(id, year, nombre, subsidio)

    var filter = Filter.filter

    var listUni: List<Universidade> = emptyList()


    private var _binding: FragmentCompromisosBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompromisosBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.compromisosHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext(),
        )
        runBlocking {
            binding.compromisosBackButton.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            val compromisos = withContext(dispatcher) {
                itemSources.compromisosOrdinarios(id, year)
            }
            val infoitso = withContext(dispatcher) {
                itemSources.compromisosOrdinarios_itso(id, year)
            }
            val infopef = withContext(dispatcher) {
                itemSources.compromisosOrdinarios_pef(id, year)
            }
            val infomatricula = withContext(dispatcher) {
                itemSources.compromisosOrdinarios_matricula(id, year)
            }


            binding.buttonCompromisos.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(InformacionGeneralWrapper(id,year,nombre,subsidio), compromisos), requireActivity()
                )
            )
            binding.buttonITSO.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(InformacionGeneralWrapper(id,year,nombre,subsidio), infoitso,"Informes ITSO"), requireActivity()
                )
            )
            binding.buttonPEF.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(InformacionGeneralWrapper(id,year,nombre,subsidio), infopef,"Informes PEF"), requireActivity()
                )
            )
            binding.buttonMatricula.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(InformacionGeneralWrapper(id,year,nombre,subsidio), infomatricula,"Informes Matricula Auditada"), requireActivity()
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}