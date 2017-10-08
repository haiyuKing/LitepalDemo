package com.why.project.litepaldemo;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.why.project.litepaldemo.model.LoginUserModel;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import static org.litepal.crud.DataSupport.where;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	private Button btn_save;
	private Button btn_saveOrUpdate;
	private Button btn_saveAll;

	private Button btn_update;
	private Button btn_updateAll;

	private Button btn_find;

	private Button btn_del;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		initEvents();
	}

	private void initViews() {
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_saveOrUpdate = (Button) findViewById(R.id.btn_saveOrUpdate);
		btn_saveAll = (Button) findViewById(R.id.btn_saveAll);

		btn_update = (Button) findViewById(R.id.btn_update);
		btn_updateAll = (Button) findViewById(R.id.btn_updateAll);

		btn_find = (Button) findViewById(R.id.btn_find);

		btn_del = (Button) findViewById(R.id.btn_del);
	}

	private void initEvents() {

		btn_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginUserModel model = new LoginUserModel();
				model.setUserId("00001");
				model.setUserName("用户名1");
				model.setPassWord("密码1");
				model.setTel("18600001");

				model.save();
			}
		});

		btn_saveOrUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginUserModel model = new LoginUserModel();
				model.setUserId("00001");
				model.setUserName("用户名1_");
				model.setPassWord("密码1_");
				model.setTel("18600001");

				model.saveOrUpdate("userid=?",model.getUserId());
			}
		});

		btn_saveAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginUserModel model2 = new LoginUserModel();
				model2.setUserId("00002");
				model2.setUserName("用户名2");
				model2.setPassWord("密码2");
				model2.setTel("18600002");

				LoginUserModel model3 = new LoginUserModel();
				model3.setUserId("00003");
				model3.setUserName("用户名3");
				model3.setPassWord("密码3");
				model3.setTel("18600003");

				List<LoginUserModel> loginList = new ArrayList<LoginUserModel>();
				loginList.add(model2);
				loginList.add(model3);

				DataSupport.saveAll(loginList);
			}
		});

		btn_update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put("username","用户名2_");
				int updateNum = DataSupport.update(LoginUserModel.class,values,2);//修改id值等于2的那一行数据
				Log.w(TAG,"{btn_update}updateNum="+updateNum);
			}
		});

		btn_updateAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put("username","用户名3_");
				int updateAllNum = DataSupport.updateAll(LoginUserModel.class,values,"userid=?","00003");//修改userid=00003的那一行数据
				Log.w(TAG,"{btn_update}updateAllNum="+updateAllNum);
			}
		});

		btn_find.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginUserModel firstModel = DataSupport.findFirst(LoginUserModel.class);
				Log.w(TAG,"userId = " + firstModel.getUserId());

				LoginUserModel lastModel = DataSupport.findLast(LoginUserModel.class);
				Log.w(TAG,"userId = " + lastModel.getUserId());

				List<LoginUserModel> findAllList = DataSupport.findAll(LoginUserModel.class);
				if(findAllList.size() > 0){
					for(LoginUserModel model : findAllList){
						Log.w(TAG,"model.getUserId()" + model.getUserId());
					}
				}

				List<LoginUserModel> findWhereList = where("userid=?","00003").find(LoginUserModel.class);
				if(findWhereList.size() > 0){
					for(LoginUserModel model : findWhereList){
						Log.w(TAG,"model.getUserId()" + model.getUserId());
					}
				}

				List<LoginUserModel> findSelectList = DataSupport.select("userid","username").where("userid=?","00003").find(LoginUserModel.class);
				if(findSelectList.size() > 0){
					for(LoginUserModel model : findSelectList){
						Log.w(TAG,"model.getUserId()" + model.getUserId());
					}
				}

				List<LoginUserModel> findOrderList = DataSupport.select("userid","username")
						.where("userid=?","00003")
						.order("userid desc")
						.find(LoginUserModel.class);
				if(findOrderList.size() > 0){
					for(LoginUserModel model : findOrderList){
						Log.w(TAG,"model.getUserId()" + model.getUserId());
					}
				}

				List<LoginUserModel> findlimitList = DataSupport.select("userid","username")
						.where("userid=?","00003")
						.limit(10)
						.find(LoginUserModel.class);
				if(findlimitList.size() > 0){
					for(LoginUserModel model : findlimitList){
						Log.w(TAG,"model.getUserId()" + model.getUserId());
					}
				}

				List<LoginUserModel> findoffsetList = DataSupport.select("userid","username")
						.where("userid=?","00003")
						.limit(10)
						.offset(10)
						.find(LoginUserModel.class);
				if(findoffsetList.size() > 0){
					for(LoginUserModel model : findoffsetList){
						Log.w(TAG,"model.getUserId()" + model.getUserId());
					}
				}

				LoginUserModel findWhereFisrt = DataSupport.where("userid=?","00003").findFirst(LoginUserModel.class);
				Log.w(TAG,"findWhereFisrt.getUserId()" + findWhereFisrt.getUserId());

				LoginUserModel findWhereLast = DataSupport.where("userid=?","00003").findLast(LoginUserModel.class);
				Log.w(TAG,"findWhereFisrt.getUserId()" + findWhereLast.getUserId());

				boolean isExist = DataSupport.isExist(LoginUserModel.class,"userid=?","00003");
				Log.w(TAG,"isExist=" + isExist);
			}
		});

		btn_del.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int DelNum = DataSupport.delete(LoginUserModel.class,1);//删除ID值等于1的那一行数据
				Log.w(TAG,"DelNum=" + DelNum);

				int delAllNum = DataSupport.deleteAll(LoginUserModel.class,"userid=?","00002");
				Log.w(TAG,"delAllNum=" + delAllNum);

//				int delAllNum = DataSupport.deleteAll(LoginUserModel.class);
//				Log.w(TAG,"delAllNum=" + delAllNum);
			}
		});

	}

}
