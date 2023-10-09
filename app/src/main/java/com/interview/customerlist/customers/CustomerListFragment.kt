package com.interview.customerlist.customers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.interview.customerlist.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class CustomerListFragment : Fragment() {

    private val viewModel: CustomerListViewModel by viewModels()

    @Inject
    lateinit var customerStickColor: CustomerStickColor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_customer_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStates(view)
        setupRecyclerView(view)
        viewModel.getListCustomers()
    }

    private fun observeStates(view: View) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customerListUiState.collect { uiState ->
                    when (uiState) {
                        CustomerListUiState.Initial -> Log.d("CustomerList", "Initial")
                        CustomerListUiState.LoadFailed -> {
                            Snackbar.make(view, "Loading Failed", Snackbar.LENGTH_LONG).show()
                        }
                        CustomerListUiState.Loading -> Log.d("CustomerList", "Loading")
                        is CustomerListUiState.Success -> {
                            Log.d("CustomerList", "Success")
                            updateRecyclerViewCustomerList(view, uiState.customers)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(view: View) {
        // Inside your activity or fragment
        val recyclerView: RecyclerView = view.findViewById(R.id.customerListRecyclerView)
        val layoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun updateRecyclerViewCustomerList(view: View, customerList: List<Customer>) {
        val linearProgressIndicator: LinearProgressIndicator =
            view.findViewById(R.id.linearProgress)
        val recyclerView: RecyclerView = view.findViewById(R.id.customerListRecyclerView)
        val adapter = CustomerAdapter(customerList, customerStickColor)
        recyclerView.adapter = adapter
        linearProgressIndicator.visibility = View.INVISIBLE
    }
}