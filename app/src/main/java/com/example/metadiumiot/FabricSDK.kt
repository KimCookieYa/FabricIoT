package com.example.metadiumiot

import android.os.Build
import androidx.annotation.RequiresApi
import org.hyperledger.fabric.gateway.Gateway
import org.hyperledger.fabric.gateway.Network
import org.hyperledger.fabric.gateway.Wallet
import org.hyperledger.fabric.gateway.Wallets
import java.nio.file.Paths

class FabricSDK() { //private val wallet: Wallet) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun executeTrade() {
        val wallet = Wallets.newFileSystemWallet(Paths.get("wallet"))
        val builder = Gateway.createBuilder()
        builder.identity(wallet, "user1").networkConfig(Paths.get("./", "config.json")).discovery(true)
        builder.connect().use { gateway ->
            kotlin.runCatching {
                val network: Network = gateway.getNetwork("testbed")
                val contract = network.getContract("fabric_iot", "com.example.metadiumiot")

                val result = contract.submitTransaction("InitLedger")

                println("Result for submitting execute ${result}")
            }.exceptionOrNull()?.let {
                println("Error submitting execute ${it}")
            }
        }
    }

}