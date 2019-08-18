package com.example.apit.task.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apit.task.R;
import com.example.apit.task.app.App;
import com.example.apit.task.model.Category;
import com.example.apit.task.model.JamaratRegiment01_09;
import com.example.apit.task.model.NEW_TASKS;
import com.example.apit.task.model.SYSCODMTI;
import com.example.apit.task.model.NEW_TASKS_ACTION;
import com.example.apit.task.model.stp_users;
import com.example.apit.task.presenter.ProceduresPresenter;
import com.example.apit.task.repositories.imp.ProceduresRepositoryImp;
import com.example.apit.task.utils.PrefUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProceduresAdapter extends RecyclerView.Adapter<ProceduresAdapter.ProceduresViewHolder> implements ProceduresInterface {

    List<NEW_TASKS_ACTION> actions;
    SYSCODMTI[] SYSCODMTIS;
    NEW_TASKS tasks;
    Category category;
    int tabs;
    ProceduresInterface proceduresInterface;
    ProceduresPresenter presenter;

    public interface OnItemClickListener {

        void onDeleteClick(String syskey);
    }

    private final OnItemClickListener listener;

    public ProceduresAdapter(NEW_TASKS tasks, List<NEW_TASKS_ACTION> actions, SYSCODMTI[] SYSCODMTIS, Category category, int tabs, OnItemClickListener listener) {
        this.actions = actions;
        this.SYSCODMTIS = SYSCODMTIS;
        this.listener = listener;
        this.tasks = tasks;
        this.category = category;
        this.tabs = tabs;
        proceduresInterface = this;
    }

    @NonNull
    @Override
    public ProceduresViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.action_item, viewGroup, false);
        return new ProceduresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProceduresViewHolder holder, final int position) {

        holder.actionTextView.setText(actions.get(position).ACTION_TAKEN);
        if (actions.get(position).ACTION_DATETIME != null)
            holder.actionTimeTextView.setText(String.valueOf(actions.get(position).ACTION_DATETIME.getHours()) + ":" + String.valueOf(actions.get(position).ACTION_DATETIME.getMinutes()) );
        if (actions.get(position).ACTION_NOTE != null)
            holder.notesTextView.setText(actions.get(position).ACTION_NOTE);
        if (actions.get(position).ACTION_DATETIME_H != null)
            holder.dateTextView.setText(actions.get(position).ACTION_DATETIME_H);
        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!actions.get(position).Action_Done) {
                    int taskCategory = tasks.TASK_CATEGORY;
                    int actionTaken = 0;
                    for (SYSCODMTI syscodmti : SYSCODMTIS){
                        if (syscodmti.ADESC.equals(actions.get(position).ACTION_TAKEN)){
                            actionTaken = syscodmti.SIKEY;
                            break;
                        }
                    }
                    if (actionTaken!=0) {
                        int taskId = tasks.TASK_SYSKEY;
                        int issueId = actions.get(holder.getAdapterPosition()).SYSKEY;
                        switch (taskCategory) {
                            case 1:
                                if (actionTaken == 11) {
                                    Intent intent = new Intent(App.getContext(), DailyDetection02_03Activity.class);
                                    intent.putExtra("task", tasks);
                                    intent.putExtra("category", category);
                                    intent.putExtra("issueId", issueId);
                                    intent.putExtra("tabs", tabs);
                                    App.getContext().startActivity(intent);

                                } else if (actionTaken == 9) {
                                    Intent intent = new Intent(App.getContext(), Form01_03Activity.class);
                                    intent.putExtra("task", tasks);
                                    intent.putExtra("category", category);
                                    intent.putExtra("issueId", issueId);
                                    intent.putExtra("tabs", tabs);
                                    App.getContext().startActivity(intent);
                                }
                                break;
                            case 2:
                                if (actionTaken == 7) {
                                    Intent intent = new Intent(App.getContext(), CampInventoryActivity.class);
                                    intent.putExtra("task", tasks);
                                    intent.putExtra("category", category);
                                    intent.putExtra("issueId", issueId);
                                    intent.putExtra("tabs", tabs);
                                    App.getContext().startActivity(intent);

                                } else if (actionTaken == 11) {
                                    Intent intent = new Intent(App.getContext(), DailyReportForHajServicesFollowingMnaActivity.class);
                                    intent.putExtra("task", tasks);
                                    intent.putExtra("category", category);
                                    intent.putExtra("issueId", issueId);
                                    intent.putExtra("tabs", tabs);
                                    App.getContext().startActivity(intent);

                                } else if (actionTaken == 9) {
                                    Intent intent = new Intent(App.getContext(), CampPreparationMnaActivity.class);
                                    intent.putExtra("task", tasks);
                                    intent.putExtra("category", category);
                                    intent.putExtra("issueId", issueId);
                                    intent.putExtra("tabs", tabs);
                                    App.getContext().startActivity(intent);

                                }
                                break;
                            case 7:
                                Intent intent = new Intent(App.getContext(), JamaratRegimentActivity.class);
                                intent.putExtra("task", tasks);
                                intent.putExtra("category", category);
                                intent.putExtra("issueId", issueId);
                                intent.putExtra("tabs", tabs);
                                App.getContext().startActivity(intent);
                                break;
                            case 8:
                                if (actionTaken == 9) {
                                    Intent intent1 = new Intent(App.getContext(), CampPreparationArafatActivity.class);
                                    intent1.putExtra("task", tasks);
                                    intent1.putExtra("category", category);
                                    intent1.putExtra("issueId", issueId);
                                    intent1.putExtra("tabs", tabs);
                                    App.getContext().startActivity(intent1);
                                } else if (actionTaken == 11) {
                                    Intent intent1 = new Intent(App.getContext(), DailyReportForHajServicesFollowingArafatActivity.class);
                                    intent1.putExtra("task", tasks);
                                    intent1.putExtra("category", category);
                                    intent1.putExtra("issueId", issueId);
                                    intent1.putExtra("tabs", tabs);
                                    App.getContext().startActivity(intent1);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }else {
                    presenter = new ProceduresPresenter(proceduresInterface, new ProceduresRepositoryImp());
                    presenter.getPdfName(tasks.TASK_SYSKEY, actions.get(position).SYSKEY);
                }
            }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(String.valueOf(actions.get(position).SYSKEY));
            }
        });
        if (!actions.get(position).Action_Done)
            holder.doneImageView.setChecked(false);
            //holder.doneImageView.setImageResource(R.mipmap.right);

        String isHead = PrefUtils.getKeys(App.getContext(), "head");
        /*if (isHead != null && isHead.equals("true")) {
           holder.editImageView.setVisibility(View.GONE);
        }*/
    }

    @Override
    public int getItemCount() {
        if (actions == null)
            return 0;
        return actions.size();
    }

    class ProceduresViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.doneImageView)
        CheckBox doneImageView;
        @BindView(R.id.actionTextView)
        TextView actionTextView;
        @BindView(R.id.actionTimeTextView)
        TextView actionTimeTextView;
        @BindView(R.id.notesTextView)
        TextView notesTextView;
        @BindView(R.id.dateTextView)
        TextView dateTextView;
        @BindView(R.id.editImageView)
        Button editImageView;
        @BindView(R.id.deleteImageView)
        Button deleteImageView;

        public ProceduresViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void Procedures(NEW_TASKS_ACTION[] actions) {

    }

    @Override
    public void by(stp_users[] stp_users) {

    }

    @Override
    public void taskNameList(SYSCODMTI[] SYSCODMTIS) {

    }

    @Override
    public void delete(Boolean result) {

    }

    @Override
    public void addAction(Boolean result) {

    }

    @Override
    public void pdfName(String pdf) {
        if (pdf.equals("failed to get pdf name")){
            Toast.makeText(App.getContext(), "لا يوجد ملف لعرضه", Toast.LENGTH_LONG).show();
        }else {
            Intent intent = new Intent(App.getContext(), ActionFilePreviewActivity.class);
            intent.putExtra("task", tasks);
            intent.putExtra("category", category);
            intent.putExtra("tabs", tabs);
            intent.putExtra("fileName", pdf);
            App.getContext().startActivity(intent);
        }
    }
}
