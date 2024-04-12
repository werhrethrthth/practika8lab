package com.example.tarasovartem3.Viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tarasovartem3.Delegation.Post
//import com.example.tarasovartem3.PostRepository
//import com.example.tarasovartem3.PostRepositoryInMemoryImpl
import com.example.tarasovartem3.Repository.PostRepository
import com.example.tarasovartem3.Repository.PostRepositoryInMemoryImpl
private val empty = Post(
    id = 0, content = " ",
    author = " ",
    likedByMe = false,
    published = " ",
    shareByMe = false,
    like = 0,
    share = 0
)
class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
     val edited = MutableLiveData(empty)
    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }
    fun edit(post:Post){
        edited.value = post
    }
    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }


    fun likeById(id: Int)=repository.likeById(id)
    fun shareById(id: Int)=repository.shareById(id)
    fun removeById(id: Int) = repository.removeById(id)
}