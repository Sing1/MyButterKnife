package sing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import sing.butterknife.BindView;
import sing.butterknife.OnClick;
import sing.mybutterknife.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (tv != null) {
            tv.setText("成功绑定");
        }
    }

    @OnClick(R.id.btn)
    public void click() {
        Toast.makeText(this, "点击成功", Toast.LENGTH_SHORT).show();
    }
}