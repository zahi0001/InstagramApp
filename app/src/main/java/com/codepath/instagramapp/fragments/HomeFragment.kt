package com.codepath.instagramapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.instagramapp.MainActivity
import com.codepath.instagramapp.MainActivity.Companion.TAG
import com.codepath.instagramapp.Post
import com.codepath.instagramapp.PostAdapter
import com.codepath.instagramapp.R
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery


open class HomeFragment : Fragment() {

    lateinit var postsRecyclerView: RecyclerView

    lateinit var adapter: PostAdapter

    var AllPosts: MutableList<Post> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // this i where we set up views

        postsRecyclerView = view.findViewById<RecyclerView>(R.id.postRecyclerView)

        //Steps to populate teh recycler view
        // 1. Create layout for each row in list DONE
        // 2. create data sources for each row (this is Post class) (Already DONE before)
        // 3. create adapter that will bridge data and row layout (DONE PostAdapter kotlin class)
        // 4. set adapter on RecyclerView

        adapter = PostAdapter(requireContext(), AllPosts)
        postsRecyclerView.adapter = adapter

        // 5. set layout manager on Recycler View
        postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        queryPosts()

    }

    open fun queryPosts() {
        val query: ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        //find all postst in our server
        query.include(Post.KEY_USER)
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

    companion object{
        const val TAG = "Home Fragment"
    }


}