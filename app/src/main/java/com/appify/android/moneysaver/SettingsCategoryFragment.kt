package com.appify.android.moneysaver

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appify.android.moneysaver.adapters.CategoryAdapter
import com.appify.android.moneysaver.data.Category
import com.appify.android.moneysaver.databinding.FragmentSettingsCategoryBinding
import com.appify.android.moneysaver.interfaces.Communicator
import com.appify.android.moneysaver.interfaces.OnRecyclerItemClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "SettingsCategoryFragment"
class SettingsCategoryFragment : Fragment(), OnRecyclerItemClick {


    private var _binding: FragmentSettingsCategoryBinding? = null
    private val binding get() = _binding!!


    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryArrayList: ArrayList<Category>
    private lateinit var myAdapter : CategoryAdapter
    private lateinit var db : FirebaseFirestore

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>? = null

    private lateinit var communicator: Communicator


    /*
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                param1 = it.getString(ARG_PARAM1)
                param2 = it.getString(ARG_PARAM2)
            }
        }
    */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {






/*
        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseFirestore.getInstance().collection("userData").document(currentuser)
            .collection("categories").get().addOnSuccessListener { result ->
            for (document in result) {


                var name = document.get("name")
                var image = document.get("image")

                var category = Category(name as String, image as String)

                categories.add(category)

                //Log.d(ContentValues.TAG, categories[0].name)
               // Log.d(ContentValues.TAG, image)
                Toast.makeText(this.context,categories[0].name,Toast.LENGTH_LONG).show()

            }


        }.addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "Error getting documents: ", exception)
        }
*/

        // Inflate the layout for this fragment
        _binding = FragmentSettingsCategoryBinding.inflate(inflater, container, false)
        val view = binding.root



        binding.categoryRecycler.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_settingsCategoryFragment_to_addTransactionFragment);
        }








        return view
    }


    private fun EventChangeListener(){


        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        db.collection("userData").document(currentuser)
            .collection("categories").addSnapshotListener(object: EventListener<QuerySnapshot>{

                override fun onEvent(
                    value : QuerySnapshot?,
                    error : FirebaseFirestoreException?
                ) {

                    if(error != null){
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }



                    for(dc: DocumentChange in value?.documentChanges!!){
                        if(dc.type == DocumentChange.Type.ADDED){

                            categoryArrayList.add(dc.document.toObject(Category::class.java))
                        }
                    }

                    myAdapter.notifyDataSetChanged()



                }

        })

    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)






        recyclerView = binding.categoryRecycler
        recyclerView.layoutManager= LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)


        categoryArrayList = arrayListOf()

        myAdapter = CategoryAdapter(categoryArrayList, this)

        recyclerView.adapter= myAdapter

        EventChangeListener()
        /*binding.categoryRecycler.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            adapter = CategoryAdapter(categories)
        }*/


    }


    override fun clickedCategory(category: Category) {
        Log.d(TAG, "Clicked: ${category.name}")
        Log.d(TAG, "Clicked: ${category.image}")

        var imageId = context?.resIdByName(category.image,"drawable")
        val bundle = bundleOf("imageId" to imageId)
        findNavController().navigate(R.id.addTransactionFragment, bundle)
    }


    fun Context.resIdByName(resIdName: String?, resType: String): Int {
        resIdName?.let {
            return resources.getIdentifier(it, resType, packageName)
        }
        throw Resources.NotFoundException()
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsCategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
/*
    override fun passDataCom(position : Int, imageResource: Int) {
        val bundle = Bundle()
        bundle.putInt("input_pos", position)
        bundle.putInt("image", imageResource)

        val transaction = this.parentFragmentManager.beginTransaction()
        val fragment2 = SettingsCategoryFragment()

        fragment2.arguments = bundle

        transaction.replace(R.id.action_settingsCategoryFragment_to_addTransactionFragment, fragment2)
        transaction.addToBackStack(null)
        transaction.commit()
    }
*/

}