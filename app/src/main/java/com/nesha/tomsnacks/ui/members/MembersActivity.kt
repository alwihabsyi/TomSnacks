package com.nesha.tomsnacks.ui.members

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.nesha.tomsnacks.databinding.ActivityMembersBinding
import com.nesha.tomsnacks.ui.members.adapter.MemberAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MembersActivity : AppCompatActivity() {

    private var _binding: ActivityMembersBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MembersViewModel>()
    private val memberAdapter by lazy { MemberAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMembersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAllMembers()
        initRecyclerView()
        observer()
        
        binding.btnAddMember.setOnClickListener { 
            startActivity(
                Intent(
                    this, AddMemberActivity::class.java
                )
            )
        }
    }

    private fun initRecyclerView() {
        binding.rvMembers.apply {
            adapter = memberAdapter
            layoutManager = LinearLayoutManager(this@MembersActivity)
        }
    }

    private fun observer() {
        val members = viewModel.members.asLiveData()
        members.observe(this) {
            memberAdapter.differ.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}