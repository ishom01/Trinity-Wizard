package com.ishom.testproject.presentation.ui.home.adaper

import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ishom.testproject.databinding.ItemUserBinding
import com.ishom.testproject.domain.entity.UserEntity
import com.ishom.testproject.presentation.ui.detail.CreateOrUpdateActivity

class UserAdapter(private val onItemClick: ((key: UserEntity) -> Unit)? = null): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private lateinit var binding: ItemUserBinding
    private var _users = listOf<UserEntity>()

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        private var _user: UserEntity? = null

        init {
            binding.root.setOnClickListener {
                _user?.let {
                    onItemClick?.invoke(it)
                }
            }
        }

        fun bind(user: UserEntity) {
            try {
                _user = user

                binding.tvCode.text = user.code;
                binding.tvName.text = user.name
                binding.tvYou.isVisible = user.isYou

            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return _users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val dataModel = _users[position]
        holder.bind(dataModel)
    }

    fun setUsers(users: List<UserEntity>) {
        try {
            _users = users
            notifyDataSetChanged()
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}