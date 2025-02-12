package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import sep.dgesui.subsidioentransparencia.databinding.UniversityItemBinding
import sep.dgesui.subsidioentransparencia.interfaces.DataList
import sep.dgesui.subsidioentransparencia.model.Universidade
//listado de universidades
class UniversityAdapter(val universidades: List<Universidade>, val itemClickListener: DataList) :
    RecyclerView.Adapter<UniversityAdapter.UniversityHolder>() {

    val filter = Filter.filter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityHolder {
        val binding = UniversityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UniversityHolder(binding)
    }


    override fun onBindViewHolder(holder: UniversityHolder, position: Int) {
        holder.bind(universidades[position], position)
    }


    override fun getItemCount(): Int = universidades.size


    inner class UniversityHolder(val binding: UniversityItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(universidade: Universidade, position: Int) {

            val subsidio = filter.subsidio


            binding.root.setOnClickListener {
                itemClickListener.onItemClickDetalle(
                    universidade.id.toString(), universidade.nombre,
                    subsidio
                )
            }

            binding.textUniversityName.text = universidade.nombre
            binding.textUniversityAcronym.text = universidade.siglas
            Picasso.get().load(universidade.logo).into(binding.imageUniversity)
        }

    }


}

