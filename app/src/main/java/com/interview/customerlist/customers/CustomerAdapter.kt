package com.interview.customerlist.customers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.interview.customerlist.R

class CustomerAdapter(
    private val customers: List<Customer>,
    private val customerStickColor: CustomerStickColor
) :
    RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    inner class CustomerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val genderTextView: TextView = itemView.findViewById(R.id.genderTextView)
        private val phoneNumberTextView: TextView = itemView.findViewById(R.id.phoneNumberTextView)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val imageViewPlaceHolder: TextView =
            itemView.findViewById(R.id.imageViewPlaceHolder)
        private val containerStickers: LinearLayoutCompat =
            itemView.findViewById(R.id.containerStickers)

        fun bind(customer: Customer) {
            nameTextView.text = "${customer.firstName} ${customer.lastName}"
            genderTextView.text = customer.gender
            phoneNumberTextView.text = customer.phoneNumber

            if (!customer.imageUrl.isNullOrBlank()) {
                Glide
                    .with(itemView)
                    .load(customer.imageUrl)
                    .centerCrop()
                    .into(imageView)
                imageViewPlaceHolder.visibility = View.GONE
                imageView.visibility = View.VISIBLE
            } else {
                imageViewPlaceHolder.text =
                    "${customer.firstName.first()} ${customer.lastName.first()}"
                imageViewPlaceHolder.visibility = View.VISIBLE
                imageView.visibility = View.INVISIBLE
            }

            itemView.setOnClickListener {
                //There is a bug in Android Studio Giraffe that doesn't compile the Arguments for Nagivation
                // https://stackoverflow.com/questions/76796254/android-navigation-define-argument
                // Using standard bundle
                val bundle = bundleOf("customerID" to customer.id)
                itemView.findNavController()
                    .navigate(
                        com.interview.customerlist.R.id.action_customerListFragment_to_customerDetailFragment,
                        bundle
                    )
            }
            createStickerViews(customer)

        }

        private fun createStickerViews(customer: Customer) {
            val layoutInflater: LayoutInflater = LayoutInflater.from(itemView.context)
            customer.stickers.forEach {

                val (letter, background) = customerStickColor.getColors(it)
                val stickerView = layoutInflater.inflate(
                    R.layout.sticker_item, containerStickers, false
                )

                val stickerTextView =
                    stickerView.findViewById<TextView>(R.id.stickerValueTextView)
                val stickerLayout =
                    stickerView.findViewById<CardView>(R.id.stickerLayout)

                stickerTextView.text = it

                stickerTextView.setTextColor(ContextCompat.getColor(itemView.context, letter))
                stickerLayout.backgroundTintList =
                    ContextCompat.getColorStateList(itemView.context, background)

                containerStickers.addView(stickerView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_customer, parent, false)
        return CustomerViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customers[position]
        holder.setIsRecyclable(false)
        holder.bind(customer)
    }

    override fun getItemCount(): Int {
        return customers.size
    }
}
