package edu.diegod5000.itopapps.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import edu.diegod5000.itopapps.R;
import edu.diegod5000.itopapps.services.models.App;

/**
 * Created by diego-d on 15/02/16.
 */
public class AppsRecyclerViewAdapter extends RecyclerView.Adapter<AppsRecyclerViewAdapter.ViewHolder> {
    ArrayList<App> apps;
    OnItemTapListener onItemTapListener;

    public AppsRecyclerViewAdapter(ArrayList<App> apps) {
        this.apps = apps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_app, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.textAppName.setText(apps.get(position).getName());
        holder.textAppAuthor.setText(apps.get(position).getArtist());
        holder.simpleDraweeImgList.setImageURI(Uri.parse(apps.get(position).getImageUrl()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemTapListener != null)
                    onItemTapListener.onTap(apps.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public void setOnItemTapListener(OnItemTapListener onItemTapListener) {
        this.onItemTapListener = onItemTapListener;
    }

    public void swapData(ArrayList<App> newApps){
        this.apps = newApps;
        notifyDataSetChanged();
    }

    public interface OnItemTapListener{
        void onTap(App app);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textAppName;
        private TextView textAppAuthor;
        private SimpleDraweeView simpleDraweeImgList;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            textAppName = (TextView) itemView.findViewById(R.id.textAppName);
            textAppAuthor = (TextView) itemView.findViewById(R.id.textAppAuthor);
            simpleDraweeImgList = (SimpleDraweeView) itemView.findViewById(R.id.simpleDraweeImgList);
        }
    }
}
