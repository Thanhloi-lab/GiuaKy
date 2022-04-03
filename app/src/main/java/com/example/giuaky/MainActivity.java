package com.example.giuaky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.giuaky.fragment.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControl();
        setEvent();

    }

    private void setEvent() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:{
                        bottomNavigationView.getMenu().findItem(R.id.menu_cong_nhan).setChecked(true);
                        break;
                    }
                    case 1:{
                        bottomNavigationView.getMenu().findItem(R.id.menu_san_pham).setChecked(true);
                        break;
                    }
                    case 2:{
                        bottomNavigationView.getMenu().findItem(R.id.menu_cham_cong).setChecked(true);
                        break;
                    }
                    case 3:{
                        bottomNavigationView.getMenu().findItem(R.id.menu_thong_ke).setChecked(true);
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_cong_nhan:{
                        viewPager.setCurrentItem(0);
                        break;
                    }
                    case R.id.menu_san_pham:{
                        viewPager.setCurrentItem(1);
                        break;
                    }
                    case R.id.menu_cham_cong:{
                        viewPager.setCurrentItem(2);
                        break;
                    }
                    case R.id.menu_thong_ke:{
                        viewPager.setCurrentItem(3);
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void setControl() {
        viewPager = findViewById(R.id.flFragment);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
}