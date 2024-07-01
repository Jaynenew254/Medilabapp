package com.JaneWanjiru.medilab

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.JaneWanjiru.medilab.constants.Constants
import com.JaneWanjiru.medilab.helpers.ApiHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        fetch six edittext input text

        val surname =findViewById<TextInputEditText>(R.id.surname)
        val others =findViewById<TextInputEditText>(R.id.others)
        val email =findViewById<TextInputEditText>(R.id.email)
        val phone =findViewById<TextInputEditText>(R.id.phone)
        val password =findViewById<TextInputEditText>(R.id.password)
        val confirm =findViewById<TextInputEditText>(R.id.confirm)

        val male =findViewById<MaterialRadioButton>(R.id.radiomale)
        val female =findViewById<MaterialRadioButton>(R.id.radiofemale)

        val btncreate =findViewById<MaterialButton>(R.id.create)
        btncreate.setOnClickListener {
//        handle radio options
            var gender="N/A"
            if (female.isChecked) {
                gender = "Female"
            }
            if (male.isChecked){
                gender="male"
            }//end of if

//            check if passwords are matching
            if(password.text.toString() ==confirm.text.toString()){
//                password match
//                save member
//                api helper
                val helper=ApiHelper(applicationContext)
//                base url endpoint
                val api=Constants.BASE_URL+"/member_signup"
//                create a json object
                val body= JSONObject()
//                insert data in the body
                body.put("surname",surname.text.toString())
                body.put("others",others.text.toString())
                body.put("email",email.text.toString())
                body.put("phone",phone.text.toString())
                body.put("dob","2020-04-02")
                body.put("password",password.text.toString())
                body.put("gender",gender)
                body.put("location_id","1")
                body.put("status","TRUE")

                helper.post(api,body,object:ApiHelper.CallBack{
                    override fun onSuccess(result: JSONArray?) {


                    }

                    override fun onSuccess(result: JSONObject?) {
                        Toast.makeText(applicationContext, "ACCOUNT CREATED SUCCESSFUL", Toast.LENGTH_SHORT).show()

                    }

                    override fun onFailure(result: String?) {
                        Toast.makeText(applicationContext, "POST FAILED", Toast.LENGTH_SHORT).show()
                    }

                })

            }else{
//                passworddont match
                Toast.makeText(applicationContext, "Password dont match", Toast.LENGTH_SHORT).show()
            }


        }

    }
}