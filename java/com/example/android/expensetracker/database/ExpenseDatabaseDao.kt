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

package com.example.android.expensetracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.android.expensetracker.database.Expense

@Dao
interface ExpenseDatabaseDao {

    @Insert
    suspend fun insert(expense: Expense)
    @Update
    suspend  fun update(expense: Expense)

    @Query("SELECT * from expense_table WHERE expenseId = :key")
    suspend fun get(key: Long): Expense?


    @Query("DELETE FROM expense_table")
    suspend fun clear()

    @Query("SELECT * FROM expense_table ORDER BY expenseId DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table ORDER BY expenseId DESC LIMIT 1")
    suspend fun getExpense(): Expense?
}
