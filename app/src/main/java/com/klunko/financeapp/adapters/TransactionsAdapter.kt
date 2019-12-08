package com.klunko.financeapp.adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.klunko.financeapp.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.transaction_list_item.view.*

class TransactionsAdapter(private var data: Cursor, context: Context): RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data.move(position)

        //TODO: update column names, do properly
        holder.mTitle.text = data.getString(2)
        val groupId = data.getString(5)
        holder.mGroup.text = groupId
        holder.mValue.text = "$" + data.getFloat(1).toString()
    }

    fun swapCursor(cursor: Cursor) {
        data = cursor
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val mTitle: TextView = view.findViewById(R.id.tv_transaction_title)
        val mGroup: TextView = view.findViewById(R.id.tv_group)
        val mValue: TextView = view.findViewById(R.id.value)
    }
}

