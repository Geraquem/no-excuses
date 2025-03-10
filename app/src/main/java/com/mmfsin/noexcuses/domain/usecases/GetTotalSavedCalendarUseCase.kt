package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import javax.inject.Inject

class GetTotalSavedCalendarUseCase @Inject constructor(
    private val repository: ICalendarRepository,
) : BaseUseCaseNoParams<Int>() {

    override suspend fun execute(): Int = repository.getTotalSavedInCalendar()
}