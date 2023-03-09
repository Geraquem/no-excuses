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
import com.mmfsin.noexcuses.databinding.IncludeButtonInitBinding
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
            setButtons(routines, R.drawable.iv_rutinas, R.string.routines)
            setButtons(exercises, R.drawable.iv_exercises, R.string.exercises)
        }
    }

    private fun setButtons(button: IncludeButtonInitBinding, image: Int, name: Int) {
        button.image.setImageResource(image)
        button.tvName.text = getString(name)
    }

    override fun setListeners() {
        binding.apply {
            routines.root.setOnClickListener {
                findNavController().navigate(actionInitToPhases())
            }
            exercises.root.setOnClickListener {
                findNavController().navigate(actionPhasesToMuscularGroups())
            }
        }
    }

    override fun flowCompleted() {
        Toast.makeText(this@InitFragment.requireContext(), "realm existe", Toast.LENGTH_SHORT)
            .show()
    }

    override fun sww() {
        Toast.makeText(this@InitFragment.requireContext(), "sww", Toast.LENGTH_SHORT).show()
    }
}