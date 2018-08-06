package com.gears42.inventory

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.PopupMenu
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.*
import android.widget.Toast
import com.gears42.Adapater.DeviceAdapter
import com.gears42.Api.InventoryApi
import com.gears42.Models.CheckInResponse
import com.gears42.Models.Device
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
     //var qrScan: IntentIntegrator
    private var device: Device =Device()
    private val checkInRequestcode=1
    private val checkOutRequestcode=2
    public var flag="";
    val context =this
    var qrScan: IntentIntegrator  = IntentIntegrator(this)
    var device_id:Int?=0
    var device_List:ArrayList<Device>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


          retrofitCallgetDevice()

        checkinfloat.setOnClickListener {
            // qrScan.setRequestCode(checkInRequestcode)
            flag="checkIn"
            qrScan?.initiateScan()

        }


    }

    private fun showmenu(view: View?, device: Device) {
        Log.d("longclick","working2")
        var popup = PopupMenu(context, view!!)
        val inflater = popup.getMenuInflater()

        inflater.inflate(R.menu.list_menu, popup.getMenu())
        popup.setOnMenuItemClickListener{
            Log.d("longclick","working3")

            if(it.title.equals("Checkout")){
                device_id= device.deviceId
                flag="checkOut"
                qrScan?.initiateScan()

            }
            true
        }
        popup.gravity = Gravity.CENTER_HORIZONTAL
        popup.show()

    }

    private fun retrofitCallgetDevice() {
        val apiService = InventoryApi.create()
        var Response:ArrayList<Device>?=null
        var EmployeeId=196;
        val call = apiService.GetMyDeviceList(EmployeeId)

        call.enqueue(object : Callback<ArrayList<Device>> {
            override fun onResponse(call: Call<ArrayList<Device>>, response: Response<ArrayList<Device>>) {
                Response = response.body()
                device_List=Response
                setadapter(Response)


                Log.d("DEBUG", "Retrofit call")
                //Toast.makeText(this!,Response.toString(),Toast.LENGTH_SHORT).show()
                //Log.d("DEBUG", "Number of items ${repository?.items}")

                //Log.d("Success",response.raw().request().url().toString()+" "+Response!!.get(0).toString() +"  "+response.raw().request().body())
            }

            override fun onFailure(call: Call<ArrayList<Device>>?, t: Throwable?) {
                //Response?.responseData="Faliure"
                //Toast.makeText(this!,Response.toString(),Toast.LENGTH_SHORT).show()
                Log.d("faliure",t.toString())
            }


        })


    }

    private fun setadapter(response: ArrayList<Device>?) {
        var adapter = DeviceAdapter(context,
                R.layout.item_list, response!!)
        device_list.adapter=adapter
       // registerForContextMenu(device_list)

//        device_list.setOnItemLongClickListener{ parent, view, position, id ->
//           // this.openContextMenu(view!!)
//            //registerForContextMenu(view)
//            var dev=   parent.getItemAtPosition(position) as Device
//
//
//
//            true
//
//
//        }
//        device_list.setOnItemLongClickListener{ parent, view, position, id ->
//
////            var dev=   parent.getItemAtPosition(position) as Device
////            showmenu(view,position,dev)
////            Log.d("longclick","working")
//
//            registerForContextMenu(view)
//           // showContextMenu(view)
//            true
//
//
//
//
//        }




    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            //Check to see if QR Code has nothing in it
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show()
            } else if(flag.equals("checkIn")){
                //QR Code contains some data
                try {
                    //Convert the QR Code Data to JSON
                    val obj = JSONObject(result.contents)
                    var gson= Gson()
                    Toast.makeText(this,obj.toString(),Toast.LENGTH_LONG).show()

                    device=gson.fromJson<Device>(obj.toString(),Device::class.java)

                        device.EmployeeId=196



                        retrofitCallCheckIn();
                        Log.d("device" ,device.toString())
                        Log.d("obj" ,obj.toString())
                       // Log.d("device" ,device.teamName!![0])
                        Log.d("obj" ,obj.toString())


                    //Set up the TextView Values using the data from JSON


                } catch (e: JSONException) {
                    e.printStackTrace()
                    //In case of exception, display whatever data is available on the QR Code
                    //This can be caused due to the format MisMatch of the JSON
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }
            } else if(flag.equals("checkOut")){
                //QR Code contains some data
                try {
                    //Convert the QR Code Data to JSON
                    val obj = JSONObject(result.contents)
                    var gson= Gson();
                    Toast.makeText(this,obj.toString(),Toast.LENGTH_LONG).show()

                    device=gson.fromJson<Device>(obj.toString(),Device::class.java)
                   // device.EmployeeId=196
                    // var arr =arrayOf("Development","Testing")
                    // var arr = gson.fromJson<Array<String>>(obj)
                    //device.teamName=arr;

                    device=gson.fromJson<Device>(obj.toString(),Device::class.java)
                    if(device_id==device.deviceId){
                        device.EmployeeId=196

                        retrofitCallCheckOut();
                        Log.d("device" ,device.toString())
                        Log.d("obj" ,obj.toString())
                      //  Log.d("device" ,device.teamName!![0])
                        Log.d("obj" ,obj.toString())
                    }else{
                        Toast.makeText(context,"You are trying to checkout some other Device",Toast.LENGTH_LONG).show()
                    }

                    //Set up the TextView Values using the data from JSON


                } catch (e: JSONException) {
                    e.printStackTrace()
                    //In case of exception, display whatever data is available on the QR Code
                    //This can be caused due to the format MisMatch of the JSON
                    Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
                }

            }



        }
    }

    private fun retrofitCallCheckOut() {
        val apiService = InventoryApi.create()
        var Response:CheckInResponse?=null
        val call = apiService.CheckOut(device!!)

        call.enqueue(object : Callback<CheckInResponse> {
            override fun onResponse(call: Call<CheckInResponse>, response: Response<CheckInResponse>) {
                Response = response.body()

                Log.d("DEBUG", "Retrofit call")
                //Toast.makeText(this!,Response.toString(),Toast.LENGTH_SHORT).show()
                //Log.d("DEBUG", "Number of items ${repository?.items}")
                retrofitCallgetDevice()

                Log.d("Success",response.raw().request().url().toString()+" "+device.toString() +"  "+response.raw().request().body())
            }

            override fun onFailure(call: Call<CheckInResponse>?, t: Throwable?) {
                Response?.responseData="Faliure"
                //Toast.makeText(this!,Response.toString(),Toast.LENGTH_SHORT).show()
                Log.d("faliure",t.toString())
            }


        })
    }

    private fun retrofitCallCheckIn() {

        val apiService = InventoryApi.create()
        var Response:CheckInResponse?=null
        val call = apiService.Checkin(device!!)

        call.enqueue(object : Callback<CheckInResponse> {
            override fun onResponse(call: Call<CheckInResponse>, response: Response<CheckInResponse>) {
                Response = response.body()

                Log.d("DEBUG", "Retrofit call")
                //Toast.makeText(this!,Response.toString(),Toast.LENGTH_SHORT).show()
                //Log.d("DEBUG", "Number of items ${repository?.items}")
                retrofitCallgetDevice()

              Log.d("Success",response.raw().request().url().toString()+" "+device.toString() +"  "+response.raw().request().body())
            }

            override fun onFailure(call: Call<CheckInResponse>?, t: Throwable?) {
                Response?.responseData="Faliure"
                //Toast.makeText(this!,Response.toString(),Toast.LENGTH_SHORT).show()
                Log.d("faliure",t.toString())
            }


        })


    }

    fun checkout(view: View, position: Int) {

        var dev=device_List?.get(position)
        showmenu(view,dev!!)

    }


}
