package com.rum.myapplication.fblogin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.rum.myapplication.databinding.ActivityFbLoginBinding


class FBLoginActivity : AppCompatActivity() {

    private lateinit var mContext: Context
    private lateinit var binding: ActivityFbLoginBinding

    private lateinit var fbCallbackManager: CallbackManager
    private var name: String? = null
    private var email: String? = null
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        binding = ActivityFbLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFB()
        setListeners()

    }

    private fun setListeners() {
        binding.btnTestFBLogin.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"));
        }
    }

    private fun initFB() {
        fbCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(
            fbCallbackManager,
            object : FacebookCallback<LoginResult> {

                override fun onCancel() {
                    Toast.makeText(mContext, "onCancel", Toast.LENGTH_SHORT).show()
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(mContext, exception.message, Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(loginResult: LoginResult) {

                    /*val profile: Profile? = Profile.getCurrentProfile()

                    Log.e("RumFB", "onSuccess--------" + loginResult.accessToken)
                    Log.e("RumFB", "Token--------" + loginResult.accessToken.token)
                    Log.e("RumFB", "Permission--------" + loginResult.recentlyGrantedPermissions)
                    val profile2 : Profile? = Profile.getCurrentProfile()
                    Log.e("RumFB", "ProfileDataNameF--" + (profile2?.firstName ?: "no fName found"))
                    Log.e("RumFB", "ProfileDataNameL--" + (profile2?.lastName ?: "no lName found"))
                    Log.e("RumFB", "FB ID --" + (profile2?.id ?: "no lName found"))
                    Log.e("RumFB", "Image URI--" + (profile?.linkUri?: "no linkUri found"))*/

                    val request = GraphRequest.newMeRequest(loginResult.accessToken) { objectt, response ->
                        Log.e("RumFB", "GraphResponse-------------" + response.toString())
                        Log.e("RumFB", "objectt-------------" + objectt.toString())
                    }
                    val parameters = Bundle()
//                    parameters.putString("fields", "id,name,link,gender,birthday,email")
                    request.parameters = parameters
                    request.executeAsync()


                    /*try {
                        GraphRequest.newMeRequest(result.accessToken) { obj, _ ->
                            if (obj != null) {
                                try {
                                    if (obj.has("first_name") && !TextUtils.isEmpty(
                                            obj.getString("first_name")
                                        )
                                    ) {
                                        name = obj.getString("first_name")
                                    }

                                    if (obj.has("email") && !TextUtils.isEmpty(
                                            obj.getString("email")
                                        )
                                    ) {
                                        email = obj.getString("email")
                                    }
                                    if (obj.has("id") && !TextUtils.isEmpty(
                                            obj.getString("id")
                                        )
                                    ) {
                                        id = obj.getString("id")
                                    }

                                    Toast.makeText(mContext, "id = " + id, Toast.LENGTH_SHORT)
                                        .show()
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        mContext,
                                        e.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(
                                    mContext,
                                    "object is null for FB ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }*/

                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fbCallbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}