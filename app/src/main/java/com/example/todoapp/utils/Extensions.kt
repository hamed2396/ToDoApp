package com.example.todoapp.utils

import android.util.Log

fun Any.logError(){
    Log.e("mytag", this.toString(), )
}