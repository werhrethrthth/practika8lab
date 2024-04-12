package com.example.tarasovartem3.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.example.tarasovartem3.Viewmodel.PostViewModel
import com.example.tarasovartem3.databinding.ActivityMainBinding

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.example.Adapter.OnInteractionListener
import com.example.Adapter.PostsAdapter
import com.example.tarasovartem3.Delegation.Post
import com.example.tarasovartem3.R
import com.example.tarasovartem3.Util.AndroidUtils


//import kotlin.android.synthetic.main.activity_main.*

class MainActivity<TextView> : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }
            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
            override fun onShare(post: Post) {
                viewModel.shareById(post.id)
            }
        })
        binding.list.adapter=adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        viewModel.edited.observe(this){ post->
            if(post.id == 0){
                return@observe
            }
            with(binding.content){
                binding.group.visibility = View.VISIBLE
                requestFocus()
                setText(post.content)
            }
        }
        binding.cancel.setOnClickListener {
            with(binding.content){
                viewModel.save()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                binding.group.visibility = View.GONE
            }
        }
        binding.save.setOnClickListener {
            with(binding.content){
                if(text.isNullOrBlank()){
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.save()
                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

    }

}


@MainThread
    public inline fun <reified VM : ViewModel> ComponentActivity.viewModels(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): Lazy<VM> {
        val factoryPromise = factoryProducer ?: {
            defaultViewModelProviderFactory
        }

        return ViewModelLazy(
            VM::class,
            { viewModelStore },
            factoryPromise,
            { this.defaultViewModelCreationExtras }
        )
    }





