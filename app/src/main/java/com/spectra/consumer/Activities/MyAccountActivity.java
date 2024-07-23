package com.spectra.consumer.Activities;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.spectra.consumer.Adapters.SelectCanLinkedAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.Models.UserData;
import com.spectra.consumer.Models.UserImage;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.CAN_ID;
import com.spectra.consumer.service.model.Request.AddContactRequest;
import com.spectra.consumer.service.model.Request.GetLinkAccountRequest;
import com.spectra.consumer.service.model.Request.GetProfileRequest;
import com.spectra.consumer.service.model.Request.RestPasswordRequest;
import com.spectra.consumer.service.model.Response.BaseResponse;
import com.spectra.consumer.service.model.Response.BillToResponse;
import com.spectra.consumer.service.model.Response.CanResponse;
import com.spectra.consumer.service.model.Response.Contact;
import com.spectra.consumer.service.model.Response.GetLinkAccountResponse;
import com.spectra.consumer.service.model.Response.GetprofileResponse;
import com.spectra.consumer.service.model.Response.LoginMobileResponse;
import com.spectra.consumer.service.model.Response.ProfileResponse;
import com.spectra.consumer.service.model.Response.ShipToResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.spectra.consumer.Utils.Constant.BASE_CAN;
import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.EMAIL;
import static com.spectra.consumer.Utils.Constant.GSTN;
import static com.spectra.consumer.Utils.Constant.MOBILE;
import static com.spectra.consumer.Utils.Constant.SEGMENT_HOME;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.Utils.Constant.TAN;
import static com.spectra.consumer.Utils.Constant.USER_IMAGE;
import static com.spectra.consumer.Utils.Constant.USER_NAME;
import static com.spectra.consumer.Utils.Constant.hasPermissions;
import static com.spectra.consumer.service.repository.ApiConstant.GET_LINK_ACCOUNT;
import static com.spectra.consumer.service.repository.ApiConstant.GET_PROFILE;
import static com.spectra.consumer.service.repository.ApiConstant.RESET_PASSWORD;

public class MyAccountActivity extends AppCompatActivity {
    private static final int PIC_CROP = 3;
    String[] PERMISSIONS;
    @BindView(R.id.txt_user_name)
    TextView txt_user_name;
    @BindView(R.id.layoutCv2)
    CardView layoutCv2;

    @BindView(R.id.view_can_select)
    AppCompatTextView view_can_select;
    @BindView(R.id.txt_mobile_number)
    TextView txt_mobile_number;
    @BindView(R.id.tvAddNew)
    TextView tvAddNew;
    @BindView(R.id.tvManegeContact)
    TextView tvManegeContact;
    @BindView(R.id.tvResetPassword)
    TextView tvResetPassword;
    @BindView(R.id.tvDeleteAccount)
    TextView tvDeleteAccount;

    @BindView(R.id.txt_edit_profile)
    TextView txt_edit_profile;
    @BindView(R.id.tvChangeProfilePic)
    TextView tvChangeProfilePic;

    @BindView(R.id.txt_email_id)
    TextView txt_email_id;
    @BindView(R.id.txt_installation_add)
    TextView txt_installation_add;
    @BindView(R.id.img_back)
    AppCompatImageView img_back;
    @BindView(R.id.layout_view)
    LinearLayout layout_view;
    @BindView(R.id.toolbar_head)
    Toolbar toolbar_head;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.account_b_2_c)
    LinearLayout account_b_2_c;
    @BindView(R.id.layout_b2B)
    LinearLayout layout_b2B;
    @BindView(R.id.txt_company_name)
    TextView txt_company_name;
    @BindView(R.id.txt_can)
    TextView txt_can;
    boolean gallery_denied = false;
    Bitmap mImageBitmap;
    @BindView(R.id.user_image)
    CircleImageView user_image;
    Uri file_upload;
    @BindView(R.id.txt_email)
    TextView txt_email;
    @BindView(R.id.txt_installation_address)
    TextView txt_installation_address;
    @BindView(R.id.txt_billing_address)
    TextView txt_billing_address;
    @BindView(R.id.txt_billing_contact)
    TextView txt_billing_contact;
    @BindView(R.id.txt_technical_contact)
    TextView txt_technical_contact;
    @BindView(R.id.txt_head)
    TextView txt_head;


    @BindView(R.id.tvGST)
    TextView tvGST;

    @BindView(R.id.tvLinkedCanID)
    TextView tvLinkedCanID;

    @BindView(R.id.tvTAN)
    TextView tvTAN;
    @BindView(R.id.rlTAN)
    RelativeLayout rlTAN;
    RelativeLayout rlResetPassword, llLogin;
    private CurrentUserData userData;
    private SpectraViewModel spectraViewModel;
    private UserImage userDataDB;
    private AlertDialog dial;
    private Context context;
    ProgressBar progress;
    CAN_ID can_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar_head);
        PERMISSIONS = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        txt_head.setText(getString(R.string.my_account));
        can_id = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);


        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        context = MyAccountActivity.this;
        get_Profile();


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        txt_user_name.setText(userData.AccountName);
    }

    @SuppressLint("SetTextI18n")
    public void get_Profile() {
        userDataDB = DroidPrefs.get(this, USER_IMAGE, UserImage.class);
        tvAddNew.setText("Add new");
        if (TextUtils.isEmpty(can_id.Linked)) {
            tvLinkedCanID.setVisibility(View.GONE);
            can_id.Linked=can_id.baseCanID;
            DroidPrefs.apply(MyAccountActivity.this, BASE_CAN, can_id);
            can_id = DroidPrefs.get(this, BASE_CAN, CAN_ID.class);
        }
        view_can_select.setText(MessageFormat.format("{0} {1}", getString(R.string.can_), can_id.Linked));
        if (Constant.isInternetConnected(this)) {
            String image = userDataDB.getResponseHashMap().get((userData.CANId));
            if (image == null || image.isEmpty()) {
                findViewById(R.id.imageHolder).setVisibility(View.VISIBLE);
                tvChangeProfilePic.setText(R.string.uploadProfilePicture);
            } else {
                user_image.setImageBitmap(Constant.convert(image));
                tvChangeProfilePic.setText(R.string.changeProfilePicture);
                findViewById(R.id.imageHolder).setVisibility(View.GONE);
            }


            GetProfileRequest getProfileRequest = new GetProfileRequest();
            getProfileRequest.setAuthkey(BuildConfig.AUTH_KEY);
            getProfileRequest.setAction(GET_PROFILE);
            getProfileRequest.setCanID(userData.CANId);
            spectraViewModel.getProfile(getProfileRequest).observe(MyAccountActivity.this, MyAccountActivity.this::consumeResponse);
        } else {
            Constant.MakeToastMessage(MyAccountActivity.this, getString(R.string.no_internet));
        }
    }


    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                showLoadingView(true);
                break;
            case SUCCESS:
                renderSuccessResponse(apiResponse.data, apiResponse.code);
                showLoadingView(false);
                break;
            case ERROR:
                assert apiResponse.error != null;
                Constant.MakeToastMessage(MyAccountActivity.this, apiResponse.error.getMessage());
                showLoadingView(false);
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {
        if (response != null) {

            switch (code) {

                case RESET_PASSWORD:
                    BaseResponse baseResponse = (BaseResponse) response;
                    if (baseResponse.getStatus().equals(STATUS_SUCCESS)) {
                        rlResetPassword.setVisibility(View.GONE);
                        llLogin.setVisibility(View.VISIBLE);
                    } else {
                        Constant.MakeToastMessage(MyAccountActivity.this, baseResponse.getMessage());
                    }
                    break;

                case GET_PROFILE:
                    GetprofileResponse getprofileResponse = (GetprofileResponse) response;
                    if (getprofileResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                        ProfileResponse profileResponse = getprofileResponse.getResponse();
                        ShipToResponse shipToResponse = profileResponse.getShipTo();
                        String email = shipToResponse.getEmail();
                        if (userData.Segment.equalsIgnoreCase(SEGMENT_HOME)) {

                            txt_email_id.setText(email);
                            txt_mobile_number.setText(shipToResponse.getMobile());
                            txt_installation_add.setText(shipToResponse.getAddress());
                            account_b_2_c.setVisibility(View.VISIBLE);
                            layout_b2B.setVisibility(View.GONE);
                            rlTAN.setVisibility(View.GONE);
                            layoutCv2.setVisibility(View.VISIBLE);
                            tvManegeContact.setVisibility(View.VISIBLE);
                            tvResetPassword.setVisibility(View.VISIBLE);
                            tvDeleteAccount.setVisibility(View.GONE);
                            txt_edit_profile.setVisibility(View.VISIBLE);
                            tvChangeProfilePic.setVisibility(View.VISIBLE);


                 /*   tvAddNew.setVisibility(View.GONE);
                    View positiveButton = findViewById(R.id.view_can_select);
                    RelativeLayout.LayoutParams layoutParams =
                            (RelativeLayout.LayoutParams) positiveButton.getLayoutParams();
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    positiveButton.setLayoutParams(layoutParams);*/
                        } else {
                            layoutCv2.setVisibility(View.VISIBLE);
                            txt_can.setText(userData.CANId);

                            txt_installation_address.setText(shipToResponse.getAddress());
                            txt_technical_contact.setText(shipToResponse.getMobile());
                            tvGST.setText(profileResponse.getGSTN().toString());
                            tvTAN.setText(profileResponse.getTAN());
                            BillToResponse billToResponse = profileResponse.getBillTo();
                            if (billToResponse != null) {
                                txt_company_name.setText(billToResponse.getName());
                                txt_email.setText(email);
                                txt_billing_address.setText(billToResponse.getAddress());
                                txt_billing_contact.setText(billToResponse.getMobile());
                            }
                            rlTAN.setVisibility(View.VISIBLE);
                            account_b_2_c.setVisibility(View.GONE);
                            layout_b2B.setVisibility(View.VISIBLE);
                            //tvAddNew.setVisibility(View.GONE);
                            tvManegeContact.setVisibility(View.VISIBLE);
                            tvResetPassword.setVisibility(View.VISIBLE);
                            tvDeleteAccount.setVisibility(View.GONE);
                            tvChangeProfilePic.setVisibility(View.VISIBLE);
                            txt_edit_profile.setVisibility(View.VISIBLE);
                            View positiveButton = findViewById(R.id.view_can_select);
                   /* RelativeLayout.LayoutParams layoutParams =
                            (RelativeLayout.LayoutParams) positiveButton.getLayoutParams();
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    positiveButton.setLayoutParams(layoutParams);*/
                        }
                        layout_view.setVisibility(View.VISIBLE);
                    } else {
                        Constant.MakeToastMessage(MyAccountActivity.this, getprofileResponse.getMessage());
                    }
                    break;
            }
        }
    }

    private void showLoadingView(boolean visible) {
        if (dial == null) {
            if (visible) {
                progress_bar.setVisibility(View.VISIBLE);
                layout_view.setVisibility(View.GONE);
            } else {
                progress_bar.setVisibility(View.GONE);
            }
        } else {
            if (visible) {
                progress.setVisibility(View.VISIBLE);
            } else {
                progress.setVisibility(View.GONE);
            }
        }
    }

    @OnClick({R.id.img_back, R.id.view_can_select, R.id.user_image, R.id.txt_edit_profile, R.id.tvManegeContact, R.id.tvAddNew, R.id.tvDeleteAccount, R.id.tvResetPassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.view_can_select:
                Intent intent = new Intent(MyAccountActivity.this, LinkedCanLdListActivity.class);
                startActivity(intent);
                break;
            // TODO update user image
            case R.id.user_image:
                if (!hasPermissions(MyAccountActivity.this, PERMISSIONS)) {
                    int PERMISSION_ALL = 1;
                    ActivityCompat.requestPermissions(MyAccountActivity.this, PERMISSIONS, PERMISSION_ALL);
                } else {
                    Intent intent_gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent_gallery, 2);
                }
                break;
            // TODO edit mobile and email functionality
            case R.id.txt_edit_profile:
                Intent edit = new Intent(MyAccountActivity.this, EditProfileActivity.class);
                if (userData.Segment.equalsIgnoreCase(SEGMENT_HOME)) {
                    edit.putExtra(USER_NAME, userData.AccountName);
                    edit.putExtra(MOBILE, txt_mobile_number.getText().toString());
                    edit.putExtra(EMAIL, txt_email_id.getText().toString());
                } else {
                    edit.putExtra(USER_NAME, txt_company_name.getText().toString());
                    edit.putExtra(MOBILE, txt_technical_contact.getText().toString());
                    edit.putExtra(EMAIL, txt_email.getText().toString());
                    edit.putExtra(TAN, tvTAN.getText().toString());
                    edit.putExtra(GSTN, tvGST.getText().toString());
                }
                startActivity(edit);
                break;
            case R.id.tvAddNew:
                Intent intent2;
                intent2 = new Intent(context, AddCanIdActivity.class);
                startActivity(intent2);
                break;
            case R.id.tvDeleteAccount:
                break;
            case R.id.tvResetPassword:
                showRestPasswordDialogDialog();
                break;
            case R.id.tvManegeContact:
                Intent intent1 = new Intent(MyAccountActivity.this, ContactListActivity.class);
                startActivity(intent1);
                break;


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // When an Image is picked
        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Uri picUri = data.getData();
            Intent intent = new Intent(MyAccountActivity.this, CropforProfile.class);
            intent.putExtra("uri", Objects.requireNonNull(picUri).toString());
            intent.putExtra("type", "profile");
            startActivityForResult(intent, PIC_CROP);
        } else if (requestCode == PIC_CROP && resultCode == Activity.RESULT_OK) {
            if (data != null) {

                String file = data.getStringExtra("file");
                file_upload = Uri.parse(data.getStringExtra("file_upload"));
                mImageBitmap = BitmapFactory.decodeFile(file);
                if (mImageBitmap != null) {
                    int width = mImageBitmap.getWidth();
                    int height = mImageBitmap.getHeight();
                    if (width > 1000 & height > 1200) {
                        mImageBitmap = Bitmap.createScaledBitmap(mImageBitmap, 700, 700, false);
                    }

                    boolean isContain = userDataDB.getResponseHashMap().containsKey(userData.CANId);
                    if (isContain) {
                        userDataDB.getResponseHashMap().remove(userData.CANId);
                    }
                    userDataDB.getResponseHashMap().put(userData.CANId, Constant.getimage(mImageBitmap));
                    DroidPrefs.apply(this, USER_IMAGE, userDataDB);
                    user_image.setImageBitmap(mImageBitmap);
                    findViewById(R.id.imageHolder).setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                //denied
                Log.e("denied", permission);

                if (permission.equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE") || permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    gallery_denied = true;
                }

            } else {
                if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
                    //allowed
                    Log.e("allowed", permission);
                    gallery_denied = false;


                } else {
                    //set to never ask again
                    Log.e("set to never ask again", permission);

                    if (permission.equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE") || permission.equalsIgnoreCase("android.permission.WRITE_EXTERNAL_STORAGE")) {
                        gallery_denied = true;
                    }
                    //do something here.
                }

            }
        }
        if (!gallery_denied) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 2);

        }
    }


    public void showRestPasswordDialogDialog() {
        AtomicBoolean checkValidation = new AtomicBoolean(false);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.reset_password, null);
        AppCompatTextView tvCancel = v.findViewById(R.id.tvCancel);
        AppCompatTextView tvSave = v.findViewById(R.id.tvSave);
        progress = v.findViewById(R.id.progress);
        AppCompatTextView tvLOGIN = v.findViewById(R.id.tvLOGIN);
        rlResetPassword = v.findViewById(R.id.rlResetPassword);
        llLogin = v.findViewById(R.id.llLogin);

        TextInputLayout inlOldPassword = v.findViewById(R.id.inlOldPassword);
        TextInputLayout inlNewPassword = v.findViewById(R.id.inlNewPassword);
        TextInputLayout inletConfirmNewPassword = v.findViewById(R.id.inletConfirmNewPassword);


        TextInputEditText etOldPassword = v.findViewById(R.id.etOldPassword);
        TextInputEditText etNewPassword = v.findViewById(R.id.etNewPassword);
        TextInputEditText etConfirmNewPassword = v.findViewById(R.id.etConfirmNewPassword);

        dialog.setView(v);
        dialog.setCancelable(true);
        dial = dialog.create();
        dial.show();

        etOldPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (checkValidation.get())
                    if (TextUtils.isEmpty(s.toString())) {
                        inlOldPassword.setError("Please enter your old password");
                    } else {
                        if (s.length() < 2) {
                            inlOldPassword.setError("Please enter valid old password");
                        } else {
                            inlOldPassword.setError(null);
                        }

                    }
            }
        });
        etNewPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (checkValidation.get())
                    if (TextUtils.isEmpty(s.toString())) {
                        inlNewPassword.setError("Please enter new password");
                    } else {
                        if (s.length() < 6) {
                            inlNewPassword.setError("Password length should be 6 to 10");
                        } else {
                            inlNewPassword.setError(null);
                        }
                    }
            }
        });

        etConfirmNewPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (checkValidation.get())
                    if (TextUtils.isEmpty(s.toString())) {
                        inletConfirmNewPassword.setError("Please enter confirm password");
                    } else {
                        if (!s.toString().equals(etNewPassword.getText().toString())) {
                            inletConfirmNewPassword.setError("New password and confirm password must be same");
                        } else {
                            inletConfirmNewPassword.setError(null);
                        }
                    }
            }
        });
        tvLOGIN.setOnClickListener(view -> {
            logOut();
        });
        tvCancel.setOnClickListener(view -> {
            dial.dismiss();
            dial = null;
        });
        tvSave.setOnClickListener(view -> {
            checkValidation.set(true);
            if (progress.getVisibility() == View.GONE) {
                String oldPassword, newPassword, confirmNewPassword;
                newPassword = Objects.requireNonNull(etNewPassword.getText()).toString();
                oldPassword = Objects.requireNonNull(etOldPassword.getText()).toString();
                confirmNewPassword = Objects.requireNonNull(etConfirmNewPassword.getText()).toString();
                if (TextUtils.isEmpty(oldPassword)) {
                    inlOldPassword.setError("Please enter your old password");
                    return;
                } else {
                    inlOldPassword.setError(null);

                }

                if (TextUtils.isEmpty(newPassword)) {
                    inlNewPassword.setError("Please enter new password");
                    return;
                } else {
                    if (validatePassword(newPassword)) {
                        inlNewPassword.setError(null);
                    } else {
                        inlNewPassword.setError("Please enter valid password");
                        return;
                    }
                }


                if (TextUtils.isEmpty(confirmNewPassword)) {
                    inletConfirmNewPassword.setError("Please enter confirm password");
                    return;
                } else {
                    if (!confirmNewPassword.equals(newPassword)) {
                        inletConfirmNewPassword.setError("New password and confirm password must be same");
                        return;
                    } else {
                        inletConfirmNewPassword.setError(null);
                    }
                }
                resetPassword(newPassword, oldPassword);
            }

        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }


    private void resetPassword(String newPassword, String oldPassword) {
        RestPasswordRequest restPasswordRequest = new RestPasswordRequest();
        restPasswordRequest.setAction(RESET_PASSWORD);
        restPasswordRequest.setAuthkey(BuildConfig.AUTH_KEY);
        restPasswordRequest.setNewPassword(newPassword);
        restPasswordRequest.setOldPassword(oldPassword);
        restPasswordRequest.setCanID(userData.CANId);
        spectraViewModel.getResetPassword(restPasswordRequest).observe(MyAccountActivity.this, MyAccountActivity.this::consumeResponse);

    }


    public void logOut() {
        UserImage userImage = DroidPrefs.get(context, USER_IMAGE, UserImage.class);
        DroidPrefs droidPrefs = DroidPrefs.getDefaultInstance(context);
        droidPrefs.clear();
        UserData.DeleteUsersInfo();
        DroidPrefs.apply(context, USER_IMAGE, userImage);
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }

    public static boolean validatePassword(String s1) {
        Pattern regexp = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$");
        Matcher matcher = regexp.matcher(s1);
        return matcher.matches();
    }

}
