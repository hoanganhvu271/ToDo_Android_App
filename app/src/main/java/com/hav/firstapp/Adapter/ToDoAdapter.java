package com.hav.firstapp.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hav.firstapp.Model.ToDoModel;
import java.util.List;
import com.hav.firstapp.MainActivity;
import com.hav.firstapp.R;
import android.widget.CheckBox;
import com.hav.firstapp.Model.ToDoModel;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>{
    private List<ToDoModel> todoList;
    private MainActivity activity;

    public ToDoAdapter(MainActivity activity){
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ToDoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
    }

    private boolean toBoolean(int n){
        return n!=0;
    }
    public void setTasks(List<ToDoModel> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
    }
    public int getItemCount(){
        return todoList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckBox);
        }

    }
}
