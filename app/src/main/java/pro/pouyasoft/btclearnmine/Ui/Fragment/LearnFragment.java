package pro.pouyasoft.btclearnmine.Ui.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import pro.pouyasoft.btclearnmine.Data.DBManager.Database;
import pro.pouyasoft.btclearnmine.Data.Models.Category;
import pro.pouyasoft.btclearnmine.R;
import pro.pouyasoft.btclearnmine.Setting.AppSetting;


public class LearnFragment extends NightSupportFragment {

//    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private boolean attached = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_main,container,false);
//        toolbar = view.findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(AppSetting.getNightMode()?getResources().getColor(R.color.colorPrimarynight):getResources().getColor(R.color.colorPrimary));

        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
//
//
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        attached = true;
    }

    @Override
    public void UpdateColor() {
//        if(adapter==null||adapter.mFragmentList==null)return;
      if(!attached)return;
        try {
            tabLayout.setSelectedTabIndicatorColor(AppSetting.getNightMode()?getResources().getColor(R.color.colorPrimarynight):getResources().getColor(R.color.colorPrimary));

        }catch (Exception e){

        }
        Log.e("t","t");
//        List<Fragment> fragments = adapter.mFragmentList;
//        for(int i=0;i<fragments.size();i++){
//            ((NightSupportFragment)fragments.get(i)).UpdateColor();
//        }
//        adapter.notifyDataSetChanged();
//        adapter.mFragmentList.clear();
        adapter.notifyDataSetChanged();
//        viewPager.removeAllViews();
//        setupViewPager(viewPager);
//        viewPager.setCurrentItem(0,false);
    }

    private void setupViewPager(ViewPager viewPager) {
         adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        ArrayList<Category> arrayList = Database.getCategorys();
        for(int i=0;i<arrayList.size();i++) {
            NightSupportFragment fragment = new TabFragment().setCategory(arrayList.get(i));
            fragment.UpdateColor();
            adapter.addFragment(fragment,arrayList.get(i).title);
        }
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((NightSupportFragment)adapter.getItem(position)).UpdateColor();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public int getItemPosition(Object object) {
            NightSupportFragment f = (NightSupportFragment ) object;
            if (f != null) {
                f.UpdateColor();
            }
            return super.getItemPosition(object);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}