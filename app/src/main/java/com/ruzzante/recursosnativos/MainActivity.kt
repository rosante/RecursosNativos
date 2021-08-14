package com.ruzzante.recursosnativos

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.*
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ruzzante.recursosnativos.adapter.ContactsAdapter
import com.ruzzante.recursosnativos.util.AccessRequest
import com.ruzzante.recursosnativos.util.ContactUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listeners()
        setRecyclerView()

    }

    private fun setRecyclerView(){
        val recyclerView = findViewById(R.id.rv_contacts) as RecyclerView
        recyclerView.adapter = ContactsAdapter(this, ContactUtil().getContacts(this))
    }


    private fun listeners(){

        img_set_event.setOnClickListener{
            val intent = Intent(Intent.ACTION_INSERT)
                .setData(CONTENT_URI)
                .putExtra(TITLE, "BootCamp DIO Inter")
                .putExtra(EVENT_LOCATION, "Online")
                .putExtra(CALENDAR_COLOR, "#FF9999")
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis()+(60*60*1000))

            startActivity(intent)
        }

        img_photos.setOnClickListener {
            changeImage()
        }

        img_camera.setOnClickListener {
            takePhoto()
        }

        img_maps.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java).apply{
                putExtra(EXTRA_MESSAGE, "this")
            }
            startActivity(intent)
        }

    }

    private fun hasImagePermissions():Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), AccessRequest.REQUEST_STORAGE)
            }else{
                return true
            }
        }
        return true
    }

    private fun hasCameraPermissions():Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                requestPermissions(arrayOf(Manifest.permission.CAMERA), AccessRequest.REQUEST_CAMERA)
            }else{
                return true
            }
        }
        return true
    }

    private fun hasStorageWritePermissions():Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), AccessRequest.REQUEST_WRITE_STORAGE)
            }else{
                return true
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == AccessRequest.IMAGE_PICK_CODE)
            img_photos.setImageURI(data?.data)

        if (resultCode == Activity.RESULT_OK && requestCode == AccessRequest.CAMERA_PHOTO_CODE){
            img_camera.setImageURI(image_uri)
        }


    }

    private fun changeImage(){
        if (hasImagePermissions()){
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, AccessRequest.IMAGE_PICK_CODE, null)
        }
        else{
            Toast.makeText(this, "Permissão as fotos negada", Toast.LENGTH_LONG).show()
        }
    }

    private fun takePhoto(){
        if (hasCameraPermissions() && hasStorageWritePermissions()){
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "Nova Foto")
            values.put(MediaStore.Images.Media.DESCRIPTION, "Imagem Capturada pela Camera")
            image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)


            startActivityForResult(cameraIntent, AccessRequest.CAMERA_PHOTO_CODE)

        }
        else{
            Toast.makeText(this, "Permissão a camera negada", Toast.LENGTH_LONG).show()
        }
    }
}