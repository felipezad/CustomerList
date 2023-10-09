package com.interview.customerlist.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.interview.customerlist.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomerDetailFragment : Fragment() {

    private val viewModel: CustomerDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_customer_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates(view)
        viewModel.getCustomerDetail(arguments?.getInt(customerID))
    }

    private fun observeStates(view: View) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customerListUiState.collect { uiState ->
                    when (uiState) {
                        CustomerDetailUiState.Initial -> {
                            Log.d("CustomerDetailUiState", "Initial")
                        }

                        CustomerDetailUiState.LoadFailed -> {
                            Log.d(
                                "CustomerDetailUiState",
                                "LoadFailed"
                            )
                        }

                        CustomerDetailUiState.Loading -> {
                            Log.d("CustomerDetailUiState", "Loading")
                        }

                        is CustomerDetailUiState.Success -> {
                            Log.d("CustomerList", "Success")
                            uiState.customer?.let { initUserDetailsView(view, it) }
                        }
                    }
                }
            }
        }
    }

    private fun initUserDetailsView(view: View, customerDetailsData: CustomerDetailsData) {
        val userName = view.findViewById<TextView>(R.id.userNameTextView)
        val tags = view.findViewById<TextView>(R.id.tagsTextView)
        val gender = view.findViewById<TextView>(R.id.genderTextView)
        val phone = view.findViewById<TextView>(R.id.phoneTextView)
        val address = view.findViewById<TextView>(R.id.addressTextView)
        val imageView: ImageView = view.findViewById(R.id.imageViewDetails)
        val imageViewPlaceHolder: TextView = view.findViewById(R.id.imageViewPlaceHolderDetails)
        val mapViewPlaceHolder: ImageView = view.findViewById(R.id.mapViewImage)

        userName.text = "${customerDetailsData.firstName} ${customerDetailsData.lastName}"
        gender.text = customerDetailsData.gender
        phone.text = customerDetailsData.phoneNumber
        tags.text = customerDetailsData.stickers.joinToString(separator = ", ")
        with(customerDetailsData.address) {
            address.text = "$street, $zip, $city"
        }

        if (!customerDetailsData.imageUrl.isNullOrBlank()) {
            Glide
                .with(view)
                .load(customerDetailsData.imageUrl)
                .centerCrop()
                .into(imageView)
        } else {
            imageViewPlaceHolder.text =
                "${customerDetailsData.firstName.first()} ${customerDetailsData.lastName.first()}"
            imageView.visibility = View.INVISIBLE
        }
        val linearProgressIndicator: LinearProgressIndicator =
            view.findViewById(R.id.linearProgressDetails)
        val buttonDetails: ImageButton = view.findViewById(R.id.buttonDetails)
        linearProgressIndicator.visibility = View.INVISIBLE
        mapViewPlaceHolder.visibility = View.VISIBLE
        buttonDetails.setOnClickListener {
            view.findNavController().navigateUp()
        }
    }

    companion object {
        private const val customerID = "customerID"
    }
}