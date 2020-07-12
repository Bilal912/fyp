package com.fyp.job_clover.Seeker;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fyp.job_clover.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class SeekerHomeFragment extends Fragment {
PDFView pdfView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seeker_home, container, false);

        pdfView = view.findViewById(R.id.pdfView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            try {

            File file = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                            + "/JOB Clover/CV.pdf");
            if (file.exists()){
                pdfView.fitToWidth();
                Uri imageUri = Uri.fromFile(file);
                pdfView.fromUri(imageUri);
                Toast.makeText(getContext(),"Success",Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(getContext(),"No Path",Toast.LENGTH_SHORT).show();

            }
            pdfView.fromFile(file);

            }
            catch (Exception e){
                Toast.makeText(getContext(),"No Data Found",Toast.LENGTH_SHORT).show();
            }
            }
        return view;
    }

}
