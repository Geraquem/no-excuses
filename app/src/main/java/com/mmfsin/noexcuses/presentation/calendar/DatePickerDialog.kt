package com.mmfsin.noexcuses.presentation.calendar

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface.BUTTON_POSITIVE
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import java.util.Calendar

class DatePickerDialog(val selected: (info: CalendarInfo) -> Unit) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        selected(CalendarInfo(dayOfMonth, month, year))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val context = activity as Context
        val dialog = DatePickerDialog(
            context,
            R.style.DatePickerTheme,
            this,
            year,
            month,
            day
        )

        dialog.getButton(BUTTON_POSITIVE)
            ?.setTextColor(ContextCompat.getColor(context, R.color.teal_700))
        dialog.getButton(BUTTON_NEGATIVE)
            ?.setTextColor(ContextCompat.getColor(context, R.color.teal_700))

        return dialog
    }
}