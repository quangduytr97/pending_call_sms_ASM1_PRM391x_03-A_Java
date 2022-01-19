package com.example.prm391x_asm1_duytqfx11834;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {
    //Khai báo các view trên màn hình
    ImageView back;
    Button btnSetup;
    EditText edtMessage, edtDelay;
    RadioButton rdbHour, rdbMinute, rdbSecond;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //Khởi tạo các view
        initViews();

        //Intent chuyển màn hình
        intentChangeActivity();

        //Đặt báo thức
        setAlarm();
    }

    /**
     * Phương thức initViews khởi tạo các view trên màn hình
     * */
    private void initViews() {
        //Ánh xạ các view
        back = findViewById(R.id.iv_back);
        btnSetup = findViewById(R.id.btnSetup);
        edtMessage = findViewById(R.id.edtMessage);
        edtDelay = findViewById(R.id.edtDelay);
        rdbHour = findViewById(R.id.radioHour);
        rdbMinute = findViewById(R.id.radioMinute);
        rdbSecond = findViewById(R.id.radioSecond);


        //Tải hiệu ứng cho button Setup
        Animation btnAlpha = AnimationUtils.loadAnimation(this,R.anim.button_alpha);
        btnSetup.startAnimation(btnAlpha);
    }

    /**
     * Phương thức intentChangeActivity chuyển màn hình
     * */
    private void intentChangeActivity() {
        //Chuyển về màn hình home bằng back
        back.setOnClickListener(v -> {
            Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
            startActivity(intent);
            //Hiệu ứng chuyển màn hình
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });
    }

    /**
     * Phương thức checkAlarm kiểm tra các thông tin trong báo thức
     *
     * @param delay Thời gian hẹn báo thức
     * @return Giá trị boolean là kết quả kiểm tra, true nếu các trường đều hợp lệ
     * */
    private boolean checkAlarm(String delay) {
        if (delay.equals("")) {
            //Nếu thời gian hẹn báo thức trống
            Toast.makeText(this,"Please set time first", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Phương thức setAlarm cho phép thiết lập báo thức
     * */
    private void setAlarm()  {
        //Thiết lập khi ấn nút Setup
        btnSetup.setOnClickListener(v -> {
            String delay = edtDelay.getText().toString();

            //Nếu các trường đều hợp lệ, tiến hành đặt báo thức
            if(checkAlarm(delay)) {
                //Đổi thời gian chờ ra mili giây
                int delayTime;
                String timeType;
                if (rdbHour.isChecked()) {
                    //Nếu chọn đơn vị thời gian là giờ
                    delayTime =  (int) (Double.parseDouble(delay) * 3600000);
                    timeType = "hour";
                } else if (rdbMinute.isChecked()) {
                    //Nếu chọn đơn vị thời gian là phút
                    delayTime =  (int) (Double.parseDouble(delay) * 60000);
                    timeType = "minute";
                } else {
                    //Nếu chọn đơn vị thời gian là giây
                    delayTime = (int) (Double.parseDouble(delay) * 1000);
                    timeType = "second";
                }

                //Đặt báo thức
                startAlarm(delayTime, timeType);
            }
        });
    }

    /**
     * Phương thức startAlarm cho phép bắt đầu báo thức
     *
     * @param delayTime Thời gian đợi để gửi tin nhắn
     * @param timeType Đơn vị thời gian
     * */
    private void startAlarm(int delayTime, String timeType) {
        //Tạo một Handler mới
        Handler handler = new Handler(Looper.getMainLooper());

        //Đặt thời gian hiện màn hình báo thức
        handler.postDelayed(() -> {
            //Dùng Intent gọi màn hình báo thức
            Intent intent = new Intent(AlarmActivity.this, AlarmResultActivity.class);
            //Truyền nội dung báo thức
            String alarmContent = edtMessage.getText().toString();
            intent.putExtra("content", alarmContent);
            startActivity(intent);
        }, delayTime);

        //Chuyển về màn hình home
        Intent intent = new Intent(AlarmActivity.this, MainActivity.class);
        startActivity(intent);
        //Hiệu ứng chuyển màn hình
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        //Hiện thông báo
        Toast.makeText(this,"Wake up after " + edtDelay.getText().toString() +
                " " + timeType, Toast.LENGTH_LONG).show();
    }
}