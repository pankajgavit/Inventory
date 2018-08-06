package com.gears42.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Device {
    @SerializedName("deviceId")
    @Expose
    var deviceId: Int? = null
    @SerializedName("deviceName")
    @Expose
    var deviceName: String? = null
    @SerializedName("deviceModel")
    @Expose
    var deviceModel: String? = null

    @SerializedName("deviceOs")
    @Expose
    var deviceOs: String? = null
    @SerializedName("deviceRam")
    @Expose
    var deviceRam: Int = 0
    @SerializedName("storage")
    @Expose
    var storage: Int = 0
    @SerializedName("sreenGuardPresent")
    @Expose
    var sreenGuardPresent: Int = 0
    @SerializedName("additionalDeviceCOverPresent")
    @Expose
    var additionalDeviceCOverPresent: Int = 0
    @SerializedName("conditionType")
    @Expose
    var conditionType: Int = 0
    @SerializedName("teamName")
    @Expose
    var teamName: Array<String>? = null

    @SerializedName("employeeId")
    @Expose
    var EmployeeId: Int = 0
    @SerializedName("osVersion")
    @Expose
    var osVersion: String? = null


    override fun toString(): String {
        return "Device{" +
                "teamName = '" + teamName + '\''.toString() +
                ",deviceOs = '" + deviceOs + '\''.toString() +
                ",deviceRam = '" + deviceRam + '\''.toString() +
                ",deviceModel = '" + deviceModel + '\''.toString() +
                ",sreenGuardPresent = '" + sreenGuardPresent + '\''.toString() +
                ",conditionType = '" + conditionType + '\''.toString() +
                ",storage = '" + storage + '\''.toString() +
                ",deviceId = '" + deviceId + '\''.toString() +
                ",deviceName = '" + deviceName + '\''.toString() +
                ",additionalDeviceCOverPresent = '" + additionalDeviceCOverPresent + '\''.toString() +
                "EmployeeId = '" + EmployeeId + '\''.toString() +
                "}"
    }
}