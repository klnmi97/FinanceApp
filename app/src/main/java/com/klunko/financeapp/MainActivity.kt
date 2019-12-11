package com.klunko.financeapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AppCompatActivity
import com.klunko.financeapp.adapters.SectionsPagerAdapter
import com.klunko.financeapp.adapters.TransactionsAdapter
import com.klunko.financeapp.interfaces.PageFragment
import com.klunko.financeapp.ui.main.TransactionActivity
import kotlinx.android.synthetic.main.activity_main.*

/* List of default transaction categories */
val DEFAULT_CAT_LIST = listOf("General", "Food", "Entertainment", "Sports", "Public Transport",
    "Home", "Work", "Health", "Electronics", "Clothing", "Family", "Services", "Holidays")

class MainActivity : AppCompatActivity(), TransactionsAdapter.OnItemClickListener {

    companion object {
        const val REQUEST_ADD = 20
        const val REQUEST_EDIT = 21
        const val REQ_ADD_OK = 21
        const val REQ_EDIT_OK = 22
        const val EXTRA_TRANSACTION = "extra_id"
        const val EXTRA_CODE = "request_code"
    }

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sectionsPagerAdapter =
            SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)

        //Add transaction button
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->

            startAddActivity()
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_ADD) {

            if(resultCode == REQ_ADD_OK) {
                val fragment = getActivePage()
                fragment.notifyDataUpdate()
            }
        } else if (requestCode == REQUEST_EDIT) {
            if(resultCode == REQ_EDIT_OK) {
                val fragment = getActivePage()
                fragment.notifyDataUpdate()
            }
        }
    }

    override fun onClick(id: Int) {
        val intent = Intent(this, TransactionActivity::class.java)
        intent.putExtra(EXTRA_CODE, REQUEST_EDIT)
        intent.putExtra(EXTRA_TRANSACTION, id)
        startActivityForResult(intent, REQUEST_EDIT)
    }

    private fun startAddActivity() {
        val intent = Intent(this, TransactionActivity::class.java)
        intent.putExtra(EXTRA_CODE, REQUEST_ADD)
        startActivityForResult(intent, REQUEST_ADD)
    }

    private fun getActivePage(): PageFragment {
        val position = view_pager.currentItem
        return sectionsPagerAdapter.getRegisteredFragment(position) as PageFragment
    }
}