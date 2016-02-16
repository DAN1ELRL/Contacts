package com.example.daniel.innocv.UI.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.daniel.innocv.Model.User;
import com.example.daniel.innocv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by daniel on 13/02/16.
 */
public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> implements View.OnClickListener{

    private ArrayList<User> mItems;
    private View.OnClickListener listener;
    private String[] allColors;

    public UsersListAdapter(ArrayList<User> items) {
        mItems = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        view.setOnClickListener(this);
        allColors = view.getContext().getResources().getStringArray(R.array.colors);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mItems.get(position);
        holder.name.setText(holder.mItem.getName());
        String date = holder.mItem.getBirthdate().split("T")[0];
        String dateOcc = date.split("-")[2]+"-"+date.split("-")[1]+"-"+date.split("-")[0];
        holder.birthdate.setText(dateOcc);
        int idx = new Random().nextInt(allColors.length);
        TextDrawable drawable = TextDrawable.builder().buildRound(holder.name.getText().toString().substring(0,1).toUpperCase(), Color.parseColor(allColors[idx]));
        holder.img.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        if(listener != null)
            listener.onClick(v);
    }

    public void setFilter(List<User> usrList) {
        mItems = new ArrayList<>();
        mItems.addAll(usrList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView name;
        public final TextView birthdate;
        public final ImageView img;
        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.name);
            birthdate = (TextView) view.findViewById(R.id.birthdate);
            img = (ImageView)view.findViewById(R.id.imageContact);
        }

    }
}
