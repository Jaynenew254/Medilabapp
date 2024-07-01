package com.JaneWanjiru.medilab.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.JaneWanjiru.medilab.R
import com.JaneWanjiru.medilab.models.LabTest
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class LabTestsCartAdapter(var context: Context):RecyclerView.Adapter<LabTestsCartAdapter.ViewHolder>() {
//    create a list and connect it with our model
    var itemList: List<LabTest> = listOf()//its empty
//    create an inner class
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

//access single lab_test_cart

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LabTestsCartAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_labtests_cart,parent,false)
        //pass the single lab to view holder
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: LabTestsCartAdapter.ViewHolder, position: Int) {
        //        find the three text views and one button
        val test_name =holder.itemView.findViewById<MaterialTextView>(R.id.test_name)
        val test_description =holder.itemView.findViewById<MaterialTextView>(R.id.test_description)
        val test_cost =holder.itemView.findViewById<MaterialTextView>(R.id.test_cost)
//button
        val btn =holder.itemView.findViewById<MaterialButton>(R.id.remove)
//        assume one cart item
        val item=itemList[position]
        test_name.text=item.test_name
        test_description.text=item.test_description
        test_cost.text=item.test_cost + "KES"

//        add on click listener
        btn.setOnClickListener {
//to do later ,to remove an item from cart

        }
//        Toast.makeText(context, "Total cost is ${item.test_cost}", Toast.LENGTH_SHORT).show()


    }

    override fun getItemCount(): Int {

//        count how many items we have in the cart
return itemList.size

    }
//    earlier we mentioned item list is empty
//    we will get data form api and then bring it to below fuction
//    the data our bring shouild pllow the labtest model
    fun setListItem (data:List<LabTest>){

        itemList=data // map or link the data to itemlist
        notifyDataSetChanged()

//    tell the adapter class  the item list is loaded
    }


}