package com.vincentlaf.story.others;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vincentlaf.story.R;

import java.util.List;

/**
 * Created by DengCun on 2017/12/30.
 */

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.ViewHolder> {
    private List<AuthorInformation> authorInformationList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView authorName;
        TextView title;
        TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            avatar=(ImageView)itemView.findViewById(R.id.imageView2);
            authorName=(TextView)itemView.findViewById(R.id.frag1_re_item_authorName);
            title=(TextView)itemView.findViewById(R.id.frag1_re_item_title);
            content=(TextView)itemView.findViewById(R.id.frag1_re_item_content);
        }
    }
    public AuthorAdapter(List<AuthorInformation>list){
        this.authorInformationList=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.frag1_recyclerview_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AuthorInformation authorInformation=authorInformationList.get(position);
        holder.avatar.setImageResource(authorInformation.getImg());
        holder.authorName.setText(authorInformation.getAuthorName());
        holder.content.setText(authorInformation.getContent());
        holder.title.setText(authorInformation.getTitle());

    }

    @Override
    public int getItemCount() {
        return authorInformationList.size();
    }
}
