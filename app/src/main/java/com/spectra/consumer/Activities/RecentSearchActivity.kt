package com.spectra.consumer.Activities

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.spectra.consumer.Adapters.RecentSearchAdapter
import com.spectra.consumer.Models.CurrentUserData
import com.spectra.consumer.Models.Recent
import com.spectra.consumer.R
import com.spectra.consumer.Utils.Constant
import com.spectra.consumer.Utils.DroidPrefs
import com.spectra.consumer.databinding.ActivityRecentSearchBinding
import com.spectra.consumer.service.model.ApiResponse
import com.spectra.consumer.service.model.Response.RecentSearchResponse
import com.spectra.consumer.service.model.Status
import com.spectra.consumer.service.repository.ApiConstant
import com.spectra.consumer.viewModel.SpectraViewModel
import kotlinx.android.synthetic.main.activity_general_seach.*
import java.net.UnknownHostException

class RecentSearchActivity : AppCompatActivity() {

    lateinit var mRecentSearchAdapter: RecentSearchAdapter
    lateinit var mSpectraViewModel: SpectraViewModel
    lateinit var Data: CurrentUserData
    lateinit var CanId: String
    lateinit var binding: ActivityRecentSearchBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recent_search)
        mSpectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel::class.java)

        Data = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData::class.java)
        CanId = Data.CANId
        val fade = Fade()
        fade.excludeTarget(cv_search_bar, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade)

        setupEditText()
        initRecyclerView()
        setupRecentSearch()
    }

    //this fun is used to detect Action seach from keybord
    private fun setupEditText() {
        binding.etSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch( binding.etSearch.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        binding.cancel.setOnClickListener {
            finishPage()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishPage()
    }

    // this fun is called when user click on  the back button or cancel icon
    private fun finishPage() {
        val intent = Intent()
        intent.putExtra(Constant.ENDPAGE, true)
        setResult(RESULT_OK, intent)
        finish()
    }


    private fun initRecyclerView() {
        mRecentSearchAdapter = RecentSearchAdapter(this) { searchValue ->
            Log.d(GeneralSeachActivity.TAG, "setUpRecyclerView: $searchValue ")
            performSearch(searchValue.keyWord)
        }
        binding.rvRecentSearch.adapter = mRecentSearchAdapter;
        binding.rvRecentSearch.refreshDrawableState()
    }


    //this fun get the recent seach from the Api
    private fun setupRecentSearch() {
        mSpectraViewModel
                .getRecentSearch(CanId, ApiConstant.ACTION_RECENTSEARCH)
                .observe(this, Observer {
                    consumeResponse(it)
                })
    }


    private fun performSearch(keyword: String) {
        val intent = Intent()
        intent.putExtra(Constant.KEYWORD, keyword)
        setResult(RESULT_OK, intent)
        finish()
    }


    private fun consumeResponse(apiResponse: ApiResponse?) {
        when (apiResponse?.status) {
            Status.LOADING -> showLoadingView(true)
            Status.SUCCESS -> {
                renderSuccessResponse(apiResponse.data, apiResponse.code)
                showLoadingView(false)
            }
            Status.ERROR -> {
                showLoadingView(false)
                if (apiResponse.error is UnknownHostException) {
                    Constant.MakeToastMessage(this, "No internet Connection")
                } else {
                    Constant.MakeToastMessage(this, apiResponse.error!!.message)
                }
            }
        }
    }

    private fun renderSuccessResponse(data: Any?, action: String?) {
        when (action) {
            ApiConstant.ACTION_RECENTSEARCH -> {
                val recentSearchResponse = data as RecentSearchResponse
                if (recentSearchResponse.statusCode == 200) {
                    val arrayList = arrayListOf<Recent>()
                    if (recentSearchResponse.data?.search_info != null) {
                        var i: Int = 0;
                        for (keyword in recentSearchResponse.data?.search_info) {
                            arrayList.add(Recent(i, keyword))
                            i++
                        }
                    }
                    if (arrayList.size == 0) {
                        binding.groupRecentSearch.visibility = View.GONE
                    } else {
                        binding.groupRecentSearch.visibility = View.VISIBLE
                    }
                    mRecentSearchAdapter.differ.submitList(arrayList);
                } else {
                    Constant.MakeToastMessage(this, recentSearchResponse.message)
                }
            }

        }

    }

    private fun showLoadingView(b: Boolean) {
        binding.progressBar.visibility = if (b) View.VISIBLE else View.GONE
    }
}