package com.example.prm391x_asm1_duytqfx11834;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class PhoneActivity extends AppCompatActivity {
    //Khai báo các view trên màn hình
    ImageView back;
    Button btnSetup;
    EditText edtPhoneNumber, edtDelay;
    RadioButton rdbHour, rdbMinute, rdbSecond;

    //Request code cấp quyền gọi điện
    static final int MY_PERMISSION_REQUEST_CODE_CALL_PHONE = 99999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        //Khởi tạo các view
        initViews();

        //Intent chuyển màn hình
        intentChangeActivity();

        //Yêu cầu cấp quyền
        askPermission();
    }

    /**
     * Phương thức initViews khởi tạo các view trên màn hình
     * */
    public void initViews() {
        //Ánh xạ các view
        back = findViewById(R.id.iv_back);
        btnSetup = findViewById(R.id.btnSetup);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtDelay = findViewById(R.id.edtDelay);
        rdbHour = findViewById(R.id.radioHour);
        rdbMinute = findViewById(R.id.radioMinute);
        rdbSecond = findViewById(R.id.radioSecond);

        //Tải hiệu ứng cho các view
        Animation btnAlpha = AnimationUtils.loadAnimation(this,R.anim.button_alpha);
        btnSetup.startAnimation(btnAlpha);
    }

    /**
     * Phương thức intentChangeActivity chuyển màn hình
     * */
    public void intentChangeActivity() {
        //Chuyển về màn hình home bằng back
        back.setOnClickListener(v -> {
            Intent intent = new Intent(PhoneActivity.this, MainActivity.class);
            startActivity(intent);
            //Hiệu ứng chuyển màn hình
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        });
    }

    /**
     * Phương thức checkPhoneDelay kiểm tra các trường thông tin gọi điện
     *
     * @param phoneNumber Số điện thoại cần gọi
     * @param delay Thời gian chờ gọi điện
     * @return Giá trị boolean là kết quả kiểm tra, true nếu các trường đều hợp lệ
     * */
    private boolean checkPhoneDelay(String phoneNumber, String delay) {
        if (phoneNumber.equals("")) {
            //Nếu số điện thoại chưa được nhập
            //Hiện thông báo
            Toast.makeText(this,"Please input phone first", Toast.LENGTH_LONG).show();
            return false;
        } else if (!phoneNumber.equals("5554") && (phoneNumber.length() != 10 || phoneNumber.charAt(0) != '0')) {
            //Nếu số điện thoại sai định dạng (khác 10 số, không có 0 ở đầu, trừ số điện thoại máy ảo để test)
            Toast.makeText(this,"The phone is not correct, please check!", Toast.LENGTH_LONG).show();
            return false;
        } else if (delay.equals("")) {
            //Nếu thời gian gửi trống
            Toast.makeText(this,"Please set time first", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Phương thức setDelayPhone cho phép thiết lập cuộc gọi
     * */
    private void setDelayPhone()  {
        //Thiết lập khi ấn nút Setup
        btnSetup.setOnClickListener(v -> {
            String phoneNumber = edtPhoneNumber.getText().toString();
            String delay = edtDelay.getText().toString();

            //Nếu các trường đều hợp lệ, tiến hành gọi điện
            if(checkPhoneDelay(phoneNumber, delay)) {
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

                //Gửi tin nhắn theo thời gian chờ
                callDelayPhone(phoneNumber, delayTime, timeType);
            }
        });
    }

    /**
     * Phương thức callDelayPhone cho phép gọi điện
     *
     * @param phoneNumber Số điện thoại cần gọi
     * @param delayTime Thời gian đợi để gọi
     * @param timeType Đơn vị thời gian
     * */
    private void callDelayPhone(String phoneNumber, int delayTime, String timeType) {
        //Tạo một Handler mới
        Handler handler = new Handler(Looper.getMainLooper());

        //Gọi điện sau một khoảng thời gian
        handler.postDelayed(() -> {
            //Dùng Implicit Intent gọi điện
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }, delayTime);

        //Chuyển về màn hình home
        Intent intent = new Intent(PhoneActivity.this, MainActivity.class);
        startActivity(intent);
        //Hiệu ứng chuyển màn hình
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        //Hiện thông báo
        Toast.makeText(this,"A call will be done after " + edtDelay.getText().toString() +
                " " + timeType, Toast.LENGTH_LONG).show();
    }

    /**
     * Phương thức askPermission hỏi người dùng cấp quyền gọi điện cho ứng dụng
     * */
    private void askPermission() {

        // Với API >= 23, cần hỏi người dùng để cấp quyền gọi điện cho ứng dụng
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //Kiểm tra xem đã được cấp quyền chưa
            int callPhonePermisson = ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

            //Nếu chưa được cấp quyền, tiến hành hiện hộp thoại để hỏi người dùng
            if (callPhonePermisson != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions( new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSION_REQUEST_CODE_CALL_PHONE);
                return;
            }
        }

        //Nếu API < 23 hoặc đã được cấp quyền
        this.setDelayPhone();
    }

    /**
     * Phương thức onRequestPermissionResult, kiểm tra kết quả người dùng trả lời trong hộp thoại
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == MY_PERMISSION_REQUEST_CODE_CALL_PHONE) {
            //Nếu người dùng đồng ý cấp quyền, lúc này phần tử đầu tiên của grantResuts là PERMISSION GRANTED
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Cho phép thiết lập tin nhắn
                this.setDelayPhone();
            }
            //Nếu người dùng không đồng ý cấp quyền
            else {
                //Chuyển về màn hình home
                Intent intent = new Intent(PhoneActivity.this, MainActivity.class);
                startActivity(intent);
                //Hiệu ứng chuyển màn hình
                overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                //Hiện thông báo
                Toast.makeText(this, "Please allow permission for using it", Toast.LENGTH_LONG).show();
            }
        }
    }
}