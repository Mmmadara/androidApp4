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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.expensetracker.R
import com.example.android.expensetracker.database.ExpenseDatabase
import com.example.android.expensetracker.databinding.ExpenseTrackerFragmentBinding
import com.google.android.material.snackbar.Snackbar


class ExpenseTrackerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: ExpenseTrackerFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.expense_tracker_fragment, container, false)

        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel Factory.
        val dataSource = ExpenseDatabase.getInstance(application).expenseDatabaseDao
        val viewModelFactory = ExpenseTrackerViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        val expenseTrackerViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(ExpenseTrackerViewModel::class.java)

        binding.expenseTrackerViewModel = expenseTrackerViewModel
        binding.lifecycleOwner = this


        expenseTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT
                ).show()

                expenseTrackerViewModel.doneShowingSnackbar()
            }
        })


        expenseTrackerViewModel.navigateToExpenseCategory.observe(viewLifecycleOwner, Observer { expense ->
            expense?.let {
                this.findNavController().navigate(
                                ExpenseTrackerFragmentDirections.actionExpenseTrackerFragmentToExpenseCategoryFragment(expense.expenseId))
                expenseTrackerViewModel.doneNavigating()
            }
        })

        return binding.root
    }


}
