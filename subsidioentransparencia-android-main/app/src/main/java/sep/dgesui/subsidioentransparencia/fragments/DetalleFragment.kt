package sep.dgesui.subsidioentransparencia.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import kotlinx.coroutines.*
import sep.dgesui.subsidioentransparencia.*
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleBinding
import sep.dgesui.subsidioentransparencia.engineadapter.FichaRepository
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.engineadapter.NotaRepository
import sep.dgesui.subsidioentransparencia.engineadapter.ReferenciasAdapter
import sep.dgesui.subsidioentransparencia.model.Detalle
import sep.dgesui.subsidioentransparencia.model.Universidade
import sep.dgesui.subsidioentransparencia.modelreferencias.Referencias


class DetalleFragment(
    private val id: String,
    private val year: String,
    private val nombre: String,
    private val subsidio: String,
    private val back_to_page: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) :
    BottomSheetDialogFragment() {
    private val fichaRepository = FichaRepository()
    private val notasRepository = NotaRepository()

    private lateinit var notaMontoDecider: ChartNotaDecider

    val informacion = InformacionGeneralWrapper(id, year, nombre, subsidio)

    var filter = Filter.filter

    var listUni: List<Universidade> = emptyList()

    init {
        filter.content.observe(this) { listaRecibida: List<Universidade> ->
            listUni = listaRecibida
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val d = dialog as BottomSheetDialog

        d.behavior.peekHeight = run {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
        d.behavior.skipCollapsed = false
        d.behavior.isDraggable = false
        d.dismissWithAnimation = true
        return dialog
    }


    override fun onResume() {
        super.onResume()
        if (filter.selectDetalle)
            dismiss()
    }

    private var _binding: FragmentDetalleBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val thisDetalle = this

        runBlocking {

            notaMontoDecider = ChartNotaDecider(binding.textNotaMonto)


            activarTableros(thisDetalle, informacion, requireActivity())

            binding.headerDetalleUniversidad.setValues(
                informacion.nombreUniversidad,
                informacion.subsidio,
                informacion.year,
                requireContext()
            )

            binding.generalesUniversidad.setOnClickListener {
                if (back_to_page == "map"){
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(
                            R.id.main_fragment_container,
                            MapsFragment()
                        ).commit()
                    }
                }else{
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                }

            }

            launch { loadDetalle() }

            launch { loadReferencias() }

            launch { loadNotas() }

        }

    }

    private fun loadNotas() = runBlocking {
        val notas = withContext(dispatcher) { notasRepository.notas(year, id, subsidio) }

        if (notas != null)
            binding.textoNotaGlobalNotas.text = notas.nota_global

    }

    private fun loadReferencias() = runBlocking {

        if(year >= "2025"){
            binding.titlereferenciaAnexoDetalle2025plus.visibility = View.VISIBLE
            binding.referenciasAnexo2025plus.visibility = View.VISIBLE
            binding.titleReferenciasDetalle.visibility = View.GONE
            binding.referenciasNumeralia.visibility = View.GONE
            binding.titleOtrasReferencias.visibility = View.GONE
        }

        val referencias = async(dispatcher) { fichaRepository.referencias(year) }.await()


        binding.referenciasNumeralia.adapter =
            ReferenciasAdapter(referencias?.numeralia ?: emptyMap())

        binding.referenciasAnexo2025plus.adapter =
            ReferenciasAdapter(referencias?.numeralia ?: emptyMap())

        when (subsidio) {
            "subsidio_extraordinario" -> cargarReferenciasSubsidioExtraordinario(
                referencias
            )
            "subsidio_profexce" -> cargarReferenciasSubsidioProfexce(referencias)

            "subsidio_ordinario" -> cargarReferenciasSubsidioOrdinario(
                referencias,
            )
            "subsidio_presupuesto" -> cargarReferenciasSubsidioPresupuesto(
                referencias,
            )

        }

    }

    private fun cargarReferenciasSubsidioProfexce(referencias: Referencias?) {
        binding.titleOtrasReferencias.visibility = View.GONE
        binding.referenciasOtras.visibility = View.GONE

        if (referencias?.subsidio_profexce?.anexo != null) {

            binding.titlereferenciaAnexoDetalle.text =
                requireContext().getString(R.string.label_referencias)

            binding.referenciasAnexo.adapter =
                ReferenciasAdapter(referencias.subsidio_profexce.anexo)

        } else {
            binding.titlereferenciaAnexoDetalle.isVisible = false
        }
    }

    private fun cargarReferenciasSubsidioExtraordinario(
        referencias: Referencias?,
    ) {
        binding.titleOtrasReferencias.visibility = View.GONE
        binding.referenciasOtras.visibility = View.GONE

        if (referencias?.subsidio_extraordinario?.get("nota monto") != null)
            referencias.subsidio_extraordinario["nota monto"].apply {
                binding.textNotaMonto.text = this
                notaMontoDecider.nota = this
            }
        else
            binding.textNotaMonto.visibility = View.GONE

        if (referencias?.subsidio_extraordinario?.get("nota global") != null)
            binding.textoNotaGlobalReferencias.text = referencias.subsidio_extraordinario["nota global"]
        else
            binding.textoNotaGlobalReferencias.visibility = View.GONE

    }

    private fun cargarReferenciasSubsidioOrdinario(
        referencias: Referencias?,
    ) {
        binding.notaRedondeo.visibility = View.VISIBLE

        if (referencias?.subsidio_ordinario?.anexo != null) {

            binding.titlereferenciaAnexoDetalle.text = String.format(
                requireContext().getString(R.string.referencias_anexo),
                year
            )

            binding.referenciasAnexo.adapter =
                ReferenciasAdapter(referencias.subsidio_ordinario.anexo)

        } else {
            binding.titlereferenciaAnexoDetalle.isVisible = false
        }

        if (referencias?.subsidio_ordinario?.otras != null) {
            binding.referenciasOtras.adapter =
                ReferenciasAdapter(referencias.subsidio_ordinario.otras)
        } else {
            binding.titleOtrasReferencias.isVisible = false
        }
    }

    private fun cargarReferenciasSubsidioPresupuesto(
        referencias: Referencias?,
    ) {
        binding.notaRedondeo.visibility = View.VISIBLE

        if (referencias?.subsidio_presupuesto?.anexo != null) {

            binding.titlereferenciaAnexoDetalle.text = String.format(
                requireContext().getString(R.string.referencias_presupuesto)
            )

            binding.referenciasAnexo.adapter =
                ReferenciasAdapter(referencias.subsidio_presupuesto.anexo)

        } else {
            binding.titlereferenciaAnexoDetalle.isVisible = false
        }
        binding.titleOtrasReferencias.isVisible = false

    }

    private fun loadDetalle() = runBlocking {


        val detalle = async(dispatcher) { fichaRepository.ficha(year, id, subsidio) }.await()!!


        cargarInformacion(detalle)
        cargarNumeralia(detalle)
        cargarGrafica(detalle)
        cargarDocumentos(detalle)
    }


    private fun cargarGrafica(detalle: Detalle) {
        detalle.montos.apply {

            val mapaMontos = mutableMapOf<String, Double>()

            if (montoFederal == 0.0 && montoEstatal == 0.0 && montoPublico > 0)
                mapaMontos["Monto Público"] = montoPublico

            if(subsidio.equals("subsidio_presupuesto")){
                if (montoFederal > 0)
                    mapaMontos["Convenio Vertiente A "] = montoFederal

                if (montoEstatal > 0)
                    mapaMontos["Convenio Vertiente C"] = montoEstatal
            }else{
                if (montoFederal > 0)
                    mapaMontos["Monto Federal"] = montoFederal

                if (montoEstatal > 0)
                    mapaMontos["Monto Estatal"] = montoEstatal
            }




            notaMontoDecider.montos = mapaMontos

            if (mapaMontos.isNotEmpty())
                binding.chartMontos.plot(mapaMontos, subsidio)
            else
                binding.chartMontos.visibility = View.GONE

        }
    }

    private fun cargarDocumentos(detalle: Detalle) {
        if (detalle.planAusteridad.isNotBlank()) {
            if(year >= "2025"){
                binding.buttonPlanAusteridad.visibility = View.GONE
                binding.buttonPlanAusteridad2025plus.apply {
                    visibility = View.VISIBLE
                    setOnClickListener(externalLink(detalle.planAusteridad))
                }
            }
            binding.buttonPlanAusteridad.apply {

                visibility = View.VISIBLE

                setOnClickListener(externalLink(detalle.planAusteridad))
            }
        }

        if (!detalle.anexoEjecucion.isNullOrEmpty()) {
            binding.titleNumeraliaDetalleProceso.text = "(Institución en proceso de consolidación)"
            binding.buttonAnexoEjecucion.apply {
                visibility = View.VISIBLE

                text = String.format(
                    requireContext().getString(R.string.anexo_ejecucion),
                    year
                )

                setOnClickListener(externalLink(detalle.anexoEjecucion))
            }
        }
        if (!detalle.MarcoColaboracion.isNullOrBlank())
            binding.buttonConvenioMarco.apply {
                visibility = View.VISIBLE
                text = String.format(
                    requireContext().getString(R.string.convenio_marco),
                    detalle.MarcoAnio
                )

                setOnClickListener(externalLink(detalle.MarcoColaboracion))
            }

        if (!detalle.convenio.isNullOrBlank())

            if(detalle.convenio.contains(",")){
                val urls = detalle.convenio.split(",")
                binding.buttonConvenio.apply {
                    visibility = View.VISIBLE
                    val tipoConvenio = urls[0].split("*")
                    text = String.format(
                        requireContext().getString(R.string.convenio),
                        "Vertiente "+tipoConvenio[0]
                    )

                    setOnClickListener(externalLink(tipoConvenio[1]))

                }

                binding.buttonConvenio2.apply {
                    visibility = View.VISIBLE
                    val tipoConvenio = urls[1].split("*")
                    text = String.format(
                        requireContext().getString(R.string.convenio),
                        "Vertiente "+tipoConvenio[0]
                    )

                    setOnClickListener(externalLink(tipoConvenio[1]))

                }

            }else{

                when (subsidio) {
                    "subsidio_presupuesto" -> {
                        binding.buttonConvenio.apply {
                            visibility = View.VISIBLE
                            val tipoConvenio = detalle.convenio.split("*")
                            text = String.format(
                                requireContext().getString(R.string.convenio),
                                "Vertiente "+tipoConvenio[0]
                            )

                            setOnClickListener(externalLink(tipoConvenio[1]))

                        }
                    }
                    else -> {
                        binding.buttonConvenio.apply {
                            visibility = View.VISIBLE
                            text = String.format(
                                requireContext().getString(R.string.convenio),
                                year
                            )

                            setOnClickListener(externalLink(detalle.convenio))

                        }
                    }
                }

            }


    }

    private fun cargarNumeralia(detalle: Detalle) {
        detalle.numeralia.apply {
            binding.titleNumeraliaDetalleProceso.visibility = View.GONE
            if (higherEducationEnrolment == 0 &&
                highSchoolEnrolment == 0 &&
                enrolmentTotal == 0 &&
                fullTimeProfessorsTotal == 0 &&
                desirableProfileProfessor == 0 &&
                nationalSystemResearchersProfessor == 0 &&
                studentAllowance == 0.0
                ){
                binding.titleNumeraliaDetalleProceso.visibility = View.VISIBLE
                binding.titleNumeraliaDetalleProceso.text = "(Institución en proceso de creación)"
                binding.numMatriculaTotalESDetalle.visibility = View.GONE
                binding.textMatriculaTotalESDetalle.visibility = View.GONE
                binding.numMatriculaTotalEMDetalle.visibility = View.GONE
                binding.textMatriculaTotalEMDetalle.visibility = View.GONE
                binding.numMatriculaTotalDetalle.visibility = View.GONE
                binding.textMatriculaTotalDetalle.visibility = View.GONE
                binding.numTotalProfesoresTCDetalle.visibility = View.GONE
                binding.textTotalProfesoresTCDetalle.visibility = View.GONE
                binding.numTotalProfesoresTCPDVDetalle.visibility = View.GONE
                binding.textTotalProfesoresTCPDVDetalle.visibility = View.GONE
                binding.numProfesoresSistemaNacionalIVDetalle.visibility = View.GONE
                binding.textProfesoresSistemaNacionalIVDetalle.visibility = View.GONE
                binding.numSubsidioAlumnoFEDetalle.visibility = View.GONE
                binding.textSubsidioAlumnoFEDetalle.visibility = View.GONE
            }
            binding.numMatriculaTotalESDetalle.text =
                integerFormatter.format(higherEducationEnrolment)

            binding.numMatriculaTotalEMDetalle.text =
                integerFormatter.format(highSchoolEnrolment)

            binding.numMatriculaTotalDetalle.text =
                integerFormatter.format(enrolmentTotal)

            binding.numTotalProfesoresTCDetalle.text =
                integerFormatter.format(fullTimeProfessorsTotal)

            binding.numTotalProfesoresTCPDVDetalle.text =
                integerFormatter.format(desirableProfileProfessor)

            binding.numProfesoresSistemaNacionalIVDetalle.text =
                integerFormatter.format(nationalSystemResearchersProfessor)
            if (year < "2025"){
                binding.numSubsidioAlumnoFEDetalle.text =
                    currencyFormatter.format(studentAllowance)
            }


            binding.numPorcentajeParticipacionFDetalle.text = String.format(
                porcentaje_template,
                federationOwnershipPercentage
            )
            binding.numPorcentajeParticipacionEDetalle.text =
                String.format(
                    porcentaje_template,
                    stateOwnershipPercentage
                )

        }
    }

    private fun cargarInformacion(detalle: Detalle) {

        binding.generalesUniversidad.setValues(
            detalle.siglas, detalle.nombre,
            detalle.webUrl, detalle.escudo
        )

        binding.direccionUniversidad.text = detalle.direccion
        binding.rectorUniversidad.text = detalle.rector
        binding.titleRectorDetalle.text = detalle.rectorCargo
        binding.gobernador.text = detalle.gobernador
        binding.titleGobernadorDetalle.text = detalle.gobernadorCargo
        binding.linkSitioTransparencia.apply {

            Link(context.getString(R.string.ir_a_sitio))
                .setHighlightAlpha(0.4F)
                .setTextColor(ContextCompat.getColor(context, R.color.gob_gold))
                .setUnderlined(false)
                .setOnClickListener {
                    ContextCompat.startActivity(
                        context,
                        Intent.getIntentOld(
                            Uri.parse(detalle.transparencyUrl).toString()
                        ),
                        null
                    )
                }.also {
                    applyLinks(it)
                }
        }
    }


    inner class ChartNotaDecider(
        private val notaMonto: TextView,
    ) {
        var montos: Map<String, Double>? = null
            set(value) {
                field = value
                decide()
            }

        var nota: String? = null
            set(value) {
                field = value
                decide()
            }

        private fun decide() {
            if (nota != null && montos != null && montos!!.isNotEmpty())
                notaMonto.visibility = View.VISIBLE
            else
                notaMonto.visibility = View.GONE
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
