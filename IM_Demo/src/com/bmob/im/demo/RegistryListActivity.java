package com.bmob.im.demo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

import com.bmob.im.demo.adapter.RegistryListAdapet;
import com.bmob.im.demo.bean.RegistryBean;
import com.bmob.im.demo.bean.RegistryTimeBean;
import com.bmob.im.demo.config.BmobUserInfo;

import android.R.menu;
import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RegistryListActivity extends Activity {
//	Handler handler=new Handler(){
//		public void handleMessage(android.os.Message msg) {
//			if (msg.what==1) {
//			}
//			
//		};
//	};
	private ListView listView;
	ArrayList<RegistryTimeBean> list=new ArrayList<RegistryTimeBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registry_list);
		 queryPersonByObjectId();
		listView=(ListView) findViewById(R.id.registry_list);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.registry_list, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private  void queryPersonByObjectId() {
		BmobQuery<RegistryBean> bmobQuery = new BmobQuery<RegistryBean>();
		BmobUserManager bmobUserManager=BmobUserManager.getInstance(this);
//		bmobQuery.addWhereContains("objectId",bmobUserManager.getCurrentUserObjectId());
		bmobQuery.findObjects(RegistryListActivity.this,new FindListener<RegistryBean>() {

			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(RegistryListActivity.this,"查找失败",Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(List<RegistryBean> object) {
				int i=1;
				for (RegistryBean registryBean: object) {
		               //获得playerName的信息
					System.out.println("得到数据");
					RegistryTimeBean registryTimeBean=new RegistryTimeBean();
					registryTimeBean.setCount(i++);
					registryTimeBean.setCreatedAt(registryBean.getCreatedAt());
					registryTimeBean.setName(registryBean.getName());
					//获得数据的objectId信息
					System.out.println("得到数据"+registryTimeBean.getName()+registryTimeBean.getCreatedAt());
					System.out.println(registryBean.toString()+"---------registryBean的 toString方法");
					list.add(registryTimeBean);//加入集合
		            }
				listView.setAdapter(new RegistryListAdapet(RegistryListActivity.this,list));
			}
		});
	}
}
