package com.mmfsin.noexcuses.presentation.init

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentInitBinding
import com.mmfsin.noexcuses.databinding.IncludeButtonHInitBinding
import com.mmfsin.noexcuses.databinding.IncludeButtonVInitBinding
import com.mmfsin.noexcuses.presentation.init.InitFragmentDirections.Companion.actionInitToPhases
import com.mmfsin.noexcuses.presentation.init.InitFragmentDirections.Companion.actionPhasesToMuscularGroups

class InitFragment : BaseFragment<FragmentInitBinding>(), InitView {

    private val presenter by lazy { InitPresenter(this) }

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentInitBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.checkWhereToCall()
    }

    override fun setUI() {
        binding.apply {
            background.visibility = View.GONE
            setVerticalButtons(routines, R.drawable.iv_rutinas, R.string.routines)
            setVerticalButtons(exercises, R.drawable.iv_exercises, R.string.exercises)
            setHorizontalButtons(myWeights, R.drawable.iv_exercises, R.string.my_weights)
            setHorizontalButtons(notes, R.drawable.iv_notes, R.string.notes)
        }
    }

    private fun setVerticalButtons(button: IncludeButtonVInitBinding, image: Int, name: Int) {
        button.image.setImageResource(image)
        button.tvName.text = getString(name)
        button.tvDescription.visibility = View.GONE
    }

    private fun setHorizontalButtons(button: IncludeButtonHInitBinding, image: Int, name: Int) {
        button.image.setImageResource(image)
        button.tvName.text = getString(name)
        button.tvDescription.visibility = View.GONE
    }

    override fun setListeners() {
        binding.apply {
            ivSwitch.setOnClickListener {
                background.visibility = if (background.visibility == View.VISIBLE) {
                    ivSwitch.setImageResource(R.drawable.iv_sw_off)
                    View.GONE
                } else {
                    ivSwitch.setImageResource(R.drawable.iv_sw_on)
                    View.VISIBLE
                }
            }

            routines.root.setOnClickListener {
                findNavController().navigate(actionInitToPhases())
            }
            exercises.root.setOnClickListener {
                findNavController().navigate(actionPhasesToMuscularGroups())
            }
        }
    }

    override fun flowCompleted() {
    }

    override fun sww() {
        Toast.makeText(this@InitFragment.requireContext(), "sww", Toast.LENGTH_SHORT).show()
    }
}