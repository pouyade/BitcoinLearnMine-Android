package pro.pouyasoft.btclearnmine.Ui;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.io.IOException;
import java.io.InputStream;

import pro.pouyasoft.btclearnmine.Data.Models.Article;
import pro.pouyasoft.btclearnmine.Helper.LayoutHelper;
import pro.pouyasoft.btclearnmine.R;
import pro.pouyasoft.btclearnmine.Setting.AppSetting;

/**
 * Created by pouyadark on 8/3/18.
 */

public class ArticleCell extends FrameLayout {
    Article article;

    public ArticleCell(Context context) {
        super(context);
        init();
    }
    public void setArticle(Article article){
        this.article = article;
        init();
    }

    public void init(){
        if(article == null) return;
        CardView cardView = new CardView(getContext());
        cardView.setCardBackgroundColor(AppSetting.getNightMode()?getResources().getColor(R.color.cardbackgrounddark):0xffffffff);
        cardView.setUseCompatPadding(true);
        cardView.setPadding(0,0,0,0);

        ImageView imgico= new ImageView(getContext());
        imgico.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        try {
            // get input stream
            InputStream ims = getContext().getAssets().open("web/" + article.icon + ".png");
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            imgico.setImageDrawable(d);
        }
        catch(IOException ex) {

        }
//        imgico.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.ic_launcher));
        cardView.addView(imgico, LayoutHelper.createFrame(80,80, Gravity.LEFT|Gravity.TOP));

        TextView txtTitle = new TextView(getContext());
        txtTitle.setTextColor(AppSetting.getNightMode()?0xffffffff:0xff000000);
        txtTitle.setText(article.title);
        cardView.addView(txtTitle,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,20,Gravity.LEFT|Gravity.TOP,85,2,5,5));

        TextView txtdetails = new TextView(getContext());
//        txtdetails.setTextColor(0xff000000);
        txtdetails.setText(article.subtitle);
        txtdetails.setMaxLines(2);
        txtdetails.setTextColor(AppSetting.getNightMode()?0x88ffffff:0xff000000);
        cardView.addView(txtdetails,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT,Gravity.LEFT|Gravity.TOP,85,22,5,5));


        addView(cardView,LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,80));
        setLayoutParams(LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT));

    }
}
