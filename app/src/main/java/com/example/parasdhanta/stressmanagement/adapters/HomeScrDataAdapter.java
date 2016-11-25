package com.example.parasdhanta.stressmanagement.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.parasdhanta.stressmanagement.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paras Dhanta on 10/25/2016.
 */

public class HomeScrDataAdapter extends RecyclerView.Adapter<HomeScrDataAdapter.ViewHolder> {

    ArrayList<String> homeScreenElements;
    private static final String TAG = HomeScrDataAdapter.class.getSimpleName();
    @Override
    public HomeScrDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,parent,false);
        return new ViewHolder(view);

    }

    public HomeScrDataAdapter(List<String> homeScreenElements) {
        this.homeScreenElements = (ArrayList<String>) homeScreenElements;
    }

    @Override
    public void onBindViewHolder(HomeScrDataAdapter.ViewHolder holder, int position) {
        holder.tv_homeScrList.setText(this.homeScreenElements.get(position));
    }

    @Override
    public int getItemCount() {
        return homeScreenElements.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_homeScrList;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_homeScrList = (TextView) itemView.findViewById(R.id.tv_home_list);
        }
    }
}
