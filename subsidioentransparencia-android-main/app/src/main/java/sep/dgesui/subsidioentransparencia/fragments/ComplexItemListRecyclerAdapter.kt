package sep.dgesui.subsidioentransparencia.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import sep.dgesui.subsidioentransparencia.databinding.LayoutListaElementosCardBinding

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplexItemViewHolder{
        val binding = LayoutListaElementosCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComplexItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ComplexItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ComplexItemViewHolder(val binding: LayoutListaElementosCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ComplexItem) {
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