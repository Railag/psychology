package com.firrael.psychology.view;

import android.os.Bundle;
import android.widget.EditText;

import com.firrael.psychology.R;
import com.firrael.psychology.Utils;
import com.firrael.psychology.presenter.NamePresenter;

import butterknife.BindView;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * Created by Railag on 07.11.2016.
 */
@RequiresPresenter(NamePresenter.class)
public class NameFragment extends BaseFragment<NamePresenter> {

    @BindView(R.id.nameField)
    EditText nameField;

    public static NameFragment newInstance() {

        Bundle args = new Bundle();

        NameFragment fragment = new NameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.nameTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_name;
    }

    @OnClick(R.id.nextButton)
    public void login() {
        Utils.hideKeyboard(getActivity());
        //startLoading();
        //getPresenter().request(nameField.getText().toString(), passwordField.getText().toString());
        getPresenter().save(nameField.getText().toString());
        getMainActivity().toAgeScreen();
        // TODO on success save
    }

   /* public void onSuccess(UserResult result) {
        stopLoading();
        if (result == null) {
            onError(new IllegalArgumentException());
            return;
        }
        if (result.invalid()) {
            toast(result.error);
            return;
        }
        toast("success login");
        User.save(result, getActivity());
        getMainActivity().updateNavigationMenu();
        getMainActivity().toUserLandingScreen();
    }

    public void onError(Throwable error) {
        error.printStackTrace();
        stopLoading();
        toast(error.getMessage());
    }*/
}
