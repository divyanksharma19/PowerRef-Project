package com.project.powerref1

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.powerref1.DataClasses.ImgModel
import com.project.powerref1.DataClasses.LoginRequest
import com.project.powerref1.DataClasses.LoginResponse
import com.project.powerref1.DataClasses.User
import com.project.powerref1.ResumeBuilder.EditDetailsActivity
import com.project.powerref1.databinding.ActivityLoginBinding
import com.project.powerref1.databinding.ActivityUserAccountBinding
import com.project.powerref1.databinding.BottomSheetLogoutBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import kotlin.math.log


class UserAccount : AppCompatActivity() {
    private lateinit var binding: ActivityUserAccountBinding
    private var imageUri: Uri? = null
    private lateinit var userId : String
    private var bitmapFront: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = UserInfoManager.firstname
        val lastname = UserInfoManager.lastname
        val email = UserInfoManager.email
        binding.tvUserEmail.setText(email)
        binding.tvUsername.setText(name + lastname)
        userId = getUserIdFromPreferences()
        Log.d("User id",userId)
        val imageUrl = "${RetrofitClient.BASE_URL}/powerRef/images/${userId}.png"

        val cacheKey = System.currentTimeMillis().toString()
        Glide.with(this)
            .asBitmap()
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .signature(ObjectKey(cacheKey))
            .placeholder(R.drawable.ic_image_picker)
            .error(R.drawable.ic_image_picker)
            .into(binding.profileImage)

        binding.cvUpdateResume.setOnClickListener {
            val intent = Intent(this@UserAccount, EditDetailsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Needed if starting activity from non-activity context
            startActivity(intent)
        }
        binding.cvLogout.setOnClickListener {
            logoutBottomSheet()
        }
        binding.ivPopOut.setOnClickListener {
            val intent = Intent(this@UserAccount, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.profileImage.setOnClickListener {

            contract.launch("image/*")
            binding.profilePicUpload.visibility  = View.VISIBLE

        }
        binding.profilePicUpload.setOnClickListener {
            if (null != imageUri) {
                try {
                    bitmapFront =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    val imagBitmap : Bitmap = bitmapFront!!
                    val UriImage : Uri = imageUri!!
                    val filename = userId
                    Log.d("File NAME :           ",filename)
                    uploadImg(
                        "1234", filename,
                        imgToString(imagBitmap)!!, false
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }


    }

    private fun logoutBottomSheet(){
        val bottomSheetDialog = BottomSheetDialog(this)
        val logoutSheetBinding = BottomSheetLogoutBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(logoutSheetBinding.root)
        logoutSheetBinding.apply {
            btnNo.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            btnLogout.setOnClickListener {
                bottomSheetDialog.dismiss()
                val sharedPreferences = getSharedPreferences("PowerRefPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear() // Clear all data
                editor.apply() // Commit the changes
                val intent = Intent(this@UserAccount, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Needed if starting activity from non-activity context
                startActivity(intent)
                finish()
            }
        }
        bottomSheetDialog.show()
    }
    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        val iv_profile_user_image = binding.profileImage
        imageUri = uri
        Log.d("Image Uri",imageUri.toString())
        Glide.with(this)
            .asBitmap()
            .load(uri)
            .centerCrop()
            .placeholder(R.drawable.ic_image_picker)
            .into(iv_profile_user_image)
    }

    private fun uploadImg(dlNumber: String, filename: String, imgToSting: String, onclick: Boolean) {
        Log.d("imgtoString",imgToSting)
        val imgModelCall =  RetrofitClient.instance.uploadDl(
            "PowerRef",
            dlNumber,
            filename,
            imgToSting
        )

        imgModelCall.enqueue(object : Callback<ImgModel> {
            override fun onResponse(call: Call<ImgModel>, response: Response<ImgModel>) {
              //  Log.d(ContentValues.TAG, "onResponse: ${response.raw().toString()}")
                if (response.isSuccessful) {
                    val imgModel = response.body()

                    Toast.makeText(this@UserAccount, "Photo Updated Successfully", Toast.LENGTH_SHORT).show()

                } else {
                    Log.d(ContentValues.TAG, "Error: ${response.code()}")
                    Toast.makeText(this@UserAccount, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ImgModel>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ", t)
                Toast.makeText(this@UserAccount, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun imgToString(bitmap: Bitmap): String? {
        val arrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, arrayOutputStream)
        val imgByte = arrayOutputStream.toByteArray()
        return Base64.encodeToString(imgByte, Base64.DEFAULT)
    }

    private fun getUserIdFromPreferences(): String {
        val sharedPreferences: SharedPreferences = getSharedPreferences("PowerRefPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId",null)!! // Replace "userId" with the actual key used
    }



}