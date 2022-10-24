package com.example.instagramapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text

class PostAdapter(val context: Context, val posts: List<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostAdapter.ViewHolder, position: Int) {
        val post = posts.get(position)
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvUsername : TextView
        val ivImage : ImageView
        val tvDescription : TextView
        val tvDate : TextView

        init {
            tvUsername = itemView.findViewById(R.id.txtPostUser)
            ivImage = itemView.findViewById(R.id.imgPost)
            tvDescription = itemView.findViewById(R.id.txtPostDesc)
            tvDate = itemView.findViewById(R.id.txtPostDate)
        }

        fun bind(post: Post){
            tvDescription.text = post.getDescription()
            tvUsername.text = post.getUser()?.username
            tvDate.text = post.getDate().toString()
            Glide.with(itemView.context).load(post.getImage()?.url).into(ivImage)
        }
    }
}