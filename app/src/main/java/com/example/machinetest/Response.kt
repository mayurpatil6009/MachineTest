package com.example.machinetest

data class Response(
    val ERROR: Int,
    val MESSAGE: String,
    val ip: String,
    val request: Request
)