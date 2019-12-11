package com.klunko.financeapp.adapters

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.klunko.financeapp.DEFAULT_CAT_LIST
import com.klunko.financeapp.R
import kotlinx.android.synthetic.main.transaction_list_item.view.*

class TransactionsAdapter(private var data: Cursor, val itemClickListener: OnItemClickListener):
    RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(data.moveToPosition(position)) {
            //TODO: update column names, do properly
            val title = data.getString(2)
            val groupId = data.getString(6).toInt()
            //TODO: Change for categories table request
            val categoryName = DEFAULT_CAT_LIST[groupId]

            val transactionValue = data.getFloat(1)

            val expense = data.getInt(3)

            val indicatorValue = if(expense == 0) R.color.colorIncome else R.color.colorExpense

            holder.bind(title, categoryName,transactionValue, indicatorValue)
        } else {
            Log.e(this.javaClass.name, "Failed to move Cursor to $position in onBindViewHOlder" )
        }


    }

    fun swapCursor(cursor: Cursor) {
        data = cursor
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        private val mTitle: TextView = itemView.tv_transaction_title
        private val mGroup: TextView = itemView.tv_group
        private val mValue: TextView = itemView.value
        private val mIndicator: View = itemView.indicator_view

        fun bind(title: String, category: String, value: Float, indicatorRes: Int) {
            mTitle.text = title
            mGroup.text = category
            mValue.text = value.toString()
            mIndicator.setBackgroundResource(indicatorRes)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val adapterPosition = adapterPosition
            data.moveToPosition(adapterPosition)
            val selectedId = data.getInt(0)
            itemClickListener.onClick(selectedId)
        }

    }

    interface OnItemClickListener{
        fun onClick(id: Int)
    }
}

