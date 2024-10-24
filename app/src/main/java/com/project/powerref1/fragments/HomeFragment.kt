package com.project.powerref1.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.project.powerref1.FindReferralActivity
import com.project.powerref1.LoginActivity
import com.project.powerref1.R
import com.project.powerref1.RetrofitClient
import com.project.powerref1.UserAccount
import com.project.powerref1.databinding.FragmentHomeBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var userId : String
    private lateinit var firstName : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        userId = getUserIdFromPreferences()
        firstName = getFirstNameFromPreferences()
        binding.tvWelcomeHeading.setText("Hello \n$firstName")
        val imageUrl = "${RetrofitClient.BASE_URL}/powerRef/images/${userId}.png"
        val cacheKey = System.currentTimeMillis().toString()
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .signature(ObjectKey(cacheKey))
            .placeholder(R.drawable.ic_image_picker)
            .error(R.drawable.ic_image_picker)
            .into(binding.ivProfileImage)
        binding.ivProfileImage.setOnClickListener {
                    moveToUserActivity()
        }
        binding.proceedToJobReferralBtn.setOnClickListener {
            val intent = Intent(requireActivity(), FindReferralActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Start in a new task
            startActivity(intent)
        }
        return binding.root
    }

    private fun moveToUserActivity() {
        // Create intent to navigate to LoginActivity
        val intent = Intent(requireActivity(), UserAccount::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Start in a new task
        startActivity(intent)
        // Close the current activity
    }

    private fun getUserIdFromPreferences(): String {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("PowerRefPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId",null)!! // Replace "userId" with the actual key used
    }

    private fun getFirstNameFromPreferences(): String {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences("PowerRefPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("firstname",null)!! // Replace "userId" with the actual key used
    }


}