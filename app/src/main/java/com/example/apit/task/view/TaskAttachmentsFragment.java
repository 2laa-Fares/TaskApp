package com.example.apit.task.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.model.HDFile;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.presenter.AttachmentsPresenter;
import com.example.apit.task.repositories.imp.AttachmentsRepositoryImp;
import com.example.apit.task.utils.PrefUtils;
import com.hintdesk.core.utils.CryptoUtil;
import com.hintdesk.core.utils.JSONHttpClient;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TaskAttachmentsFragment extends Fragment implements AttachmentsInterface {

    private static final int PICKFILE_RESULT_CODE = 12;
    private Bitmap currentImage;
    @BindView(R.id.uploadImageButton)
    FloatingActionButton uploadFile;
    @BindView(R.id.attachmentsRecyclerView)
    RecyclerView attachmentsRecyclerView;
    @BindView(R.id.imageView4)
    ImageView attachmentsImage;
    @BindView(R.id.wview)
    WebView webView;
    ProgressDialog progressDialog;
    private List<HDFile> uploadedFiles;
    NEW_TASKS task;
    Category category;
    String userId;
    Context context = App.getContext();
    AttachmentsInterface Interface;
    AttachmentsPresenter presenter;
    ViewPagerAdapter viewPagerAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_attachments_fragment, container, false);
        ButterKnife.bind(this, view);

        /*TextView toolbatTitle =((TaskActivity) getActivity()).findViewById(R.id.mytext);
        toolbatTitle.setText(" المرفقات");*/

        Interface = this;
        presenter = new AttachmentsPresenter(new AttachmentsRepositoryImp(), Interface);

        progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("يتم تحميل المرفقات...");
        progressDialog.show();

        context = App.getContext();
        userId = PrefUtils.getKeys(context, context.getString(R.string.user_id));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        attachmentsRecyclerView.setLayoutManager(layoutManager);

        if (getArguments() != null) {
            task = (NEW_TASKS) getArguments().getSerializable("Task");
            category = (Category) getArguments().getSerializable("Category");
            if (task != null) {
                presenter.getAttachments(String.valueOf(task.TASK_SYSKEY));
            }
        }

        final String[] pdfs = {".pdf"};

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.getContext(), FilePickerActivity.class);
                intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(false)
                        .setShowVideos(false)
                        .enableImageCapture(false)
                        .setShowFiles(true)
                        .setMaxSelection(10)
                        .setSkipZeroSizeFiles(true)
                        .setSuffixes(".pdf")
                        .build());
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            List<HDFile> result = new ArrayList<HDFile>();
            for (int i = 0; i < files.size(); i++) {
                File source = new File(files.get(i).getPath());

                progressDialog = new ProgressDialog(context,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setMessage("يتم رفع المرفقات...");
                progressDialog.show();

                HDFile wallpaper = new HDFile();
                wallpaper.setId(CryptoUtil.Md5(source.getName()));
                wallpaper.setName(source.getName());
                wallpaper.setFilePath(files.get(i).getPath());
                result.add(wallpaper);
            }
            int size = result.size();
            new UploadFilesTask(String.valueOf(task.TASK_SYSKEY), userId).execute(result.toArray(new HDFile[size]));
        }
    }

    class UploadFilesTask extends AsyncTask<HDFile, String, Integer> {

        Integer totalCount = 0;
        String task_id, user_id;

        UploadFilesTask(String task_id, String user_id) {
            this.task_id = task_id;
            this.user_id = user_id;
        }

        @Override
        protected Integer doInBackground(HDFile... params) {
            Integer uploadCount = 0;
            totalCount = params.length;
            uploadedFiles = new ArrayList<HDFile>();
            for (HDFile param : params) {
                File file = new File(param.getFilePath());
                JSONHttpClient jsonHttpClient = new JSONHttpClient();
                HDFile[] hdFiles = jsonHttpClient.PostFile("ApiAttachmentUrl", param.getId(), file, param.getName(), HDFile[].class);
                if (hdFiles != null && hdFiles.length == 1) {
                    uploadCount++;
                    uploadedFiles.add(hdFiles[0]);
                }

            }
            return uploadCount;
        }

        @Override
        protected void onPostExecute(Integer uploadCount) {
            progressDialog.dismiss();

            if (uploadCount == 0)
                Toast.makeText(App.getContext(), "فشل رفع المرفقات", Toast.LENGTH_LONG).show();
            else if (uploadCount < totalCount) {
                Toast.makeText(App.getContext(), "فشل رفع المرفقات", Toast.LENGTH_LONG).show();
                presenter.getAttachments(String.valueOf(task.TASK_SYSKEY));
            } else {
                Toast.makeText(App.getContext(), "لقد تم رفع المرفقات بنجاح", Toast.LENGTH_LONG).show();
                presenter.getAttachments(String.valueOf(task.TASK_SYSKEY));
            }

        }
    }


    @Override
    public void result(FileInfo[] files) {
        progressDialog.dismiss();
        if (files != null && files.length > 0) {
            attachmentsRecyclerView.setVisibility(View.VISIBLE);
            attachmentsImage.setVisibility(View.GONE);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            attachmentsRecyclerView.setLayoutManager(layoutManager);
            AttachmentsAdapter adapter = new AttachmentsAdapter(files, task.TASK_SYSKEY, webView);
            attachmentsRecyclerView.setAdapter(adapter);
        }
    }
}