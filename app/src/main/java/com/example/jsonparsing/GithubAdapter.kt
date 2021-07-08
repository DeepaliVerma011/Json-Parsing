package com.example.jsonparsing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row.view.*

class GithubAdapter(val githubusers:ArrayList<GithubUser>) : RecyclerView.Adapter<GithubAdapter.GithubViewHolder>() {
    class GithubViewHolder(itemView: View?) :RecyclerView.ViewHolder(itemView!!){
fun bind(githubUser: GithubUser){
    itemView?.textView.text=githubUser.login
    itemView?.textView2.text=githubUser.score.toString()
    itemView?.textView3.text=githubUser.html_url
    Picasso.get().load(githubUser?.avatar_url).into(itemView?.image)
}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder =
        GithubViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_row,parent,false)
        )


    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
             holder?.bind(githubusers[position])
    }

    override fun getItemCount()=githubusers.size

}