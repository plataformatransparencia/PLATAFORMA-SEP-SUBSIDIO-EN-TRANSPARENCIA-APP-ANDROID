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
import kotlinx.android.synthetic.main.fragment_detalle.*
import kotlinx.android.synthetic.main.fragment_detalle_compromiso_estado_profexce.detalleEstadoProfexceTitle
import kotlinx.coroutines.*
import sep.dgesui.subsidioentransparencia.*
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.engineadapter.FichaRepository
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.engineadapter.NotaRepository
import sep.dgesui.subsidioentransparencia.engineadapter.ReferenciasAdapter
import sep.dgesui.subsidioentransparencia.model.Detalle
import sep.dgesui.subsidioentransparencia.model.Universidade
import sep.dgesui.subsidioentransparencia.modelreferencias.Referencias
import timber.log.Timber


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
        Timber.d("Loading detail for: Year [$year] ID [$id] Name [$nombre] Subsidio [$subsidio]")


        filter.content.observe(this) { listaRecibida: List<Universidade> ->
            listUni = listaRecibida
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val d = dialog as BottomSheetDialog

        d.behavior.peekHeight = run {
            val displayMetrics = DisplayMetrics()
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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_detalle, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val thisDetalle = this

        runBlocking {

            notaMontoDecider = ChartNotaDecider(textNotaMonto)


            activarTableros(thisDetalle, informacion, requireActivity())

            headerDetalleUniversidad.setValues(
                informacion.nombreUniversidad,
                informacion.subsidio,
                informacion.year,
                requireContext()
            )

            generalesUniversidad.setOnClickListener {
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
            textoNotaGlobalNotas.text = notas.nota_global

    }

    private fun loadReferencias() = runBlocking {

        val referencias = async(dispatcher) { fichaRepository.referencias(year) }.await()


        referenciasNumeralia.adapter =
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
        titleOtrasReferencias.visibility = View.GONE
        referenciasOtras.visibility = View.GONE

        if (referencias?.subsidio_profexce?.anexo != null) {

            titlereferenciaAnexoDetalle.text =
                requireContext().getString(R.string.label_referencias)

            referenciasAnexo.adapter =
                ReferenciasAdapter(referencias.subsidio_profexce.anexo)

        } else {
            titlereferenciaAnexoDetalle.isVisible = false
        }
    }

    private fun cargarReferenciasSubsidioExtraordinario(
        referencias: Referencias?,
    ) {
        titleOtrasReferencias.visibility = View.GONE
        referenciasOtras.visibility = View.GONE

        if (referencias?.subsidio_extraordinario?.get("nota monto") != null)
            referencias.subsidio_extraordinario["nota monto"].apply {
                textNotaMonto.text = this
                notaMontoDecider.nota = this
            }
        else
            textNotaMonto.visibility = View.GONE

        if (referencias?.subsidio_extraordinario?.get("nota global") != null)
            textoNotaGlobalReferencias.text = referencias.subsidio_extraordinario["nota global"]
        else
            textoNotaGlobalReferencias.visibility = View.GONE

    }

    private fun cargarReferenciasSubsidioOrdinario(
        referencias: Referencias?,
    ) {
        notaRedondeo.visibility = View.VISIBLE

        if (referencias?.subsidio_ordinario?.anexo != null) {

            titlereferenciaAnexoDetalle.text = String.format(
                requireContext().getString(R.string.referencias_anexo),
                year
            )

            referenciasAnexo.adapter =
                ReferenciasAdapter(referencias.subsidio_ordinario.anexo)

        } else {
            titlereferenciaAnexoDetalle.isVisible = false
        }

        if (referencias?.subsidio_ordinario?.otras != null) {
            referenciasOtras.adapter =
                ReferenciasAdapter(referencias.subsidio_ordinario.otras)
        } else {
            titleOtrasReferencias.isVisible = false
        }
    }

    private fun cargarReferenciasSubsidioPresupuesto(
        referencias: Referencias?,
    ) {
        notaRedondeo.visibility = View.VISIBLE

        if (referencias?.subsidio_presupuesto?.anexo != null) {

            titlereferenciaAnexoDetalle.text = String.format(
                requireContext().getString(R.string.referencias_presupuesto)
            )

            referenciasAnexo.adapter =
                ReferenciasAdapter(referencias.subsidio_presupuesto.anexo)

        } else {
            titlereferenciaAnexoDetalle.isVisible = false
        }
        titleOtrasReferencias.isVisible = false

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
                chartMontos.plot(mapaMontos, subsidio)
            else
                chartMontos.visibility = View.GONE

        }
    }

    private fun cargarDocumentos(detalle: Detalle) {
        if (detalle.planAusteridad.isNotBlank())
            buttonPlanAusteridad.apply {

                visibility = View.VISIBLE

                setOnClickListener(externalLink(detalle.planAusteridad))
            }

        if (!detalle.anexoEjecucion.isNullOrEmpty()) {
            titleNumeraliaDetalleProceso.text = "(Institución en proceso de consolidación)"
            buttonAnexoEjecucion.apply {
                visibility = View.VISIBLE

                text = String.format(
                    requireContext().getString(R.string.anexo_ejecucion),
                    year
                )

                setOnClickListener(externalLink(detalle.anexoEjecucion))
            }
        }
        if (!detalle.MarcoColaboracion.isNullOrBlank())
            buttonConvenioMarco.apply {

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
                buttonConvenio.apply {
                    visibility = View.VISIBLE

                    val tipoConvenio = urls[0].split("*")

                    text = String.format(
                        requireContext().getString(R.string.convenio),
                        "Vertiente "+tipoConvenio[0]
                    )

                    setOnClickListener(externalLink(tipoConvenio[1]))

                }

                buttonConvenio2.apply {
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
                        buttonConvenio.apply {
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
                        buttonConvenio.apply {
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
            titleNumeraliaDetalleProceso.visibility = View.GONE
            if (higherEducationEnrolment == 0 &&
                highSchoolEnrolment == 0 &&
                enrolmentTotal == 0 &&
                fullTimeProfessorsTotal == 0 &&
                desirableProfileProfessor == 0 &&
                nationalSystemResearchersProfessor == 0 &&
                studentAllowance == 0.0
                ){
                titleNumeraliaDetalleProceso.visibility = View.VISIBLE
                titleNumeraliaDetalleProceso.text = "(Institución en proceso de creación)"
                numMatriculaTotalESDetalle.visibility = View.GONE
                textMatriculaTotalESDetalle.visibility = View.GONE
                numMatriculaTotalEMDetalle.visibility = View.GONE
                textMatriculaTotalEMDetalle.visibility = View.GONE
                numMatriculaTotalDetalle.visibility = View.GONE
                textMatriculaTotalDetalle.visibility = View.GONE
                numTotalProfesoresTCDetalle.visibility = View.GONE
                textTotalProfesoresTCDetalle.visibility = View.GONE
                numTotalProfesoresTCPDVDetalle.visibility = View.GONE
                textTotalProfesoresTCPDVDetalle.visibility = View.GONE
                numProfesoresSistemaNacionalIVDetalle.visibility = View.GONE
                textProfesoresSistemaNacionalIVDetalle.visibility = View.GONE
                numSubsidioAlumnoFEDetalle.visibility = View.GONE
                textSubsidioAlumnoFEDetalle.visibility = View.GONE
            }
            numMatriculaTotalESDetalle.text =
                integerFormatter.format(higherEducationEnrolment)

            numMatriculaTotalEMDetalle.text =
                integerFormatter.format(highSchoolEnrolment)

            numMatriculaTotalDetalle.text =
                integerFormatter.format(enrolmentTotal)

            numTotalProfesoresTCDetalle.text =
                integerFormatter.format(fullTimeProfessorsTotal)

            numTotalProfesoresTCPDVDetalle.text =
                integerFormatter.format(desirableProfileProfessor)

            numProfesoresSistemaNacionalIVDetalle.text =
                integerFormatter.format(nationalSystemResearchersProfessor)

            numSubsidioAlumnoFEDetalle.text =
                currencyFormatter.format(studentAllowance)


            numPorcentajeParticipacionFDetalle.text = String.format(
                porcentaje_template,
                federationOwnershipPercentage
            )
            numPorcentajeParticipacionEDetalle.text =
                String.format(
                    porcentaje_template,
                    stateOwnershipPercentage
                )

        }
    }

    private fun cargarInformacion(detalle: Detalle) {

        generalesUniversidad.setValues(
            detalle.siglas, detalle.nombre,
            detalle.webUrl, detalle.escudo
        )

        direccionUniversidad.text = detalle.direccion
        rectorUniversidad.text = detalle.rector
        titleRectorDetalle.text = detalle.rectorCargo
        gobernador.text = detalle.gobernador
        titleGobernadorDetalle.text = detalle.gobernadorCargo
        linkSitioTransparencia.apply {

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


}
