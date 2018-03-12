package onlinemarketing.net.sudanjobnet.Adapter;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;


import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import onlinemarketing.net.sudanjobnet.Fragment.Fragment_Job_Details;
import onlinemarketing.net.sudanjobnet.Json.CustomVolleyRequest;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.R;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by muawia.ibrahim on 1/11/2016.
 */
public class RecyclerAdapterJobs extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ImageLoader imageLoader;

    private static ArrayList<JobItems> items;
    private static Context context;
    private static OnItemClick onItemClick;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private boolean isLoading;
    private Activity activity;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    private OnLoadMoreListener onLoadMoreListener;

    private boolean isMoreLoading = true;

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public RecyclerAdapterJobs(ArrayList<JobItems> items, Context context) {
        RecyclerAdapterJobs.context = context;
        RecyclerAdapterJobs.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_job_row, parent, false);

        return new ListItemViewHolder(view);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        JobItems jobItems = items.get(position);
        final ListItemViewHolder ListItemViewHolder = (ListItemViewHolder) holder;
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(jobItems.getClogo(), ImageLoader.getImageListener(ListItemViewHolder.clogo, R.mipmap.no_image, R.mipmap.no_image));
        ListItemViewHolder.title.setText(Html.fromHtml(jobItems.getTitle()));
        ListItemViewHolder.company_name.setText(Html.fromHtml(jobItems.getCompany_name()));
        ListItemViewHolder.closing.setText(jobItems.getClosing());


        // LocalDate today = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMMM yyyy");

        String closing_date = jobItems.getClosing();
        DateTime c_date = fmt.parseDateTime(closing_date);


        DateTime today = new DateTime();

      //  Period period = new Period(today, c_date);
        Days days = Days.daysBetween(today.withTimeAtStartOfDay(), c_date.withTimeAtStartOfDay());

        ListItemViewHolder.time_ago.setText(days.getDays() + " Day(s) Remain");
        if (days.getDays() == 0) {
      //ListItemViewHolder.title.setHighlightColor(Color.MAGENTA);
            Period period = new Period(c_date, today, PeriodType.dayTime());

            PeriodFormatter formatter = new PeriodFormatterBuilder()
//                    .appendDays().appendSuffix(" day ", " days ")
                    .appendHours().appendSuffix(" hour ", " hours ")
                    .appendMinutes().appendSuffix(" minute ", " minutes ")
//                    .appendSeconds().appendSuffix(" second ", " seconds ")
                    .toFormatter();
            ListItemViewHolder.time_ago.setText("Today is the Last day");
            ListItemViewHolder.time_ago.setTextColor(ContextCompat.getColorStateList(context,R.color.colorAccent));
        }
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView pid, title, company_name, closing, time_ago;
        ImageView clogo;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            pid = itemView.findViewById(R.id.pid);
            title = itemView.findViewById(R.id.title);
            company_name = itemView.findViewById(R.id.company_name);
            closing = itemView.findViewById(R.id.closing);
            clogo = itemView.findViewById(R.id.clogo);
            time_ago = itemView.findViewById(R.id.mago);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Fragment_Job_Details.class);

                    JobItems item1 = new JobItems();


                    JobItems item = items.get(getLayoutPosition());
                    //   intent.putExtra("item",item);
                    //context.startActivity(intent);
                    onItemClick.OnClick(item, getLayoutPosition());
                }
            });

            //         clogo.setOnClickListener(new View.OnClickListener() {
            //      @Override
            //      public void onClick(View v) {
            //         onItemClick.OnClick(items.get(getLayoutPosition()),getLayoutPosition());
            //       }
            //   });

        }

        public void bind(JobItems jobItems) {
            title.setText(jobItems.getTitle());

        }
    }


    public interface OnItemClick {
        void OnClick(Object objet, int position);
    }

    public void setOnItemClickListener(OnItemClick onItemClick) {
        RecyclerAdapterJobs.onItemClick = onItemClick;

    }


    public void setFilter(List<JobItems> jobItemses) {
        items = new ArrayList<>();
        items.addAll(items);
        notifyDataSetChanged();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            //    progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    public void setLoaded() {
        isLoading = false;
    }

}
