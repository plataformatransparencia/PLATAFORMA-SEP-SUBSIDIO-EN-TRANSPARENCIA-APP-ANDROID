package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_datos_generales_universidad.view.*
import sep.dgesui.subsidioentransparencia.R

class DatosGeneralesUniversidad
@JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    styleSelector: Int = 0,
) : ConstraintLayout(context, attributes, styleSelector) {

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_datos_generales_universidad, this)
    }

    fun setValues(
        siglas: String,
        nombreUniversidad: String,
        urlUniversidad: String,
        urlLogo: String,
    ) {

        Picasso.get().load(urlLogo).into(cardUniversidadLogoImagen)

        cardSiglasUniversidad.text = siglas
        cardNombreUniversidad.text = nombreUniversidad

        cardLinkUniversidad.apply {
            val content = String.format(context.getString(R.string.ir_a), siglas)

            text = content


            Link(content)
                .setTextColor(ContextCompat.getColor(context, R.color.gob_gold))
                .setUnderlined(false)
                .setOnClickListener {
                    startActivity(
                        context,
                        Intent.getIntentOld(Uri.parse(urlUniversidad).toString()),
                        null
                    )
                }.also {
                    applyLinks(it)
                }
        }


    }
}