package com.klunko.financeapp.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.klunko.financeapp.R
import com.klunko.financeapp.data.DBOpenHelper
import com.klunko.financeapp.interfaces.PageFragment
import kotlinx.android.synthetic.main.fragment_statistics.*


/**
 * A simple [Fragment] subclass.
 */
class StatisticsFragment : PageFragment() {

    private var balance: Float = 0f

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

    }

    fun updateUI() {
        tv_balance.text = balance.toString()
    }

    override fun notifyDataUpdate() {
        fetchData()
        updateUI()
    }
}
