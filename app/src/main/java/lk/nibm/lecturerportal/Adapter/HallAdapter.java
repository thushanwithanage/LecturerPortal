package lk.nibm.lecturerportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lk.nibm.lecturerportal.Model.LectureHall;
import lk.nibm.lecturerportal.R;
import lk.nibm.lecturerportal.RequestHall;

public class HallAdapter extends RecyclerView.Adapter<HallAdapter.HallViewHolder>
{
    Context context;
    List<LectureHall> hallsList;

    public HallAdapter(Context context, List<LectureHall> hallsList) {
        this.context = context;
        this.hallsList = hallsList;
    }

    public static final class HallViewHolder extends RecyclerView.ViewHolder {
        TextView hallname, floorno;

        public HallViewHolder(@NonNull View itemView) {
            super(itemView);
            hallname = itemView.findViewById( R.id.txtlechallName2);
            floorno = itemView.findViewById(R.id.txtFloorno);
        }
    }

    @NonNull
    @Override
    public HallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.lecture_halls, parent, false);
        return new HallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HallViewHolder holder, final int position) {
        holder.hallname.setText(hallsList.get(position).getHallName());
        holder.floorno.setText(hallsList.get(position).getFloorNo());

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RequestHall.class);
                intent.putExtra( "lechallId", hallsList.get(position).getHallId() );
                intent.putExtra( "lechallName", hallsList.get(position).getHallName() );
                context.startActivity(intent);
            }
        } );
    }

    @Override
    public int getItemCount() {
        return hallsList.size();
    }

}
