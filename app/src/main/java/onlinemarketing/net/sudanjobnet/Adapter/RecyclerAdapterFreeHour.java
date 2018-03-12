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

import onlinemarketing.net.sudanjobnet.Fragment.Fragment_freehour_Details;
import onlinemarketing.net.sudanjobnet.Json.CustomVolleyRequest;
import onlinemarketing.net.sudanjobnet.Model.FreeHourItems;
import onlinemarketing.net.sudanjobnet.Model.JobItems;
import onlinemarketing.net.sudanjobnet.R;


/**
 * Created by muawia.ibrahim on 1/11/2016.
 */
public class RecyclerAdapterFreeHour extends RecyclerView.Adapter<RecyclerAdapterFreeHour.ListItemViewHolder> {
    private ImageLoader imageLoader;

    private static ArrayList<FreeHourItems> items;
    private static Context context;
    private static OnItemClick onItemClick;

    public RecyclerAdapterFreeHour(ArrayList<FreeHourItems> items, Context context) {
        RecyclerAdapterFreeHour.context = context;
        RecyclerAdapterFreeHour.items = items;
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_freehour_row, viewGroup, false);

        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder listItemViewHolder, int position) {

        FreeHourItems learningItems = items.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(learningItems.getClogo(), ImageLoader.getImageListener(listItemViewHolder.clogo11, R.mipmap.no_image, R.mipmap.no_image));
        listItemViewHolder.title11.setText(Html.fromHtml(learningItems.getTitle()));
        listItemViewHolder.company_name11.setText(Html.fromHtml(learningItems.getCompany_name()));
        listItemViewHolder.closing11.setText(learningItems.getClosing());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView pid11, title11, company_name11, closing11;
        ImageView clogo11;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            pid11 = itemView.findViewById(R.id.pid11);
            title11 = itemView.findViewById(R.id.title11);
            company_name11 = itemView.findViewById(R.id.company_name11);
            closing11 = itemView.findViewById(R.id.closing11);
            clogo11 = itemView.findViewById(R.id.clogo11);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Fragment_freehour_Details.class);

                    FreeHourItems item1 = new FreeHourItems();


                    FreeHourItems item = items.get(getLayoutPosition());
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
        RecyclerAdapterFreeHour.onItemClick = onItemClick;

    }


    public void setFilter(List<JobItems> jobItemses) {
        items = new ArrayList<>();
        items.addAll(items);
        notifyDataSetChanged();
    }
}
