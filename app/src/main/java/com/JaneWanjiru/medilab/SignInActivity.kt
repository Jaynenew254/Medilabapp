package com.JaneWanjiru.medilab

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.JaneWanjiru.medilab.constants.Constants
import com.JaneWanjiru.medilab.helpers.ApiHelper
import com.JaneWanjiru.medilab.helpers.PrefsHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.json.JSONArray
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//         link to register while login
        val linktoregister =findViewById<MaterialTextView>(R.id.linktoregister)
        linktoregister.setOnClickListener{
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }//end of listener

//        fetch two edittext
        val email=findViewById<TextInputEditText>(R.id.email)
        val password=findViewById<TextInputEditText>(R.id.password)
        val loginbtn=findViewById<MaterialButton>(R.id.login)
        loginbtn.setOnClickListener{
//            specify your member signin endpoint

            val api =  Constants.BASE_URL + "/member_signin"
//            specify apihelper object
            val helper = ApiHelper(applicationContext)
//            create a json object of email and password
            val body = JSONObject()
            body.put("email",email.text.toString())
            body.put("password",password.text.toString())
//            post
            helper.post(api,body,object:ApiHelper.CallBack{
                override fun onSuccess(result: JSONArray?) {


                }

                override fun onSuccess(result: JSONObject?) {
//                    check if access_token exists in responce
                    if (result!!.has("access_token")){
//                        Access token found,login success
//                        access the token and the member fromj the json returned
                        val access_token= result.getString("access_token")
                        val member =result.getString("member")
//                        toast a success message
                        Toast.makeText(applicationContext, "LOGIN SUCCESSFUL", Toast.LENGTH_SHORT).show()
                        //save access token to shared prefs
                        PrefsHelper.savePrefs(applicationContext,"access_token",access_token)
//                        convert member to an object
                        val memberObject =JSONObject(member)
                        val member_id =memberObject.getString("member_id")
                        val memberemail =memberObject.getString("email")
                        val surname =memberObject.getString("surname")
//                        save member_id
                        PrefsHelper.savePrefs(applicationContext,"member_id",member_id)
//                        save memberemail
                        PrefsHelper.savePrefs(applicationContext,"email",memberemail)
//save surname
                        PrefsHelper.savePrefs(applicationContext,"surname",surname)
//                        save member
                        PrefsHelper.savePrefs(applicationContext,"userObject",member)

//                        Toast.makeText(applicationContext, "$access_token", Toast.LENGTH_SHORT).show()
//redirect to min activity apon to succefull log in
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()

                    }else{
//                        No access token found ,login failed
                        Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()


                    }

                }

                override fun onFailure(result: String?) {


                }

            })


        }


    }
}

