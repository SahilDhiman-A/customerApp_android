package com.spectra.consumer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.spectra.consumer.Adapters.SelectCaseAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.GetCasetypeRequest;
import com.spectra.consumer.service.model.Response.CaseTypeResponse;
import com.spectra.consumer.service.model.Response.GetcaseTypeResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.service.repository.ApiConstant.GET_CASE_TYPE;

public class SelectcaseTypeActivity extends AppCompatActivity {
    SelectCaseAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.view_can_ids)
    RecyclerView view_can_ids;
   private List<CaseTypeResponse> caseTypeDataList=new ArrayList<>();
    SpectraViewModel spectraViewModel;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_can_layout);
        ButterKnife.bind(this);
        linearLayoutManager=new LinearLayoutManager(this);
        view_can_ids.setLayoutManager(linearLayoutManager);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        getCaseType();

    }

    public void getCaseType(){
        if(Constant.isInternetConnected(this)){
            GetCasetypeRequest getCasetypeRequest=new GetCasetypeRequest();
            getCasetypeRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getCasetypeRequest.setAction(GET_CASE_TYPE);
            spectraViewModel.getCaseType(getCasetypeRequest).observe(SelectcaseTypeActivity.this, SelectcaseTypeActivity.this::consumeResponse);
          }
    }

    @OnClick({R.id.img_cross})
    public void onClick(View view) {
        if (view.getId() == R.id.img_cross) {
            Intent intent = new Intent(SelectcaseTypeActivity.this, CreateSrActivity.class);
            startActivity(intent);
            finish();
        }
        }
    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case SUCCESS:
                renderSuccessResponse(apiResponse.data);
                break;
            case LOADING:
            case ERROR:
            default:
                break;
        }
    }
    private void renderSuccessResponse(Object response) {
        if (response != null) {
            GetcaseTypeResponse getcaseTypeResponse= (GetcaseTypeResponse) response;
            if(getcaseTypeResponse.getStatus().equalsIgnoreCase(Constant.STATUS_SUCCESS)){
                caseTypeDataList=getcaseTypeResponse.getResponse();
                if(caseTypeDataList!=null && caseTypeDataList.size()>0){
                    adapter=new SelectCaseAdapter(this, caseTypeDataList, (view, position) -> {
                        int id =view.getId();
                        if(id==R.id.layout_account){
                            Intent intent=new Intent(SelectcaseTypeActivity.this,CreateSrActivity.class);
                            intent.putExtra("type",caseTypeDataList.get(position).getCaseDesc());
                            intent.putExtra("id",caseTypeDataList.get(position).getCaseId());
                            startActivity(intent);
                            finish();
                        }
                    });
                    view_can_ids.setAdapter(adapter);
            }

            }
            else{
                Constant.MakeToastMessage(SelectcaseTypeActivity.this,getcaseTypeResponse.getMessage());
            }

        }
    }

}
