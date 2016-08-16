package com.ssyijiu.verticalscrolltextlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VerticalScrollTextLayout scroll_layout;
    private VerticalScrollTextLayout scroll_layout2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scroll_layout = (VerticalScrollTextLayout) findViewById(R.id.scroll_layout);
        scroll_layout2 = (VerticalScrollTextLayout) findViewById(R.id.scroll_layout2);
        List<String> textList = Arrays.asList(new String[]{
                "春眠不觉晓",
                "处处闻啼鸟",
                "夜来风雨声",
                "花落知多少"
        });

        scroll_layout.setTextList(textList).setStaticTime(1000).setScrollTime(500);
        scroll_layout.startScroll();

        scroll_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, scroll_layout.getIndexText(), Toast.LENGTH_SHORT).show();
            }
        });


        scroll_layout2.setTextList(Arrays.asList(new String[]{
                "春眠不觉晓 处处闻啼鸟 夜来风雨声 花落知多少",
                "床前明月光 疑是地上霜 举头望明月 低头思故乡",
        }));
        scroll_layout2.startScroll();


    }

    @Override
    protected void onDestroy() {
        scroll_layout.stopScroll();
        scroll_layout2.stopScroll();
        super.onDestroy();
    }
}
