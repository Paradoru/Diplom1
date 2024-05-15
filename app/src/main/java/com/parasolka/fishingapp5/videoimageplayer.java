package com.parasolka.fishingapp5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.parasolka.fishingapp5.ui.main.SectionsPagerAdapter;
import com.parasolka.fishingapp5.databinding.ActivityVideoimageplayerBinding;
public class videoimageplayer extends AppCompatActivity {
    private ActivityVideoimageplayerBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoimageplayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;

        tabs.setupWithViewPager(viewPager);
    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}