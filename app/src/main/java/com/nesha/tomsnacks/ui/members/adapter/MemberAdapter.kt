package com.nesha.tomsnacks.ui.members.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nesha.tomsnacks.data.model.Member
import com.nesha.tomsnacks.databinding.ItemInventoryBinding

class MemberAdapter: RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    inner class MemberViewHolder(private val binding: ItemInventoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(member: Member) {
            with(binding) {
                tvNamaInventory.text = member.nama
                tvHargaInventory.text = member.alamat
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        return MemberViewHolder(
            ItemInventoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }
}