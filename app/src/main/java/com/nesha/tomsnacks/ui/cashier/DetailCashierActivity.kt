package com.nesha.tomsnacks.ui.cashier

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ncorti.slidetoact.SlideToActView
import com.nesha.tomsnacks.R
import com.nesha.tomsnacks.data.model.Inventory
import com.nesha.tomsnacks.data.model.Member
import com.nesha.tomsnacks.data.model.SalesReport
import com.nesha.tomsnacks.databinding.ActivityDetailCashierBinding
import com.nesha.tomsnacks.ui.cashier.adapter.CashierAdapter
import com.nesha.tomsnacks.utils.currencyFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DetailCashierActivity : AppCompatActivity() {

    private var _binding: ActivityDetailCashierBinding? = null
    private val binding get() = _binding!!
    private val cashierAdapter by lazy { CashierAdapter() }
    private var membersList = mutableListOf<String>()
    private val viewModel by viewModels<CashierViewModel>()
    private var totalPrice = 0

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

            it.forEach { inventory ->
                totalPrice += inventory.hargaJual!!
            }

            binding.tvPrice.text = (totalPrice - 10000).currencyFormat()
            binding.tvDiscountPrice.text = totalPrice.currencyFormat()

            binding.slideToAct.onSlideCompleteListener =
                object : SlideToActView.OnSlideCompleteListener {
                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onSlideComplete(view: SlideToActView) {
                        val formatter = DateTimeFormatter.ofPattern("HH:mm")
                        val salesReport = SalesReport(
                            date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()),
                            time = LocalDateTime.now().format(formatter),
                            total = totalPrice
                        )
                        viewModel.reportSales(salesReport)
                        startActivity(Intent(this@DetailCashierActivity, LoadingActivity::class.java))
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