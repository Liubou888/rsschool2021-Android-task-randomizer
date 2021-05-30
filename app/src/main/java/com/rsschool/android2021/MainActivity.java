package com.rsschool.android2021;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FragmentsInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment, "FIRST");
        transaction.commit();
        // TODO: invoke function which apply changes of the transaction
    }

    private void openSecondFragment(int min, int max) {
        // TODO: implement it
        final Fragment secondFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, secondFragment, "SECOND");
        transaction.commit();
    }

    @Override
    public void onGenerate(int min, int max) {
        openSecondFragment(min, max);
    }

    @Override
    public void onBackToFirst(int prevRes) {
        openFirstFragment(prevRes);
    }

    @Override
    public void onBackPressed() {
        SecondFragment secFragment = (SecondFragment) getSupportFragmentManager().findFragmentByTag("SECOND");
        if (secFragment != null && secFragment.isVisible()) {
            // add your code here
            onBackToFirst(secFragment.getResult());
        } else super.onBackPressed();
    }
}