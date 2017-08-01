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

    private class VIEWS_TYPES {
        public static final int Header=1;
        public static final int Normal=2;
        public static final int Footer=3;
    }
    boolean isFooter = false;
    boolean isHeader = false;

    public TeamAdapter(List<Team> teamsList) {
        this.teamsList = teamsList;
    }

    @Override
    public TeamAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView;

        switch (viewType) {
            case VIEWS_TYPES.Normal:
                isHeader = false;
                isFooter = false;
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
                break;
            case VIEWS_TYPES.Header:
                isHeader = true;
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_list, parent, false);
                break;
            case VIEWS_TYPES.Footer:
                isFooter = true;
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_list, parent, false);
                break;
            default:
                isHeader = false;
                isFooter = false;
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team, parent, false);
                break;
        }
        return  new TeamAdapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(TeamAdapter.ViewHolder holder, int position) {

        if (!isHeader && !isFooter) {
            holder.tvTeamName.setText(teamsList.get(position).getName());
            holder.tvTeamCity.setText("City: " + teamsList.get(position).getCity());
            holder.tvTeamStadium.setText("Stadium: " + teamsList.get(position).getStadium());
        } else if (isHeader)
            holder.tvTitle.setText("Teams");
        else if (isFooter)
            holder.tvFooter.setText("Load More Teams...");
    }

    @Override
    public int getItemViewType(int position) {
        if (teamsList.get(position).isFooter())
            return TeamAdapter.VIEWS_TYPES.Footer;
        else if (teamsList.get(position).isHeader())
            return TeamAdapter.VIEWS_TYPES.Header;
        else
            return TeamAdapter.VIEWS_TYPES.Normal;
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
        TextView tvTitle;
        TextView tvFooter;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTeamName = (TextView) itemView.findViewById(R.id.item_team_name);
            tvTeamCity = (TextView) itemView.findViewById(R.id.item_team_city);
            tvTeamStadium = (TextView) itemView.findViewById(R.id.item_team_stadium);
            teamPic = (ImageView) itemView.findViewById(R.id.item_team_pic);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_header);
            tvFooter = (TextView) itemView.findViewById(R.id.tv_footer);
        }
    }
}
