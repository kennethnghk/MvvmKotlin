package im.tobe.mvvmkotlin.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import im.tobe.mvvmkotlin.R
import im.tobe.mvvmkotlin.model.Animal
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.item_animal, parent, false)
        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animalName.text = animalList[position].name
    }

    override fun getItemCount() = animalList.size

    fun updateAnimalList(newAnimalList : List<Animal>) {
        animalList.clear()
        animalList.addAll(newAnimalList)

        // tell the system that the list is changed and refresh the whole list
        notifyDataSetChanged()
    }
}