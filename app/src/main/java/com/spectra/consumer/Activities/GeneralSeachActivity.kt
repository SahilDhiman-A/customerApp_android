package com.spectra.consumer.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.spectra.consumer.Adapters.FaqAdapter
import com.spectra.consumer.Adapters.FaqChildAdapter
import com.spectra.consumer.Adapters.KeyWordAdapter
import com.spectra.consumer.Adapters.RecentSearchAdapter
import com.spectra.consumer.Models.CurrentUserData
import com.spectra.consumer.Models.KeyWord
import com.spectra.consumer.Models.Recent
import com.spectra.consumer.R
import com.spectra.consumer.Utils.Constant
import com.spectra.consumer.Utils.DroidPrefs
import com.spectra.consumer.Utils.KeyBoardUtils
import com.spectra.consumer.Utils.SpectraKeyWordData
import com.spectra.consumer.alertDailogs.ThanksFeedbackDailog
import com.spectra.consumer.databinding.ActivityGeneralSeachBinding
import com.spectra.consumer.service.model.ApiResponse
import com.spectra.consumer.service.model.Request.RequestThumbsDown
import com.spectra.consumer.service.model.Response.*
import com.spectra.consumer.service.model.SegmentResponseBase
import com.spectra.consumer.service.model.Status
import com.spectra.consumer.service.repository.ApiConstant
import com.spectra.consumer.viewModel.SpectraViewModel
import kotlinx.android.synthetic.main.activity_general_seach.*
import kotlinx.android.synthetic.main.activity_general_seach.view.*
import java.net.UnknownHostException


class GeneralSeachActivity : AppCompatActivity(), View.OnClickListener, FaqAdapter.FAQItemClickListener {

    lateinit var faqChildAdapter: FaqChildAdapter
    private var keyboardListenersAttached: Boolean = false
    private var isKeyWordEmpty: Boolean = false;
    lateinit var CanId: String
    lateinit var mRecentSearchAdapter: RecentSearchAdapter
    lateinit var SegmentId: String
    lateinit var binding: ActivityGeneralSeachBinding
    lateinit var mKeyWordAdapter: KeyWordAdapter
    lateinit var mSpectraViewModel: SpectraViewModel
    var position = -1
    var groupPosition = -1
    var childPosition = -1
    lateinit var Data: CurrentUserData
    private val titleList = arrayListOf<FaqInfo>()
    var listData = HashMap<FaqInfo, FaqInfo>()
    private lateinit var bundle :Bundle

    companion object {
        const val TAG = "GeneralSeachActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_general_seach)
        mSpectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel::class.java)
        Data = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData::class.java)
        CanId = Data.CANId
        bundle = intent.extras!!
        setUpRecyclerView();
        setupSearchBar()
        mSpectraViewModel.getSegmentlist(ApiConstant.ACTION_SEGMENT).observe(this, Observer {
            consumeResponse(it)
        })
        setupRecentSearch()
        setupClickListener()
        keyboardListenersAttached = true;
        setupFaqRecyclerView()
    }

    ///set Recycler view
    private fun setupFaqRecyclerView() {
        faqChildAdapter = FaqChildAdapter(this, 0, this, binding.VideoPlayerRecyclerView)
        binding.VideoPlayerRecyclerView.setAdapter(faqChildAdapter)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupClickListener() {
        binding.parent.cancel.setOnClickListener(this)
        binding.clSearchBar.setOnClickListener(this)
    }

    ///Set up recent search   Api and get recent search quary
    private fun setupRecentSearch() {
        mSpectraViewModel
                .getRecentSearch(CanId, ApiConstant.ACTION_RECENTSEARCH)
                .observe(this, Observer {
                    consumeResponse(it)
                })
    }

    //Search bar action for KEYBORAD that is use to detect user has pressed Search or not
    private fun setupSearchBar() {
        binding.etSearch.setOnEditorActionListener() { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val Quary = binding.etSearch.text.toString().trim()
                performKeywordSearching(Quary);
                getFAQbyKeywrd(Quary)
                KeyBoardUtils.hideSoftKeyboard(this)
                true
            }
            false
        }
    }


    //  use for hit the  Api  and find the faq by segment that is on the basis of Keyword
    private fun getFAQbyKeywrd(quary: String) {
        if (Constant.isInternetConnected(this)) {
            mSpectraViewModel.getFAQListbySegment(SegmentId, quary, CanId, ApiConstant.FAQCATEGORY).observe(this, Observer<ApiResponse> { apiResponse: ApiResponse? -> consumeResponse(apiResponse!!) })
        } else {
            Constant.MakeToastMessage(this, "Internet Connection Not Present")
        }
    }

    // this function is use to get local String data for perform Genral serach for spectra application
    private fun performKeywordSearching(Quary: String) {
        //Nikhil - Search keyword
        if (bundle.getString(Constant.SEARCHSOURCE) == "HOME" && binding.etSearch.text != null){
            Toast.makeText(this, "Searching "+binding.etSearch.text.toString(), Toast.LENGTH_SHORT).show()
            SpectraApplication.getInstance().postEvent(Constant.EVENT.CATEGORY_HOME, "Home_Search", "Search "+binding.etSearch.text.toString(), CanId)
        }
        val dataMap = SpectraKeyWordData.data
        val keywordlist = arrayListOf<KeyWord>()
        isKeyWordEmpty = false;
        dataMap.forEach { (key, value) ->
            println("$key = $value")
            if (key.keyName.equals(Quary, ignoreCase = true)) {
                when (Data.Segment) {
                    Constant.SEGMENT_BUSINESS -> {
                        if (value.isForB2BUser) {
                            keywordlist.add(value)
                        }
                    }
                    Constant.HOME -> {
                        if (value.isForB2CUser) {
                            keywordlist.add(value)
                        }
                    }
                }
            }
        }


        if (keywordlist.size == 0) {
            isKeyWordEmpty = true;
        }

        Log.d(TAG, "performKeywordSearching:  $keywordlist")
        mKeyWordAdapter.differ.submitList(keywordlist);
    }

    private fun setUpRecyclerView() {
        mKeyWordAdapter = KeyWordAdapter(this) {
            Log.d(TAG, "setUpRecyclerView: $it ")
            binding.etSearch.clearFocus()
            navigateToAnotherScreen(it)
        }
        binding.rvKeyword.adapter = mKeyWordAdapter;
        binding.rvKeyword.refreshDrawableState()

    }


    //this method is use to navigate the another screen on behalf of keyword stored in  Local  String
    private fun navigateToAnotherScreen(keyWord: KeyWord) {
        when (keyWord.className) {
            Constant.VIEW_SR_STATUS -> {
                Intent(this@GeneralSeachActivity, HomeActivity::class.java).apply {
                    putExtra("Key", "SR")
                }.also {
                    startActivity(it)
                }
            }
            Constant.CREATE_SR -> {
                Intent(this@GeneralSeachActivity, CreateSrActivity::class.java).also {
                    startActivity(it)
                }
            }

            Constant.VIEW_INVOICE_LIST, Constant.VIEW_TRANSECTION, Constant.TRACK_MY_ORDER -> {
                Intent(this@GeneralSeachActivity, HomeActivity::class.java).apply {
                    putExtra("Key", "GetInvoice")
                }.also {
                    startActivity(it)
                }
            }
            Constant.HOME -> {
                Intent(this@GeneralSeachActivity, HomeActivity::class.java).apply {
                    putExtra("Key", "Home")
                }.also {
                    startActivity(it)
                }
            }
            Constant.SWITCH_ACCOUNT -> {
                Intent(this@GeneralSeachActivity, MyAccountActivity::class.java).also {
                    startActivity(it)
                }
            }

            Constant.MY_ACCOUNT, Constant.MY_PROFILE -> {
                Intent(this@GeneralSeachActivity, MyAccountActivity::class.java).also {
                    startActivity(it)
                }
            }
            Constant.CHANGE_PASSWORD -> {
                Intent(this@GeneralSeachActivity, MyAccountActivity::class.java).putExtra(Constant.KEY, Constant.CHANGE_PASSWORD).also {
                    startActivity(it)
                }
            }
            Constant.LINK_MULTIPLE_ACCOUNT -> {
                Intent(this@GeneralSeachActivity, MyAccountActivity::class.java).putExtra(Constant.KEY, Constant.LINK_MULTIPLE_ACCOUNT).also {
                    startActivity(it)
                }
            }
            Constant.UPDATE_EMAIL_ID, Constant.UPDATE_MOBILE_NUMBER,Constant.VIEW_CONTACT, Constant.ADD_CONTACT-> {
                Intent(this@GeneralSeachActivity, MyAccountActivity::class.java).putExtra(Constant.KEY, Constant.UPDATE_ACCOUNT).also {
                    startActivity(it)
                }
            }
            Constant.FORGOT_PASSWORD -> {
                Intent(this@GeneralSeachActivity, ForgotPasswordActivity::class.java).also {
                    startActivity(it)
                }
            }
            Constant.VIEW_USAGE -> {
                Intent(this@GeneralSeachActivity, DataUsageActivity::class.java).also {
                    startActivity(it)
                }
            }
            Constant.MY_PLAN, Constant.VIEW_PLAN_CHANGE_OFFER -> {
                Intent(this@GeneralSeachActivity, MyPlanActivity::class.java).also {
                    startActivity(it)
                }
            }
            Constant.VIEW_TOP_UP_OFFERS, Constant.TOP_UP_AVAILED -> {
                Intent(this@GeneralSeachActivity, TopUpActivity::class.java).also {
                    startActivity(it)
                }
            }

            Constant.CHECK_AUTO_PAY_STATUS,Constant.ADD_AUTO_PAY -> {
                Intent(this@GeneralSeachActivity, StandingInstructionsActivity::class.java).also {
                    startActivity(it)
                }
            }
            Constant.FAQ -> {
                Intent(this@GeneralSeachActivity, FAQActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }


    private fun consumeResponse(apiResponse: ApiResponse) {
        when (apiResponse.status) {
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
            ApiConstant.ACTION_SEGMENT -> {
                val mSegmentResponseBase = data as SegmentResponseBase
                val Data = DroidPrefs.get(this, Constant.CurrentuserKey, CurrentUserData::class.java)
                for (mSegment in mSegmentResponseBase.data) {
                    if (mSegment.name.equals(Data.Segment, ignoreCase = true)) {
                        SegmentId = mSegment._id
                    }
                }
            }

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
//                    if (arrayList.size > 0) {
                    binding.clSearchBar.performClick()
//                    }
//                    mRecentSearchAdapter.differ.submitList(arrayList);
                } else {
                    Constant.MakeToastMessage(this, recentSearchResponse.message)
                }
            }

            ApiConstant.FAQCATEGORY -> {
                val mFAQResponseBase = data as FAQResponseBase
                if (mFAQResponseBase.statusCode == Constant.STATUS_CODE) {
                    titleList.clear()
                    listData.clear()

                    val faqs = ArrayList<FaqInfo>();

                    for (faq in mFAQResponseBase.data) {
                        for (faqinfo in faq.faq_info) {
                            listData.put(faqinfo, faqinfo)
                            if (faqinfo.is_active) {
                                faqs.add(faqinfo)
                            }
                        }
                    }
//                    titleList.addAll(listData.keys)
                    if (listData.size == 0) {
                        binding.GroupFAQ.visibility = View.GONE
                        binding.tvNoRecordFund.visibility = if (isKeyWordEmpty) View.VISIBLE else View.GONE
                    } else {
                        binding.GroupFAQ.visibility = View.VISIBLE
                        binding.tvNoRecordFund.visibility = View.GONE

                        binding.VideoPlayerRecyclerView.setMediaObjects(faqs)
                        faqChildAdapter.mDiffer.submitList(faqs)

//                        mFAQExpandableListAdapter = FAQExpandableListAdapter(this, titleList, listData, lastExpandedPosition)
//                        mFAQExpandableListAdapter.setFAQItemClickListener(this)
//                        binding.ExpandableListViewFaq.setAdapter(mFAQExpandableListAdapter)
//                        binding.ExpandableListViewFaq.setGroupIndicator(null)
//                        binding.ExpandableListViewFaq.collapseGroup(0)
//                        binding.ExpandableListViewFaq.setDividerHeight(0)
//                        binding.ExpandableListViewFaq.setOnGroupExpandListener({ groupPosition ->
//                            if (lastExpandedPosition != -1 && groupPosition !== lastExpandedPosition) {
//                                binding.ExpandableListViewFaq.collapseGroup(lastExpandedPosition)
//                            }
//                            lastExpandedPosition = groupPosition
//                        })
                    }
                } else {
                    Constant.MakeToastMessage(this, mFAQResponseBase.message)
                }
            }
            ApiConstant.LIKE_FAQ -> {
                val faqLikeUnlikeUpdate = data as FaqLikeUnlikeUpdate
                Constant.MakeToastMessage(this, faqLikeUnlikeUpdate.message)
            }
            ApiConstant.UNLIKE_FAQ -> {
                ThanksFeedbackDailog(this).show();
            }
            ApiConstant.GETTHOUMBCOUNT -> {
                val mThoumbsCountResponse = data as ThoumbsCountResponse
                if (mThoumbsCountResponse.additionalInfo.equals("1", ignoreCase = true)) {
                    faqChildAdapter.updateList(position, childPosition, groupPosition)
                }
            }
        }

    }

    private fun showLoadingView(b: Boolean) {
        binding.progressBar.visibility = if (b) View.VISIBLE else View.GONE
    }

    //this fun is used to click on  Like
    override fun onLikeClick(faqId: String?) {
        mSpectraViewModel.likeFaq(faqId, CanId, ApiConstant.LIKE_FAQ).observe(this, Observer {
            consumeResponse(it)
        })
    }

    //this fun is used to click on  UnLike
    override fun onUnLikeClick(faqId: String?, reason: String?) {
        val requestThumbsDown = RequestThumbsDown();
        requestThumbsDown.can_id = CanId;
        requestThumbsDown.faq_id = faqId;
        requestThumbsDown.reason = reason;
        mSpectraViewModel.unlikeFaq(requestThumbsDown, ApiConstant.UNLIKE_FAQ).observe(this, Observer {
            consumeResponse(it)
        });

    }

    //this is used to navigate the video in WebView class
    override fun onVideoClick(url: String?) {
        val intent = Intent(this, NotPDFActivity::class.java)
        intent.putExtra("URL", url)
        intent.putExtra("TYPE", "video")
        startActivity(intent)
    }

     //this fun is use when user click on  arrow down  icon of the faq
    override fun onGroupExpend(faqId: String?, position: Int) {
        mSpectraViewModel.addViewCountinFaq(faqId, ApiConstant.VIEWCOUNT).observe(this, Observer {
            consumeResponse(it)
        })
    }

    //this fun is use to identify user submited feedback  or Not
    override fun onChildLoad(faqId: String?, position: Int, childPosition: Int) {
        this.position = position
        this.childPosition = childPosition
//        this.groupPosition = groupPosition
        mSpectraViewModel.getThumbsCount(faqId, CanId, ApiConstant.GETTHOUMBCOUNT).observe(this, Observer {
            consumeResponse(it)
        })
    }


    override fun onStop() {
        super.onStop()
        //use for pouse the running video in FAQs
        binding.VideoPlayerRecyclerView.pauseVideo()
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.cancel -> {
                finish()
            }
            R.id.cl_search_bar -> {
                navigateToRecentSearch()
            }
        }
    }


    //onclick of edit text open the recent search
    private fun navigateToRecentSearch() {
        val fade = Fade();
        fade.excludeTarget(cv_search_bar, true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade)
        val intent = Intent(this, RecentSearchActivity::class.java);
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, binding.cvSearchBar, "cv_search_bar");
        startActivityForResult(intent, Constant.UICODE, options.toBundle())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == Constant.UICODE) {
                if (data.hasExtra(Constant.ENDPAGE)) {
                    finish()
                } else if (data.hasExtra(Constant.KEYWORD)) {
                    val keyword = data.getStringExtra(Constant.KEYWORD)
                    binding.etSearch.setText(keyword)
                    keyword?.let {
                        performKeywordSearching(it)
                        getFAQbyKeywrd(keyword)
                    }
                }
            }
        }
    }
}
