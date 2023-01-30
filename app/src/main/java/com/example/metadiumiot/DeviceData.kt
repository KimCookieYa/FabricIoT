package com.example.metadiumiot

data class DeviceData(
    val deviceName: String,
    val macAddress: String,
    val ownerName : String,
    val ownerPhone : String,
) : java.io.Serializable
