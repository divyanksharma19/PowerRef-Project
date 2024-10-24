package com.project.powerref1.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.powerref1.LoginActivity
import com.project.powerref1.R
import com.project.powerref1.ResumeBuilder.EditDetailsActivity
import com.project.powerref1.UserInfoManager
import com.project.powerref1.databinding.BottomSheetLogoutBinding
import com.project.powerref1.databinding.FragmentHomeBinding
import com.project.powerref1.databinding.FragmentUserAccountBinding

class UserAccountFragment : Fragment() {

    private var _binding: FragmentUserAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserAccountBinding.inflate(inflater, container, false)
        val name = UserInfoManager.firstname
        val lastname = UserInfoManager.lastname
        val email = UserInfoManager.email
        binding.tvUserEmail.setText(email)
        binding.tvUsername.setText(name + lastname)
        binding.cvLogout.setOnClickListener {
            logoutBottomSheet()
        }
        binding.cvUpdateResume.setOnClickListener {
            val intent = Intent(requireActivity(), EditDetailsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Needed if starting activity from non-activity context
            startActivity(intent)
        }
        return binding.root
    }

    private fun logoutBottomSheet(){
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val logoutSheetBinding = BottomSheetLogoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(logoutSheetBinding.root)
        logoutSheetBinding.apply {
            btnNo.setOnClickListener {
                bottomSheetDialog.dismiss()

                    // Clear SharedPreferences
                    val sharedPreferences = requireActivity().getSharedPreferences("PowerRefPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.clear() // Clear all data
                    editor.apply() // Commit the changes
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Needed if starting activity from non-activity context
                startActivity(intent)
                requireActivity().finish()


            }
            btnLogout.setOnClickListener {
                bottomSheetDialog.dismiss()

            }
        }
        bottomSheetDialog.show()
    }

}