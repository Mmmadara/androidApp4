package com.example.android.expensetracker.expensecategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.expensetracker.database.ExpenseDatabaseDao

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the night and the SleepDatabaseDao to the ViewModel.
 */
class ExpenseCategoryViewModelFactory(
        private val expenseKey: Long,
        private val dataSource: ExpenseDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseCategoryViewModel::class.java)) {
            return ExpenseCategoryViewModel(expenseKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
