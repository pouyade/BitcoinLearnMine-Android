package pro.pouyasoft.btclearnmine.Ui.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pro.pouyasoft.btclearnmine.Data.Models.Category;
import pro.pouyasoft.btclearnmine.Helper.LayoutHelper;
import pro.pouyasoft.btclearnmine.Setting.AppSetting;
import pro.pouyasoft.btclearnmine.Ui.Adapter.ArticlesAdapter;

/**
 * Created by pouyadark on 8/3/18.
 */

public class TabFragment extends NightSupportFragment {
    Category category;
    RecyclerView listView;
    public TabFragment() {
    }

    public TabFragment setCategory(Category category) {
        this.category = category;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(getContext());
         listView =new RecyclerView(getContext());
        listView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listView.setAdapter(new ArticlesAdapter(getContext(),category.articles));
        listView.setBackgroundColor(0x00ffbf);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                listView.scrollToPosition(1);
            }
        },1000);

        frameLayout.addView(listView, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT,LayoutHelper.MATCH_PARENT));
        return frameLayout;

    }

    @Override
    public void onResume() {
        UpdateColor();
        super.onResume();
    }

    @Override
    public void UpdateColor() {

        if(listView == null)return;
        listView.getAdapter().notifyDataSetChanged();
        listView.setBackgroundColor(AppSetting.getNightMode()?0xff000000:0xffffffff);
    }
}