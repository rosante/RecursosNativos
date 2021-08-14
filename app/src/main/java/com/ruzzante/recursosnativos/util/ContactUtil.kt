package com.ruzzante.recursosnativos.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import com.ruzzante.recursosnativos.model.Contact

class ContactUtil {

    val REQUEST_CONTACT = AccessRequest.LOCATION_PERMISSION_REQUEST_CODE

    @SuppressLint("Range")
    fun getContacts(context: Context):List<Contact>{
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.READ_CONTACTS), REQUEST_CONTACT)
        }
        val contactList:MutableList<Contact> = mutableListOf()
        val cursor = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        if (cursor != null){
            while(cursor.moveToNext()){
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactList.add(Contact(name, phone))
            }
            cursor.close()
        }
        return contactList
    }
}
