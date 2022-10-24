package com.example.instagramapplication.fragments

import android.util.Log
import android.widget.Toast
import com.example.instagramapplication.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment:FeedFragment() {

    override fun queryPosts(){
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if(e != null){
                    Toast.makeText(requireContext(), "Error refreshing feed", Toast.LENGTH_SHORT).show()
                    Log.e("ROB", "ERROR GETTING DATA")
                }else{
                    if (posts != null){
                        for (post in posts){
                            Log.i("ROB", "Post: " + post.getDescription() +
                                    " ,Username: " + post.getUser()?.username
                            )
                        }
                        clear()
                        allPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}