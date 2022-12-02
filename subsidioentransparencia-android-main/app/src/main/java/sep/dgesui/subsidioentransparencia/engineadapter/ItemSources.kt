package sep.dgesui.subsidioentransparencia.engineadapter

import sep.dgesui.subsidioentransparencia.flattenItems
import sep.dgesui.subsidioentransparencia.fragments.ComplexItem
import sep.dgesui.subsidioentransparencia.fragments.CumplimientoInfo
import sep.dgesui.subsidioentransparencia.fragments.Item
import sep.dgesui.subsidioentransparencia.model.Compromisos
import sep.dgesui.subsidioentransparencia.services.AccionesEmprenderService
import sep.dgesui.subsidioentransparencia.services.CommitmentService
import sep.dgesui.subsidioentransparencia.tableroext.acciones.Accion
import sep.dgesui.subsidioentransparencia.tableroext.acciones.AccionesWrapper
import sep.dgesui.subsidioentransparencia.tableroext.profexce.compromisos.CompromisoEstado
import sep.dgesui.subsidioentransparencia.tableroext.profexce.compromisos.CompromisoUniversidad
import sep.dgesui.subsidioentransparencia.tableroext.profexce.compromisos.TablaMontoUniversidad
import timber.log.Timber

class ItemSources {
    private val compromisos = TransparenciaRetrofit.serviceFactory(CommitmentService::class.java)
    private val acciones =
        TransparenciaRetrofit.serviceFactory(AccionesEmprenderService::class.java)

    fun compromisosOrdinarios(idUniversidad: String, year: String): List<Item> =
        compromisos
            .getCommitmentOrdinario(year, idUniversidad)
            .execute()
            .let { response ->
                val compromisos = response.body() ?: Compromisos(emptyList())

                compromisos.compromisos.map {
                    Item(
                        descripcion = it.compromiso,
                        cumplimiento = it.cumplimiento,
                        fechaCompromiso = it.fecha,
                        observacion = it.observacion
                    )
                }
            }

    fun compromisosProfexceEstado(idUniversidad: String, year: String): List<Item> =
        compromisos
            .getCompromisoEstadoProfexce(year, idUniversidad)
            .execute()
            .let { response ->
                val compromisos = response.body() ?: CompromisoEstado(emptyList())

                compromisos.compromisos.map {
                    Item(
                        descripcion = it.compromiso,
                        fechaCompromiso = it.fechaEntrega,
                        observacion = it.observacion
                    )
                }
            }


    fun compromisosProfexceUniversidad(
        idUniversidad: String,
        year: String
    ): CompromisoUniversidadWrapper =
        compromisos
            .getCompromisoUniversidadProfexce(year, idUniversidad)
            .execute()
            .let { response ->

                val compromisos =
                    response.body() ?: CompromisoUniversidad(
                        emptyList(),
                        TablaMontoUniversidad("", "", 0.0, "")
                    )

                val listaItems = compromisos.compromisos.map {
                    Item(
                        descripcion = it.compromiso,
                        fechaCompromiso = it.fechaEntrega,
                        observacion = it.observacion
                    )
                }

                CompromisoUniversidadWrapper(listaItems, compromisos.tablaMontoUniversidad)
            }

    fun getAccionesEmprenderUniversidad(year: String, id: String): AccionesResponseWrapper =
        acciones.getPorEmprenderUniversidad(year, id)
            .execute()
            .body()
            .let { response ->
                val accionesPorEmprender = response!!.Acciones

                val items = accionesPorEmprender?.Acciones?.toItems()

                val itemsMateriales = response.Acciones?.Materiales_Suministros_Acciones?.toItems()

                val itemsServiciosGenerales =
                    response.Acciones?.Servicios_Generales_Acciones?.toItems()

                val itemsAsignacionesSubsidios =
                    response.Acciones?.Asignaciones_Subsidios_otras_Ayudas?.toItems()

                val itemsBienes = response.Acciones?.Bienes_muebles_inmuebles?.toItems()

                val itemsObrasRemodelaciones = response.Acciones?.Obras_remodelaciones?.toItems()

                AccionesResponseWrapper(
                    items,
                    response.referencias,
                    itemsMateriales,
                    itemsServiciosGenerales,
                    itemsAsignacionesSubsidios,
                    itemsBienes,
                    itemsObrasRemodelaciones
                )
            }

    fun getAccionesEmprenderEstado(year: String, id: String): AccionesResponseWrapper =
        acciones.getPorEmprenderEstado(year, id)
            .execute()
            .body()
            .let { response ->
                val accionesPorEmprender = response?.Acciones ?: AccionesWrapper(emptyList())

                val items = accionesPorEmprender.Acciones.toItems()

                AccionesResponseWrapper(items, response?.referencias)
            }

    fun getCompromisosExtraordinarios2018(
        id: String
    ): CumplimientosExtraordinariosWrapper =
        compromisos.getCommitmentExt(id)
            .execute()
            .body()
            .let { respuesta ->
                val compromisos = respuesta!!.compromisos?.map {
                    Item(
                        descripcion = it.compromiso,
                        cumplimiento = it.cumplimiento,
                        fechaCompromiso = it.fechaEstipulada,
                        fechaEjecucion = it.fechaEjecucion,
                        observacion = it.observacion,
                    )
                }

                val actividades = respuesta.actividades.map { activiidad ->
                    val cumplimientos = activiidad.cumplimientos.map {
                        CumplimientoInfo(
                            cumplimiento = it.cumplimiento,
                            fechaEstipulada = it.fechaEstipulada,
                            fechaEjecucion = it.fechaEjecucion,
                            observacion = it.observacion,
                        )
                    }

                    ComplexItem(activiidad.actividad, cumplimientos)
                }

                val entregas = respuesta.entregas?.map { entrega ->
                    val cumplimientos = entrega.cumplimientos.map {
                        CumplimientoInfo(
                            cumplimiento = it.cumplimiento,
                            fechaEstipulada = it.fechaEstipulada,
                            fechaEjecucion = it.fechaEjecucion,
                            observacion = it.observacion,
                        )
                    }

                    ComplexItem(entrega.entrega, cumplimientos)
                }

                CumplimientosExtraordinariosWrapper(
                    compromisos ?: emptyList(),
                    actividades,
                    entregas ?: emptyList(),
                    respuesta.referencias ?: emptyMap(),
                    respuesta.nota_compromisos ?: ""
                )
            }


    fun accionesEmprendidas2018(id: String): AccionesResponseWrapper =
        acciones.getPorEmprender2018Emprendidas(id)
            .execute()
            .body()
            .let { response ->

                Timber.d("Acciones emprendidas 2018 response:$response ")

                val items = response!!.Acciones?.Acciones?.toItems()

                AccionesResponseWrapper(items, response.referencias)
            }

    fun accionesPorEmprender2018(id: String): AccionesResponseWrapper =
        acciones.getPorEmprender2018Universidad(id)
            .execute()
            .body()
            .let { response ->

                val items = response!!.Acciones?.Acciones?.toItems()

                //La UAEMEX (id 15) tiene subacciones, pero estas deben ser mostradas al mismo
                //nivel que las acciones

                if (id == "15") {
                    val flattenItems: List<Item> =
                        flattenItems(items!!)

                    AccionesResponseWrapper(flattenItems, response.referencias)
                } else {
                    AccionesResponseWrapper(items, response.referencias)
                }

            }

    private fun List<Accion>.toItems(): List<Item> = map {

        val subaccionesItems = it.subacciones?.map { subaccion ->
            Item(

                descripcion = subaccion.subaccion,
                fechaEjecucion = subaccion.fechaEjecucion,
                fechaCompromiso = subaccion.fechaEstipulada,
                cumplimiento = subaccion.cumplimiento,
                observacion = subaccion.observacion,
            )
        }

        Item(
            descripcion = it.accion,
            fechaEjecucion = it.fechaEjecucion,
            fechaCompromiso = it.fechaEstipulada ?: "",
            cumplimiento = it.cumplimiento,
            observacion = it.observacion,
            subacciones = subaccionesItems,
            imagen = it.imagen ?: "",
        )
    }

}

data class AccionesResponseWrapper(
    val items: List<Item>?,
    val referencias: Map<String, String>?,
    val itemsMaterialesSuministros: List<Item>? = null,
    val itemsServiciosGenerales: List<Item>? = null,
    val itemsAsignacionesSubsidios: List<Item>? = null,
    val itemsBienesMueblesInmuebles: List<Item>? = null,
    val itemsObrasRemodelaciones: List<Item>? = null,
)

data class CompromisoUniversidadWrapper(
    val items: List<Item>,
    val tablaMonto: TablaMontoUniversidad
)

data class CumplimientosExtraordinariosWrapper(
    val compromisos: List<Item> = emptyList(),
    val actividades: List<ComplexItem> = emptyList(),
    val entregas: List<ComplexItem> = emptyList(),
    val referencias: Map<String, String> = emptyMap(),
    val notaCompromiso: String = ""
)