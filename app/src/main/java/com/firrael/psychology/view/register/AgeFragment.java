package com.firrael.psychology.view.register;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.firrael.psychology.R;
import com.firrael.psychology.presenter.AgePresenter;
import com.firrael.psychology.view.base.BaseFragment;

import butterknife.BindView;
import nucleus.factory.RequiresPresenter;

/**
 * Created by Railag on 07.11.2016.
 */
@RequiresPresenter(AgePresenter.class)
public class AgeFragment extends BaseFragment<AgePresenter> {

    @BindView(R.id.ageEdit)
    EditText ageEdit;
    @BindView(R.id.ageLayout)
    TextInputLayout ageLayout;

    public static AgeFragment newInstance() {

        Bundle args = new Bundle();

        AgeFragment fragment = new AgeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.ageTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_age;
    }

    @Override
    protected void initView(View v) {
        ageEdit.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                getPresenter().save(ageEdit.getText().toString());
                getMainActivity().toTimeScreen();

                return true;
            } else {
                return false;
            }
        });
    }

}
