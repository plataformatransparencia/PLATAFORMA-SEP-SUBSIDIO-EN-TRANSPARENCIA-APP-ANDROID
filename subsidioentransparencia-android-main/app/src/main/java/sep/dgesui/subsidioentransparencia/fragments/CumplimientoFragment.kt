package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_cumplimiento.*
import kotlinx.coroutines.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.ComponenteTrimestreProfexce
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.engineadapter.CumplimientoRepository
import sep.dgesui.subsidioentransparencia.tableroext.profexce.tablero.DetalleCumplimiento
import timber.log.Timber

class CumplimientoFragment(
    private val informacion: InformacionGeneralWrapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : Fragment() {
    private val repository = CumplimientoRepository()
    lateinit var labelCumplimiento: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_cumplimiento, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        labelCumplimiento = requireContext().getString(R.string.label_cumplimiento)

        ministracionBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        ministracionHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        cumplimientoTrimestre1.trimestre = 1
        cumplimientoTrimestre2.trimestre = 2
        cumplimientoTrimestre3.trimestre = 3
        cumplimientoTrimestre4.trimestre = 4

        runBlocking {

            Timber.d("Tablero profexce para ${informacion.id}  ${informacion.year}")

            launch {
                val tablero = withContext(dispatcher) {
                    repository.tableroProfexce(
                        informacion.id,
                        informacion.year
                    )?.tablero_cumplimiento
                }

                if (tablero != null) {

                    tablero.Resultado_general.apply {
                        cumplimientoFechaEntrega.text = fechaEntrega
                        cumplimientoAprobacion.text = aprobacion
                        cumplimientoObservaciones.text = observacion
                    }


                    val btn1DescipcionAcademico =
                        requireContext().getString(R.string.entrega_avance_academico_primer_trimestre)
                    val btn1DescripcionFinanciero =
                        requireContext().getString(R.string.entrega_avance_financiero_primer_trimestre)
                    val btn1DescipcionInforme =
                        requireContext().getString(R.string.publicacion_primer_informe)
                    cumplimientoTrimestre1.titulos = ComponenteTrimestreProfexce.Titulos(
                        btn1DescipcionAcademico to targetFactory(
                            tablero.informe_academico.primer_trimestre,
                            btn1DescipcionAcademico
                        ),
                        btn1DescripcionFinanciero to targetFactory(
                            tablero.informe_financiero.primer_trimestre,
                            btn1DescripcionFinanciero
                        ),
                        btn1DescipcionInforme to targetFactory(
                            tablero.informe_publicacion.primer_trimestre,
                            btn1DescipcionInforme
                        ),
                    )

                    val btn2DescipcionAcademico =
                        requireContext().getString(R.string.entrega_avance_academico_segundo_trimestre)
                    val btn2DescripcionFinanciero =
                        requireContext().getString(R.string.entrega_avance_financiero_segundo_trimestre)
                    val btn2DescripcionInforme =
                        requireContext().getString(R.string.publicacion_segundo_informe)
                    cumplimientoTrimestre2.titulos = ComponenteTrimestreProfexce.Titulos(
                        btn2DescipcionAcademico to targetFactory(
                            tablero.informe_academico.segundo_trimestre,
                            btn2DescipcionAcademico
                        ),
                        btn2DescripcionFinanciero to targetFactory(
                            tablero.informe_financiero.segundo_trimestre,
                            btn2DescripcionFinanciero
                        ),
                        btn2DescripcionInforme to targetFactory(
                            tablero.informe_publicacion.segundo_trimestre,
                            btn2DescripcionInforme
                        ),
                    )

                    val btn3DescipcionAcademico =
                        requireContext().getString(R.string.entrega_avance_academico_tercer_trimestre)
                    val btn3DescripcionFinanciero =
                        requireContext().getString(R.string.entrega_avance_financiero_tercer_trimestre)
                    val btn3DescripcionInforme =
                        requireContext().getString(R.string.publicacion_tercer_informe)
                    cumplimientoTrimestre3.titulos = ComponenteTrimestreProfexce.Titulos(
                        btn3DescipcionAcademico to targetFactory(
                            tablero.informe_academico.tercero_trimestre,
                            btn3DescipcionAcademico
                        ),
                        btn3DescripcionFinanciero to targetFactory(
                            tablero.informe_financiero.tercero_trimestre,
                            btn3DescripcionFinanciero
                        ),
                        btn3DescripcionInforme to targetFactory(
                            tablero.informe_publicacion.tercero_trimestre,
                            btn3DescripcionInforme
                        ),
                    )

                    val btn4DescripcionAcademico =
                        requireContext().getString(R.string.entrega_avance_academico_cuarto_trimestre)
                    val btn4DescripcionFinanciero =
                        requireContext().getString(R.string.entrega_avance_financiero_cuarto_trimestre)
                    val btn4DescipcionInforme =
                        requireContext().getString(R.string.publicacion_cuarto_informe)
                    cumplimientoTrimestre4.titulos = ComponenteTrimestreProfexce.Titulos(
                        btn4DescripcionAcademico to targetFactory(
                            tablero.informe_academico.cuarto_trimestre,
                            btn4DescripcionAcademico
                        ),
                        btn4DescripcionFinanciero to targetFactory(
                            tablero.informe_financiero.cuarto_trimestre,
                            btn4DescripcionFinanciero
                        ),
                        btn4DescipcionInforme to targetFactory(
                            tablero.informe_publicacion.cuarto_trimestre,
                            btn4DescipcionInforme
                        ),
                    )

                }
            }
        }

    }

    private fun targetFactory(
        detalle: DetalleCumplimiento,
        descripcion: String
    ): (view: View) -> Unit =
        loadFragment(
            DetalleTableroCumplimientoProfexceFragment(
                informacion,
                detalle.toItem(descripcion)
            ),
            requireActivity()
        )


    private fun DetalleCumplimiento.toItem(descripcion: String): Item = Item(
        descripcion = descripcion,
        fechaCompromiso = fechaLimite,
        fechaEjecucion = fechaEntrega,
        cumplimiento = cumplimiento,
        observacion = observacion,
        porcentajeIncremento = null
    )
}
