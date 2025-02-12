package sep.dgesui.subsidioentransparencia.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import sep.dgesui.subsidioentransparencia.databinding.LayoutListaElementosCardBinding

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder{
        val binding = LayoutListaElementosCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(val binding: LayoutListaElementosCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemPresupuesto) {
            binding.descripcion.text = item.descripcion
            if (targetFactory != null) {
                binding.descripcion.setOnClickListener(
                    loadFragment(
                        targetFactory.invoke(item),
                        activity
                    )
                )
                binding.flechaIr.setOnClickListener(
                    loadFragment(
                        targetFactory.invoke(item),
                        activity
                    )
                )
            }
        }


    }

}
