package com.example.shoppinglist

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var list: ArrayList<DataModel>
    private lateinit var shoppingAdapter: ShoppingAdapter

    // to store data permanently in the app
    private lateinit var sharedPref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPref = getSharedPreferences("shopping", Context.MODE_PRIVATE)
        editor = sharedPref.edit()

        fetchData()

        shoppingAdapter = ShoppingAdapter(list)
        shoppingList.adapter = shoppingAdapter

        fab.setOnClickListener {
            showInputDialog()
        }

    }

    private fun showInputDialog() {
        val cusomeView = LayoutInflater.from(this).inflate(R.layout.input_view, null, false)

        val dialog = AlertDialog.Builder(this)
            .setView(cusomeView)
            .create()

        cusomeView.findViewById<Button>(R.id.button).setOnClickListener {
            val text = cusomeView.findViewById<EditText>(R.id.editText).text.toString()
            val dataModel = DataModel(text)
            list.add(dataModel)
            Toast.makeText(this, "added", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun fetchData() {
        val serializesList = sharedPref.getString("list", null)
        list = if (serializesList != null) {
            Gson().fromJson(serializesList, DataModelContainer::class.java).list
        } else {
            ArrayList()
        }
    }

    //store data list before when destroying the activity
    override fun onDestroy() {
        super.onDestroy()
        val serializesList = Gson().toJson(DataModelContainer(list))
        editor.putString("list", serializesList).commit()
    }
}
