package com.klunko.financeapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.klunko.financeapp.DEFAULT_CAT_LIST
import java.text.SimpleDateFormat
import java.util.*

private const val SQL_CREATE_GROUP_ENTRIES =
    "CREATE TABLE ${DBContract.CategoryEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DBContract.CategoryEntry.COLUMN_NAME} TEXT NOT NULL," +
            "${DBContract.CategoryEntry.COLUMN_PARENT_GROUP_ID} INTEGER"

private const val SQL_CREATE_TRANSACTION_ENTRIES =
    "CREATE TABLE ${DBContract.TransactionEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DBContract.TransactionEntry.COLUMN_VALUE} REAL DEFAULT 0," +
            "${DBContract.TransactionEntry.COLUMN_TITLE} TEXT" +
            "${DBContract.TransactionEntry.COLUMN_IS_EXPENSE} INTEGER DEFAULT 1," +
            "${DBContract.TransactionEntry.COLUMN_DATE} TEXT," +
            "${DBContract.TransactionEntry.COLUMN_DESCRIPTION} TEXT," +
            "${DBContract.TransactionEntry.COLUMN_CATEGORY} INTEGER NOT NULL," +
            "FOREIGN KEY ${DBContract.TransactionEntry.COLUMN_CATEGORY} REFERENCES " +
            "${DBContract.CategoryEntry.TABLE_NAME}(${BaseColumns._ID})" +
            ");"

private const val SQL_DROP_GROUPS = "DROP TABLE IF EXISTS ${DBContract.CategoryEntry.TABLE_NAME}"
private const val SQL_DROP_TRANSACTIONS = "DROP TABLE IF EXISTS ${DBContract.TransactionEntry.TABLE_NAME}"

class DBOpenHelper(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "finances.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_GROUP_ENTRIES)
        db?.execSQL(SQL_CREATE_TRANSACTION_ENTRIES)
        createDefaultGroups(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DROP_TRANSACTIONS)
        db?.execSQL(SQL_DROP_GROUPS)
        onCreate(db)
    }

    /**
     * Is used to initialize categories table. Inserts basic categories.
     *
     * @param db The database
     * */
    private fun createDefaultGroups(db: SQLiteDatabase?) {

        for (cat in DEFAULT_CAT_LIST) {
            val group = ContentValues().apply {
                put(DBContract.CategoryEntry.COLUMN_NAME, "General") }
            db?.insert(DBContract.CategoryEntry.TABLE_NAME, null, group)
        }
    }

    fun getGroups(): Cursor? {
        val db = this.readableDatabase
        return db?.rawQuery("SELECT ${DBContract.CategoryEntry.COLUMN_NAME} from " +
                "${DBContract.CategoryEntry.TABLE_NAME}", null)
    }

    fun insertTransaction(value: Float, isExpense: Boolean, date: Date, title: String, category: Int,
                          desc: String): Long? {
        val expenseFlag = if(isExpense) 1 else 0
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val newTransaction = ContentValues().apply {
            put(DBContract.TransactionEntry.COLUMN_VALUE, value)
            put(DBContract.TransactionEntry.COLUMN_IS_EXPENSE, expenseFlag)
            put(DBContract.TransactionEntry.COLUMN_DATE, dateFormat.format(date))
            put(DBContract.TransactionEntry.COLUMN_TITLE, title)
            put(DBContract.TransactionEntry.COLUMN_CATEGORY, category)
            put(DBContract.TransactionEntry.COLUMN_DESCRIPTION, desc)
        }

        val db = this.writableDatabase

        return db?.insert(DBContract.TransactionEntry.TABLE_NAME, null, newTransaction)
    }

    fun getAllTransactions(): Cursor? {
        val db = this.readableDatabase
        return db?.rawQuery("SELECT * from ${DBContract.TransactionEntry.TABLE_NAME}", null)
    }

}