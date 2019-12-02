package com.klunko.financeapp.ui.main

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.klunko.financeapp.R
import kotlinx.android.synthetic.main.activity_transaction.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionActivity : AppCompatActivity() {

    //TODO: update with newer import
    private lateinit var date: Date
    private var calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        date = Calendar.getInstance().time

        date_spinner.text = convertDate(date)

        val dateListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                updateDate()
            }
        }

        date_spinner!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                DatePickerDialog(this@TransactionActivity,
                    dateListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun convertDate(date: Date): String {
        var format = SimpleDateFormat("dd-MM-YYYY")
        return format.format(date)
    }

    private fun updateDate() {
        val date = convertDate(calendar.time)
        date_spinner!!.text = (date)
    }
}
