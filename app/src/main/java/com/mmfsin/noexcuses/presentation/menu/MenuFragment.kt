package com.mmfsin.noexcuses.presentation.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.mmfsin.noexcuses.base.BaseFragment
import com.mmfsin.noexcuses.databinding.FragmentMenuBinding
import com.mmfsin.noexcuses.domain.models.MenuAction
import com.mmfsin.noexcuses.domain.models.MenuAction.*
import com.mmfsin.noexcuses.domain.models.MenuItem
import com.mmfsin.noexcuses.presentation.menu.MenuFragmentDirections.Companion.actionMenuToMuscularGroups
import com.mmfsin.noexcuses.presentation.menu.adapter.MenuAdapter
import com.mmfsin.noexcuses.presentation.menu.interfaces.IMenuListener
import com.mmfsin.noexcuses.utils.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>(), IMenuListener {

    override val viewModel: MenuViewModel by viewModels()

    private lateinit var mContext: Context

    override fun inflateView(
        inflater: LayoutInflater, container: ViewGroup?
    ) = FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkVersion()
    }

    override fun setUI() {
        binding.apply {
            background.visibility = View.GONE
            loading.root.visibility = View.VISIBLE
        }
    }

    override fun setListeners() {}

    override fun observe() {
        viewModel.event.observe(this) { event ->
            when (event) {
                is MenuEvent.Completed -> viewModel.getMenuItems()
                is MenuEvent.MenuItems -> setUpMenu(event.items)
                is MenuEvent.SomethingWentWrong -> error()
            }
        }
    }

    private fun setUpMenu(items: List<MenuItem>) {
        binding.apply {
            binding.rvMenu.apply {
                layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
                adapter = MenuAdapter(items, this@MenuFragment)
            }
            loading.root.visibility = View.GONE
        }
    }

    override fun onItemClick(action: MenuAction) {
        when (action) {
            ROUTINES -> {}
            EXERCISES -> navigateTo(actionMenuToMuscularGroups())
            NOTES -> {}
            WEIGHTS -> {}
            CHRONOMETER -> {}
        }
    }

    private fun navigateTo(directions: NavDirections) = findNavController().navigate(directions)

    private fun error() = activity?.showErrorDialog()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}