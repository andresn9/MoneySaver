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
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.Communicator
import com.appify.android.moneysaver.OnRecyclerItemClick
import com.appify.android.moneysaver.R
import com.appify.android.moneysaver.SettingsCategoryFragment
import com.appify.android.moneysaver.data.Category
import com.appify.android.moneysaver.databinding.FragmentCategoryItemBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CategoryAdapter(
    private val categoryList: ArrayList<Category>,
    private val ClickHandler : OnRecyclerItemClick,

) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>(),OnRecyclerItemClick {

        private lateinit var context: Context
    private val db = FirebaseFirestore.getInstance()


    private lateinit var listener: Communicator




        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {


/*
            val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.category_layout, viewGroup,false)





            context = itemView.context

*/
            var viewHolder : ViewHolder = ViewHolder(FragmentCategoryItemBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false))

            context = viewHolder.itemView.context


           return viewHolder

         //   Toast.makeText(context,categories[0].name,Toast.LENGTH_LONG).show()

        }


        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {



            val category : Category = categoryList[position]
            viewHolder.name.text = category.name
            viewHolder.image.setImageResource(context.resIdByName(category.image,"drawable"))




/*

            viewHolder.name.setOnClickListener{
                //communicator.passDataCom(viewHolder.image.id)
                Toast.makeText(context,viewHolder.image.id.toString(),Toast.LENGTH_LONG).show()


                    var categoryId = viewHolder.image.id


                    val category = hashMapOf(
                        "category" to categoryList[position],
                        "dateCreated" to Timestamp.now()

                    )

                    val currentuser = FirebaseAuth.getInstance().currentUser!!.uid

                    db.collection("userData").document(currentuser).collection("transactions").document()
                        .set(category)
                        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }



            }
*/






        }



        override fun getItemCount(): Int {
            return categoryList.size
        }
/*
     interface OnNoteListener{
        fun onNoteClick(position: Int)
    }
*/

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
                ClickHandler.clickedItem(currentCategory)

            }


/*


            i
            init {
                itemView.setOnClickListener (this)
            }

             override fun onClick(v: View?) {
                val position = absoluteAdapterPosition
                val image = categoryList[position].image
                val name =  categoryList[position].name
                if (position != RecyclerView.NO_POSITION) {
                    listener.passDataCom(position, context.resIdByName(categoryList[position].image,"drawable"))
                }
            }

*/


        }

    override fun clickedItem(category: Category) {
        TODO("Not yet implemented")
    }


}
