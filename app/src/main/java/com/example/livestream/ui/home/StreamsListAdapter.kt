package com.example.livestream.ui.home

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.livestream.databinding.StreamItemBinding
import com.example.livestream.models.Stream
import com.squareup.picasso.Picasso

class StreamsListAdapter constructor(
    private var streams: List<Stream> = emptyList(),
    private val onStreamClick: (Stream) -> Unit
) : RecyclerView.Adapter<StreamsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StreamItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun update(newStreams: List<Stream>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize() = streams.size

            override fun getNewListSize() = newStreams.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                streams[oldItemPosition].id == newStreams[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                streams[oldItemPosition] == newStreams[newItemPosition]
        })
        this.streams = newStreams
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = streams.size

    inner class ViewHolder(private val binding: StreamItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(position: Int) {
            binding.titleTextView.text = streams[position].title
            if (streams[position].status == "ended") {
                binding.durationTextView.text = streams[position].vod_duration
            } else if (streams[position].status == "started") {
                binding.durationTextView.setTextColor(Color.parseColor("#ffffff"))
                binding.durationTextView.setBackgroundColor(Color.parseColor("#ff1c1c"))
                binding.durationTextView.text = "LIVE"
            }
            Picasso.get().load("https://leven-tv.com/thumbnail-pictures/" + streams[position].id + ".png").into(binding.imageView);
        }

        override fun onClick(v: View?) {
            onStreamClick(streams[layoutPosition])
        }
    }
}