package com.example.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tarasovartem3.Delegation.Post
import com.example.tarasovartem3.R
import com.example.tarasovartem3.databinding.CardPostBinding

import kotlin.math.ln
import kotlin.math.pow
typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
interface OnInteractionListener {
        fun onLike(post: Post) {}
            fun onShare(post: Post) {}
                fun onEdit(post: Post) {}
                    fun onRemove(post: Post) {}
        }
class PostsAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}
            class PostViewHolder(
            private val binding: CardPostBinding,
            private val onInteractionListener: OnInteractionListener
            ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(post: Post) {
            binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            nrav.text = post.like.toString()
            podelytsya.text = post.share.toString()
                like.isChecked = post.likedByMe

            tri.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.popup_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            like.setOnClickListener {
            onInteractionListener.onLike(post)
            }
            like.text = post.like.toString()
            when {
                post.like in 1000..999999 -> nrav.text = "${post.like / 1000}K"
                post.like < 1000 -> nrav.text = post.like.toString()
                else -> nrav.text = String.format("%.1fM", post.like.toDouble() / 1000000)
            }
                share.setOnClickListener {
                    onInteractionListener.onShare(post)
                }
                podelytsya.text = post.share.toString()
                when {
                    post.share < 1000 -> podelytsya.text = post.share.toString()
                    post.share in 1000..999999 -> podelytsya.text = "${post.share / 1000}K"
                    else -> podelytsya.text = String.format(
                        "%.1fM", post.share.toDouble() / 1000000
                    )
                }
    }}
}


//
class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem }
}