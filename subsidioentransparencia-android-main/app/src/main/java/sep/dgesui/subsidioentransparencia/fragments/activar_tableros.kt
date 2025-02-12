package sep.dgesui.subsidioentransparencia.fragments
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.engineadapter.CumplimientoRepository
import sep.dgesui.subsidioentransparencia.engineadapter.ItemSources
import sep.dgesui.subsidioentransparencia.engineadapter.MinistracionRepository

val itemSources = ItemSources()
private val ministracionesRepository = MinistracionRepository()
private val repositoryCumplimiento = CumplimientoRepository()


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
            "subsidio_presupuesto" -> activarSubsidioExtraordinarioPresupuesto(informacion, detalle, activity)
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
                    detalle.binding.linkCompromisos.isVisible = true
                    detalle.binding.linkCompromisos.setOnClickListener(
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
                    detalle.binding.linkAccionesEmprendidas.isVisible = true
                    detalle.binding.linkAccionesEmprendidas.setOnClickListener(
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

                    detalle.binding.linkAccionesPorEmprender.isVisible = true
                    detalle.binding.linkAccionesPorEmprender.setOnClickListener(
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

                detalle.binding.linkAccionesPorEmprender.isVisible = true
                detalle.binding.linkAccionesPorEmprender.setOnClickListener(
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

                    detalle.binding.linkCumplimientoMinistracionesSEU.isVisible = true
                    detalle.binding.linkCumplimientoMinistracionesSEU.setOnClickListener(
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
                    detalle.binding.linkCumplimientoMinistracionesEU.isVisible = true
                    detalle.binding.linkCumplimientoMinistracionesEU.setOnClickListener(
                        loadFragment(
                            MinistracionFederalExtraordinariaFragment(
                                informacion,
                                ministracionFederal,
                                null
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
                    detalle.binding.linkAccionesPorEmprenderUniversidad.isVisible = true
                    detalle.binding.linkAccionesPorEmprenderUniversidad.setOnClickListener(
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
                    detalle.binding.linkAccionesPorEmprenderGobierno.isVisible = true
                    detalle.binding.linkAccionesPorEmprenderGobierno.setOnClickListener(
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
                    detalle.binding.linkCumplimientoMinistracionesEU.isVisible = true
                    detalle.binding.linkCumplimientoMinistracionesEU.setOnClickListener(
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
                    detalle.binding.linkCumplimientoMinistracionesSEU.isVisible = true
                    detalle.binding.linkCumplimientoMinistracionesSEU.setOnClickListener(
                        loadFragment(
                            MinistracionFederalExtraordinariaFragment(
                                informacion,
                                ministracionFederalExtraordinaria,
                                null
                            ),
                            activity
                        )
                    )

                }
            }


        }
    }
}

private fun activarSubsidioExtraordinarioPresupuesto(
    informacion: InformacionGeneralWrapper,
    detalle: DetalleFragment,
    activity: FragmentActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) = runBlocking {



    launch {

        val compromisos = withContext(dispatcher) {
            itemSources.compromisosPresupuesto(informacion.id, informacion.year)
        }

        if(compromisos.containsKey("compromisoC") && compromisos.containsKey("compromisoA")){


            detalle.binding.linkCompromisosUniversidadA.isVisible = true
            detalle.binding.linkCompromisosUniversidadA.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(informacion, compromisos["compromisoA"]!!), activity
                )
            )

            detalle.binding.linkCompromisosUniversidadC.isVisible = true
            detalle.binding.linkCompromisosUniversidadC.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(informacion, compromisos["compromisoC"]!!), activity
                )
            )
        }else if(compromisos.containsKey("compromisoC") ){

            detalle.binding.linkCompromisosUniversidad.isVisible = true
            detalle.binding.linkCompromisosUniversidad.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(informacion, compromisos["compromisoC"]!!), activity
                )
            )

        }else if(compromisos.containsKey("compromisoB") ){

            detalle.binding.linkCompromisosUniversidad.isVisible = true
            detalle.binding.linkCompromisosUniversidad.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(informacion, compromisos["compromisoB"]!!), activity
                )
            )

        }
        else if(compromisos.containsKey("compromisoA") ){

            detalle.binding.linkCompromisosUniversidad.isVisible = true
            detalle.binding.linkCompromisosUniversidad.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(informacion, compromisos["compromisoA"]!!), activity
                )
            )

        }

    }

    launch {
        val ministracionFederalExtraordinaria =
            withContext(dispatcher) {
                ministracionesRepository.ministracionFederalExtraordinarioPresupuedto(
                    informacion.id,
                    informacion.year
                )
            }

        if (ministracionFederalExtraordinaria!!.federal.ministracionA != null && ministracionFederalExtraordinaria!!.federal.ministracionC != null  ) {

            if (ministracionFederalExtraordinaria != null) {
                detalle.binding.linkCumplimientoMinistracionesSEUVA.isVisible = true
                detalle.binding.linkCumplimientoMinistracionesSEUVA.setOnClickListener(
                    loadFragment(
                        MinistracionFederalExtraordinariaFragment(
                            informacion,
                            null,
                            ministracionFederalExtraordinaria.federal.ministracionA
                        ),
                        activity
                    )
                )

            }

            detalle.binding.linkCumplimientoMinistracionesSEUVC.isVisible = true
            detalle.binding.linkCumplimientoMinistracionesSEUVC.setOnClickListener(
                loadFragment(
                    MinistracionFederalExtraordinariaFragment(
                        informacion,
                        null,
                        ministracionFederalExtraordinaria!!.federal.ministracionC
                    ),
                    activity
                )
            )

        }else if (ministracionFederalExtraordinaria!!.federal.ministracionC != null){

            if (ministracionFederalExtraordinaria != null) {
                detalle.binding.linkCumplimientoMinistracionesSEU.isVisible = true
                detalle.binding.linkCumplimientoMinistracionesSEU.setOnClickListener(
                    loadFragment(
                        MinistracionFederalExtraordinariaFragment(
                            informacion,
                            null,
                            ministracionFederalExtraordinaria!!.federal.ministracionC
                        ),
                        activity
                    )
                )

            }

        }else if (ministracionFederalExtraordinaria!!.federal.ministracionB != null){

            if (ministracionFederalExtraordinaria != null) {
                detalle.binding.linkCumplimientoMinistracionesSEU.isVisible = true
                detalle.binding.linkCumplimientoMinistracionesSEU.setOnClickListener(
                    loadFragment(
                        MinistracionFederalExtraordinariaFragment(
                            informacion,
                            null,
                            ministracionFederalExtraordinaria!!.federal.ministracionB
                        ),
                        activity
                    )
                )

            }

        }else if (ministracionFederalExtraordinaria!!.federal.ministracionA != null){

            if (ministracionFederalExtraordinaria != null) {
                detalle.binding.linkCumplimientoMinistracionesSEU.isVisible = true
                detalle.binding.linkCumplimientoMinistracionesSEU.setOnClickListener(
                    loadFragment(
                        MinistracionFederalExtraordinariaFragment(
                            informacion,
                            null,
                            ministracionFederalExtraordinaria!!.federal.ministracionA
                        ),
                        activity
                    )
                )

            }

        }
    }

    launch {
        val tablero = withContext(dispatcher) {
            repositoryCumplimiento.tableroPresupuesto(
                informacion.id,
                informacion.year
            )?.tablero_cumplimiento
        }

        detalle.binding.linkTableroCumplimiento.isVisible = true
        detalle.binding.linkTableroCumplimiento.setOnClickListener(
            loadFragment(
                CumplimentoPresupuestoFragment(informacion,tablero!!),
                activity
            )
        )
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

        detalle.binding.linkCompromisosEstado.isVisible = true
        detalle.binding.linkCompromisosEstado.setOnClickListener(
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

        detalle.binding.linkCompromisosUniversidad.isVisible = true
        detalle.binding.linkCompromisosUniversidad.setOnClickListener(
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

    detalle.binding.linkTableroCumplimiento.isVisible = true
    detalle.binding.linkTableroCumplimiento.setOnClickListener(
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

        detalle.binding.linkCompromisosUniversidad.isVisible = true
        if (informacion.year == "2025"){
            detalle.binding.linkCompromisosUniversidad.setOnClickListener {
                activity.supportFragmentManager.beginTransaction().replace(R.id.main_fragment_container, CompromisosFragment(
                    informacion.id,
                    informacion.year,
                    informacion.nombreUniversidad,
                    informacion.subsidio
                )).addToBackStack(null).commit()
            }
        }else {
            detalle.binding.linkCompromisosUniversidad.setOnClickListener(
                loadFragment(
                    ListaCompromisosUniversidadSimplifiedFragment(informacion, compromisos),
                    activity
                )
            )
        }
    }

    launch {

        val ministracionFederal = withContext(dispatcher) {
            ministracionesRepository.ministracionFederalOrdinaro(informacion.id, informacion.year)
        }

        if (ministracionFederal != null) {
            detalle.binding.linkCumplimientoMinistracionesSEU.isVisible = true
            detalle.binding.linkCumplimientoMinistracionesSEU.setOnClickListener(
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

            detalle.binding.linkCumplimientoMinistracionesEU.isVisible = true
            detalle.binding.linkCumplimientoMinistracionesEU.setOnClickListener(
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
