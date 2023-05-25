package com.example.aws_storage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.StorageException
import com.amplifyframework.storage.options.StorageListOptions
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text_view)

        // Poga "Augšupielādēt failu"
        val uploadBtn = findViewById<Button>(R.id.upload_file_btn)
        uploadBtn.setOnClickListener {
            uploadObject()
        }

        // Poga "Lejupielādēt failu"
        val downloadBtn = findViewById<Button>(R.id.download_file_btn)
        downloadBtn.setOnClickListener {
            downloadObject()
        }

        // Poga "Iegūt failu sarakstu"
        val listBtn = findViewById<Button>(R.id.list_files_btn)
        listBtn.setOnClickListener {
            getObjectList()
        }

        // Poga "Izdzēst failu"
        val deleteBtn = findViewById<Button>(R.id.delete_file_btn)
        deleteBtn.setOnClickListener {
            deleteObject()
        }
    }

    // Funkcija faila dzēšanai
    private fun deleteObject() {
        try {
            val result = Amplify.Storage.remove("ExampleKey2",
                {
                    textView.text = "Object is deleted."
                }, {
                    textView.text = "Object is not deleted. There was an error."
                })
        } catch (error: StorageException) {
            textView.text = "Object is not deleted. There was an error."
        }
    }

    // Funkcija failu saraksta ieguvei
    private fun getObjectList() {
        val options = StorageListOptions.builder()
            .build()

        var objects = "Objects: "
        Amplify.Storage.list("", options,
            { result ->
                result.items.forEach { item ->
                    objects += item.key + " "
                }
                textView.text = "Objects are listed: " + objects
            },
            {
                textView.text = "Objects are not listed. There was an error."
            }
        )
    }

    // Funkcija faila augšupielādei
    private fun uploadObject() {
        val exampleFile = File("/sdcard/Download/file3.txt")

        Amplify.Storage.uploadFile("ExampleKey2", exampleFile,
            {
                textView.text = "Object is uploaded."
            },
            {
                textView.text = "Object is not uploaded. There was an error."
            }
        )
    }

    // Funkcija faila lejupielādei
    private fun downloadObject() {
        val file = File("/sdcard/Download/fileaws.txt")
        Amplify.Storage.downloadFile("ExampleKey", file,
            {
                textView.text = "Object is downloaded."
            },
            {
                textView.text = "Object is not downloaded. There was an error."
            }
        )
    }
}