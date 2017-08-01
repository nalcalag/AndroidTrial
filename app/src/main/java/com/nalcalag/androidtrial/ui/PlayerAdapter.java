package com.nalcalag.androidtrial.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nalcalag.androidtrial.R;
import com.nalcalag.androidtrial.rest.model.Player;

import java.util.List;

/**
 * Created by nalcalag on 31/07/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<Player> playerList;

    public PlayerAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvPlayerName.setText(playerList.get(position).getFirstName() + " " + playerList.get(position).getSecondName());
        holder.tvPlayerAge.setText("Age: " + playerList.get(position).getAge().toString());
        holder.tvPlayerClub.setText("Club: " + playerList.get(position).getClub());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlayerName;
        TextView tvPlayerAge;
        TextView tvPlayerClub;

        ViewHolder(View view) {
            super(view);
            tvPlayerName = (TextView) view.findViewById(R.id.item_player_name);
            tvPlayerAge = (TextView) view.findViewById(R.id.item_player_age);
            tvPlayerClub = (TextView) view.findViewById(R.id.item_player_club);
        }
    }
}
