package com.klunko.financeapp.interfaces

import android.database.Cursor
import androidx.fragment.app.Fragment

open class PageFragment: Fragment() {
    open fun notifyDataUpdate() = Unit
}