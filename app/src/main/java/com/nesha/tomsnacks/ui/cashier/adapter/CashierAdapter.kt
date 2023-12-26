package com.nesha.tomsnacks.ui.cashier.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nesha.tomsnacks.data.model.Inventory
import com.nesha.tomsnacks.databinding.ItemPaymentBinding
import com.nesha.tomsnacks.utils.currencyFormat

class CashierAdapter: RecyclerView.Adapter<CashierAdapter.InventoryViewHolder>() {

    inner class InventoryViewHolder(val binding: ItemPaymentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(inventory: Inventory) {
            with(binding) {
                tvNamaInventory.text = inventory.namaItem
                tvHargaInventory.text = inventory.hargaJual!!.currencyFormat()
                var quantity = 1
                tvQuantity.text = quantity.toString()

                btnIncreaseItem.setOnClickListener {
                    if (quantity < inventory.stok!!) {
                        quantity += 1
                        tvQuantity.text = quantity.toString()
                    }
                }

                btnDecreaseItem.setOnClickListener {
                    if (quantity > 1) {
                        quantity -= 1
                        tvQuantity.text = quantity.toString()
                    }
                }
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
            ItemPaymentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            onClick?.invoke(item)
        }
    }

    var onClick: ((Inventory) -> Unit)? = null
}