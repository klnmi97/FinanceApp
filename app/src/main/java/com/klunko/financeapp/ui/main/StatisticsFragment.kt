package com.klunko.financeapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

import com.klunko.financeapp.R
import com.klunko.financeapp.data.DBOpenHelper
import com.klunko.financeapp.interfaces.PageFragment
import kotlinx.android.synthetic.main.fragment_statistics.*


/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : PageFragment() {

    private var balance: Float = 0f
    private var allIncomes: Float = 0f
    private var allExpenses: Float = 0f
    private var expenseMap = HashMap<String, Float>()
    private var incomeMap = HashMap<String, Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initialize fields with data from the db
        fetchData()
        //set new values to UI
        updateUI()
    }

    fun fetchData() {
        val db = DBOpenHelper(activity!!.applicationContext)
        balance = db.getBalance()
        expenseMap = db.getValueByCategory(true)
        incomeMap = db.getValueByCategory(false)
        allIncomes = db.getOperations(false)
        allExpenses = db.getOperations(true)

    }

    fun updateUI() {
        tv_balance.text = balance.toString()
        setupChart(expenseMap, expenses_piechart, "Expenses")
        setupChart(incomeMap, incomes_piechart, "Incomes")
    }

    override fun notifyDataUpdate() {
        fetchData()
        updateUI()
    }

    private fun setupChart(map: HashMap<String, Float>, chart: PieChart,
                           description: String) {


        var list = ArrayList<PieEntry>()
        if(map.size > 0) {
            for((key, value) in map){
                list.add(PieEntry(value, key))
            }

            val desc = Description()
            desc.text = description
            desc.textSize = 20f
            chart.description = desc

            val dataSet = PieDataSet(list, "Categories")

            val data = PieData(dataSet)
            chart.data = data
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS.toList())
            chart.notifyDataSetChanged()
        }
    }
}
