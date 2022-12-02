package sep.dgesui.subsidioentransparencia.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_lista_elementos_card.view.*
import sep.dgesui.subsidioentransparencia.R

data class CumplimientoInfo(
    val cumplimiento: String = "",
    val fechaEstipulada: String = "",
    val fechaEjecucion: String = "",
    val observacion: String = "",
)

data class ComplexItem(
    val descripcion: String,
    val cumplimientos: List<CumplimientoInfo> = emptyList(),
)

class ComplexItemListRecyclerAdapter(
    private val items: List<ComplexItem>,
    private val activity: FragmentActivity,
    private val targetFactory: ((ComplexItem) -> Fragment)? = null
) : RecyclerView.Adapter<ComplexItemListRecyclerAdapter.ComplexItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplexItemViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_lista_elementos_card, parent, false)
            .let {
                ComplexItemViewHolder(it)
            }

    override fun onBindViewHolder(holder: ComplexItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ComplexItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ComplexItem) {
            view.descripcion.text = item.descripcion
            if (targetFactory != null)
                view.flecha_ir.setOnClickListener(
                    loadFragment(
                        targetFactory.invoke(item),
                        activity
                    )
                )
        }
    }

}