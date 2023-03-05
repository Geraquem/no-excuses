package com.mmfsin.noexcuses.presentation.phases.interfaces

interface IPhaseListener {
    fun onClick(id: String)
    fun editPhase(id: String)
    fun deletePhase(id: String)
}