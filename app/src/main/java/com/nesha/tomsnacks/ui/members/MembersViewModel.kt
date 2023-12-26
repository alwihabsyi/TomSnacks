package com.nesha.tomsnacks.ui.members

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nesha.tomsnacks.data.model.Member
import com.nesha.tomsnacks.data.repository.TomSnackRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MembersViewModel @Inject constructor(
    private val tomSnackRepository: TomSnackRepository
): ViewModel() {
    private val _members = MutableStateFlow<List<Member>>(listOf())
    val members = _members.asStateFlow()

    private val _lastId = MutableStateFlow(0)
    val lastId = _lastId.asStateFlow()

    init {
        getLastId()
    }

    private fun getLastId() {
        viewModelScope.launch {
            tomSnackRepository.getLastMemberId().collect {
                _lastId.value = it
            }
        }
    }

    fun getAllMembers() {
        viewModelScope.launch {
            tomSnackRepository.getAllMembers().collect {
                _members.value = it
            }
        }
    }

    fun addMember(member: Member) {
        viewModelScope.launch {
            tomSnackRepository.insertMember(member)
        }
    }
}