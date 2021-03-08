package com.eunidev.edlibrary

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RVAdapter1(val context: Context, var list: ArrayList<ContentModel.EDGifViewModel>): RecyclerView.Adapter<RVAdapter1.ViewHolder>() {

    class ViewHolder(iv: View): RecyclerView.ViewHolder(iv) {
        val tvName = iv.findViewById<TextView>(R.id.tvName_CardViewGifViewLayout)
        val gifView = iv.findViewById<EDGifView>(R.id.gifView_CardViewGifViewLayout)

        fun bind(context: Context, model: ContentModel.EDGifViewModel) {
            tvName.text = model.name
            gifView.setVideoUri(Uri.parse("android.resource://${context.packageName}/${R.raw.badi}"))
            gifView.setOnCompletionListener(object : EDGifView.OnCompletionListener {
                override fun onComplete(durationSet: Int) {
                    Toast.makeText(context, "Komplit", Toast.LENGTH_SHORT).show();
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_gifview, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position])
    }

    override fun getItemCount(): Int = list.size

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

        holder.gifView.start(5000)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

        holder.gifView.stop()
    }
}