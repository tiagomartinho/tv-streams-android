package com.tm.tvstreams

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import user.SharedPreferencesUserRepository
import user.User

class LoginActivity : AppCompatActivity() {

    private lateinit var options: GoogleSignInOptions
    private lateinit var client: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val signInButton: Button? = this.findViewById(R.id.sign_in_button)
        signInButton?.setOnClickListener { signIn() }
    }

    private fun signIn() {
        options = Builder(DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .build()
        client = GoogleSignIn.getClient(this, options)
        client.signOut()
        client.revokeAccess()
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
            val account = task?.getResult(ApiException::class.java)
            account?.let {
                val repository = SharedPreferencesUserRepository(this)
                val user = User.buildWith(it)
                repository.save(user)
            }
        } catch (e: ApiException) {
            Log.d("LoginActivity", e.toString())
            Log.d("LoginActivity", e.localizedMessage)
            Toast.makeText(this,R.string.unexpected_error, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val RC_SIGN_IN = 101
    }

    private fun User.Companion.buildWith(account: GoogleSignInAccount): User {
        return User(account.id, account.email, account.displayName, account.photoUrl?.toString())
    }
}