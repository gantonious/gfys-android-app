package ca.antonious.gfys;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gfycat.core.gfycatapi.pojo.Gfycat;

import ca.antonious.gfys.features.Features;
import ca.antonious.viewcelladapter.annotations.BindListener;
import ca.antonious.viewcelladapter.viewcells.GenericViewCell;

/**
 * Created by George on 2017-10-26.
 */

public class GfycatViewCell extends GenericViewCell<GfycatViewCell.GfycatViewHolder, Gfycat> {

    public GfycatViewCell(Gfycat gfycat) {
        super(gfycat);
    }

    @Override
    public int getLayoutId() {
        return R.layout.gif_list_item;
    }

    @Override
    public void bindViewCell(GfycatViewHolder viewHolder) {
        Gfycat gfycat = getData();
        viewHolder.titleTextView.setText(gfycat.getTitle());
        viewHolder.usernameTextView.setText(gfycat.getUserName());
        viewHolder.viewCountTextView.setText(String.valueOf(gfycat.getViews()));

        Glide.with(viewHolder.itemView.getContext())
                .asGif()
                .load(getGifUrl())
                .into(viewHolder.gifImageView);
    }

    public interface OnGyfcatClickedListener {
        void onGfycatClicked(String gifLink);
    }

    @BindListener
    public void bindOnClickListener(GfycatViewHolder viewHolder, OnGyfcatClickedListener listener) {
        if (Features.isFullScreenEnabled()) {
            viewHolder.itemView.setOnClickListener(v -> listener.onGfycatClicked(getGifUrl()));
        }
    }

    private String getGifUrl() {
        return String.format("https://thumbs.gfycat.com/%s-size_restricted.gif", getData().getGfyName());
    }

    public static class GfycatViewHolder extends RecyclerView.ViewHolder {
        public final ImageView gifImageView;
        public final TextView titleTextView;
        public final TextView usernameTextView;
        public final TextView viewCountTextView;

        public GfycatViewHolder(View itemView) {
            super(itemView);

            gifImageView = (ImageView) itemView.findViewById(R.id.image);
            titleTextView = (TextView) itemView.findViewById(R.id.title);
            usernameTextView = (TextView) itemView.findViewById(R.id.username);
            viewCountTextView = (TextView) itemView.findViewById(R.id.view_count);
        }
    }
}
