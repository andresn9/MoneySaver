package com.appify.android.moneysaver.interfaces

import com.appify.android.moneysaver.adapters.CategoryAdapter
import com.appify.android.moneysaver.data.Category
import com.appify.android.moneysaver.data.Transaction
import com.appify.android.moneysaver.data.Wallet

interface OnRecyclerItemClick {

    fun clickedCategory(category: Category)

    fun clickedWallet(wallet: Wallet)

    fun clickedTransaction(transaction: Transaction)



}