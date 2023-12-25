package com.nesha.tomsnacks.ui.inventory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nesha.tomsnacks.databinding.ItemInventoryBinding
import com.nesha.tomsnacks.data.model.Inventory
import com.nesha.tomsnacks.utils.currencyFormat

class InventoryAdapter: RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    inner class InventoryViewHolder(val binding: ItemInventoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(inventory: Inventory) {
            with(binding) {
                tvNamaInventory.text = inventory.namaItem
                tvHargaInventory.text = inventory.hargaJual!!.currencyFormat()
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Inventory>() {
        override fun areItemsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
            return oldItem.kode == newItem.kode
        }

        override fun areContentsTheSame(oldItem: Inventory, newItem: Inventory): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        return InventoryViewHolder(
            ItemInventoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }
}