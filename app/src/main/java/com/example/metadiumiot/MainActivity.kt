package com.example.metadiumiot

import android.bluetooth.BluetoothClass.Device
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.metahackathon.DeviceAdapter
import com.example.metahackathon.ItemClickListener
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var devicePrefs : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    companion object {
        var listDatas : ArrayList<DeviceData> = arrayListOf()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Smart Devices"

        devicePrefs = this.getSharedPreferences("devices",Context.MODE_PRIVATE)
        editor = devicePrefs.edit()

//        listDatas.add(DeviceData("1", "One","A","010-0000-0001"))
//        listDatas.add(DeviceData("2", "Two","B","010-0000-0002"))
//        listDatas.add(DeviceData("3", "Three","C","010-0000-0003"))
//

        //putPrefData(listDatas)

        // create an instance of the Normal Floating Action Button
        // and register with the appropriate ID
        val fab: ExtendedFloatingActionButton = findViewById(R.id.extendedFAB)

        //get extra && make data list

        // create instance of RecyclerView and register with the appropriate ID
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // create list of RecyclerViewData

        listDatas = getPrefData()
        // create a vertical layout manager
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // create instance of MyRecyclerViewAdapter
        val myRecyclerViewAdapter = DeviceAdapter(listDatas)

        // attach the adapter to the recycler view
        recyclerView.adapter = myRecyclerViewAdapter

        // also attach the layout manager
        recyclerView.layoutManager = layoutManager

        // make the adapter the data set
        // changed for the recycler view
        myRecyclerViewAdapter.notifyDataSetChanged()

        //
        myRecyclerViewAdapter.setItemClickListener(object : ItemClickListener{
            override fun onClick(value: DeviceData) {
                makeToast(value.toString())
            }
        })
        fab.setOnClickListener {
            val dialog = HomeDialog(this)
            dialog.showDialog()
//            val intent = Intent(applicationContext, FormActivity::class.java)
//            // put extra
//            intent.putExtra("from", "smartHome")
//            startActivity(intent)
        }

        // now creating the scroll listener
        // for the recycler view
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // if the recycler view is scrolled
                // above shrink the FAB
                if (dy > 10 && fab.isExtended) {
                    fab.shrink()
                }

                // if the recycler view is scrolled
                // above extend the FAB
                if (dy < -10 && !fab.isExtended) {
                    fab.extend()
                }

                // of the recycler view is at the first
                // item always extend the FAB
                if (!recyclerView.canScrollVertically(-1)) {
                    fab.extend()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

    }
    private fun getPrefData() : ArrayList<DeviceData>{
        var makeGson = GsonBuilder().create()
        var listType : TypeToken<MutableList<DeviceData>> = object : TypeToken<MutableList<DeviceData>>() {}

        // 저장되어 있는 객체 json 반환
        var strContact = devicePrefs.getString("deviceList", "")
        // list 형태로 변환
        var datas : ArrayList<DeviceData> = makeGson.fromJson(strContact,listType.type)
        return datas
    }
    private fun putPrefData(putDatas : ArrayList<DeviceData>) {
        var makeGson = GsonBuilder().create()
        // 저장 타입 지정
        var listType : TypeToken<MutableList<DeviceData>> = object : TypeToken<MutableList<DeviceData>>() {}
        // 데이터를 Json 형태로 변환
        var strContact = makeGson.toJson(putDatas, listType.type)
        // Json 으로 변환한 객체 저장
        editor.putString("deviceList", strContact)
    }

    private fun makeToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}