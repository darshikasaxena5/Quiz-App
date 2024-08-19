package com.example.quizapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.view.View
import com.google.firebase.ktx.Firebase
import android.view.View as View1

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var quizModelList : MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizModelList = mutableListOf()
        getDataFromFirebase()
    }

    private fun setupRecyclerView(){
        binding.progressBar.visibility=View1.GONE
        adapter= QuizListAdapter(quizModelList)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        binding.recyclerView.adapter=adapter
    }

    private fun getDataFromFirebase(){
     binding.progressBar.visibility=View1.VISIBLE
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {dataSnapshot->
             if(dataSnapshot.exists()){
                 for (snapshot in dataSnapshot.children){
                     val quizModel = snapshot.getValue(QuizModel::class.java)
                     if (quizModel != null) {
                         quizModelList.add(quizModel)
                     }
                 }
             }
                setupRecyclerView()
            }

    }
    }
