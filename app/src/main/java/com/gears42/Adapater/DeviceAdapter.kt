package com.gears42.Adapater

import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import com.gears42.Models.Device
import com.gears42.inventory.R
import android.widget.Button
import com.gears42.inventory.MainActivity


class DeviceAdapter(context: Context, resource: Int, objects: ArrayList<Device>) : ArrayAdapter<Device>(context, resource, objects) {



    var resource: Int
    var list: ArrayList<Device>
    var vi: LayoutInflater
   // DeviceAdapter()

    init {
        this.resource = resource
        this.list = objects
        this.vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    internal class ViewHolder {
        var serial: TextView? = null
        var name: TextView? = null
        var operatingsystem: TextView? = null
        var option:Button?=null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {





        var holder :ViewHolder
        var retView:View
        if(convertView==null){
            retView=vi.inflate(resource,null)
            holder= ViewHolder()
          //  holder.serial= retView.findViewById(R.id.serial) as TextView?
            holder.name= retView.findViewById(R.id.name) as TextView?
            holder.operatingsystem= retView.findViewById(R.id.osversion) as TextView?
            holder.option=retView.findViewById(R.id.popup) as Button


            //holder.serial!!.text=position.toString()
            holder.name!!.text="$position. "+list.get(position).deviceName
            holder.operatingsystem!!.text=list.get(position).osVersion
            retView.tag=holder



        }else{
            holder = convertView.tag as ViewHolder
            Log.d("Success"," "+list.get(0).toString())
            //holder.serial!!.text=position.toString()
            holder.name!!.text="$position. "+list.get(position).deviceName
            holder.operatingsystem!!.text=list.get(position).osVersion
            retView = convertView
        }

        holder.option?.setOnClickListener{
            if (context is MainActivity) {
                (context as MainActivity).checkout(it,position)
            }
        }
        return retView

    }


}
