package com.JaneWanjiru.medilab.adapters

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.JaneWanjiru.medilab.LabTestActivity
import com.JaneWanjiru.medilab.R
import com.JaneWanjiru.medilab.models.Lab
import com.google.android.material.textview.MaterialTextView

//we provide context in below class to make it be like an activity

class LabAdapter (var context:Context):RecyclerView.Adapter<LabAdapter.ViewHolder>() {
//    name of class is LadAdapter
// recycler view.adapter it is provided by android to work with rescyler view
//    labadapter.view holderit means that adapter will work with view holder class namedview holder
//    create a list and connect it with our model
    var itemList:List<Lab> = listOf()//its empty
    // var itemList means value can be changed
    //listof() means there are no labs in the ist initially

    //create a class which will hold our views in single_lab.xml
   inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

//    access single_lab xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //inflate the single lab.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_lab,parent,false)
    //pass the single lab to view holder
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //bind data to views from single_lab xml
        //find ur three text views
        val lab_name =holder.itemView.findViewById<MaterialTextView>(R.id.lab_name)
        val permit_id=holder.itemView.findViewById<MaterialTextView>(R.id.permit_id)
        val email=holder.itemView.findViewById<MaterialTextView>(R.id.email)

        //assium 1 lab ,and then bind data,it will loop other labs
        val lab = itemList[position]
        lab_name.text=lab.lab_name
        permit_id.text="permit_id" + lab.permit_id
        email.text=lab.email

//        when one lab is clicked move to lab test activity
        holder.itemView.setOnClickListener {
            //to navigate to lab test on each lab click
//carry lab_id clicked with click bundle
            val id = lab.lab_id
//            pass this id along with intent
            val intent= Intent (context,LabTestActivity::class.java)
//            we use key1to save it
            intent.putExtra("key1",id)
            intent.flags =Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)

        }

    }
//    count the number of items
    override fun getItemCount(): Int {
        return itemList.size //count how many items are in the list
    }
//  function to  filter data
    fun filterList(filterList: List<Lab>){
        itemList=filterList
        notifyDataSetChanged()
    }


//    earlier get data from our api,then bring it to the below function
//    te data u bring hre must follow the lab model
    fun setListItems(data:List<Lab>){
        itemList=data //link our data to item list
        notifyDataSetChanged()
//    tell this adapter class that our item list is loaded with data

    }

}