package yasheth.charusatcoin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class Payment extends AppCompatActivity {

    String vendor,buyer;
    public final static int WIDTH=700;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.payment_layout);
        Bundle bundle=getIntent().getExtras();
        int position=bundle.getInt("value");
        TextView info=(TextView)findViewById(R.id.vendorinfo);
        TextView name=(TextView)findViewById(R.id.vendorname);
        ImageView cover=(ImageView)findViewById(R.id.vendorcover);
        if(position==1){
            vendor="V01IB";
            name.setText("ICEBERG");
            cover.setImageResource(R.drawable.iceberg);
            info.setText("IceBerg is first and one of the most famous shop on campus. From grill sandwiches, burgers, slushes and shakes Its a complete Bonanza for you. Its affordable and delicious. Plan your visit soon..!");
        }
        else if(position==2){
            vendor="V02NC";
            name.setText("NESCAFE");
            cover.setImageResource(R.drawable.nescafe);
            info.setText("Nesccafes refreshing coffee is all you need to calm your nerves. Hot,Cold,Espresso or Hot Chocolate whatever you like will be available. Also Have a break with KitKat or Maggi. Plan your visit soon..!");
        }
        else if(position==3){
            vendor="V03HC";
            name.setText("HARI OM CANTEEN");
            cover.setImageResource(R.drawable.hariom);
            info.setText("Offers you a great variety of options at very nominal rates. You can have Punjabi, Chinese, Gujarati, Fix dish, snacks etc. Chole-Puri are most famous and delicious over here. Plan your visit soon..!");
        }
        else if(position==4){
            vendor="V04SS";
            name.setText("STUDENT STORE");
            cover.setImageResource(R.drawable.collegestore);
            info.setText("Every need of student is satisfied here. Everything that a student needs is available here from a small pen to large drawing board. You can get your files, indexes and certificates here which are primary needs for any submission. Plan your visit soon..!");
        }
        else if(position==5){
            vendor="V05DC";
            name.setText("DANNYS COFFEE BAR");
            cover.setImageResource(R.drawable.dannys);
            info.setText("A well known franchise of famous coffee bar of Ahmedabad serves with you with different types of sandwiches,pizzas,garlic bread,cold bournvita,etc. Cold coffee here is a must try for everyone at least once in lifetime. Plan your visit soon..!");
        }
        else if(position==6){
            vendor="V06XC";
            name.setText("Xerox Centre");
            cover.setImageResource(R.drawable.xerox);
            info.setText("A place where you can get your any document printed, copied, spiral binded etc. Useful place for every student at time of submissions. Plan your visit soon..!");
        }
        else if(position==7){
            vendor="V07AP";
            name.setText("AMUL PARLOUR");
            cover.setImageResource(R.drawable.amul);
            info.setText("Just located beside Shreeji canteen so you can have something for your dessert after your lunch. You can have a variety of ice-creams of havmor,amul,etc and take a chill with your friends. Plan your visit soon..!");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    public void onclick(View v) {
        if(v.getId()==R.id.btnpay) {
            progress = new ProgressDialog(this);
            progress.setTitle("Please Wait!!");
            progress.setMessage("Generating QRCode for Secured Transaction");
            progress.setCancelable(true);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.show();
            // create thread to avoid ANR Exception
            Thread t = new Thread(new Runnable() {
            public void run() {
            final TextView tvmsg=(TextView)findViewById(R.id.tvmsg);
            EditText amount=(EditText)findViewById(R.id.et_amount);
            final ImageView qr=(ImageView)findViewById(R.id.payqr);
            final String qrinfo=amount.getText().toString()+vendor;
                    try {
                        synchronized (this) {
                            wait(5000);
                        // runOnUiThread method used to do UI task in main thread.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Bitmap bitmap = null;
                                        bitmap = encodeAsBitmap(qrinfo);
                                        qr.setImageBitmap(bitmap);
                                        tvmsg.setVisibility(View.VISIBLE);
                                        progress.cancel();
                                    } catch (WriterException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

    }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 700, 0, 0, w, h);
        return bitmap;
    }
}
