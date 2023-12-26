package com.nesha.tomsnacks.ui.report

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.nesha.tomsnacks.databinding.ActivityReportBinding
import com.nesha.tomsnacks.ui.report.adapter.ReportAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding
    private val viewModel by viewModels<ReportViewModel>()
    private val reportAdapter by lazy { ReportAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.rvReports.apply {
            adapter = reportAdapter
            layoutManager = LinearLayoutManager(this@ReportActivity)
        }

        val reports = viewModel.allReports.asLiveData()
        reports.observe(this) {
            reportAdapter.differ.submitList(it)
        }
    }
}