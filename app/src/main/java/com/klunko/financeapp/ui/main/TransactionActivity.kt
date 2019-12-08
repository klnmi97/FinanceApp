package com.klunko.financeapp.ui.main

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.klunko.financeapp.DEFAULT_CAT_LIST
import com.klunko.financeapp.MainActivity
import com.klunko.financeapp.R
import com.klunko.financeapp.data.DBOpenHelper
import kotlinx.android.synthetic.main.activity_transaction.*
import java.text.SimpleDateFormat
import java.util.*

class TransactionActivity : AppCompatActivity() {

    //TODO: update with newer import
    private lateinit var date: Date
    private var calendar = Calendar.getInstance()
    private var selectedCategoryId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        date = Calendar.getInstance().time

        date_spinner.text = convertDate(date)

        val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            updateDate()
        }

        date_spinner!!.setOnClickListener {
            DatePickerDialog(this@TransactionActivity,
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        //TODO: change list for data from the db
        group_spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
            DEFAULT_CAT_LIST)
        group_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int,
                                        id: Long) {
                selectedCategoryId = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCategoryId = 0
            }

        }

        button_ok.setOnClickListener {
            //TODO: add try/catch
            val value = value_input.text.toString().toFloat()
            val db = DBOpenHelper(this)
            db.insertTransaction(value, expense_switch.isChecked, date, title.toString(),
                selectedCategoryId, description.toString())
            val resultIntent = Intent()
            //TODO: add edit request code
            setResult(MainActivity.REQ_ADD_OK, resultIntent)
            finish()
        }
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
