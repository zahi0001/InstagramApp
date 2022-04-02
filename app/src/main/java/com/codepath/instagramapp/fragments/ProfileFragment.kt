package com.codepath.instagramapp.fragments

import android.util.Log
import androidx.fragment.app.Fragment
import com.codepath.instagramapp.Post
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser

class ProfileFragment : HomeFragment() {

    override fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //find all postst in our server
        query.include(Post.KEY_USER)
        //only user post
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser())
        query.addDescendingOrder("createdAt")
        query.findInBackground(object : FindCallback<Post> {
            override fun done(posts: MutableList<Post>?, e: ParseException?) {
                if (e != null){
                    // oops
                    Log.e(TAG, "Error Fetching Posts")
                } else {
                    if (posts != null){
                        for (post in posts){
                            Log.i(
                                TAG, "Post: " + post.getDescription()+ " , username: " +
                                        post.getUser()?.username)
                        }
                        AllPosts.addAll(posts)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })
    }

}