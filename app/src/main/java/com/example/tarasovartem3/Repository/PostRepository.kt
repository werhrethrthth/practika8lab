package com.example.tarasovartem3.Repository
import androidx.lifecycle.LiveData
import com.example.tarasovartem3.Delegation.Post


interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Int)
    fun shareById(id: Int)
    fun removeById(id: Int)
    fun save(post: Post)
}