package ca.antonious.gfys;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gfycat.core.gfycatapi.pojo.Gfycat;

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

        Glide.with(viewHolder.itemView.getContext())
                .asGif()
                .load(gfycat.getGif1mbUrl())
                .into(viewHolder.gifImageView);

        viewHolder.titleTextView.setText(gfycat.getTitle());
        viewHolder.usernameTextView.setText(gfycat.getUserName());
        //viewHolder.viewCountTextView.setText(gfycat.getViews());
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
