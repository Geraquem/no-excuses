package com.mmfsin.noexcuses.presentation.phases.interfaces

interface IPhasesListener {
    fun onClick(id: String)
    fun editPhase(id: String)
    fun deletePhase(id: String)
}