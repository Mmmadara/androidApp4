/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.expensetracker.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.android.expensetracker.database.ExpenseDatabaseDao
import com.example.android.expensetracker.database.Expense
import kotlinx.coroutines.launch
import com.example.android.expensetracker.formatExpenses

/**
 * ViewModel for SleepTrackerFragment.
 */
class ExpenseTrackerViewModel(
    val database: ExpenseDatabaseDao,
    application: Application) : AndroidViewModel(application) {

    private var expense = MutableLiveData<Expense?>()

    private val expenses = database.getAllExpenses()

    val expensesString = Transformations.map(expenses) { expenses ->
        formatExpenses(expenses)
    }

    val clearButtonVisible = Transformations.map(expenses) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent

    private val _navigateToExpenseCategory = MutableLiveData<Expense>()

    val navigateToExpenseCategory: LiveData<Expense>
        get() = _navigateToExpenseCategory


    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    fun doneNavigating() {
        _navigateToExpenseCategory.value = null
    }

    init {
        initializeExpense()
    }

    private fun initializeExpense() {
        viewModelScope.launch {
            expense.value = getExpenseFromDatabase()
        }
    }
    private suspend fun getExpenseFromDatabase(): Expense? {
        return database.getExpense()
    }

    private suspend fun insert(expense: Expense) {
        database.insert(expense)
    }

    private suspend fun update(expense: Expense) {
        database.update(expense)
    }

    private suspend fun clear() {
        database.clear()
    }


    fun onSubmit(amount: String) {
        viewModelScope.launch {
            val newExpense = Expense()
            newExpense.expenseAmount = amount.toLong()
            insert(newExpense)

            expense.value = getExpenseFromDatabase()
            _navigateToExpenseCategory.value = expense.value ?:return@launch
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clear()

            expense.value = null
            _showSnackbarEvent.value = true
        }
    }

}