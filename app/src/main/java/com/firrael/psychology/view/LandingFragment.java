package com.firrael.psychology.view;

import com.firrael.psychology.R;

/**
 * Created by Railag on 21.03.2017.
 */

public class LandingFragment extends SimpleFragment {

    @Override
    protected String getTitle() {
        return getString(R.string.landingScreenTitle);
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_landing;
    }
}
