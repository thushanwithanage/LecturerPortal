package lk.nibm.lecturerportal.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lk.nibm.lecturerportal.Model.LecHallBooking;
import lk.nibm.lecturerportal.R;

public class LecturehallAdapter extends RecyclerView.Adapter<LecturehallAdapter.LecturehallViewHolder>
{
    Context context;
    List<LecHallBooking> hallsList;

    public LecturehallAdapter(Context context, List<LecHallBooking> hallsList) {
        this.context = context;
        this.hallsList = hallsList;
    }

    public static final class LecturehallViewHolder extends RecyclerView.ViewHolder {
        TextView hallname, batch, bdate, btime;

        public LecturehallViewHolder(@NonNull View itemView) {
            super(itemView);
            hallname = itemView.findViewById( R.id.txtlechallName);
            batch = itemView.findViewById(R.id.stdBatch);
            bdate =  itemView.findViewById(R.id.txtBookingdate);
            btime =  itemView.findViewById(R.id.txtBookingtime);
        }
    }

    @NonNull
    @Override
    public LecturehallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lecturehall_list, parent, false);
        return new LecturehallViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final LecturehallViewHolder holder, final int position) {
        holder.hallname.setText(hallsList.get(position).getLechallName());
        holder.batch.setText(hallsList.get(position).getBatchName());
        holder.btime.setText(hallsList.get(position).getStartTime() + " - " + hallsList.get(position).getEndTime());

        String bookingdate = hallsList.get(position).getBookDate();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-yyyy");
        LocalDate day = LocalDate.parse(bookingdate, formatter);
        LocalDate today = LocalDate.now();

        if(day.compareTo(today)==0)
        {
            holder.bdate.setText("Today");
        }
        else if(day.compareTo(today)==1)
        {
            holder.bdate.setText("Tomorrow");
        }
        else {
            holder.bdate.setText(bookingdate);
        }
    }

    @Override
    public int getItemCount() {
        return hallsList.size();
    }

}