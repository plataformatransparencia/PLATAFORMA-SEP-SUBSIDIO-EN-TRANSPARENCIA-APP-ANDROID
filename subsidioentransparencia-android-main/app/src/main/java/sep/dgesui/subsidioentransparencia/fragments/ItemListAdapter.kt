package sep.dgesui.subsidioentransparencia.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import sep.dgesui.subsidioentransparencia.databinding.LayoutListaElementosCardBinding

data class Item(
    val descripcion: String = "",
    val cumplimiento: String = "",
    val fechaCompromiso: String = "",
    val fechaEjecucion: String = "",
    val observacion: String = "",
    val imagen: String = "",
    val subacciones: List<Item>? = emptyList(),
    val porcentajeIncremento: Double?,
)


class ItemListCardRecyclerAdapter(
    private val items: List<Item>,
    private val activity: FragmentActivity,
    private val targetFactory: ((Item) -> Fragment)? = null,

    ) :
    RecyclerView.Adapter<ItemListCardRecyclerAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
        val binding = LayoutListaElementosCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(val binding: LayoutListaElementosCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.descripcion.text = item.descripcion

            if (targetFactory != null)
                binding.flechaIr.setOnClickListener(
                    loadFragment(
                        targetFactory.invoke(item),
                        activity
                    )
                )
        }

    }
}