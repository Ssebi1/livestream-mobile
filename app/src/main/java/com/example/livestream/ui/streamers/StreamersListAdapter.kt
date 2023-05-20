package com.example.livestream.ui.streamers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.livestream.databinding.StreamItemBinding
import com.example.livestream.databinding.StreamerItemBinding
import com.example.livestream.models.User
import com.squareup.picasso.Picasso

class StreamersListAdapter constructor(
    private var streamers: List<User> = emptyList(),
    private val onStreamerClick: (User) -> Unit
) : RecyclerView.Adapter<StreamersListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StreamerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun update(newStreamers: List<User>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = streamers.size

            override fun getNewListSize() = newStreamers.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                streamers[oldItemPosition].id == newStreamers[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                streamers[oldItemPosition] == newStreamers[newItemPosition]
        })
        this.streamers = newStreamers
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = streamers.size

    inner class ViewHolder(private val binding: StreamerItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

        }

        fun bind(position: Int) {
            binding.titleTextView.text = streamers[position].name
            binding.subscribersTextView.text = streamers[position].followersNr.toString() + " Followers"
            Picasso.get().load("https://leven-tv.com/profile-pictures/" + streamers[position].id + ".png").into(binding.imageView);
        }
    }
}