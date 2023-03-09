package com.mmfsin.noexcuses.presentation.musculargroups

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMuscularGroupsBinding
import com.mmfsin.noexcuses.databinding.IncludeMuscularGroupBinding

class MGroupsFragment : BaseFragment<FragmentMuscularGroupsBinding>(), MGroupsView {

    private val presenter by lazy { MGroupsPresenter(this) }

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMuscularGroupsBinding.inflate(inflater, container, false)

    override fun setUI() {
        binding.apply {
            toolbar.title.text = getString(R.string.muscularGroups)
            setMuscularGroupData(hombro, R.drawable.iv_hombros, R.string.mg_hombro)
            setMuscularGroupData(pecho, R.drawable.iv_pecho, R.string.mg_pecho)
            setMuscularGroupData(biceps, R.drawable.iv_biceps, R.string.mg_biceps)
            setMuscularGroupData(triceps, R.drawable.iv_triceps, R.string.mg_triceps)
            setMuscularGroupData(espalda, R.drawable.iv_espalda, R.string.mg_espalda)
            setMuscularGroupData(pierna, R.drawable.iv_piernas, R.string.mg_piernas)
            setMuscularGroupData(abdominales, R.drawable.iv_abdominales, R.string.mg_abdominales)
            setMuscularGroupData(cardio, R.drawable.iv_cardio, R.string.mg_cardio)
        }
    }

    private fun setMuscularGroupData(mgroup: IncludeMuscularGroupBinding, image: Int, name: Int) {
        mgroup.image.setImageResource(image)
        mgroup.tvName.text = getString(name)
    }

    override fun setListeners() {
        binding.apply {
            toolbar.ivBack.setOnClickListener { activity?.onBackPressed() }

        }
    }

    override fun sww() {
        Toast.makeText(mContext, getString(R.string.sww), Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}