package com.appify.android.moneysaver

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.appify.android.moneysaver.databinding.ActivityAuthBinding
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback

import com.facebook.login.LoginManager
import com.facebook.AccessToken
import java.util.*
import com.google.firebase.auth.OAuthProvider

import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.auth.AuthResult

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory


class AuthActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAuthBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var callbackManager: CallbackManager
    private lateinit var provider:OAuthProvider.Builder


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root



        //Initialize firebase support
        FirebaseApp.initializeApp(/*context=*/this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )



        provider = OAuthProvider.newBuilder("twitter.com")
        callbackManager = CallbackManager.Factory.create();

        emailLoginSetup()
        googleLoginSetup()
        facebookLoginSetup()
        twitterLoginSetup()

        setContentView(view)




        //Check if user is logged in

        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired


        auth = Firebase.auth
    }


    private fun googleLoginSetup(){
        binding.buttonLoginGoogle.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("34913928981-rqbuqlvl82pdi6o7j27f13e707h2jn0d.apps.googleusercontent.com")
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)
            googleSignInClient.signOut()

            startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
        }
    }


    private fun twitterLoginSetup(){
        binding.buttonLoginTwitter.setOnClickListener{
            signInWithTwitter()
        }
    }


    private fun facebookLoginSetup(){
        binding.buttonLoginFacebook.setOnClickListener {

            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(loginResult: LoginResult?) {
                        // App code
                        showHome()
                    }

                    override fun onCancel() {
                        // App code
                    }

                    override fun onError(exception: FacebookException) {
                        // App code
                    }
                })


            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile"));
        }
    }


    private fun signInWithTwitter(){
        val pendingResultTask: Task<AuthResult>? = auth.getPendingAuthResult()
        if (pendingResultTask != null) {
            // There's something already here! Finish the sign-in for your user.
            pendingResultTask
                .addOnSuccessListener {
                    // User is signed in.
                    // IdP data available in
                    // authResult.getAdditionalUserInfo().getProfile().
                    // The OAuth access token can also be retrieved:
                    // authResult.getCredential().getAccessToken().
                    // The OAuth secret can be retrieved by calling:
                    // authResult.getCredential().getSecret().
                }
                .addOnFailureListener {
                    // Handle failure.
                }
        } else {
            auth
                .startActivityForSignInWithProvider( /* activity= */this, provider.build())
                .addOnSuccessListener(
                    OnSuccessListener<AuthResult?> {
                        // User is signed in.
                        // IdP data available in
                        // authResult.getAdditionalUserInfo().getProfile().
                        // The OAuth access token can also be retrieved:
                        // authResult.getCredential().getAccessToken().
                        // The OAuth secret can be retrieved by calling:
                        // authResult.getCredential().getSecret().
                        showHome()
                    })
                .addOnFailureListener(
                    OnFailureListener {
                        // Handle failure.
                    })
            // There's no pending result so you need to start the sign-in flow.
            // See below.
        }
    }


    private fun emailLoginSetup() {


        binding.buttonLoginEmail.setOnClickListener {

            val email = binding.textLoginEmail.text

            val password = binding.textLoginPassword.text



            if (email.isNotEmpty() && password.isNotEmpty()) {


                auth.createUserWithEmailAndPassword(
                    email.toString(),
                    password.toString()
                ).addOnCompleteListener(this) {

                    val task = it


                    if (task.isSuccessful) {
                        showAlert("Message", "Usuario creado")
                        signInEmail(email.toString(), password.toString())


                    } else (
                            try {
                                throw task.exception!!

                            } catch (e: FirebaseAuthUserCollisionException) {
                                signInEmail(email.toString(), password.toString())


                            } catch (e: Exception) {
                                showAlert("Error", "Email o contraseña no cumplen los requisitos")
                            })

                }


            }
        }
    }

    private fun showAlert(type: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(type)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun showHome(/*email: String, provider: ProviderType*/) {


        val MainIntent = Intent(this, MainActivity::class.java).apply {
            // putExtra("email", email)
            // putExtra("provider", provider.name)
        }

        startActivity(MainIntent)


    }

    private fun signInEmail(email: String, password: String) {

        auth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                showAlert("Mensaje", "Has iniciado sesion")
                showHome(/*it.result?.user?.email ?: "", ProviderType.BASIC*/)

            } else {
                showAlert("Error", "Email o contraseña no válido")
            }

        }

/*
        firebaseAnalytics = Firebase.analytics
        val bundle = Bundle()
        bundle.putString("Message", "User " + email.toString() + " has signed up")
        firebaseAnalytics.logEvent("InitScreen", bundle)*/
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    // [START onactivityresult]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
    // [END onactivityresult]


    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    showHome()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    // [END auth_with_google]



    // [START signin]
    private fun signInEmail() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }
    // [END signin]

    private fun updateUI(user: FirebaseUser?) {

    }

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

}