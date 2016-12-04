IconLoadingView
=====
![](https://github.com/zjdyhant/IconLoadingView/blob/master/app/src/main/res/raw/iconloadingview.gif?raw=true)
##SCENE
The page loads the data asynchronously with multiple interfaces.
##HOW TO USE?
You can simply use the two methods.
###in the code
 * init
```java
mIconLoadingView1 = new IconLoadingView(this);
mIconLoadingView1.setForegroundProgressColor(Color.parseColor("#1AE66B"))
                .setBackgroundProgressColor(Color.parseColor("#4D2BD5")).setIcon(R.drawable.tiger);
```
 * show LoadingView
```java
mIconLoadingView1.attachToView(mTextView1,0,5,0,5);
```
 * hide LoadingView
```java
mIconLoadingView1.detachFromView();
```
###in the layout
 * init
```java
<com.mm.hant.iconloadingview.widget.IconLoadingView
            android:id="@+id/icon_loading_view3"
            android:layout_width="50dp"
            android:layout_height="50dp"
            app:BackgroundProgressColor="@color/color_divider"
            app:ForegroundProgressColor="@android:color/black"
            app:Icon="@drawable/tiger" />
```
 * using
```java
mIconLoadingView3.setVisibility(View.VISIBLE);
mIconLoadingView3.setVisibility(View.GONE);
```
