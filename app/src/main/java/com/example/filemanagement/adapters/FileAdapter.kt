package com.example.filemanagement.adapters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanagement.R
import com.example.filemanagement.TextFileViewerActivity
import com.example.filemanagement.ui.FileListFragment
import java.io.File

class FileAdapter(private val files: List<File>) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.file_item, parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        holder.fileName.text = file.name
        if (file.isDirectory) {
            holder.fileIcon.setImageResource(R.drawable.ic_folder)
        } else {
            holder.fileIcon.setImageResource(R.drawable.ic_file)
        }
        holder.itemView.setOnClickListener {
            if (file.isDirectory) {
                val fragment = FileListFragment().apply {
                    arguments = Bundle().apply {
                        putString("path", file.absolutePath)
                    }
                }
                (holder.itemView.context as AppCompatActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            } else {
                Log.d("FileAdapter", "Opening file: ${file.absolutePath}")
                val intent = Intent(holder.itemView.context, TextFileViewerActivity::class.java).apply {
                    putExtra("filePath", file.absolutePath)
                }
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = files.size

    class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileName: TextView = itemView.findViewById(R.id.fileName)
        val fileIcon: ImageView = itemView.findViewById(R.id.fileIcon)
    }
}
