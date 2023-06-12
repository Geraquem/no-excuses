package com.mmfsin.noexcuses.presentation.musculargroups

import com.mmfsin.noexcuses.data.repository.MGroupsRepository

class MGroupsPresenter(val view: MGroupsView) {

    private val repository by lazy { MGroupsRepository() }

    fun getMuscularGroups() = view.getMuscularGroups(repository.getMuscularGroupsFromRealm())
}