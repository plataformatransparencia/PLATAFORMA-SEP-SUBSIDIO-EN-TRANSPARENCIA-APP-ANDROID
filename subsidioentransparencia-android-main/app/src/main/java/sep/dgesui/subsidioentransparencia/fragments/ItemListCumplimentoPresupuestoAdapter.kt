package sep.dgesui.subsidioentransparencia.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_lista_elementos_card.view.*
import kotlinx.android.synthetic.main.university_item.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.model.Universidade

data class ItemPresupuesto(
    val descripcion: String = "",
    val cumplimiento: String = "",
    val fechaCompromiso: String = "",
    val fechaEjecucion: String = "",
    val observacion: String = "",
    var tipo: String = "",

)


class ItemListCumplimientoPresupuestoRecyclerAdapter(
    private val items: List<ItemPresupuesto>,
    private val activity: FragmentActivity,
    private val targetFactory: ((ItemPresupuesto) -> Fragment)? = null,

    ) :
    RecyclerView.Adapter<ItemListCumplimientoPresupuestoRecyclerAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_lista_elementos_card, parent, false)
            .let { ItemViewHolder(it) }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ItemPresupuesto) {
            view.descripcion.text = item.descripcion
            if (targetFactory != null) {
                view.descripcion.setOnClickListener(
                    loadFragment(
                        targetFactory.invoke(item),
                        activity
                    )
                )
                view.flecha_ir.setOnClickListener(
                    loadFragment(
                        targetFactory.invoke(item),
                        activity
                    )
                )
            }
        }


    }

}
