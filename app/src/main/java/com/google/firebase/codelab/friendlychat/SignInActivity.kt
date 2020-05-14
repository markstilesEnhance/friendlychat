/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.firebase.codelab.friendlychat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import androidx.test.filters.LargeTest
//import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
//import androidx.test.rule.ActivityTestRule
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

//import org.junit.Rule
//import org.junit.runner.RunWith

class SignInActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    private var mSignInButton: SignInButton? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private lateinit var mFirebaseAuth: FirebaseAuth

    // Firebase instance variables
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Assign fields
        mSignInButton = findViewById<View?>(R.id.sign_in_button) as SignInButton

        // Set click listeners
        mSignInButton!!.setOnClickListener(this)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        // Initialize FirebaseAuth
        mFirebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View) {
        when (v.id) {
                R.id.sign_in_button -> {
                    signIn()
                }
            }
    }

    private fun signIn() {
        val intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:$connectionResult")
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private val TAG: String? = "SignInActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result != null) {
                if(result.isSuccess) {
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account!!)
                } else {
                    Log.e(TAG, "Google Sign-In failed.")
                }
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) {
                    Log.d(TAG, "signInWithCredential:onComplete:" + it.isSuccessful)
                    if(!it.isSuccessful) {
                        Log.w(TAG, "signInWithCredential", it.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
    }
}