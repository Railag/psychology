package com.firrael.psychology.view;

import android.os.Bundle;
import android.view.View;

import com.firrael.psychology.R;
import com.firrael.psychology.view.base.SimpleFragment;

import butterknife.OnClick;

/**
 * Created by Railag on 07.11.2016.
 */
public class TestsFragment extends SimpleFragment {

    public static TestsFragment newInstance() {

        Bundle args = new Bundle();

        TestsFragment fragment = new TestsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.tests);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_tests;
    }

    @OnClick({R.id.focusingButton, R.id.attentionStabilityButton, R.id.stressResistanceButton, R.id.englishButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.focusingButton:
                getMainActivity().toInstructionFragment(InstructionFragment.Test.FOCUSING);
                break;
            case R.id.attentionStabilityButton:
                getMainActivity().toInstructionFragment(InstructionFragment.Test.ATTENTION_STABILITY);
                break;
            case R.id.stressResistanceButton:
                getMainActivity().toInstructionFragment(InstructionFragment.Test.STRESS_RESISTANCE);
                break;
            case R.id.englishButton:
                getMainActivity().toInstructionFragment(InstructionFragment.Test.ENGLISH);
                break;
        }
    }
}
