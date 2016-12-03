IconLoadingView
=====
![](https://github.com/zjdyhant/IconLoadingView/blob/master/app/src/main/res/raw/iconloadingview.gif?raw=true)
###scene
The page loads the data asynchronously with multiple interfaces.
###how to use?
>in the layout
```java
<RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:gravity="center">

        <TextView
            android:id="@+id/txt_3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

        <com.mm.hant.iconloadingview.widget.IconLoadingView
            android:id="@+id/icon_loading_view3"
            android:layout_width="50dp"
            android:layout_height="50dp"
            app:BackgroundProgressColor="@color/color_divider"
            app:ForegroundProgressColor="@android:color/black"
            app:Icon="@drawable/tiger" />
    </RelativeLayout>
```
