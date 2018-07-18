package com.uuzo.gcjs.Main;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.uuzo.gcjs.Adapter.MainAdapter;

import com.uuzo.gcjs.BaseTemplate.BaseLazyFragment;
import com.uuzo.gcjs.R;

import java.util.List;

/**
 * Created by handsome on 2016/4/7.
 */
public class MessageFragment extends BaseLazyFragment implements ViewPager.OnPageChangeListener{
//    private MessageTabButton mtb_notice,mtb_office,mtb_assignment,mtb_system;
    private ViewPager vp_message;
    private List<Fragment> list;
    private MainAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public void initViews() {
//        mtb_notice=findView(R.id.message_notice);
//        mtb_assignment=findView(R.id.message_assignment);
//        mtb_office=findView(R.id.message_office);
//        mtb_system=findView(R.id.message_system);
//        vp_message=findView(R.id.vp_message);
    }

    @Override
    public void initListener() {
//        setOnClick(mtb_notice);
//        setOnClick(mtb_assignment);
//        setOnClick(mtb_system);
//        setOnClick(mtb_office);
//        vp_message.setOnPageChangeListener(this);
    }

    @Override
    public void initData() {
        setTitle("消息");
        //初始化碎片
        initFragments();
    }

    @Override
    public void processClick(View v) {
//        switch (v.getId()) {
//            case R.id.message_notice:
//                selectPager(0);
//                break;
//            case R.id.message_office:
//                selectPager(1);
//                break;
//            case R.id.message_assignment:
//                selectPager(2);
//                break;
//            case R.id.message_system:
//                selectPager(3);
//                break;
//        }
    }
    /**
     * 初始化碎片  以后开注解
     */
    private void initFragments() {
//        list = new ArrayList<>();
//        list.add(new MessageCompanyNoticeFragment());
//        list.add(new MessageOfficeFragment());
//        list.add(new MessageAssignmentFragment());
//        list.add(new MessageSystemFragment());
//        adapter = new MainAdapter(getActivity().getSupportFragmentManager(), list);
//        vp_message.setAdapter(adapter);
//        selectPager(0);
    }
    public void selectPager(int position){
//        vp_message.setCurrentItem(position);
//        mtb_notice.isSelected(position==0?true:false);
//        mtb_system.isSelected(position==3?true:false);
//        mtb_office.isSelected(position==1?true:false);
//        mtb_assignment.isSelected(position==2?true:false);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {
        selectPager(position);
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
