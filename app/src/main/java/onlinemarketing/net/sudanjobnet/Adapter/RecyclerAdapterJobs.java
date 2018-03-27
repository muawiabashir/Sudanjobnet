package onlinemarketing.net.sudanjobnet.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.List;

import onlinemarketing.net.sudanjobnet.Fragment.Fragment_Job_Details;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.R;


/**
 * Created by muawia.ibrahim on 1/11/2016.
 */
public class RecyclerAdapterJobs extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static ArrayList<JobItems> items;
    private static Context context;
    private static OnItemClick onItemClick;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private ImageLoader imageLoader;
    private boolean isLoading;
    private Activity activity;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    private OnLoadMoreListener onLoadMoreListener;

    private boolean isMoreLoading = true;

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
        Glide.with(context).load(jobItems.getClogo())
                .thumbnail(0.5f)

                .apply(new RequestOptions().placeholder(R.mipmap.no_image).error(R.mipmap.no_image))

                .into(ListItemViewHolder.clogo);
//        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
//        imageLoader.get(jobItems.getClogo(), ImageLoader.getImageListener(ListItemViewHolder.clogo, R.mipmap.no_image, R.mipmap.no_image));
        ListItemViewHolder.title.setText(Html.fromHtml(jobItems.getTitle()));
        ListItemViewHolder.company_name.setText(Html.fromHtml(jobItems.getCompany_name()));
        ListItemViewHolder.closing.setText(jobItems.getClosing());


        // LocalDate today = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd MMMM yyyy");
        DateTimeFormatter fmt1 = DateTimeFormat.forPattern("dd MMMM yyyy");

        String closing_date = jobItems.getClosing();
        DateTime c_date = fmt.parseDateTime(closing_date);
        String posted_on = jobItems.getPosted_on();


        //   DateTime posted = fmt1.parseDateTime(posted_on);
        DateTime today = new DateTime();
//        DateTime dateTime = new DateTime(fmt1.parseDateTime(posted_on));
        //  Period period = new Period(today, c_date);
        Days days = Days.daysBetween(today.withTimeAtStartOfDay(), c_date.withTimeAtStartOfDay());

        ListItemViewHolder.time_ago.setText(days.getDays() + " Day(s) Remain");


        //   DateTime posted = DateTime.parse(posted_on);

//        DateTime posted = fmt1.parseDateTime(posted_on);
        //   SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");

//          if (dateFormat.parse(posted_on==today.toDate().toString()) {
//                ListItemViewHolder.posted_on.setVisibility(View.VISIBLE);
//           } else {
//                 ListItemViewHolder.posted_on.setVisibility(View.INVISIBLE);
//             }
//

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
            //    ListItemViewHolder.time_ago.setTextColor(ContextCompat.getColorStateList(context, R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
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

    public void setLoaded() {
        isLoading = false;
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnItemClick {
        void OnClick(Object objet, int position);
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView pid, title, company_name, closing, time_ago;
        ImageView clogo, posted_on;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            pid = itemView.findViewById(R.id.pid);
            title = itemView.findViewById(R.id.title);
            company_name = itemView.findViewById(R.id.company_name);
            closing = itemView.findViewById(R.id.closing);
            clogo = itemView.findViewById(R.id.clogo);
            time_ago = itemView.findViewById(R.id.mago);
            posted_on = itemView.findViewById(R.id.btn_new);

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

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            //    progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

}
