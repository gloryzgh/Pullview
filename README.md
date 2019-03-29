# Pullview
可滑动的LinearLayout



有两种滑动模式margin 和 padding，可以在xml中设置

  <com.ghzhang.pullview.PullLayout
            android:orientation="vertical"
            app:style="margin"
            app:stretchHeight="200"
            android:background="@color/colorPrimaryDark"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>

stretchHeight 参数用来设置最大拉伸宽度，默认为PullLayout的高度

margin 效果图

![image](https://github.com/gloryzgh/Pullview/blob/master/b.gif)


padding 效果图

![image](https://github.com/gloryzgh/Pullview/blob/master/c.gif)

