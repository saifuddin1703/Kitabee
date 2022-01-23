package com.example.kitabee.ui.home.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.kitabee.R
import com.example.kitabee.databinding.BookLayoutBinding
import com.example.kitabee.models.book
import com.example.kitabee.utils.HOST
import com.example.kitabee.utils.TAG
import com.squareup.picasso.Picasso

class BookAdapter(
    private val bookList: List<book>
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = BookLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_layout,parent,false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        Log.d(TAG,book.toString())


        val price = "₹ ${book.price}"

        // setting image
        Glide.with(holder.itemView.context).load(book.titleImage.replace("localhost", HOST))
            .listener(
                object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        e?.rootCauses?.forEach {
                            Log.d(TAG,it.message.toString())
                        }
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
//                        dataSource.
                        Log.d(TAG,"success")
                        return true
                    }

                }
            )
            .into(holder.binding.bookImage)
//
//        Picasso.get()
//            .
//            .load(book.titleImage).into(holder.binding.bookImage)
        // setting title
        holder.binding.bookTitle.text = book.title
        // setting edition
        holder.binding.editionName.text = book.edition
        // setting author
        holder.binding.authorName.text = book.author
        // setting publisher
        holder.binding.publisherName.text = book.publisher
        // setting price
        holder.binding.price.text = price

    }

    override fun getItemCount(): Int {
       return bookList.size
    }


}