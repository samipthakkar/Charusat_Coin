package yasheth.charusatcoin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Vendor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ZXingScannerView.ResultHandler {

    ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ZXingScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendorhome_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            alertMessage();        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        if (id == R.id.nav_transaction) {
            tabLayout.setScrollPosition(0,0,true);
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_history) {
            tabLayout.setScrollPosition(1,0,true);
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_wallet) {
            tabLayout.setScrollPosition(2,0,true);
            viewPager.setCurrentItem(2);
        } else if (id == R.id.nav_settings) {
            return true;
        }else if (id == R.id.nav_logout) {
            alertMessage();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onclick(View v){
        if(v.getId()==R.id.btnscan) {
            mScannerView=new ZXingScannerView(this);
            setContentView(mScannerView);
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }
    }

    public void onPause(){
        super.onPause();
        mScannerView.stopCamera();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Transaction(), "TRANSACTION");
        adapter.addFragment(new History(), "HISTORY");
        adapter.addFragment(new Wallet(), "WALLET");
        viewPager.setAdapter(adapter);
    }
    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            { switch (which){
                case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                    Toast.makeText(getApplicationContext(), "Successfully Logged Out", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Vendor.this, LogIn.class);
                    startActivity(i);
                    finish();
                    break;
                case DialogInterface.BUTTON_NEGATIVE: // No button clicked do nothing
                    break;
            }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Log Out?") .setPositiveButton("Yes", dialogClickListener) .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    public void handleResult(Result result) {
        String amount=result.getText().toString();
        int i=amount.indexOf("V",0);
        final String vendor=amount.substring(i,amount.length());
        amount=amount.substring(0,i);
        System.out.println(vendor+"  "+amount);
        CharSequence option[] = new CharSequence[] {"Accept", "Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Accept Rs "+amount+"\nPaymentId "+result.getText().toString());
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println(which);
                if(which==0){
                    SharedPreferences prefs = getSharedPreferences("user", Context.MODE_PRIVATE);
                    final String name = prefs.getString("name", "No name defined");
                    if(name.equals(vendor)) {
                        System.out.println("MAKE CHANGES IN DB");
                        Toast.makeText(getApplicationContext(), "Amount ACCEPTED", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Vendor.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Payment Failed. Wrong Vendor!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), Vendor.class));
                    }
                }
                else if(which==1){
                    Toast.makeText(getApplicationContext(), "Cancelled Transaction" , Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Vendor.class));

                }
            }
        });
        AlertDialog alertDialog=builder.create();

        alertDialog.show();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.v("gI","Get Item "+position);
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
            Log.v("gpt","Get Page Title");
            return mFragmentTitleList.get(position);
        }
    }
}