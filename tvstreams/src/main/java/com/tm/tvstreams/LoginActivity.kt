package com.tm.tvstreams

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import user.SharedPreferencesUserRepository
import user.User

class LoginActivity : AppCompatActivity() {

    private lateinit var options: GoogleSignInOptions
    private lateinit var client: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        options = Builder(DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestId()
            .requestEmail()
            .build()
        client = GoogleSignIn.getClient(this, options)
        client.signOut()
        client.revokeAccess()
        firebaseAuth = FirebaseAuth.getInstance()
        val signInButton: Button? = this.findViewById(R.id.sign_in_button)
        signInButton?.setOnClickListener { signIn() }
    }

    private fun signIn() {
        startActivityForResult(client.signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            task?.getResult(ApiException::class.java)?.let {
                firebaseAuthWithGoogle(it)
            }
        } catch (e: ApiException) {
            Log.d("LoginActivity", e.toString())
            Log.d("LoginActivity", e.localizedMessage)
            Toast.makeText(this, R.string.unexpected_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    firebaseAuth.currentUser?.let {
                        val repository = SharedPreferencesUserRepository(applicationContext)
                        val user = User.buildWith(acct, it)
                        repository.save(user)
                        val intent = Intent(applicationContext, InitialActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent, null)
                    }
                } else {
                    Log.d("LoginActivity", task.exception.toString())
                    Log.d("LoginActivity", task.exception?.localizedMessage)
                    Toast.makeText(applicationContext, R.string.unexpected_error, Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        const val RC_SIGN_IN = 101
    }

    private fun User.Companion.buildWith(
        account: GoogleSignInAccount,
        currentUser: FirebaseUser
    ): User {
        return User(currentUser.uid, account.email, account.displayName, account.photoUrl?.toString())
    }
}