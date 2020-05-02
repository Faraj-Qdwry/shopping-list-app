package com.example.shoppinglist

class DataModel (val textContent : String, var checked : Boolean = false)

class DataModelContainer (val list : ArrayList<DataModel>)