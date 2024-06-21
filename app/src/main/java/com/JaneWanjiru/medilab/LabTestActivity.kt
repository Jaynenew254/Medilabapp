package com.JaneWanjiru.medilab

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.JaneWanjiru.medilab.adapters.LabAdapter
import com.JaneWanjiru.medilab.adapters.LabTestAdapter
import com.JaneWanjiru.medilab.constants.Constants
import com.JaneWanjiru.medilab.helpers.ApiHelper
import com.JaneWanjiru.medilab.models.Lab
import com.JaneWanjiru.medilab.models.LabTest
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class LabTestActivity : AppCompatActivity() {
//    declare globals
        lateinit var recycler: RecyclerView
        lateinit var adapter: LabTestAdapter
        lateinit var progress: ProgressBar
        lateinit var itemList: List<LabTest>
        lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lab_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }//end
//        fecth
        recycler = findViewById(R.id.recyclerview)
        progress = findViewById(R.id.progress)
        swipeRefreshLayout=findViewById(R.id.swiperefresh)


//        set recycler and layout adapter

        val layoutManager= LinearLayoutManager(applicationContext)
        recycler.layoutManager=layoutManager
        recycler.setHasFixedSize(true)

//                link our adapter
        adapter= LabTestAdapter(applicationContext)
        recycler.adapter=adapter
        getlabtest()

//        find the swiper
        swipeRefreshLayout.setOnRefreshListener {
            getlabtest() //get data again
        }


    }
    //end of oncreate
//function to fetch to get labtest but we use post method
    fun getlabtest(){
        val api=Constants.BASE_URL+ "/lab_test"
        val helper=ApiHelper(applicationContext)
//        create a json object that will hold the input values
        val body =JSONObject()
//        RETRIEVE THE ID FROM INTENT EXTRAS -we will add this id bundle extras later
        val id = intent.extras?.getString("key1")
//        provide the id to the api
        body.put("lab_id",id)
        helper.post(api, body,object:ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                //  convert above result from json array to list <labtest>
                val labtestjson= GsonBuilder().create()
                itemList=labtestjson.fromJson(result.toString(),Array<LabTest>::class.java).toList()
                //finally our adapter has the data
                adapter.setListItem(itemList)
                // for the sake of recycling /looping items,add the adapter to recycler
                recycler.adapter=adapter
                progress.visibility= View.GONE
                swipeRefreshLayout.isRefreshing=false
//                search data
                val search =findViewById<EditText>(R.id.search)
                search.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }//end of before

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        filter(s.toString())
                    }//end of text changed

                    override fun afterTextChanged(s: Editable?) {

                    }//end of after text changed
                })


            }

            override fun onSuccess(result: JSONObject?) {

                progress.visibility=View.GONE
            }

            override fun onFailure(result: String?) {
                progress.visibility= View.GONE
                Log.d("onFailure: ",result.toString())


            }


        })


} //end of fetch data

    //function to filter data
//  private function
    private fun filter (text:String){
//        create a new array list to filter our data
        val filteredList:ArrayList<LabTest> = ArrayList()
//    run a for loop to compare elements
        for (item in itemList){
//            check if entered screen matched with any item of our recycler view
            if (item.test_name.lowercase().contains(text.lowercase())){
//                if the item is mathched we are
//                adding it to our filtered list
                filteredList.add(item)

            }//end of if statement
        } //end of for loop
        if (filteredList.isEmpty()){
//        if no item is added in filtered list
//        we are going to display a toast message as no data found

//        Toast.makeText(applicationContext, "No Data Found", Toast.LENGTH_SHORT).show()
            adapter.filterList(filteredList)
        } else{
//        atleast we are passing filtered data
            adapter.filterList(filteredList)
        }
    }

}