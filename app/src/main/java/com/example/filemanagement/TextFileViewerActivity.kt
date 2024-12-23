package com.example.filemanagement


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File

class TextFileViewerActivity : AppCompatActivity() {

    private val REQUEST_READ_STORAGE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_content)

        val filePath = intent.getStringExtra("filePath")
        Log.d("FileContentActivity", "Received file path: $filePath")
        if (filePath != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_STORAGE)
            } else {
                displayFileContent(filePath)
            }
        } else {
            Log.e("FileContentActivity", "File path is null")
        }
    }

    private fun displayFileContent(filePath: String) {
        val file = File(filePath)
        if (file.exists() && file.isFile) {
            val content = file.readText()
            findViewById<TextView>(R.id.fileContent).text = content
        } else {
            Log.e("FileContentActivity", "File does not exist or is not a file: $filePath")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_STORAGE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                val filePath = intent.getStringExtra("filePath")
                if (filePath != null) {
                    displayFileContent(filePath)
                }
            } else {
                Log.e("FileContentActivity", "Permission denied to read external storage")
            }
        }
    }
}
