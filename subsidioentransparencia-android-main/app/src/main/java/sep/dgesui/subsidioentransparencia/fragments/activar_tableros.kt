package sep.dgesui.subsidioentransparencia.fragments

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.fragment_detalle.*
import kotlinx.coroutines.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.engineadapter.ItemSources
import sep.dgesui.subsidioentransparencia.engineadapter.MinistracionRepository

private val itemSources = ItemSources()
private val ministracionesRepository = MinistracionRepository()

fun activarTableros(
    detalle: DetalleFragment,
    informacion: InformacionGeneralWrapper,
    activity: FragmentActivity
) = runBlocking {

    launch {

        when (informacion.subsidio) {
            "subsidio_ordinario" -> activarSubsidioOrdinario(informacion, detalle, activity)
            "subsidio_extraordinario" -> activarSubsidioExtraordinario(
                informacion,
                detalle,
                activity
            )
            "subsidio_profexce" -> activarSubsidioProfexe(informacion, detalle, activity)
        }
    }

}

private fun activarSubsidioExtraordinario(
    informacion: InformacionGeneralWrapper,
    detalle: DetalleFragment,
    activity: FragmentActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) = runBlocking {
    when (informacion.year) {
        "2018" -> {
            launch {

                val compromisos =
                    withContext(dispatcher) {
                        itemSources.getCompromisosExtraordinarios2018(
                            informacion.id
                        )
                    }

                if (compromisos.actividades.isNotEmpty()) {
                    detalle.linkCompromisos.isVisible = true
                    detalle.linkCompromisos.setOnClickListener(
                        loadFragment(
                            FragmentListaCompromisosExtraordinario2018(
                                informacion,
                                compromisos.compromisos,
                                compromisos.actividades,
                                compromisos.entregas,
                                compromisos.referencias,
                                compromisos.notaCompromiso
                            ), activity
                        )
                    )
                }
            }

            launch {

                val accionesEmprendidas =
                    withContext(dispatcher) {
                        itemSources.accionesEmprendidas2018(
                            informacion.id
                        )
                    }

                if (accionesEmprendidas.items != null) {
                    detalle.linkAccionesEmprendidas.isVisible = true
                    detalle.linkAccionesEmprendidas.setOnClickListener(
                        loadFragment(
                            FragmentAccionesEmprendidasExtraordinario2018(
                                informacion,
                                accionesEmprendidas.items,
                                accionesEmprendidas.referencias
                            ),
                            activity
                        )
                    )
                }

            }

            launch {

                val accionesPorEmprender =
                    withContext(dispatcher) {
                        itemSources.accionesPorEmprender2018(
                            informacion.id
                        )
                    }

                if (accionesPorEmprender.items != null) {

                    detalle.linkAccionesPorEmprender.isVisible = true
                    detalle.linkAccionesPorEmprender.setOnClickListener(
                        loadFragment(
                            FragmentAccionesPorEmprenderExtraordinario2018(
                                informacion,
                                accionesPorEmprender.items,
                                accionesPorEmprender.referencias
                            ),
                            activity
                        )
                    )
                }
            }

        }

        "2019" -> {

            launch {

                val accionesPorEmprender =
                    withContext(dispatcher) {
                        itemSources.getAccionesEmprenderUniversidad(
                            informacion.year,
                            informacion.id
                        )
                    }

                detalle.linkAccionesPorEmprender.isVisible = true
                detalle.linkAccionesPorEmprender.setOnClickListener(
                    loadFragment(
                        ListaAccionesUniversidadExtraordinario2020(
                            informacion,
                            accionesPorEmprender.referencias,
                            accionesPorEmprender.items,
                            accionesPorEmprender.itemsMaterialesSuministros,
                            accionesPorEmprender.itemsServiciosGenerales,
                            accionesPorEmprender.itemsAsignacionesSubsidios,
                            accionesPorEmprender.itemsBienesMueblesInmuebles,
                            accionesPorEmprender.itemsObrasRemodelaciones,
                        ), activity
                    )
                )
            }

            launch {

                val ministracionEstatal = withContext(dispatcher) {
                    ministracionesRepository.ministracionEstatalExtraordinario(
                        informacion.id,
                        informacion.year
                    )
                }

                if (ministracionEstatal != null) {

                    detalle.linkCumplimientoMinistracionesSEU.isVisible = true
                    detalle.linkCumplimientoMinistracionesSEU.setOnClickListener(
                        loadFragment(
                            MinistracionEstatalExtraordinariaFragment(
                                informacion,
                                ministracionEstatal
                            ),
                            activity
                        )
                    )
                }
            }


            launch {
                val ministracionFederal = withContext(dispatcher) {
                    ministracionesRepository.ministracionFederalExtraordinario(
                        informacion.id,
                        informacion.year
                    )
                }

                if (ministracionFederal != null) {
                    detalle.linkCumplimientoMinistracionesEU.isVisible = true
                    detalle.linkCumplimientoMinistracionesEU.setOnClickListener(
                        loadFragment(
                            MinistracionFederalExtraordinariaFragment(
                                informacion,
                                ministracionFederal
                            ), activity
                        )
                    )
                }
            }
        }

        else -> {

            launch {
                val accionesPorEmprender = withContext(dispatcher) {
                    itemSources.getAccionesEmprenderUniversidad(informacion.year, informacion.id)
                }


                if (accionesPorEmprender.items?.isNotEmpty() == true) {
                    detalle.linkAccionesPorEmprenderUniversidad.isVisible = true
                    detalle.linkAccionesPorEmprenderUniversidad.setOnClickListener(
                        loadFragment(
                            ListaAccionesUniversidadExtraordinario2020(
                                informacion,
                                accionesPorEmprender.referencias,
                                accionesPorEmprender.items,
                            ), activity
                        )
                    )
                }
            }


            launch {

                val accionesEmprenderGobierno =
                    withContext(dispatcher) {
                        itemSources.getAccionesEmprenderEstado(informacion.year, informacion.id)
                    }

                if (accionesEmprenderGobierno.items?.isNotEmpty() == true) {
                    detalle.linkAccionesPorEmprenderGobierno.isVisible = true
                    detalle.linkAccionesPorEmprenderGobierno.setOnClickListener(
                        loadFragment(
                            ListaAccionesEmprenderEstadoExtraordinario2020(
                                informacion,
                                accionesEmprenderGobierno.items,
                                accionesEmprenderGobierno.referencias
                            ),
                            activity
                        )
                    )
                }
            }


            launch {
                val ministracionEstatalExtraordinaria =
                    withContext(dispatcher) {
                        ministracionesRepository.ministracionEstatalExtraordinario(
                            informacion.id,
                            informacion.year
                        )
                    }

                if (ministracionEstatalExtraordinaria != null) {
                    detalle.linkCumplimientoMinistracionesEU.isVisible = true
                    detalle.linkCumplimientoMinistracionesEU.setOnClickListener(
                        loadFragment(
                            MinistracionEstatalExtraordinariaFragment(
                                informacion,
                                ministracionEstatalExtraordinaria
                            ), activity
                        )
                    )
                }
            }


            launch {
                val ministracionFederalExtraordinaria =
                    withContext(dispatcher) {
                        ministracionesRepository.ministracionFederalExtraordinario(
                            informacion.id,
                            informacion.year
                        )
                    }

                if (ministracionFederalExtraordinaria != null) {
                    detalle.linkCumplimientoMinistracionesSEU.isVisible = true
                    detalle.linkCumplimientoMinistracionesSEU.setOnClickListener(
                        loadFragment(
                            MinistracionFederalExtraordinariaFragment(
                                informacion,
                                ministracionFederalExtraordinaria
                            ),
                            activity
                        )
                    )

                }
            }


        }
    }
}


private fun activarSubsidioProfexe(
    informacion: InformacionGeneralWrapper,
    detalle: DetalleFragment,
    activity: FragmentActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) = runBlocking {

    launch {
        val compromisosEstado = withContext(dispatcher) {
            itemSources.compromisosProfexceEstado(
                informacion.id,
                informacion.year
            )
        }

        detalle.linkCompromisosEstado.isVisible = true
        detalle.linkCompromisosEstado.setOnClickListener(
            loadFragment(
                ListaCompromisosEstadoProfexce(informacion, compromisosEstado),
                activity
            )
        )

    }

    launch {

        val compromisosUniversidad = withContext(dispatcher) {
            itemSources.compromisosProfexceUniversidad(informacion.id, informacion.year)
        }

        detalle.linkCompromisosUniversidad.isVisible = true
        detalle.linkCompromisosUniversidad.setOnClickListener(
            loadFragment(
                ListaCompromisosUniversidadProfexceFragment(
                    informacion,
                    compromisosUniversidad.items,
                    compromisosUniversidad.tablaMonto
                ),
                activity
            )
        )
    }

    detalle.linkTableroCumplimiento.isVisible = true
    detalle.linkTableroCumplimiento.setOnClickListener(
        loadFragment(CumplimientoFragment(informacion), activity)
    )
}

private fun activarSubsidioOrdinario(
    informacion: InformacionGeneralWrapper,
    detalle: DetalleFragment,
    activity: FragmentActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) = runBlocking {

    launch {

        val compromisos = withContext(dispatcher) {
            itemSources.compromisosOrdinarios(informacion.id, informacion.year)
        }

        detalle.linkCompromisosUniversidad.isVisible = true
        detalle.linkCompromisosUniversidad.setOnClickListener(
            loadFragment(
                ListaCompromisosUniversidadSimplifiedFragment(informacion, compromisos), activity
            )
        )
    }

    launch {

        val ministracionFederal = withContext(dispatcher) {
            ministracionesRepository.ministracionFederalOrdinaro(informacion.id, informacion.year)
        }

        if (ministracionFederal != null) {
            detalle.linkCumplimientoMinistracionesSEU.isVisible = true
            detalle.linkCumplimientoMinistracionesSEU.setOnClickListener(
                loadFragment(
                    MinFederalFragment(informacion, ministracionFederal), activity
                )
            )
        }
    }

    launch {

        val ministracionEstatal = withContext(dispatcher) {
            ministracionesRepository.ministracionEstatalOrdinario(informacion.id, informacion.year)
        }

        if (ministracionEstatal != null) {

            detalle.linkCumplimientoMinistracionesEU.isVisible = true
            detalle.linkCumplimientoMinistracionesEU.setOnClickListener(
                loadFragment(MinEstatalFragment(informacion, ministracionEstatal), activity)
            )
        }
    }
}

fun loadFragment(fragment: Fragment, activity: FragmentActivity): (View) -> Unit =
    { _: View ->
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
