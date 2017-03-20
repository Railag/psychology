package com.firrael.psychology.view;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.pdf.PrintedPdfDocument;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firrael.psychology.R;
import com.firrael.psychology.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;

/**
 * Created by Railag on 20.03.2017.
 */

public class AttentionVolumeResultsFragment extends SimpleFragment {

    public final static String RESULTS = "results";

    public static AttentionVolumeResultsFragment newInstance(Bundle args) {

        AttentionVolumeResultsFragment fragment = new AttentionVolumeResultsFragment();
        fragment.setHasOptionsMenu(true);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.winsNumber)
    TextView winsNumber;

    @Override
    protected String getTitle() {
        return getString(R.string.resultsTitle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.results, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
            default:
                save();
        }

        return super.onOptionsItemSelected(item);
    }

    private void save() {
        PrintAttributes attributes = new PrintAttributes.Builder()
                .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
                .setMediaSize(PrintAttributes.MediaSize.ISO_A4.asLandscape())
                .setResolution(new PrintAttributes.Resolution("Attention Volume results", "Attention Volume results", 300, 300))
                .setMinMargins(PrintAttributes.Margins.NO_MARGINS)
                .build();

        PrintedPdfDocument document = new PrintedPdfDocument(getActivity(),
                attributes);

        PdfDocument.Page page = document.startPage(0);

        View content = getView();
        content.draw(page.getCanvas());

        document.finishPage(page);

        if (!canWriteOnExternalStorage() || !Utils.canWrite(getActivity())) {
            // TODO
            return;
        }

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/psychology");
        dir.mkdir();

        File newFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/psychology/attention_volume_results.pdf");

        String name = newFile.getAbsolutePath();

        try {
            OutputStream stream =
                    new FileOutputStream(newFile, true);
            document.writeTo(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        document.close();

        Toast.makeText(getActivity(), "Results saved to " + name, Toast.LENGTH_SHORT).show();
    }

    public static boolean canWriteOnExternalStorage() {
        // get the state of your external storage
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // if storage is mounted return true
            return true;
        }
        return false;
    }

    @Override
    protected int getViewId() {
        return R.layout.results_attention_volume_layout;
    }

    @Override
    protected void initView(View v) {
        Bundle args = getArguments();

        Utils.verifyStoragePermissions(getActivity());

        if (args != null) {
            if (args.containsKey(RESULTS)) {
                int wins = args.getInt(RESULTS);

                winsNumber.setText(String.valueOf(wins));
            }

        }
    }
}
