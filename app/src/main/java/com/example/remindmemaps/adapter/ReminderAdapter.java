package com.example.remindmemaps.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remindmemaps.R;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    Context context;
    List<Reminders> remind;
    public ReminderAdapter(Context context, List<Reminders> remind){
        this.context = context;
        this.remind = remind;
    }

    @NonNull
    @Override
    public ReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context con = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.show_reminders,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ReminderAdapter.ViewHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.reminder.setText(remind.get(position).reminder);
    }

    @Override
    public int getItemCount() {
        return remind.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView reminder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            reminder = itemView.findViewById(R.id.reminder_name);
        }
    }
}
