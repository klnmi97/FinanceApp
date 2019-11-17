package com.klunko.financeapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.klunko.financeapp.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.transaction_list_item.view.*

class TransactionsAdapter(val items: ArrayList<String> ,context: Context): RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder?.mTitle?.text = items[position]
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mTitle: TextView = view.findViewById(R.id.tv_transaction_item)

    }
}

