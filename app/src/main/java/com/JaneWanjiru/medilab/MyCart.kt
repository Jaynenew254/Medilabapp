package com.JaneWanjiru.medilab

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.JaneWanjiru.medilab.adapters.LabTestsCartAdapter
import com.JaneWanjiru.medilab.helpers.PrefsHelper
import com.JaneWanjiru.medilab.helpers.SQLiteCartHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class MyCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        fetch recycler view and text view and check out button
        val recyclerView = findViewById<RecyclerView>(R.id.recycler)
        val total = findViewById<MaterialTextView>(R.id.total)
        val btn = findViewById<MaterialButton>(R.id.checkout)
        btn.setOnClickListener {
//            using prefs check if token exists
            val token = PrefsHelper.getPrefs(applicationContext,"access_token")
//            Toast.makeText(applicationContext, "$token", Toast.LENGTH_SHORT).show()
            if(token.isEmpty()){
//                token does not exist...means not log in
                Toast.makeText(applicationContext, "Not logged in", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext,SignInActivity::class.java)
                startActivity(intent)
                finish()

            }else{
//                token exist...meaning ur logged in and can proceed to check out step one
                Toast.makeText(applicationContext, "Logged In", Toast.LENGTH_SHORT).show()



            }

        }

//        put total cost in a text view
        val helper = SQLiteCartHelper(applicationContext)

        //clear all cart items
//        helper.clearCart()

//        get total from the helper
        total.text = "Total Cost: " + helper.totalCost()
//        if totalcost is ZERO and our cart is empty hide the check out btn
//        if (helper.totalCost() == 0.0) {
//            btn.visibility = View.GONE
//        }//END OF IF

//        set ur layout
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager=layoutManager
        recyclerView.setHasFixedSize(true)
//if total item is zero, show cart is empty
        if (helper.getNumberOfItems() == 0) {
            Toast.makeText(applicationContext, "Your Cart IS Empty", Toast.LENGTH_LONG).show()
        }else{
//            access adapter and provide access and provide it with data using get all items
            val adapter=LabTestsCartAdapter(applicationContext)
            adapter.setListItem(helper.getAllItems())//pass our data
//            link adapter to rycycler
            recyclerView.adapter=adapter
        }
    }
}