package com.example.apit.task.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.FileInfo;
import com.example.apit.task.utils.PrefUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.AttachmentViewHolder> {

    FileInfo[] fileInfos;
    int taskNo;
    WebView webView;

    ProgressDialog progressDialog;


    public AttachmentsAdapter(FileInfo[] fileInfos, int taskNo, WebView webView) {
        this.fileInfos = fileInfos;
        this.taskNo = taskNo;
        this.webView = webView;
    }


    @NonNull
    @Override
    public AttachmentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attachment_items, viewGroup, false);
        return new AttachmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull AttachmentViewHolder holder, final int i) {
        final String fileName = fileInfos[i].OriginalPath;
        holder.attachmentTV.setText(fileName);
        holder.attachmentTV.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                Toast.makeText(App.getContext(), "جارى التحميل...", Toast.LENGTH_LONG).show();
                webView.setVisibility(View.VISIBLE);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.setBackgroundColor(android.R.color.transparent);
                webView.loadUrl("http://docs.google.com/viewer?url=YourAttachmentUrl");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (fileInfos != null)
            return fileInfos.length;
        else return 0;
    }

    public class AttachmentViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.attachmentTV)
        TextView attachmentTV;

        public AttachmentViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
