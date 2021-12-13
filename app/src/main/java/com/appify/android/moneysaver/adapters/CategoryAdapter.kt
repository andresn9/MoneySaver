package com.appify.android.moneysaver.adapters

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.interfaces.Communicator
import com.appify.android.moneysaver.interfaces.OnRecyclerItemClick
import com.appify.android.moneysaver.data.Category
import com.appify.android.moneysaver.data.Transaction
import com.appify.android.moneysaver.data.Wallet
import com.appify.android.moneysaver.databinding.FragmentCategoryItemBinding
import com.google.firebase.firestore.FirebaseFirestore

class CategoryAdapter(
    private val categoryList: ArrayList<Category>,
    private val ClickHandler : OnRecyclerItemClick,

    ) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(), OnRecyclerItemClick {

    private lateinit var context: Context
    private val db = FirebaseFirestore.getInstance()


    private lateinit var listener: Communicator




        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {


            var viewHolder : ViewHolder = ViewHolder(FragmentCategoryItemBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

            context = viewHolder.itemView.context


           return viewHolder



        }


        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {



            val category : Category = categoryList[position]
            viewHolder.name.text = category.name
            viewHolder.image.setImageResource(context.resIdByName(category.image,"drawable"))






        }



        override fun getItemCount(): Int {
            return categoryList.size
        }


    fun Context.resIdByName(resIdName: String?, resType: String): Int {
        resIdName?.let {
            return resources.getIdentifier(it, resType, packageName)
        }
        throw Resources.NotFoundException()
    }

        inner class ViewHolder(binding : FragmentCategoryItemBinding) :RecyclerView.ViewHolder(binding.root), View.OnClickListener{


            var image: ImageView = binding.categoryImage
            var name : TextView = binding.categoryName


            init{
                binding.root.setOnClickListener(this)
            }


            override fun onClick(p0: View?) {

                val currentCategory = categoryList[bindingAdapterPosition]
                ClickHandler.clickedCategory(currentCategory)

            }




        }



    override fun clickedCategory(category: Category) {
        TODO("Not yet implemented")
    }

     fun clickedWallet(wallet: Wallet) {
        TODO("Not yet implemented")
    }

    fun clickedTransaction(transaction: Transaction) {
        TODO("Not yet implemented")
    }


}
