package com.example.prm391x_asm1_duytqfx11834;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Khai báo 3 button trên màn hình
    Button btnMessage;
    Button btnPhone;
    Button btnAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Khởi tạo các view
        initViews();

        //Intent chuyển màn hình
        intentChangeActivity();
    }

    /**
     * Phương thức initViews khởi tạo các view trên màn hình
     * */
    private void initViews() {
        //Ánh xạ các view
        btnMessage = findViewById(R.id.btnMesseage);
        btnPhone = findViewById(R.id.btnPhone);
        btnAlarm = findViewById(R.id.btnAlarm);

        //Tải hiệu ứng cho các view
        Animation btnAlpha = AnimationUtils.loadAnimation(this,R.anim.button_alpha);
        btnMessage.startAnimation(btnAlpha);
        btnPhone.startAnimation(btnAlpha);
        btnAlarm.startAnimation(btnAlpha);
    }

    /**
     * Phương thức intentChangeActivity chuyển màn hình
     * */
    private void intentChangeActivity() {
        //Chuyển qua màn hình nhắn tin
        btnMessage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SMSActivity.class);
            startActivity(intent);
            //Hiệu ứng chuyển màn hình
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

        //Chuyển qua màn hình gọi điện
        btnPhone.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
            startActivity(intent);
            //Hiệu ứng chuyển màn hình
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });

        //Chuyển qua màn hình báo thức
        btnAlarm.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlarmActivity.class);
            startActivity(intent);
            //Hiệu ứng chuyển màn hình
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });
    }
}