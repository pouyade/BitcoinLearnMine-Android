package pro.pouyasoft.btclearnmine.Ui.Adapter;

import android.content.Context;
import android.content.Intent;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pro.pouyasoft.btclearnmine.ApplicationLoader;
import pro.pouyasoft.btclearnmine.Data.Models.Article;
import pro.pouyasoft.btclearnmine.Ui.Activity.ViewArticleActivity;
import pro.pouyasoft.btclearnmine.Ui.cells.ArticleCell;
import pro.pouyasoft.btclearnmine.Ui.cells.NativeDialogCell;

/**
 * Created by pouyadark on 8/3/18.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {
    Context context;
    ArrayList<Article> articles;
    public ArticlesAdapter(Context context, ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==1) {
            ArticleCell view = new ArticleCell(context);
            return new MyViewHolder(view);
        }else{
            NativeDialogCell view = new NativeDialogCell(context);
            return new MyViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position==0?2:1;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(holder.getItemViewType()==1) {
            ((ArticleCell) holder.itemView).setArticle(articles.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ViewArticleActivity.class);
                    intent.putExtra("title", articles.get(position).title);
                    intent.putExtra("web", articles.get(position).web + ".html");
                    context.startActivity(intent);
                }
            });
        }else{
            holder.itemView.setOnClickListener(null);
            ((NativeDialogCell) holder.itemView).setUnifiedNativeAd(ApplicationLoader.nativeAd);

        }
    }



    @Override
    public int getItemCount() {
        return articles.size();
    }
    protected class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(View itemView) {
            super(itemView);
        }

    }
}
