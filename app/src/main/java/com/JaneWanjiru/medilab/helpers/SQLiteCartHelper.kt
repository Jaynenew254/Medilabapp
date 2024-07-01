package com.JaneWanjiru.medilab.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf
import com.JaneWanjiru.medilab.models.LabTest

//our class name is called SQLiteCartHelper and its going to accept 1 parameter called context

class SQLiteCartHelper (context:Context) : SQLiteOpenHelper(context,"cart.db",null,1){
//    make context visible to other functions
    val context =context
    override fun onCreate(db: SQLiteDatabase?) {
//        create table if it does not exist
        val createtable =("CREATE TABLE IF NOT EXISTS cart(test_id Integer PRIMARY KEY AUTOINCREMENT,test_name VARCHAR,test_description TEXT,test_cost INTEGER,test_discount INTEGER,lab_id INTEGER)")
        db?.execSQL(createtable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS cart")
        onCreate(db)

    }//end of onupgrade
//    insert save to cart
    fun insertData(test_id:String,test_name:String,test_description:String,test_cost:String,test_discount:String,lab_id:String){

//        ask permission to our db
        val db = this .writableDatabase
//        select before insert to see if id already exists
        val values = ContentValues()
        values.put("test_id",test_id)
        values.put("test_name",test_name)
        values.put("test_description",test_description)
        values.put("test_cost",test_cost)
        values.put("test_discount",test_discount)
        values.put("lab_id",lab_id)

//    save insert to cart table
        val result:Long=db.insert("cart",null,values)
        if (result < 1){
            println("Failed to Add")
            Toast.makeText(context, "Item Already In Cart", Toast.LENGTH_SHORT).show()

        }else{
            println("Item Added To Cart")
            Toast.makeText(context, "Item Is Added To Cart", Toast.LENGTH_SHORT).show()
        } //end of else statement
    }// end of insert data
//    check the number of items saved in our table
    fun getNumberOfItems():Int{

//        Get permission to read the db
        val db = this.readableDatabase
    val result:Cursor = db.rawQuery("select * from cart ",null)
//    return result count
    return result.count
    } //end of getnumberitem
    //function to clear cart
    fun clearCart(){
//        get permission to write the db
        val db= this.writableDatabase
        db.delete("cart",null,null)
        println("Cart Cleared")
        Toast.makeText(context, "Cart Cleared", Toast.LENGTH_SHORT).show()


    }//end of clear cart
//    function to retrive our records
//we loop in an array list

    fun getAllItems(): ArrayList<LabTest>{
//        get permission to wirte
        val db =this.writableDatabase
        val items = ArrayList<LabTest>()
//        creATE a cursor for our results
        val result:Cursor=db.rawQuery("select * from cart",null)
//        lets add all the raws into the items in array list
        while (result.moveToNext()){
//            access the labtest model
            val model =LabTest()
            model.test_id=result.getString(0)//assume one row ,for our test_id
            model.test_name=result.getString(1)
            model.test_description=result.getString(2)
            model.test_cost=result.getString(3)
            model.test_discount=result.getString(4)
            model.lab_id=result.getString(5)

//            add model to arrarlist
            items.add(model)


        }//end of while loop
        return items


    }//end of get all items

//    delete record by id .this deletes one record at a time
//    fun to remove one item
fun clearCartById(test_id:String){
//    GET WRITE PERMISSION TO DB
    val db =this.writableDatabase
//    provide the testing we are deleting
    db.delete("cart","test_id=?", arrayOf(test_id))
    println("Item ID $test_id Removed")
//    Toast.makeText(context, "Item Id $test_id Removed", Toast.LENGTH_SHORT).show()
} //end of delete by id

    //function to get the total cost of cart items
    fun totalCost(): Double{
//        get permission to read data
        val db =this .readableDatabase
        val result :Cursor= db.rawQuery("select test_cost from cart",null)
//        create a variable clled portal and assing it 0.0
        var total:Double=0.0
//        loop
while (result.moveToNext()){
//    the  cursor result returns a list of test _cost
//    loop through as u add them in to total
    total = total+result.getDouble(0)
}//END OF WHILE LOOP
//        return the updated total
//        Toast.makeText(context, "The total cost is $total", Toast.LENGTH_SHORT).show()
return total

    }



}