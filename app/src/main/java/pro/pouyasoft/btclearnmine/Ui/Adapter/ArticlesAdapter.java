package pro.pouyasoft.btclearnmine.Ui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pro.pouyasoft.btclearnmine.Data.Models.Article;
import pro.pouyasoft.btclearnmine.Data.Models.Category;
import pro.pouyasoft.btclearnmine.Ui.Activity.ViewArticleActivity;
import pro.pouyasoft.btclearnmine.Ui.ArticleCell;

/**
 * Created by pouyadark on 8/3/18.
 */

public class ArticlesAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Article> articles;
    public ArticlesAdapter(Context context, ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ArticleCell view=new ArticleCell(context);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((ArticleCell)holder.itemView).setArticle(articles.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewArticleActivity.class);
                intent.putExtra("title",articles.get(position).title);
                intent.putExtra("web",articles.get(position).web + ".html");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size()-1;
    }
    private class viewHolder extends RecyclerView.ViewHolder{

        public viewHolder(View itemView) {
            super(itemView);
        }

    }
}
