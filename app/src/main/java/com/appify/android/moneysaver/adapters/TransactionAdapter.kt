package com.appify.android.moneysaver.adapters

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.ChronologyFragment
import com.appify.android.moneysaver.data.Category
import com.appify.android.moneysaver.data.Transaction
import com.appify.android.moneysaver.data.Wallet
import com.appify.android.moneysaver.databinding.FragmentTransactionItemBinding
import com.appify.android.moneysaver.interfaces.OnRecyclerItemClick
import com.google.firebase.firestore.FirebaseFirestore

class TransactionAdapter(
    private val transactionList: ArrayList<Transaction>,
    private val ClickHandler: ChronologyFragment,


    ): RecyclerView.Adapter<TransactionAdapter.ViewHolder>(), OnRecyclerItemClick {


    private lateinit var context: Context
    private val db = FirebaseFirestore.getInstance()
    private var categoryImage : String?= null





    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {


        var viewHolder : ViewHolder = ViewHolder(FragmentTransactionItemBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

        context = viewHolder.itemView.context






        return viewHolder



    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val transaction : Transaction = transactionList[position]


        val docRef = transaction.category
        if (docRef != null) {
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        categoryImage = document.get("image") as String?
                        viewHolder.image.setImageResource(context.resIdByName(categoryImage, "drawable"))
                        viewHolder.categoryName.text = document.get("name") as String?
                        categoryImage?.let { Log.d(TAG, it) }
                    } else {
                        Log.d(TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(TAG, "get failed with ", exception)
                }
        }



        viewHolder.note.text = transaction.note
        viewHolder.amount.text = transaction.amount


    }
    fun Context.resIdByName(resIdName: String?, resType: String): Int {
        resIdName?.let {
            return resources.getIdentifier(it, resType, packageName)
        }
        throw Resources.NotFoundException()
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }


    inner class ViewHolder(binding : FragmentTransactionItemBinding) :RecyclerView.ViewHolder(binding.root), View.OnClickListener{


        var image: ImageView = binding.chrCategoryImage
        var note : TextView = binding.transNoteText
        var amount : TextView = binding.amount
        var categoryName : TextView = binding.chrCategoryName

        init{
            binding.root.setOnClickListener(this)
        }


        override fun onClick(p0: View?) {

            val currentTransaction = transactionList[bindingAdapterPosition]
            ClickHandler.clickedTransaction(currentTransaction)

        }



    }

    override fun clickedCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override fun clickedWallet(wallet: Wallet) {
        TODO("Not yet implemented")
    }

    override fun clickedTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }
}