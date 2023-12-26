package com.nesha.tomsnacks.ui.members

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.nesha.tomsnacks.R
import com.nesha.tomsnacks.data.model.Member
import com.nesha.tomsnacks.databinding.ActivityAddMemberBinding
import com.nesha.tomsnacks.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AddMemberActivity : AppCompatActivity() {

    private var _binding: ActivityAddMemberBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MembersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddMemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpPage()
        setActions()
    }

    private fun setUpPage() {
        val lastId = viewModel.lastId.asLiveData()
        lastId.observe(this) { binding.etIdMember.setText(it.toString()) }
        val dateFormatted = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        binding.etTanggal.setText(dateFormatted)
    }

    private fun setActions() {
        binding.apply {
            btnInsertMember.setOnClickListener {
                val idMember = etIdMember.text.toString()
                val tanggalEntry = etTanggal.text.toString()
                val nama = etNama.text.toString()
                val alamat = etAlamat.text.toString()
                val catatan = etCatatan.text.toString()

                if (idMember.isEmpty() || tanggalEntry.isEmpty() || nama.isEmpty() || alamat.isEmpty() || catatan.isEmpty()) {
                    toast("Harap isi semua fields")
                    return@setOnClickListener
                }

                val member = Member(
                    tanggalEntry = tanggalEntry,
                    nama = nama,
                    alamat = alamat,
                    catatan = catatan
                )
                viewModel.addMember(member)
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}