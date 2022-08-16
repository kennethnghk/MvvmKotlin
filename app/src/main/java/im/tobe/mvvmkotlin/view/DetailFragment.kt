package im.tobe.mvvmkotlin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import im.tobe.mvvmkotlin.R
import im.tobe.mvvmkotlin.model.Animal
import im.tobe.mvvmkotlin.util.getProgressDrawable
import im.tobe.mvvmkotlin.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    var animal: Animal? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        context?.let {
            animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
        }

        animalName.text = animal?.name
        animalLocation.text = animal?.location
        animalLifeSpan.text = animal?.lifeSpan
        animalDiet.text = animal?.diet
    }
}