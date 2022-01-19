package com.example.prm391x_asm1_duytqfx11834;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class AlarmResultActivity extends AppCompatActivity {
    //Khai báo các view trên màn hình
    Button btnCancel;
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_result);

        //Khởi tạo các view
        initViews();

        //Intent chuyển màn hình
        intentChangeActivity();
    }

    @Override
    protected void onStart (){
        super.onStart();
        //Hiển thị nội dung báo thức
        Intent intent = getIntent();
        String alarmContent = intent.getStringExtra("content");
        tvContent.setText(alarmContent);
    }

    /**
     * Phương thức initViews khởi tạo các view trên màn hình
     * */
    private void initViews() {
        //Ánh xạ các view
        btnCancel = findViewById(R.id.btnCancel);
        tvContent = findViewById(R.id.alarmContent);

        //Tải hiệu ứng cho button Cancel
        Animation btnAlpha = AnimationUtils.loadAnimation(this,R.anim.button_alpha);
        btnCancel.startAnimation(btnAlpha);
    }

    /**
     * Phương thức intentChangeActivity chuyển màn hình
     * */
    private void intentChangeActivity() {
        //Chuyển về màn hình home bằng Cancel
        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(AlarmResultActivity.this, MainActivity.class);
            startActivity(intent);
            //Hiệu ứng chuyển màn hình
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });
    }
}