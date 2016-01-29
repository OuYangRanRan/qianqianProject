package com.bmob.im.demo.ui;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.im.BmobUserManager;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.baidu.a.a.a.c;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.bmob.im.demo.CustomApplcation;
import com.bmob.im.demo.R;
import com.bmob.im.demo.R.id;
import com.bmob.im.demo.R.layout;
import com.bmob.im.demo.R.menu;
import com.bmob.im.demo.RegistryListActivity;
import com.bmob.im.demo.bean.RegistryBean;
import com.bmob.im.demo.config.BmobConstants;
import com.bmob.im.demo.config.BmobUserInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegistryActivity extends Activity {
	private static final double EARTH_RADIUS = 6378137.0;  
	private LocationManager mLocationManager; 
	/**经度*/
	private double latitude = 0;
	/**纬度*/
	private double longitude = 0;
	/*目标经度*/
	private double mainlatitude=126.658443;
	/*目标纬度*/
	private double mainlongitude=45.827892;
	private Button registry_set;
	private RelativeLayout  layout_registry_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registry);
		registry_set=(Button) findViewById(R.id.registry_set);//签到按钮
		layout_registry_info=(RelativeLayout) findViewById(R.id.layout_registry_info);//查看签到记录
		registry_set.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				init();
//				Toast.makeText(RegistryActivity.this,"签到成功",Toast.LENGTH_LONG).show();
				Intent intent = new Intent(RegistryActivity.this, MyLocationActivity.class);
				intent.putExtra("type", "select");
				startActivityForResult(intent, BmobConstants.REQUESTCODE_TAKE_LOCATION);
			}
		});
		layout_registry_info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(RegistryActivity.this,RegistryListActivity.class);
				startActivity(intent);
			}
		});
	}
	public void init(){
		BmobUserManager bmobUserManager=BmobUserManager.getInstance(this);
		RegistryBean registryBean=new RegistryBean();
		String name=bmobUserManager.getCurrentUserName();
		System.out.println("name的值为:"+name);
		registryBean.setLocation(new  GeoPoint(latitude, longitude));
		registryBean.setName(name);
		System.out.println(registryBean.toString()+"---------------");
		//保存到数据库 代码
		registryBean.save(RegistryActivity.this,new SaveListener() {
			@Override
			public void onSuccess() {
				System.out.println("上传成功");
					Toast.makeText(RegistryActivity.this,"签到成功"+ ",数据在服务端的创建时间为:",Toast.LENGTH_LONG).show();
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(RegistryActivity.this,"签到失败,请允许程序访问地位",Toast.LENGTH_LONG).show();
			}
		});
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
	private void updatePersonByObjectId(String name,String objectId,RegistryBean registryBean) {
		registryBean.save(this,new SaveListener() {
			
			@Override
			public void onSuccess() {
					Toast.makeText(RegistryActivity.this,"签到成功"+ ",数据在服务端的创建时间为：",Toast.LENGTH_LONG).show();
					init();
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				Toast.makeText(RegistryActivity.this,"签到失败,请允许程序访问地位",Toast.LENGTH_LONG).show();
			}
		});
	}
	public GeoPoint getLocation(){
		if (latitude==0.0&&longitude==0.0) {
			Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			/**经度*/
			latitude = location.getLatitude();
			/**纬度*/
			longitude = location.getLongitude();	
		}
		System.out.println(latitude+"------"+longitude);
		/**谢谢到message中*/
		GeoPoint geoPoint=new GeoPoint(latitude, longitude);
		System.out.println(geoPoint.toString()+"位置");
		return geoPoint;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case BmobConstants.REQUESTCODE_TAKE_LOCAL:
				if (data != null) {
					Uri selectedImage = data.getData();
					if (selectedImage != null) {
						Cursor cursor = getContentResolver().query(
								selectedImage, null, null, null, null);
						cursor.moveToFirst();
						int columnIndex = cursor.getColumnIndex("_data");
						String localSelectPath = cursor.getString(columnIndex);
						cursor.close();
					}
				}
				break;
			case BmobConstants.REQUESTCODE_TAKE_LOCATION:// 地理位置
				double latitude = data.getDoubleExtra("x", 0);// 维度
				double longtitude = data.getDoubleExtra("y", 0);// 经度
				String address = data.getStringExtra("address");
				if (address != null && !address.equals("")) {
//					if (getDistance(longitude, latitude,mainlongitude, mainlatitude)<=1000) {
					if (true) {
						
					init();
						Toast.makeText(this,"签到成功",Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(this,"签到失败,不在指定签到地",Toast.LENGTH_LONG).show();
					}
				}
					
				else {
					Toast.makeText(this,"无法获取到您的位置信息!", Toast.LENGTH_LONG).show(); 
				}

				break;
			}
		}
	}
		//通过传入两个点的经纬度返回米
		 public static double getDistance(double longitude1, double latitude1,  
				   double longitude2, double latitude2) {  
				  double Lat1 = rad(latitude1);  
				  double Lat2 = rad(latitude2);  
				  double a = Lat1 - Lat2;  
				  double b = rad(longitude1) - rad(longitude2);  
				  double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
				    + Math.cos(Lat1) * Math.cos(Lat2)  
				    * Math.pow(Math.sin(b / 2), 2)));  
				  s = s * EARTH_RADIUS;  
				  s = Math.round(s * 10000) / 10000;  
				  return s;  
				 }  
		 private static double rad(double d) {  
			  return d * Math.PI / 180.0;  
			 }  
}
