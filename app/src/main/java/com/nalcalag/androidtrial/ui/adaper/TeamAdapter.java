package com.nalcalag.androidtrial.ui.adaper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nalcalag.androidtrial.R;
import com.nalcalag.androidtrial.rest.adapter.APIAdapter;
import com.nalcalag.androidtrial.rest.model.DataResult;
import com.nalcalag.androidtrial.rest.model.Team;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nalcalag on 31/07/2017.
 */

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    private Context context;
    private List<Team> teamsList;
    private String search;
    private int offset;
    private static String TYPE_SEARCH = "teams";
    private String order = null;

    private class VIEWS_TYPES {
        private static final int Header=1;
        private static final int Normal=2;
        private static final int Footer=3;
    }
    private boolean isFooter = false;
    private boolean isHeader = false;

    public TeamAdapter(List<Team> teamsList, Context context, String search, int offset) {
        this.teamsList = teamsList;
        this.context = context;
        this.search = search;
        this.offset = offset;
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
    public void onBindViewHolder(final TeamAdapter.ViewHolder holder,final int position) {

        if (isHeader) {
            holder.tvTitle.setText(R.string.teams);
        } else if (isFooter) {
            holder.tvFooter.setText(R.string.more_teams);

            //Set Load More OnClick
            holder.tvFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.progressBar.setVisibility(View.VISIBLE);
                    Call<DataResult> callResults = new APIAdapter().getResults(search,TYPE_SEARCH,offset,order);
                    callResults.enqueue(new Callback<DataResult>() {
                        @Override
                        public void onResponse(Call<DataResult> call, Response<DataResult> response) {
                            List<Team> newTeams = response.body().getTeams();
                            if (newTeams == null)
                                holder.tvFooter.setVisibility(View.GONE);
                            else {
                                //Add Items
                                for (int i = 0; i < newTeams.size()-1; i++) {
                                    addItem(teamsList.size()-1, newTeams.get(i));
                                }
                                if (newTeams.size() < 10)
                                    holder.tvFooter.setVisibility(View.GONE);
                                else
                                    offset += 10;
                            }
                            holder.progressBar.setVisibility(View.GONE);
                        }
                        @Override
                        public void onFailure(Call<DataResult> call, Throwable t) {
                            holder.tvFooter.setVisibility(View.GONE);
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });
        } else {
            holder.tvTeamName.setText(teamsList.get(position).getName());
            String city = "City: " + teamsList.get(position).getCity();
            holder.tvTeamCity.setText(city);
            String stadium = "Stadium: " + teamsList.get(position).getStadium();
            holder.tvTeamStadium.setText(stadium);
        }
    }

    public final void addItem(int position, Team team) {
        teamsList.add(position, team);
        notifyItemInserted(position);
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
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTeamName = (TextView) itemView.findViewById(R.id.item_team_name);
            tvTeamCity = (TextView) itemView.findViewById(R.id.item_team_city);
            tvTeamStadium = (TextView) itemView.findViewById(R.id.item_team_stadium);
            teamPic = (ImageView) itemView.findViewById(R.id.item_team_pic);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_header);
            tvFooter = (TextView) itemView.findViewById(R.id.tv_footer);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar_load);
        }
    }
}
