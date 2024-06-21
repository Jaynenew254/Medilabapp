package com.JaneWanjiru.medilab

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.JaneWanjiru.medilab.constants.Constants
import com.JaneWanjiru.medilab.helpers.ApiHelper
import com.JaneWanjiru.medilab.models.Lab
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
//    Global declarations they can be accessed all over this class
//    declare variables as lateinit means they can be initialized in future
    lateinit var recycler:RecyclerView
    lateinit var adapter:LabAdapter
    lateinit var progress: ProgressBar
    lateinit var itemList: List<Lab>
    lateinit var swiper:SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        fetch recycler view
         recycler = findViewById(R.id.recyclerview)
         progress = findViewById(R.id.progress)
//        set recycler and layout adapter

        val layoutManager= LinearLayoutManager(applicationContext)
        recycler.layoutManager=layoutManager
        recycler.setHasFixedSize(true)

//        link our adapter
        adapter=LabAdapter(applicationContext)
        recycler.adapter=adapter
        fetchData()

        swiper=findViewById(R.id.swiperefresh)
        swiper.setOnRefreshListener {
            fetchData() //fetch data again
        }





    }//      end of on create

//    functions to fetch laboratories from api and bind them in recyclerview
    fun fetchData (){
//        we need api
        val api= Constants.BASE_URL +"/laboratories"
        val helper=ApiHelper(applicationContext)
        helper.get(api,object:ApiHelper.CallBack{
            @SuppressLint("SuspiciousIndentation")
            override fun onSuccess(result: JSONArray?) {
                //take the above results to adapter
//                convert above result from json array to list <lab>
                val labjson=GsonBuilder().create()
                itemList=labjson.fromJson(result.toString(),Array<Lab>::class.java).toList()
                //finally our adapter has the data
                adapter.setListItems(itemList)
//                for the sake of recycling /looping items,add the adapter to recycler
                recycler.adapter=adapter
                progress.visibility= View.GONE
//set refreshing to false when data are loaded
                swiper.isRefreshing=false
//                search
            val search =findViewById<EditText>(R.id.search)
                search.addTextChangedListener(object :TextWatcher{
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

            }//end of on success

            override fun onSuccess(result: JSONObject?) {

            }//end of on success

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, "Error"+result.toString(), Toast.LENGTH_SHORT).show()
                progress.visibility= View.GONE


            }//end of failure
        })
    }//end of fetch data
//function to filter data
//  private function
    private fun filter (text:String){
//        create a new array list to filter our data
        val filteredList:ArrayList<Lab> = ArrayList()
//    run a for loop to compare elements
        for (item in itemList){
//            check if entered screen matched with any item of our recycler view
            if (item.lab_name.lowercase().contains(text.lowercase())){
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