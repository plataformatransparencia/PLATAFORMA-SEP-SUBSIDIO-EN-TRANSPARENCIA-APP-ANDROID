package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.databinding.LayoutLinkBinding
import sep.dgesui.subsidioentransparencia.fragments.DetalleMinistracionExtraordinariaEstatal
import sep.dgesui.subsidioentransparencia.fragments.loadFragment
import sep.dgesui.subsidioentransparencia.tableroext.minest.Ministracion

class MontosEstatalAdapter(
    private val montos: List<Ministracion>,
    private val informacion: InformacionGeneralWrapper,
    private val activity: FragmentActivity,
) :
    RecyclerView.Adapter<MontosEstatalAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder{
        val binding = LayoutLinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(montos[position])
    }

    override fun getItemCount(): Int = montos.size

    inner class Holder(private val binding: LayoutLinkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ministracion: Ministracion) {
            binding.linkTitle.text = currencyFormatter.format(ministracion.monto)
            binding.linkButton.setOnClickListener(
                    loadFragment(
                        DetalleMinistracionExtraordinariaEstatal(informacion, montos),
                        activity
                    )
                )
        }
    }
}