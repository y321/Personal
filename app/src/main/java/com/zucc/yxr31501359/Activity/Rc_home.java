package com.zucc.yxr31501359.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import com.zucc.yxr31501359.R;
import com.zucc.yxr31501359.calendar.CalendarView;
import com.zucc.yxr31501359.calendar.ChildModel;
import com.zucc.yxr31501359.calendar.ContactsInfoAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by 45773 on 2017-07-07.
 */

public class Rc_home extends Fragment {
    private CalendarView calendar;
    private ImageButton calendarLeft;
    private TextView calendarCenter;
    private ImageButton calendarRight;
    private SimpleDateFormat format;
    private ExpandableListView mListView;
    private List<String> group;           //组列表
    private List<List<ChildModel>> child;     //子列表
    private ContactsInfoAdapter adapter;  //数据适配器
    private List<Integer> spotList;
//    private Context c ;
//    public Rc_home(Context context){
//        Context c = context;
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_rc, container, false);

        spotList = new ArrayList<Integer>();
        group = new ArrayList<String>();
        child = new ArrayList<List<ChildModel>>();
        format = new SimpleDateFormat("yyyy-MM-dd");
        //获取日历控件对象
        calendar = (CalendarView)view.findViewById(R.id.calendar);
        mListView = (ExpandableListView)view. findViewById(R.id.list);
        calendar.setSelectMore(false); //单选

        calendarLeft = (ImageButton) view.findViewById(R.id.calendarLeft);
        calendarCenter = (TextView) view.findViewById(R.id.calendarCenter);
        calendarRight = (ImageButton) view.findViewById(R.id.calendarRight);
        adapter = new ContactsInfoAdapter(group, child, MainActivity.context, getStrings(calendar.getYearAndmonthAndDate()), calendar.getNowWeek());
        mListView.setAdapter(adapter);
        setCalendar();
        initializeData();

        return view;
    }

    //数据初始化
    private void initializeData() {
        group.clear();
        child.clear();
        int maxDayNum = calendar.getMaxDayNum();    //当前月份总天数
        for (int i = 1; i <= maxDayNum; i++) {
            List<ChildModel> childitem = getChildModels(i);
            group.add(i + "");    //日期
            child.add(childitem);
        }
        //设置默认全部打开
        for (int i = 0; i < group.size(); i++) {
            mListView.expandGroup(i);
        }

        mListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                if(child.get(groupPosition).size() == childPosition + 1){
                    spotList.add(Integer.valueOf(group.get(groupPosition)) - 1 + calendar.getcurStartIndex());
                    calendar.change(spotList);
                   // Toast.makeText(MainActivity, "添加了一个日程" , Toast.LENGTH_SHORT).show();
                }else {
                   // Toast.makeText(MainActivity, "hah" + childPosition , Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        adapter.notifyDataSetChanged();
        mListView.post(new Runnable() { //ListView数据没有更改之前，setselection()方法调用效果一切正常；而填充数据更改之后，同样的代码片段却莫名其妙无效了。
            @Override
            public void run() {
                mListView.setSelectedGroup(calendar.getNowDate() - 1);  //设置初始listview的显示位置，为本日
            }
        });
    }

    /**
     * 构造child里面的数据
     *
     * @param i
     * @return
     */
    private List<ChildModel> getChildModels(int i) {
        List<ChildModel> childitem = new ArrayList<ChildModel>();
        ChildModel model;
        for (int j = 0; j < 3; j++) {
            model = new ChildModel();
            model.setId(i + "");
            model.setTime("17：30");
            model.setContent("假装这里有内容" + j);
            childitem.add(model);
        }
        //在末尾加入新建日程条目
        model = new ChildModel();
        model.setContent("新建日程");
        childitem.add(model);
        return childitem;
    }


    private void setCalendar() {
        try {
            //设置日历日期
            Date date = format.parse("2015-01-01");
            calendar.setCalendarData(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        calendar.change(num, true);

        setHeadDate(calendar.getYearAndmonthAndDate());
        calendarLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击上一月 同样返回年月
                String leftYearAndmonth = calendar.clickLeftMonth();
                clickCalendar(leftYearAndmonth);
            }
        });

        calendarRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击下一月
                String rightYearAndmonth = calendar.clickRightMonth();
                clickCalendar(rightYearAndmonth);
            }
        });

        //设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
        calendar.setOnItemClickListener(new CalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(Date selectedStartDate,
                                    Date selectedEndDate, Date downDate) {
                setHeadDate(format.format(downDate));   //头上的日期显示更换
                String[] ya = getStrings(format.format(downDate));  //获取按下的日期，目的是获取“日”，与mListView的下标对比，得到要显示的位置
                String[] nowDate = getStrings(calendar.getYearAndmonthAndDate());
                if(ya[1].equals(nowDate[1])){   //用于限制本页显示灰色的上一月和下一月，点击后不执行以下代码
                    mListView.setSelectedGroup(Integer.valueOf(ya[2]) - 1); //日期比下标大1，因此这里减1，可定位到正确位置
                }
            }
        });
    }

    private void clickCalendar(String leftYearAndmonth) {
        initializeData();
        setHeadDate(leftYearAndmonth);
        spotList.clear();
        calendar.change(spotList);
    }

    //获取日历中年月 ya[0]为年，ya[1]为月,ya[1]为日
    private void setHeadDate(String date) {
        String[] ya = getStrings(date);
        calendarCenter.setText(ya[0] + "年" + ya[1] + "月" + ya[2] + "日");
    }

    private String[] getStrings(String date) {
        return date.split("-");
    }
}

