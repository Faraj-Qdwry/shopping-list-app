package com.example.shoppinglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class ShoppingAdapter(private val list: ArrayList<DataModel>) : RecyclerView.Adapter<SPViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SPViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return SPViewHolder(view, deleteAction, refresh)
    }

    private val deleteAction: (item: DataModel) -> Unit = {
        list.remove(it)
        notifyDataSetChanged()
    }

    private val refresh: () -> Unit = {
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SPViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

}


class SPViewHolder(
    view: View,
    deleteAction: (item: DataModel) -> Unit,
    refresh: () -> Unit
) : RecyclerView.ViewHolder(view) {

    private val textView: TextView = view.findViewById<TextView>(R.id.textView)
    private val checkBox: CheckBox = view.findViewById<CheckBox>(R.id.checkbox)
    lateinit var dataModel: DataModel

    init {
        view.setOnLongClickListener {
            deleteAction(dataModel)
            Toast.makeText(view.context, "deleted", Toast.LENGTH_SHORT).show()
            true
        }

        view.setOnClickListener {
            dataModel.checked = !dataModel.checked
            refresh()
        }

        checkBox.setOnClickListener {
            view.performClick()
        }
    }


    fun bind(item: DataModel) {
        dataModel = item
        textView.text = item.textContent
        checkBox.isChecked = item.checked
    }

}