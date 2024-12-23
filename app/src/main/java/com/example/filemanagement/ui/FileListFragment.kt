package com.example.filemanagement.ui

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemanagement.R
import com.example.filemanagement.adapters.FileAdapter
import java.io.File

class FileListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fileAdapter: FileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_file, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val path = arguments?.getString("path") ?: Environment.getExternalStorageDirectory().absolutePath
        fileAdapter = FileAdapter(getFiles(File(path)))
        recyclerView.adapter = fileAdapter
        return view
    }

    private fun getFiles(dir: File): List<File> {
        return dir.listFiles { file -> file.isFile || file.isDirectory || file.isHidden }?.toList() ?: emptyList()
    }
}
