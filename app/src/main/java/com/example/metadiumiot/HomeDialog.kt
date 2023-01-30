package com.example.metadiumiot

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences.Editor
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class HomeDialog (context: Context)
{
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener
    var devicePrefs = context.getSharedPreferences("devices",Context.MODE_PRIVATE)
    var editor = devicePrefs.edit()

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog()
    {
        dialog.setContentView(R.layout.place_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

//        val edit_name = dialog.findViewById<EditText>(R.id.name_edit)
        val finish_button = dialog.findViewById<AppCompatButton>(R.id.finish_button)
        val cancel_button = dialog.findViewById<AppCompatButton>(R.id.cancel_button)
        val deviceName = dialog.findViewById<EditText>(R.id.device_name)
        val macAddress = dialog.findViewById<EditText>(R.id.mac_address)
        val ownerName = dialog.findViewById<EditText>(R.id.owner_name)
        val ownerPhone = dialog.findViewById<EditText>(R.id.owner_phone)

        finish_button.setOnClickListener {
            var data = DeviceData(deviceName.text.toString(),
                macAddress.text.toString(),
                ownerName.text.toString(),
                ownerPhone.text.toString())
            MainActivity.listDatas.add(data)

            putPrefData(MainActivity.listDatas)
            dialog.dismiss()
        }
        cancel_button.setOnClickListener {
            dialog.dismiss()
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun putPrefData(putDatas : ArrayList<DeviceData>) {
        var makeGson = GsonBuilder().create()
        // 저장 타입 지정
        var listType : TypeToken<MutableList<DeviceData>> = object : TypeToken<MutableList<DeviceData>>() {}
        // 데이터를 Json 형태로 변환
        var strContact = makeGson.toJson(putDatas, listType.type)
        // Json 으로 변환한 객체 저장
        editor.putString("deviceList", strContact)
        editor.commit()

        FabricSDK().executeTrade()
    }

    interface OnDialogClickListener
    {
        fun onClicked(name: String)
    }

}