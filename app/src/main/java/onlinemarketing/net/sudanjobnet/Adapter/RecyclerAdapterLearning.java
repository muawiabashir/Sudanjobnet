package onlinemarketing.net.sudanjobnet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;



import java.util.ArrayList;
import java.util.List;

import onlinemarketing.net.sudanjobnet.Fragment.Fragment_learning_Details;
import onlinemarketing.net.sudanjobnet.Json.CustomVolleyRequest;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.Model.LearningItems;
import onlinemarketing.net.sudanjobnet.R;


/**
 * Created by muawia.ibrahim on 1/11/2016.
 */
public class RecyclerAdapterLearning extends RecyclerView.Adapter<RecyclerAdapterLearning.ListItemViewHolder> {
    private ImageLoader imageLoader;

    private static ArrayList<LearningItems> items;
    private static Context context;
    private static OnItemClick onItemClick;

    public RecyclerAdapterLearning(ArrayList<LearningItems> items, Context context) {
        RecyclerAdapterLearning.context = context;
        RecyclerAdapterLearning.items = items;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_learning_row, viewGroup, false);

        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder listItemViewHolder, int position) {

        LearningItems learningItems = items.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(learningItems.getClogo(), ImageLoader.getImageListener(listItemViewHolder.clogo1, R.mipmap.no_image, R.mipmap.no_image));
        listItemViewHolder.title1.setText(Html.fromHtml(learningItems.getTitle()));
        listItemViewHolder.company_name1.setText(Html.fromHtml(learningItems.getCompany_name()));
        listItemViewHolder.closing1.setText(learningItems.getClosing());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView pid1, title1, company_name1, closing1;
        ImageView clogo1;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            pid1 = itemView.findViewById(R.id.pid1);
            title1 = itemView.findViewById(R.id.title1);
            company_name1 = itemView.findViewById(R.id.company_name1);
            closing1 = itemView.findViewById(R.id.closing1);
            clogo1 = itemView.findViewById(R.id.clogo1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Fragment_learning_Details.class);

                    JobItems item1 = new JobItems();


                    LearningItems item = items.get(getLayoutPosition());
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
//        public void bind(JobItems jobItems){
//            title1.setText(jobItems.getTitle());
//
//        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface OnItemClick {
        void OnClick(Object objet, int position);
    }

    public void setOnItemClickListener(OnItemClick onItemClick) {
        RecyclerAdapterLearning.onItemClick = onItemClick;

    }


    public void setFilter(List<JobItems> jobItemses) {
        items = new ArrayList<>();
        items.addAll(items);
        notifyDataSetChanged();
    }
}
