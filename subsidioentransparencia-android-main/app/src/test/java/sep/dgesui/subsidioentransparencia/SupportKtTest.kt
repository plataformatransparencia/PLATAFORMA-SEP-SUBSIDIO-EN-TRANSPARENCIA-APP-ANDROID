package sep.dgesui.subsidioentransparencia

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import sep.dgesui.subsidioentransparencia.fragments.Item

class SupportKtTest {

    private val original = listOf(
        Item(descripcion = "i1", fechaCompromiso = "fecha", observacion = ""),
        Item(
            descripcion = "i2",
            fechaCompromiso = "fecha",
            observacion = "",
            subacciones = listOf(
                Item(
                    descripcion = "sa21",
                    fechaCompromiso = "fecha",
                    observacion = ""
                ), Item(descripcion = "sa22", fechaCompromiso = "fecha", observacion = "")
            )
        ),
        Item(
            descripcion = "i3",
            fechaCompromiso = "fecha",
            observacion = "",
            subacciones = listOf(
                Item(
                    descripcion = "Etapa 1 sa31",
                    fechaCompromiso = "fecha",
                    observacion = ""
                ), Item(descripcion = "Etapa 2 sa32", fechaCompromiso = "fecha", observacion = "")
            )
        ),
    )

    val esperado = listOf(
        Item(descripcion = "i1", fechaCompromiso = "fecha", observacion = ""),
        Item(
            descripcion = "i2",
            fechaCompromiso = "fecha",
            observacion = "",
        ),
        Item(
            descripcion = "sa21",
            fechaCompromiso = "fecha",
            observacion = ""
        ),
        Item(descripcion = "sa22", fechaCompromiso = "fecha", observacion = ""),
        Item(
            descripcion = "i3",
            fechaCompromiso = "fecha",
            observacion = "",
            subacciones = listOf(
                Item(
                    descripcion = "Etapa 1 sa31",
                    fechaCompromiso = "fecha",
                    observacion = ""
                ), Item(descripcion = "Etapa 2 sa32", fechaCompromiso = "fecha", observacion = "")
            )
        ),
    )

    @Test
    fun respeatSiNoHaySubacciones() {
        val entrada = original.subList(0, 1)

        val salida = flattenItems(entrada)

        assertThat(salida).isEqualTo(entrada)
    }

    @Test
    fun extraeSubacciones() {
        val entrada = original.subList(0, 2)

        val salida = flattenItems(entrada)

        assertThat(salida).containsExactly(*esperado.subList(0, 4).toTypedArray())
    }

    @Test
    fun noExtraeSiIniciaConEtapa() {
        val entrada = original

        val salida = flattenItems(entrada)

        assertThat(salida).containsExactly(*esperado.toTypedArray())
    }
}
