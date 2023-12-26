package com.nesha.tomsnacks.ui.report.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nesha.tomsnacks.data.model.SalesReport
import com.nesha.tomsnacks.databinding.ItemReportBinding
import com.nesha.tomsnacks.utils.currencyFormat

class ReportAdapter: RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(val binding: ItemReportBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(salesReport: SalesReport) {
            with(binding) {
                tvKode.text = "Kode: ${salesReport.id}"
                tvTanggal.text = "Tanggal: ${salesReport.date}"
                tvWaktu.text = "Waktu: ${salesReport.time}"
                tvTotal.text = "Total: ${salesReport.total.currencyFormat()}"
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<SalesReport>() {
        override fun areItemsTheSame(oldItem: SalesReport, newItem: SalesReport): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SalesReport, newItem: SalesReport): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        return ReportViewHolder(
            ItemReportBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }
}