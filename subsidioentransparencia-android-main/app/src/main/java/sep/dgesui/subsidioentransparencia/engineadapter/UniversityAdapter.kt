package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.university_item.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.interfaces.DataList
import sep.dgesui.subsidioentransparencia.model.Universidade

class UniversityAdapter(val universidades: List<Universidade>, val itemClickListener: DataList) :
    RecyclerView.Adapter<UniversityAdapter.UniversityHolder>() {

    val filter = Filter.filter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversityHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UniversityHolder(layoutInflater.inflate(R.layout.university_item, parent, false))
    }


    override fun onBindViewHolder(holder: UniversityHolder, position: Int) {
        holder.bind(universidades[position], position)
    }


    override fun getItemCount(): Int = universidades.size


    inner class UniversityHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(universidade: Universidade, position: Int) {

            val subsidio = filter.subsidio


            view.setOnClickListener {
                itemClickListener.onItemClickDetalle(
                    universidade.id.toString(), universidade.nombre,
                    subsidio
                )
            }
            view.text_university_name.text = universidade.nombre
            view.text_university_acronym.text = universidade.siglas
            Picasso.get().load(universidade.logo).into(view.image_university)
        }

    }


}

