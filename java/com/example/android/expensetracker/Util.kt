
package com.example.android.expensetracker

import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.android.expensetracker.database.Expense


fun convertNumericCategoryToString(category: Int): String {
    var categoryString = "Transport"
    when (category) {
        -1 -> categoryString = "--"
        0 -> categoryString = "Pocket money"
        1 -> categoryString = "Home expenses"
        2 -> categoryString = "Leisure"
        3 -> categoryString = "Clothes"
        4 -> categoryString = "Food"
        5 -> categoryString = "Transport"
    }
    return categoryString
}


fun formatExpenses(expenses: List<Expense>): Spanned {
    val sb = StringBuilder()
    sb.apply {
        expenses.forEach {
            append("<br>")
            append("<b>Category: </b>")
            append("\t${convertNumericCategoryToString(it.expenseCategory)}<br>")
            append("<b>Amount: </b>")
            append("\t${it.expenseAmount}<br><br>")
            }
        }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}

