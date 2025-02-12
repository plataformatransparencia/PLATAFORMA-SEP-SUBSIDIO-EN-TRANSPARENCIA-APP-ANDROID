package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.klinker.android.link_builder.Link
import com.klinker.android.link_builder.applyLinks
import com.squareup.picasso.Picasso
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.LayoutDatosGeneralesUniversidadBinding

class DatosGeneralesUniversidad
@JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    styleSelector: Int = 0,
) : ConstraintLayout(context, attributes, styleSelector) {
    private var binding: LayoutDatosGeneralesUniversidadBinding? = null
    init {
        binding = rootView.let {
            LayoutDatosGeneralesUniversidadBinding.inflate(LayoutInflater.from(context), it as ViewGroup, true)
        }
    }

    fun setValues(
        siglas: String,
        nombreUniversidad: String,
        urlUniversidad: String,
        urlLogo: String,
    ) {

        Picasso.get().load(urlLogo).into(binding?.cardUniversidadLogoImagen)

        binding?.cardSiglasUniversidad?.text = siglas
        binding?.cardNombreUniversidad?.text = nombreUniversidad
        binding?.cardLinkUniversidad?.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_accion_link,0,0,0)
        binding?.cardLinkUniversidad?.apply {
            val content =String.format(context.getString(R.string.ir_a), siglas)
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