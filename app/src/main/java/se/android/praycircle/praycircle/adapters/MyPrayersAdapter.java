package se.android.praycircle.praycircle.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import se.android.praycircle.praycircle.R;
import se.android.praycircle.praycircle.fragments.MyPrayersFragment;
import se.android.praycircle.praycircle.helpers.VarHolder;
import se.android.praycircle.praycircle.objects.MyPrayerSubject;

/** * Created by Paulo Vila Nova on 2017-02-03.
 */

public class MyPrayersAdapter extends RecyclerView.Adapter<MyPrayersAdapter.MyViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    private List<MyPrayerSubject> myPrayerSubjectsList;


    public MyPrayersAdapter(List<MyPrayerSubject> myPrayerSubjectsList, Context context) {
        this.myPrayerSubjectsList = new ArrayList<MyPrayerSubject>(myPrayerSubjectsList);
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_prayers_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyPrayerSubject myPrayerSubject = myPrayerSubjectsList.get(position);

        holder.circleImgViewUserAvatar.setImageResource(R.drawable.avatar_default);
        holder.circleImgViewClockAlarm.setImageResource(R.drawable.ic_alarm_on_gray_24dp);
        holder.txtMyPrayerDate.setText(myPrayerSubject.getDate());
        holder.txtPrayerTitle.setText(myPrayerSubject.getPrayerTitle());
        holder.txtPrayerSubject.setText(myPrayerSubject.getPraySubject());


    }

    @Override
    public int getItemCount() {
        return myPrayerSubjectsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CircleImageView circleImgViewUserAvatar;
        public CircleImageView circleImgViewClockAlarm;
        public RelativeLayout relativeLayoutMyPrayersView;
        public LinearLayout linearLayoutClockAlarm;
        public TextView txtMyPrayerDate, txtPrayerTitle, txtPrayerSubject;
        private View container;

        public MyViewHolder(View itemView) {
            super(itemView);

            circleImgViewUserAvatar = (CircleImageView)itemView.findViewById(R.id.circleImgViewUserAvatar);
            circleImgViewClockAlarm = (CircleImageView)itemView.findViewById(R.id.circleImgViewClockAlarm);
            relativeLayoutMyPrayersView = (RelativeLayout)itemView.findViewById(R.id.relativeLayoutMyPrayersView);
            linearLayoutClockAlarm = (LinearLayout)itemView.findViewById(R.id.linearLayoutClockAlarm);
            txtMyPrayerDate = (TextView)itemView.findViewById(R.id.txtMyPrayerDate);
            txtPrayerTitle = (TextView)itemView.findViewById(R.id.txtPrayerTitle);
            txtPrayerSubject = (TextView)itemView.findViewById(R.id.txtPrayerSubject);

            container = (itemView.findViewById(R.id.myPayersContainer));
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            RecyclerView.ViewHolder vh = RecyclerViewAdapterUtils.getViewHolder(view);
            final int position = vh.getAdapterPosition();

            switch (view.getId()){

                case R.id.myPayersContainer:
                    Toast.makeText(context,"Position: " + position, Toast.LENGTH_LONG).show();
                    break;
            }



        }
    }
}
