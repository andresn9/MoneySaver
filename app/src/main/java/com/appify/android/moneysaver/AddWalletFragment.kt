package com.appify.android.moneysaver

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.appify.android.moneysaver.databinding.FragmentAddWalletBinding
import com.appify.android.moneysaver.databinding.FragmentWalletBinding
import com.google.firebase.firestore.FirebaseFirestore

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddWalletFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddWalletFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentAddWalletBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddWalletBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.btnCreateWallet.setOnClickListener {

            var name = binding.editTextWalletName.text.toString()
            var amount = binding.editTextAmount.text.toString().toInt()
            var currency = binding.spinnerCurrency.selectedItem.toString()

            val wallet = hashMapOf(
                "name" to name,
                "amount" to amount,
                "currency" to currency
            )

            db.collection("wallets").document(name)
                .set(wallet)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }



        }








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
         * @return A new instance of fragment AddWalletFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddWalletFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}