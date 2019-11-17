package com.klunko.financeapp.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter

import com.klunko.financeapp.R
import com.klunko.financeapp.adapters.TransactionsAdapter
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val transactions = ArrayList<String>()
        transactions.add("Transaction 1")
        transactions.add("Transaction 2")
        transactions.add("Transaction 3")
        transactions.add("Transaction 4")
        transactions.add("Transaction 5")


        transaction_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = TransactionsAdapter(transactions, context)
        }
    }


}
