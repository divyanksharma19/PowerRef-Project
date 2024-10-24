package com.project.powerref1.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.project.powerref1.DataClasses.EmployeeInfo

import com.project.powerref1.R

import com.project.powerref1.databinding.EmployeeDetailCardBinding

import kotlinx.coroutines.Job

class EmployeeAdapter(

    private val onEmailClick: (employeeInfo: EmployeeInfo) -> Unit,
    private val onLinkedInClick: (employeeInfo: EmployeeInfo) -> Unit,
    private val activity: FragmentActivity
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeAdapterViewHolder>() {

    private var employeeInfo: MutableList<EmployeeInfo> = mutableListOf()

    inner class EmployeeAdapterViewHolder(
        private val binding: EmployeeDetailCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(employeeInfo: EmployeeInfo) {
            binding.apply {

                val imageUrl = employeeInfo.imageUrl // Assuming profileImageUrl is a field in EmployeeInfo
                val cacheKey = System.currentTimeMillis().toString()

                Glide.with(itemView.context)
                    .asBitmap()
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .signature(ObjectKey(cacheKey))
                    .placeholder(R.drawable.ic_image_picker)
                    .error(R.drawable.ic_image_picker)
                    .into(binding.ivCompanyLogo)
                employeeName.text = employeeInfo.name
                employeePosition.text = employeeInfo.position

                employeeEmail.text = employeeInfo.email
                btnApply.setOnClickListener {
                    onEmailClick(employeeInfo)
                }
                linkedinBtn.setOnClickListener {
                    onLinkedInClick(employeeInfo)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapterViewHolder {
        return EmployeeAdapterViewHolder(
            EmployeeDetailCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EmployeeAdapterViewHolder, position: Int) {
        holder.bind(employeeInfo[position])
    }

    override fun getItemCount(): Int = employeeInfo.size

    fun setJobListData(newJobList : List<EmployeeInfo>){
        employeeInfo.clear()
        employeeInfo.addAll(newJobList)
        notifyDataSetChanged()
    }
}