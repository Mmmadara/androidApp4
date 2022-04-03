
package com.example.android.expensetracker.expensecategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.expensetracker.R
import com.example.android.expensetracker.database.ExpenseDatabase
import com.example.android.expensetracker.databinding.ExpenseCategoryFragmentBinding


class ExpenseCategoryFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: ExpenseCategoryFragmentBinding = DataBindingUtil.inflate(
                inflater, R.layout.expense_category_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = ExpenseCategoryFragmentArgs.fromBundle(requireArguments())

        val dataSource = ExpenseDatabase.getInstance(application).expenseDatabaseDao
        val viewModelFactory = ExpenseCategoryViewModelFactory(arguments.expenseKey, dataSource)

        val expenseCategoryViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(ExpenseCategoryViewModel::class.java)


        binding.expenseCategoryViewModel = expenseCategoryViewModel

        expenseCategoryViewModel.navigateToExpenseTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                        ExpenseCategoryFragmentDirections.actionExpenseCategoryFragmentToExpenseTrackerFragment())

                expenseCategoryViewModel.doneNavigating()
            }
        })

        return binding.root
    }
}
