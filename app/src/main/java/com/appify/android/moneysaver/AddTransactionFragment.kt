package com.appify.android.moneysaver

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import com.appify.android.moneysaver.databinding.FragmentAddTransactionBinding
import com.appify.android.moneysaver.databinding.FragmentAddWalletBinding
import com.appify.android.moneysaver.databinding.FragmentSettingsCategoryBinding
import com.facebook.FacebookSdk.getApplicationContext
import com.facebook.appevents.codeless.internal.ViewHierarchy.setOnClickListener
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.lang.Exception
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant.now

import java.time.LocalDate.now
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_IMAGEID = "imageId"
private const val ARG_PARAM2 = "param2"


private const val TAG = "TransactionFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTransactionFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var param1: Int? = null


    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_IMAGEID)
        }
        Log.d(TAG, "param: " + param1)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        val view = binding.root





        binding.categoryButton.setOnClickListener {


            Navigation.findNavController(view)
                .navigate(R.id.action_addTransactionFragment_to_settingsCategoryFragment)
        }

        binding.addButton.setOnClickListener {
            addTransaction()
            Navigation.findNavController(view).navigate(R.id.action_addTransactionFragment_to_chronologyFragment)
        }

        binding.buttonGoBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addTransactionFragment_to_chronologyFragment)
        }


        param1?.let {
            binding.imageTrans.setImageResource(it)
        }
        Log.d(TAG, "onCreateView: " + param1)


        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTransactionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            AddTransactionFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_IMAGEID, param1)
                    Log.d(TAG, "onCreate: $param1 ")
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun addTransaction() {


        val currentuser = FirebaseAuth.getInstance().currentUser!!.uid



        var imageString = param1?.let { resources.getResourceEntryName(it) }

        //Get Date
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateInstance()
        val formatedDate = formatter.format(date)


        //Insert Transaction, needs to be optimized, gets a reference to the category it uses from firebase

        var query = db.collection("userData").document(currentuser).collection("categories")
            .whereEqualTo("image", imageString).get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d(TAG, "REFERENCIA" + document.get("image"))

                var categoryRef = document.reference
                var amount = binding.ammountTransaction.text.toString()
                var currency = binding.currencySpinner.selectedItem.toString()
                var wallet = "placeHolder"
                var frequency = binding.spinnerFrequency.selectedItem.toString()
                var note = binding.noteText.text.toString()

                val transaction = hashMapOf(
                    "category" to categoryRef,
                    "amount" to amount,
                    "currency" to currency,
                    "wallet" to wallet,
                    "frequency" to frequency,
                    "time" to date,
                    "note" to note
                    )

                            db.collection("userData").document(currentuser).collection("transactions").document()
                        .set(transaction)
                        .addOnSuccessListener {
                            Log.d(
                                ContentValues.TAG,
                                "DocumentSnapshot successfully written!"
                            )
                        }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }



            }
        }








}}