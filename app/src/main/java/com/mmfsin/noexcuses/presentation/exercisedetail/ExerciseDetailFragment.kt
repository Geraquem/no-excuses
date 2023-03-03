package com.mmfsin.noexcuses.presentation.exercisedetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.intefaces.IFragment
import com.mmfsin.noexcuses.presentation.exercises.model.ExerciseDTO

class ExerciseDetailFragment(private val listener: IFragment, val exercise: ExerciseDTO) :
    Fragment(),
    ExerciseDetailView {

    private val presenter by lazy { ExerciseDetailPresenter(this) }

    private lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //loading VISIBLE

//        close.setOnClickListener { listener.close() }
//        watchVideo.setOnClickListener{
//            //go to video
//        }
//
//        exerciseName.text = exercise.name
//        Glide.with(mContext).load(exercise.image).into(exerciseImage)
//        muscularGroups.text = exercise.id
//        exerciseDescription.text = exercise.description

        //loading GONE

    }

    override fun somethingWentWrong() {
        Toast.makeText(mContext, getString(R.string.sww), Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}