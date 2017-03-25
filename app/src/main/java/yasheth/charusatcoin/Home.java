package yasheth.charusatcoin;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int contributors;
    private int listcount=0;
    ArrayList<String> CList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);
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
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        String name = prefs.getString("name", "No name defined");
        System.out.println(name);
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

        if (id == R.id.nav_shop) {
            tabLayout.setScrollPosition(0,0,true);
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_history) {
            tabLayout.setScrollPosition(1,0,true);
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_wallet) {
            tabLayout.setScrollPosition(2,0,true);
            viewPager.setCurrentItem(2);
        } else if (id == R.id.nav_bill) {
            tabLayout.setScrollPosition(3,0,true);
            viewPager.setCurrentItem(3);
        } else if (id == R.id.nav_reminder) {
            tabLayout.setScrollPosition(4,0,true);
            viewPager.setCurrentItem(4);
        }else if (id == R.id.nav_settings) {
            return true;
        }else if (id == R.id.nav_logout) {
            alertMessage();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onclick(View v){

        if(v.getId()==R.id.btniceberg) {
            Intent i=new Intent(getApplicationContext(),Payment.class);
            i.putExtra("value",1);
            startActivity(i);
        }
        else if(v.getId()==R.id.btnnescafe) {
            Intent i=new Intent(getApplicationContext(),Payment.class);
            i.putExtra("value",2);
            startActivity(i);
        }
        else if(v.getId()==R.id.btnhariom) {
            Intent i=new Intent(getApplicationContext(),Payment.class);
            i.putExtra("value",3);
            startActivity(i);
        }else if(v.getId()==R.id.btncollegestore) {
            Intent i=new Intent(getApplicationContext(),Payment.class);
            i.putExtra("value",4);
            startActivity(i);
        }else if(v.getId()==R.id.btndannys) {
            Intent i=new Intent(getApplicationContext(),Payment.class);
            i.putExtra("value",5);
            startActivity(i);
        }else if(v.getId()==R.id.btnxerox) {
            Intent i=new Intent(getApplicationContext(),Payment.class);
            i.putExtra("value",6);
            startActivity(i);
        }else if(v.getId()==R.id.btnamul) {
            Intent i=new Intent(getApplicationContext(),Payment.class);
            i.putExtra("value",7);
            startActivity(i);
        }else if(v.getId()==R.id.btn_addperson) {
            addPerson();
        }else if(v.getId()==R.id.btnsplit) {
            splitBill();
        }
        else if(v.getId()==R.id.btnreset) {
            resetBill();
        }
        else if(v.getId()==R.id.btnrefresh) {
            Fragment w = getSupportFragmentManager().findFragmentByTag("android:switcher:2131493005:2");
            if (w != null) {
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.detach(w);
                fragmentTransaction.attach(w);
                fragmentTransaction.commit();
            }
        }
    }

    private void splitBill() {
        int flag=0;
        EditText etamount=(EditText)findViewById(R.id.et_splitamount);
        EditText etcount=(EditText)findViewById(R.id.et_contributors);
        EditText etperson=(EditText)findViewById(R.id.et_personi);

        if(etamount.getText().toString().equals("")){
            etamount.setError("Please enter amount to split");
            flag++;
        }
        if(etcount.getText().toString().equals("")){
            etcount.setError("Please enter number of Contributors");
            flag++;
        }

        if(flag==0) {
            contributors = Integer.valueOf(etcount.getText().toString().trim());
            if (listcount != contributors) {
                etperson.setError("Add all contributors to list");
            } else {
                Double amount = Double.valueOf(etamount.getText().toString().trim());
                Integer count = Integer.valueOf(etcount.getText().toString().trim());
                Double perPerson = amount / count;
                System.out.println("Per person Cost is : Rs " + perPerson);
                Toast.makeText(getApplicationContext(), "Split per person is Rs "+perPerson, Toast.LENGTH_LONG).show();
                resetBill();
            }
        }

    }

    private void resetBill() {
        EditText amount=(EditText)findViewById(R.id.et_splitamount);
        EditText count=(EditText)findViewById(R.id.et_contributors);
        EditText person=(EditText)findViewById(R.id.et_personi);
        TextView personList=(TextView)findViewById(R.id.tv_clist);
        count.setText("");
        person.setText("");
        personList.setText("");
        amount.setText("");
        contributors=0;
        listcount=0;
    }

    private void addPerson() {
        int flag=0;
        EditText etamount=(EditText)findViewById(R.id.et_splitamount);
        EditText etcount=(EditText)findViewById(R.id.et_contributors);
        EditText etperson=(EditText)findViewById(R.id.et_personi);
        //
        TextView personList=(TextView)findViewById(R.id.tv_clist);

        if(etamount.getText().toString().equals("")){
            etamount.setError("Please enter amount to split");
            flag++;
        }
        if(etcount.getText().toString().equals("")){
            etcount.setError("Please enter number of Contributors");
            flag++;
        }
        String name=etperson.getText().toString();
        if(name.equals("")){
            etperson.setError("Please enter name of Contributor");
        }
        if(flag==0) {
            contributors = Integer.valueOf(etcount.getText().toString().trim());
            if (listcount == contributors) {
                System.out.println("Full");
                Toast.makeText(getApplicationContext(), "Contributors List Full", Toast.LENGTH_LONG).show();
            } else {
                personList.setText(personList.getText().toString() + "\n" + name);
                listcount++;
                CList.add(name);
                System.out.println("Person Added");

            }
        }

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Shop(), "SHOP");
        adapter.addFragment(new History(), "HISTORY");
        adapter.addFragment(new Wallet(), "WALLET");
        adapter.addFragment(new Bill(), "BILL");
        adapter.addFragment(new Reminder(), "REMINDER");
        viewPager.setAdapter(adapter);
    }
    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            { switch (which){
                case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                    Toast.makeText(getApplicationContext(), "Successfully Logged Out", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Home.this, LogIn.class);
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



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
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