package com.gears42.Api

import android.bluetooth.BluetoothClass
import com.gears42.Models.CheckInResponse
import com.gears42.Models.Device
import retrofit2.Call
import retrofit2.http.*

interface InventoryApi {
    @POST("api/values/PostCheckIn")
    fun Checkin(@Body device: Device): Call<CheckInResponse>
    @POST("api/values/PostCheckOut")
    fun CheckOut(@Body device: Device): Call<CheckInResponse>
    @GET("api/values/GetMyDevices/{Id}")
    fun GetMyDeviceList(@Path("Id") Employyeid: Int): Call<ArrayList<Device>>


    companion object {
        fun create(): InventoryApi{
            val retrofit = retrofit2.Retrofit.Builder()
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .baseUrl("http://192.168.2.225/")
                    .build()

            return retrofit.create(InventoryApi ::class.java)
        }
    }
}