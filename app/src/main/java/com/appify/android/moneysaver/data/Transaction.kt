package com.appify.android.moneysaver.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import java.util.*

data class Transaction(var amount : String ?= null,  var note : String ?= null, var category: DocumentReference?= null)



//data class Transaction(var amount : String ?= null,var category:Category ?= null, var currency: String?= null, var frequency : String ?= null, var time: Timestamp ?= null,  var wallet: Wallet ?=null,  var note : String ?= null)
