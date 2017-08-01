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

    private class VIEWS_TYPES {
        public static final int Header=1;
        public static final int Normal=2;
        public static final int Footer=3;
    }
    boolean isFooter = false;
    boolean isHeader = false;

    public PlayerAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView;

        switch (viewType) {
            case VIEWS_TYPES.Normal:
                isHeader = false;
                isFooter = false;
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
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
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
                break;
        }

        return  new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (!isHeader && !isFooter) {
            holder.tvPlayerName.setText(playerList.get(position).getFirstName() + " " + playerList.get(position).getSecondName());
            holder.tvPlayerAge.setText("Age: " + playerList.get(position).getAge().toString());
            holder.tvPlayerClub.setText("Club: " + playerList.get(position).getClub());
        } else if (isHeader)
            holder.tvTitle.setText("Players");
        else if (isFooter)
            holder.tvFooter.setText("Load More Players...");
    }

    @Override
    public int getItemViewType(int position) {
        if (playerList.get(position).isFooter())
            return VIEWS_TYPES.Footer;
        else if (playerList.get(position).isHeader())
            return VIEWS_TYPES.Header;
        else
            return VIEWS_TYPES.Normal;
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlayerName;
        TextView tvPlayerAge;
        TextView tvPlayerClub;
        TextView tvTitle;
        TextView tvFooter;

        ViewHolder(View view) {
            super(view);
            tvPlayerName = (TextView) view.findViewById(R.id.item_player_name);
            tvPlayerAge = (TextView) view.findViewById(R.id.item_player_age);
            tvPlayerClub = (TextView) view.findViewById(R.id.item_player_club);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_header);
            tvFooter = (TextView) itemView.findViewById(R.id.tv_footer);
        }
    }
}
