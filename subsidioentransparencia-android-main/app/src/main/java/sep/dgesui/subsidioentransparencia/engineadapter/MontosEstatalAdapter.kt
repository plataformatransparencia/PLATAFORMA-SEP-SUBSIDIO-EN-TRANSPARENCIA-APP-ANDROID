package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_link.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.fragments.DetalleMinistracionExtraordinariaEstatal
import sep.dgesui.subsidioentransparencia.fragments.loadFragment
import sep.dgesui.subsidioentransparencia.tableroext.minest.Ministracion

class MontosEstatalAdapter(
    private val montos: List<Ministracion>,
    private val informacion: InformacionGeneralWrapper,
    private val activity: FragmentActivity,
) :
    RecyclerView.Adapter<MontosEstatalAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_link, parent, false)
            .let { Holder(it) }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(montos[position])
    }

    override fun getItemCount(): Int = montos.size

    inner class Holder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(ministracion: Ministracion) {
            view.linkTitle.text = currencyFormatter.format(ministracion.monto)
            view.linkButton.setOnClickListener(
                loadFragment(
                    DetalleMinistracionExtraordinariaEstatal(informacion, ministracion),
                    activity
                )
            )
        }
    }
}