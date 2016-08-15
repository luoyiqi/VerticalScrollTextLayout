#VerticalScrollTextLayout

TextView垂直滚动布局。

> 目前Github的TextView垂直滚动大多是通过重写onDraw方法，使用Canvas重新绘制文字实现。
> 但是这样做有一个致命的缺点:在重绘后TextView的自动折行、maxLines、ellipsize等属性将会失效。

##原理

使用两个TextView不停的交替展示文字，形成滚动效果。

##使用方法

###xml

```html
<com.ssyijiu.verticalscrolltextlayout.VerticalScrollTextLayout
        android:layout_marginTop="30dp"
        android:id="@+id/scroll_layout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <!--
            VerticalScrollTextLayout使用两个TextView交替展示动画，
            请确保下面的两个TextView的代码一模一样。
        -->
        <TextView
            android:textColor="#000"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:textSize="23sp" />

        <TextView
            android:textColor="#000"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="center"
            android:textSize="23sp" />
    </com.ssyijiu.verticalscrolltextlayout.VerticalScrollTextLayout>
```

###代码

```
scroll_layout.setTextList(textList) // 设置滚动文字
             .setStaticTime(1000)   // 设置停留时间
             .setScrollTime(500);   // 设置滚动时间
scroll_layout.startScroll();        // 开始滚动

@Override
protected void onDestroy() {
    // 停止滚动
    scroll_layout.stopScroll();
    super.onDestroy();
}
```
