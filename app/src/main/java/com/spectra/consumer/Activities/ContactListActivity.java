package com.spectra.consumer.Activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.spectra.consumer.Adapters.ContactListListAdapter;
import com.spectra.consumer.BuildConfig;
import com.spectra.consumer.Models.CurrentUserData;
import com.spectra.consumer.R;
import com.spectra.consumer.Utils.Constant;
import com.spectra.consumer.Utils.DroidPrefs;
import com.spectra.consumer.service.model.ApiResponse;
import com.spectra.consumer.service.model.Request.AddContactRequest;
import com.spectra.consumer.service.model.Request.ContactRequest;
import com.spectra.consumer.service.model.Response.AddContactResponse;
import com.spectra.consumer.service.model.Response.Contact;
import com.spectra.consumer.service.model.Response.ContactListResponse;
import com.spectra.consumer.viewModel.SpectraViewModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.spectra.consumer.Utils.Constant.CurrentuserKey;
import static com.spectra.consumer.Utils.Constant.STATUS_SUCCESS;
import static com.spectra.consumer.service.repository.ApiConstant.ADD_CONTACT;
import static com.spectra.consumer.service.repository.ApiConstant.GET_CONTACT;
import static com.spectra.consumer.service.repository.ApiConstant.UPDATE_CONTACT;

public class ContactListActivity extends AppCompatActivity {
    @BindView(R.id.no_internet)
    LinearLayout no_internet;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rvContacts)
    RecyclerView rvContacts;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.txt_head)
    TextView txt_head;

    @BindView(R.id.txt_share)
    TextView tvAddNew;

    @BindView(R.id.txt_payment)
    TextView noContactMassage;

    @BindView(R.id.try_again)
    AppCompatTextView tvAddNewContact;

    @BindView(R.id.img_payment_status)
    AppCompatImageView ivIcon;
    ContactListListAdapter contactListListAdapter;
    SpectraViewModel spectraViewModel;
    List<Contact> contacts = new ArrayList<>();
    Contact contactState;
    AlertDialog dial;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_contect);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        txt_head.setText(R.string.contact);
        tvAddNew.setText(R.string.addNew);
        tvAddNew.setTextColor(ContextCompat.getColor(this, R.color.back_color));
        tvAddNew.setVisibility(View.VISIBLE);
        spectraViewModel = ViewModelProviders.of(this).get(SpectraViewModel.class);
        findViewById(R.id.txt_retry).setVisibility(View.GONE);
        getContactList();
    }

    private void getContactList() {
        CurrentUserData userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        if (Constant.isInternetConnected(this)) {
            ContactRequest contactRequest = new ContactRequest();
            contactRequest.setAuthkey(BuildConfig.AUTH_KEY);
            contactRequest.setAction(GET_CONTACT);
            contactRequest.setCanID(userData.CANId);
            spectraViewModel.getContactList(contactRequest).observe(ContactListActivity.this, ContactListActivity.this::consumeResponse);
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status) {
            case LOADING:
                showLoadingView(true);
                break;
            case SUCCESS:
                showLoadingView(false);
                renderSuccessResponse(apiResponse.data, apiResponse.code);

                break;
            case ERROR:
                showLoadingView(false);
                assert apiResponse.error != null;
                Constant.MakeToastMessage(ContactListActivity.this, apiResponse.error.getMessage());
                break;
            default:
                break;
        }
    }

    private void renderSuccessResponse(Object response, String code) {

        if (response != null) {
            if (code.equalsIgnoreCase(GET_CONTACT)) {
                ContactListResponse contactListResponse = (ContactListResponse) response;
                if (contactListResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    contacts = contactListResponse.getResponse().getResults();
                    if (contacts != null && contacts.size() > 0) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        rvContacts.setLayoutManager(linearLayoutManager);
                        contactListListAdapter = new ContactListListAdapter(this, contacts);
                        rvContacts.setAdapter(contactListListAdapter);
                    } else {
                        ivIcon.setImageResource(R.drawable.ghost);
                        noContactMassage.setText(R.string.no_contact);
                        tvAddNewContact.setText(R.string.addNew);
                        no_internet.setVisibility(View.VISIBLE);
                    }
                } else
                    Constant.MakeToastMessage(ContactListActivity.this, contactListResponse.getMessage());

            } else {
                AddContactResponse addContactResponse = (AddContactResponse) response;
                dial.dismiss();
                if (addContactResponse.getStatus().equalsIgnoreCase(STATUS_SUCCESS)) {
                    Contact contact = null;
                    if (addContactResponse.getResponse() != null) {
                        try {
                            Gson gson = new Gson();
                            JSONObject jsonObject = new JSONObject(addContactResponse.getResponse().toString());
                            contact = gson.fromJson(jsonObject.toString(), Contact.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (contact != null) {
                        no_internet.setVisibility(View.GONE);
                        if (contacts != null && contacts.size() > 0 && contactState != null) {
                            contacts.remove(contactState);
                        }
                        assert contacts != null;
                        contacts.add(contact);
                        if (contactListListAdapter == null) {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                            rvContacts.setLayoutManager(linearLayoutManager);
                            contactListListAdapter = new ContactListListAdapter(this, contacts);
                            rvContacts.setAdapter(contactListListAdapter);
                        } else {
                            contactListListAdapter.updateList(contacts);
                        }
                    }

                }
                Constant.MakeToastMessage(ContactListActivity.this, addContactResponse.getMessage());

            }
        }
    }

    private void showLoadingView(boolean visible) {
        if (visible) {
            progress_bar.setVisibility(View.VISIBLE);
        } else {
            progress_bar.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.img_back, R.id.txt_share, R.id.try_again})
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            finish();
        }
        if (view.getId() == R.id.txt_share || view.getId() == R.id.try_again) {
            showConfirmationDialog(null);
        }
    }


    private void addContactList(Contact contact, String fName, String lName, String jTitle, String email, String mobile) {
        CurrentUserData userData = DroidPrefs.get(this, CurrentuserKey, CurrentUserData.class);
        if (Constant.isInternetConnected(this)) {
            AddContactRequest contactRequest = new AddContactRequest();
            contactRequest.setAuthkey(BuildConfig.AUTH_KEY);
            if (contact == null) {
                contactRequest.setAction(ADD_CONTACT);
            } else {
                contactRequest.setAction(UPDATE_CONTACT);
                contactRequest.setContactId(contact.getContactId());
            }
            contactRequest.setCanID(userData.CANId);
            contactRequest.setFirstName(fName);
            contactRequest.setLastName(lName);
            contactRequest.setJobTitle(jTitle);
            contactRequest.setMobilePhone(mobile);
            contactRequest.setEmail(email);
            spectraViewModel.addContact(contactRequest).observe(ContactListActivity.this, ContactListActivity.this::consumeResponse);
        }
    }

    //9205726030
    public void showConfirmationDialog(Contact contact) {
        contactState = contact;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        AtomicBoolean checkValidation = new AtomicBoolean(false);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View v = getLayoutInflater().inflate(R.layout.add_new_contect, null);
        AppCompatTextView tvCancel = v.findViewById(R.id.tvCancel);
        AppCompatTextView tvSave = v.findViewById(R.id.tvSave);
        AppCompatTextView tvHeading = v.findViewById(R.id.tvHeading);
        TextInputLayout inlEmailId = v.findViewById(R.id.inlEmailId);
        TextInputLayout inlDesignation = v.findViewById(R.id.inlDesignation);
        TextInputLayout inlFirstName = v.findViewById(R.id.inlFirstName);
        TextInputLayout inlLastName = v.findViewById(R.id.inlLastName);
        TextInputLayout inlMobileNumber = v.findViewById(R.id.inlMobileNumber);
        ProgressBar progress_bar = v.findViewById(R.id.progress_bar);

        TextInputEditText etLastName = v.findViewById(R.id.etLastName);
        TextInputEditText etEmailId = v.findViewById(R.id.etEmailId);
        TextInputEditText etMobileNumber = v.findViewById(R.id.etMobileNumber);
        TextInputEditText etFirstName = v.findViewById(R.id.etFirstName);
        TextInputEditText etDesignation = v.findViewById(R.id.etDesignation);
        if (contact == null) {
            tvHeading.setText(R.string.add_new_contact);
        } else {
            tvHeading.setText(R.string.edit_contact);
            etFirstName.setText(contact.getFirstName());
            etLastName.setText(contact.getLastName());
            etMobileNumber.setText(contact.getMobilePhone());
            etDesignation.setText(contact.getJobTitle());
            etEmailId.setText(contact.getEmail());
        }
        dialog.setView(v);
        dialog.setCancelable(true);
        dial = dialog.create();
        dial.show();

        etFirstName.addTextChangedListener(new TextWatcher() {

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
                if (checkValidation.get() && s.length() != 0)
                    if (TextUtils.isEmpty(s.toString())) {
                        inlFirstName.setError("Please enter your first name");
                    } else {
                        if (s.length() < 2) {
                            inlFirstName.setError("Please enter valid first name");
                        } else {
                            inlFirstName.setError(null);
                        }

                    }
            }
        });
        etLastName.addTextChangedListener(new TextWatcher() {

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
                if (checkValidation.get() && s.length() != 0)
                    if (TextUtils.isEmpty(s.toString())) {
                        inlLastName.setError("Please enter your last name");
                    } else {
                        if (s.length() < 2) {
                            inlLastName.setError("Please enter valid last name");
                        } else {
                            inlLastName.setError(null);
                        }
                    }
            }
        });

        etEmailId.addTextChangedListener(new TextWatcher() {

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
                if (checkValidation.get() && s.length() != 0)
                    if (TextUtils.isEmpty(s.toString())) {
                        inlEmailId.setError("Please enter email address");
                    } else {
                        if (!s.toString().trim().matches(emailPattern)) {
                            inlEmailId.setError("Please enter valid email address");
                        } else {
                            inlEmailId.setError(null);
                        }
                    }
            }
        });
        etDesignation.addTextChangedListener(new TextWatcher() {

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
                if (checkValidation.get() && s.length() != 0)
                    if (TextUtils.isEmpty(s.toString())) {
                        inlDesignation.setError("Please enter Designation");

                    } else {
                        if (s.length() < 3) {
                            inlDesignation.setError("Please enter valid Designation");
                        } else {
                            inlDesignation.setError(null);
                        }

                    }
            }
        });
        etMobileNumber.addTextChangedListener(new TextWatcher() {

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
                if (checkValidation.get() && s.length() != 0)
                    if (s.toString().length() < 10) {
                        inlMobileNumber.setError("Please enter valid mobile number");
                    } else {
                        inlMobileNumber.setError(null);
                    }
            }
        });
        tvCancel.setOnClickListener(view -> dial.dismiss());
        tvSave.setOnClickListener(view -> {
            checkValidation.set(true);
            if (progress_bar.getVisibility() == View.GONE) {
                String lName, fName, title, email, mobileNumber;
                fName = Objects.requireNonNull(etFirstName.getText()).toString();
                lName = Objects.requireNonNull(etLastName.getText()).toString();
                title = Objects.requireNonNull(etDesignation.getText()).toString();
                email = Objects.requireNonNull(etEmailId.getText()).toString();
                mobileNumber = Objects.requireNonNull(etMobileNumber.getText()).toString();
                if (TextUtils.isEmpty(fName)) {
                    inlFirstName.setError("Please enter your first name");
                    return;
                } else {
                    inlFirstName.setError(null);

                }
                if (TextUtils.isEmpty(lName)) {
                    inlLastName.setError("Please enter your last name");
                    return;
                } else {
                    inlLastName.setError(null);
 
                }

                if (TextUtils.isEmpty(email)) {
                    inlEmailId.setError("Please enter email address");
                    return;
                } else {
                    if (!email.toString().trim().matches(emailPattern)) {
                        inlEmailId.setError("Please enter valid email address");
                        return;
                    } else {
                        inlEmailId.setError(null);
                    }
                }
                if (TextUtils.isEmpty(title)) {
                    inlDesignation.setError("Please enter Designation");
                    return;
                } else {
                    if (title.length() < 3) {
                        inlDesignation.setError("Please enter valid Designation");
                        return;
                    } else {
                        inlDesignation.setError(null);
                    }
                }
                if (TextUtils.isEmpty(mobileNumber)) {
                    inlMobileNumber.setError("Please enter mobile number");
                    return;
                } else {
                    if (mobileNumber.length() < 10) {
                        inlMobileNumber.setError("Please enter valid mobile number");
                        return;
                    } else {
                        inlMobileNumber.setError(null);
                    }
                }
                progress_bar.setVisibility(View.VISIBLE);
                addContactList(contact, fName, lName, title, email, mobileNumber);
            }

        });
        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
