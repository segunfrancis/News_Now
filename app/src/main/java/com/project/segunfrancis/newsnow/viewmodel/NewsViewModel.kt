package com.project.segunfrancis.newsnow.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.segunfrancis.newsnow.model.News

/**
 * Created by SegunFrancis
 */
class NewsViewModel : ViewModel() {
    private val list: MutableLiveData<List<News>> = MutableLiveData()
    private val ref = FirebaseDatabase.getInstance().getReference("news")

    fun getNewsLiveData(): LiveData<List<News>> {
        val query = ref.limitToLast(100)
        val newsList: List<News> = ArrayList()
        query.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    (newsList as ArrayList).clear()
                    for (snapshot in dataSnapshot.children) {
                        newsList.add(snapshot.getValue(News::class.java)!!)
                        Log.e("NewsRepository", snapshot.getValue(News::class.java)!!.description)
                    }
                    newsList.reverse()
                    list.value = newsList
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("NewsRepository", error.details)
                }
            })
        return list
    }
}