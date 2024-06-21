package com.JaneWanjiru.medilab.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.JaneWanjiru.medilab.LabTestActivity
import com.JaneWanjiru.medilab.R
import com.JaneWanjiru.medilab.SingleLabTest
import com.JaneWanjiru.medilab.models.Lab
import com.JaneWanjiru.medilab.models.LabTest
import com.google.android.material.textview.MaterialTextView


class LabTestAdapter(var context: Context):RecyclerView.Adapter<LabTestAdapter.ViewHolder>(){
//    create a list and connect it with our model
    var itemList: List<LabTest> = listOf()//its empty
//    create inner class which holds our views in single_labtest
inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
//    access the single labtest xml

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabTestAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_single_labtests,parent,false)
        //pass the single lab to view holder
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabTestAdapter.ViewHolder, position: Int) {
//        find the three text views
        val test_name =holder.itemView.findViewById<MaterialTextView>(R.id.test_name)
        val test_description =holder.itemView.findViewById<MaterialTextView>(R.id.test_description)
        val test_cost =holder.itemView.findViewById<MaterialTextView>(R.id.test_cost)
    //assume one lab test
        val item=itemList[position]
        test_name.text=item.test_name
        test_description.text=item.test_description
        test_cost.text=item.test_cost + "KES"

        holder.itemView.setOnClickListener {
//            navigate to a single lab activity
//            pass this id along with intent
            val intent= Intent (context, SingleLabTest::class.java)
//            we use key1to save it
            intent.putExtra("lab_id",item.lab_id)
            intent.putExtra("test_id",item.test_id)
            intent.putExtra("test_name",item.test_name)
            intent.putExtra("test_description",item.test_description)
            intent.putExtra("test_cost",item.test_cost)
            intent.putExtra("test_discount",item.test_discount)
            intent.putExtra("reg_date",item.reg_date)


            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)




        }//end

    }

    override fun getItemCount(): Int {
//        count the number of lab test
        return itemList.size //count how many items are in the list


    }
//    filter data

    fun filterList(filterList: List<LabTest>){
        itemList = filterList
        notifyDataSetChanged()
    }
    fun setListItem(data: List<LabTest>){
        itemList=data
        notifyDataSetChanged()

//        tell this class that our item list is coded with data

    }

}