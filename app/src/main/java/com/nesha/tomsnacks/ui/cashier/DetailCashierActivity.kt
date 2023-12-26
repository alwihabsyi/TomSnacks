package com.nesha.tomsnacks.ui.cashier

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ncorti.slidetoact.SlideToActView
import com.nesha.tomsnacks.R
import com.nesha.tomsnacks.data.model.Inventory
import com.nesha.tomsnacks.data.model.Member
import com.nesha.tomsnacks.databinding.ActivityDetailCashierBinding
import com.nesha.tomsnacks.ui.cashier.adapter.CashierAdapter
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailCashierActivity : AppCompatActivity() {

    private var _binding: ActivityDetailCashierBinding? = null
    private val binding get() = _binding!!
    private val cashierAdapter by lazy { CashierAdapter() }
    private var membersList = mutableListOf<String>()
    private val viewModel by viewModels<CashierViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailCashierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPage()
        observer()
    }

    private fun setUpPage() {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableArrayListExtra("items", Inventory::class.java)
        } else {
            intent.getParcelableArrayListExtra("items")
        }

        data?.let {
            binding.rvItemPayment.apply {
                adapter = cashierAdapter
                layoutManager = LinearLayoutManager(this@DetailCashierActivity)
            }

            cashierAdapter.differ.submitList(it)

            binding.slideToAct.onSlideCompleteListener =
                object : SlideToActView.OnSlideCompleteListener {
                    override fun onSlideComplete(view: SlideToActView) {
                    }
                }
        }
    }

    private fun observer() {
        val members = viewModel.members.asLiveData()
        val listMember = mutableListOf<Member>()
        members.observe(this) {
            it.forEach { member ->
                val memberString = "${member.id} | ${member.nama} - ${member.alamat}"
                membersList.add(memberString)
                listMember.add(member)
            }
        }

        val nota = viewModel.lastNota.asLiveData()
        nota.observe(this) {
            val notaText = "#Nota $it"
            binding.etNota.setText(notaText)
        }

        val arrayAdapter = ArrayAdapter(this, R.layout.item_spinner, membersList)
        binding.acMembers.setAdapter(arrayAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}