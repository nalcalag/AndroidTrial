package com.nalcalag.androidtrial.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nalcalag.androidtrial.R;
import com.nalcalag.androidtrial.rest.model.Team;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by nalcalag on 31/07/2017.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private Context context;
    private List<Team> teamsList;

    public TeamAdapter(List<Team> teamsList) {
        this.teamsList = teamsList;
    }

    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
        context = parent.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TeamAdapter.ViewHolder holder, int position) {
        holder.tvTeamName.setText(teamsList.get(position).getName());
        holder.tvTeamCity.setText("City: " + teamsList.get(position).getCity());
        holder.tvTeamStadium.setText("Stadium: " + teamsList.get(position).getStadium());
    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTeamName;
        TextView tvTeamCity;
        TextView tvTeamStadium;
        ImageView teamPic;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTeamName = (TextView) itemView.findViewById(R.id.item_team_name);
            tvTeamCity = (TextView) itemView.findViewById(R.id.item_team_city);
            tvTeamStadium = (TextView) itemView.findViewById(R.id.item_team_stadium);
            teamPic = (ImageView) itemView.findViewById(R.id.item_team_pic);
        }
    }
}
