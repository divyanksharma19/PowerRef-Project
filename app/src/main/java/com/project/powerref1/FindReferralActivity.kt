package com.project.powerref1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.powerref1.Adapter.EmployeeAdapter
import com.project.powerref1.DataClasses.EmployeeInfo
import com.project.powerref1.databinding.ActivityFindReferralBinding

class FindReferralActivity : AppCompatActivity() {
    private var _binding: ActivityFindReferralBinding? = null
    private val binding get() = _binding!!
    private lateinit var employeeAdapter: EmployeeAdapter
    private lateinit var adapter: ArrayAdapter<String>
    private val predefinedNames = listOf(
        "Amazon", "American Express", "Apple", "Facebook", "Google", "Microsoft",
        "Netflix", "Stripe", "Uber", "Walmart", "Zoom"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFindReferralBinding.inflate(layoutInflater)
        // Initial empty RecyclerView setup
        binding.rvJobs.visibility = View.GONE

        setupSearchInput()


        binding.btnApply.setOnClickListener {
            setupRecyclerView(emptyList()) // Pass an empty list to avoid blank space initially
            populateDummyData()
        }
        setContentView(binding.root)
    }

    private fun setupRecyclerView(employeeList: List<EmployeeInfo>) {
        employeeAdapter = EmployeeAdapter(
            onEmailClick = { employeeInfo ->
                sendEmailSingleRecipient(
                    recipient = employeeInfo.email,
                    subject = "Referral Request",
                    body = "Hi ${employeeInfo.name},\n\nI would like to connect regarding a referral opportunity.",
                    filePath = null
                )
            },
            onLinkedInClick = { employeeInfo ->
                openLinkedInProfile(employeeInfo.linkedin) // Open LinkedIn profile
            },
            activity = this
        )
        binding.rvJobs.adapter = employeeAdapter
        binding.rvJobs.layoutManager = LinearLayoutManager(this)

        // Initially pass empty list to the adapter
        employeeAdapter.setJobListData(employeeList)
    }



    private fun populateDummyData() {
        // Create some dummy EmployeeInfo data
        val dummyData = listOf(
            EmployeeInfo(
                "Rakesh kumar",
                "Software Engineer",
                "rakeshkumar@google.com",
                "https://linkedin.com/in/charudatta-pathak-37554a13",
                "Apple",
                ""
            ),
            EmployeeInfo("Sanjay Singh", "Product Manager", "sanjay@stripe.com", "rgfx","Stripe", ""),
            EmployeeInfo("Anuj Kumar", "UX Designer", "anujKumar@uber.com", "dsgdsg","Uber", ""),
            EmployeeInfo("Divyansh Sisodiya", "Data Scientist", "divyansh@ukg.com", "Sdfsd","UKG", "")
        )

        // Set the data in the adapter
        employeeAdapter.setJobListData(dummyData)
        binding.rvJobs.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    fun sendEmailSingleRecipient(
        recipient: String,
        subject: String?,
        body: String?,
        filePath: Uri?
    ) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)
        emailIntent.putExtra(Intent.EXTRA_STREAM, filePath)
        emailIntent.setType("message/rfc822") // only email apps should handle this
        startActivity(emailIntent)
    }


    private fun setupSearchInput() {
        // Sort the predefined names alphabetically
        val sortedNames = predefinedNames.sorted()

        // Create an ArrayAdapter with the sorted names
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            sortedNames
        )

        // Set the adapter to the AutoCompleteTextView
        binding.etSearch.setAdapter(adapter)

        // Show the dropdown immediately when the AutoCompleteTextView gains focus
        binding.etSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.etSearch.showDropDown() // Show dropdown when the field gets focus
            }
        }

        // Also show the dropdown when the user clicks inside the AutoCompleteTextView
        binding.etSearch.setOnClickListener {
            binding.etSearch.showDropDown() // Ensure the dropdown is shown on click
        }

        // Optional: Add a TextWatcher to filter the results dynamically
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filter the list based on user input
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
    fun openLinkedInProfile(linkedInProfileUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)

        // Create LinkedIn app URI
        val linkedInAppUri = Uri.parse("linkedin://in/${linkedInProfileUrl.substringAfterLast("/")}") // Extracting the LinkedIn user ID from the URL

        try {
            intent.data = linkedInAppUri
            intent.setPackage("com.linkedin.android") // LinkedIn package name
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // If LinkedIn app is not installed, open in the browser
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedInProfileUrl))
            startActivity(browserIntent)
        }
    }


}