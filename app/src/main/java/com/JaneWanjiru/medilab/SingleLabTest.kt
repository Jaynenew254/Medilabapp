package com.JaneWanjiru.medilab

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.JaneWanjiru.medilab.helpers.SQLiteCartHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class SingleLabTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_lab_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name =findViewById<MaterialTextView>(R.id.test_name)
        val description =findViewById<MaterialTextView>(R.id.test_description)
        val discount = findViewById<MaterialTextView>(R.id.test_discount)
        val cost = findViewById<MaterialTextView>(R.id.test_cost)

        val btn = findViewById<MaterialButton>(R.id.addtocart)



//        retrieve data from intent extras
        val lab_id = intent.extras?.getString("lab_id")
        val test_id = intent.extras?.getString("test_id")
        val test_name = intent.extras?.getString("test_name")
        val test_description = intent.extras?.getString("test_description")
        val test_discount = intent.extras?.getString("test_discount")
        val test_cost = intent.extras?.getString("test_cost")
        val reg_date = intent.extras?.getString("reg_date")

        name.text =test_name
        description.text =test_description
        discount.text ="$test_discount %OFF"
        cost.text ="$test_cost KES"

        btn.setOnClickListener {
//            call our class SQLITE cart helper
            val helper = SQLiteCartHelper(applicationContext)
            try {
                helper.insertData(test_id!!,test_name!!,test_description!!,test_cost!!,test_discount!!,lab_id!!)
            }//end of try

            catch (e: Exception){
                Toast.makeText(applicationContext, "An ERROR Occurred", Toast.LENGTH_SHORT).show()

            }//end of catch

        } //end of on click listener
//        test item count inside our cart
        val helper =SQLiteCartHelper(applicationContext)
        val count = helper.getNumberOfItems()
        Toast.makeText(applicationContext, "Item Count is $count", Toast.LENGTH_SHORT).show()
//        get all the items
        val items = helper.getAllItems()
        for (item in items){
            Toast.makeText(applicationContext, "${item.test_id}", Toast.LENGTH_SHORT).show()

        }//end of for loop
        helper.clearCartById("47")
        helper.totalCost()

    }

}