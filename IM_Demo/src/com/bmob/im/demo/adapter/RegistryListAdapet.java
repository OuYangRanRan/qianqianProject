package com.bmob.im.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.im.bean.BmobChatUser;

import com.bmob.im.demo.R;
import com.bmob.im.demo.adapter.base.BaseListAdapter;
import com.bmob.im.demo.adapter.base.ViewHolder;
import com.bmob.im.demo.bean.RegistryTimeBean;
import com.bmob.im.demo.util.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RegistryListAdapet extends BaseAdapter{
//签到 次数查询 的ListView的适配器
	List<RegistryTimeBean> list;
	Context context;
	public LayoutInflater mInflater;
	public RegistryListAdapet(Context context,List<RegistryTimeBean> list) {
	this.context=context;
	this.list=list;
	System.out.println("这是什么鬼 为什么不进来");
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
		view = LayoutInflater.from(context).inflate(R.layout.registry_item, null);
		} else {
		view = convertView;
		}
		TextView tv_name = (TextView) view.findViewById(R.id.registry_name);
		TextView tv_time=(TextView) view.findViewById(R.id.registry_time);
		TextView tv_count=(TextView) view.findViewById(R.id.registry_count);
		System.out.println(list.get(position).getCount()+"getCount");
		tv_count.setText(list.get(position).getCount()+"");
		tv_name.setText(list.get(position).getName());
		tv_time.setText(list.get(position).getCreatedAt().toString());
		System.out.println(list.get(position).toString()+"-------contract的值");
		return view;
		
	}
		
}
