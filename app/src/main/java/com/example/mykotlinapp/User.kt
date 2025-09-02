package com.example.mykotlinapp

data class User(
    val id :Int,
    val name: String,
    val username : String,
    val email : String,
    val phont : String ? = null,
    val website :String ?=null,
)
