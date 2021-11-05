package com.example.flowdemo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SimpleSwipeListener
import com.daimajia.swipe.SwipeLayout
import com.example.flowdemo.databinding.ItemListBinding
import com.example.flowdemo.model.Post
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl


class PostAdapter(private var postlist: List<Post>) :
    RecyclerSwipeAdapter<PostAdapter.PostViewHolder>() {
    val mItemManger: SwipeItemRecyclerMangerImpl = SwipeItemRecyclerMangerImpl(this)
    fun setData(postlist: List<Post>) {
        this.postlist = postlist
        var name = postlist.filter { it.id == 2 }
        Log.d("this", "list of user" + name)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemListBinding: ItemListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            com.example.flowdemo.R.layout.item_list, parent, false
        )
        return PostViewHolder(itemListBinding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post: Post = postlist[position]
        holder.itemListBinding.swipe.showMode = SwipeLayout.ShowMode.LayDown
        holder.itemListBinding.swipe.addSwipeListener(object : SimpleSwipeListener() {
            override fun onOpen(layout: SwipeLayout?) {
                super.onOpen(layout)


            }
        })

        holder.itemListBinding.delete.setOnClickListener({
            mItemManger.removeShownLayouts(holder.itemListBinding.swipe)
//            val arraytwo = postlist.toMutableList().apply {
//                removeAt(position)
//            }
//            arraytwo.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, postlist.size)
            mItemManger.closeAllItems()
        })

        // var listodduser = this.postlist.forEach { it.id == 2 }


        holder.bind(post)

    }

    override fun getItemCount(): Int = postlist.size
    override fun getSwipeLayoutResourceId(position: Int): Int {
        return com.example.flowdemo.R.id.swipe
    }

    class PostViewHolder(itemListBinding: ItemListBinding) :
        RecyclerView.ViewHolder(itemListBinding.root) {
        val itemListBinding = itemListBinding
        fun bind(item: Post) {
            itemListBinding.post = item
        }
    }
}