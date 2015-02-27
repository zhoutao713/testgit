package com.way.chat.activity;

import java.util.LinkedList;

import com.way.chat.common.util.Constants;
import com.way.client.Client;
import com.way.util.SharePreferenceUtil;

import android.app.Application;
import android.app.NotificationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MyApplication extends Application {
	private  Client client;// 客户端
	private boolean isClientStart;// 客户端连接是否启动
	private NotificationManager mNotificationManager;
	private int nClientewMsgNum = 0;// 后台运行的消息
	private LinkedList<RecentChatEntity> mRecentList;
	private RecentChatAdapter mRecentAdapter;
	private int recentNum = 0;
	private Handler handler=null;
	private int newMsgNum = 0;
	@Override
	public void onCreate() {
	/*	SharePreferenceUtil util = new SharePreferenceUtil(this,
				Constants.SAVE_USER);
		System.out.println(util.getIp() + " " + util.getPort());
		client = new Client(util.getIp(), util.getPort());// 从配置文件中读ip和地址
*/		
		FetchHandler();
		Thread t = new GetClient(handler,this);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		mRecentList = new LinkedList<RecentChatEntity>();
		mRecentAdapter = new RecentChatAdapter(getApplicationContext(),
				mRecentList);
		super.onCreate();
	}
	private void FetchHandler(){
		handler = new Handler(){

			@Override
			public void handleMessage(Message message) {
				Bundle b  = message.getData();
				client = (Client) b.get("client");
				
			}
			
		};
		
	}
	
	public class GetClient extends Thread{
		private Handler handler;
		private MyApplication application;
		public GetClient(Handler handler, MyApplication application){
			this.handler = handler;
			this.application = application;
		}
		@Override
		public void run() {
			
			SharePreferenceUtil util = new SharePreferenceUtil(application,
					Constants.SAVE_USER);
			System.out.println(util.getIp() + " " + util.getPort());
			Client mclient = new Client(util.getIp(), util.getPort());// 从配置文件中读ip和地址
			if(null!=mclient){
				Message message =  new Message();
				Bundle b = new Bundle();
				b.putSerializable("client", mclient);
				message.setData(b);
				handler.sendMessage(message);
				
			}
		}
		
	}

	public Client getClient() {
		return client;
	}

	public boolean isClientStart() {
		return isClientStart;
	}

	public void setClientStart(boolean isClientStart) {
		this.isClientStart = isClientStart;
	}

	public NotificationManager getmNotificationManager() {
		return mNotificationManager;
	}

	public void setmNotificationManager(NotificationManager mNotificationManager) {
		this.mNotificationManager = mNotificationManager;
	}

	
	public LinkedList<RecentChatEntity> getmRecentList() {
		return mRecentList;
	}

	public void setmRecentList(LinkedList<RecentChatEntity> mRecentList) {
		this.mRecentList = mRecentList;
	}

	public RecentChatAdapter getmRecentAdapter() {
		return mRecentAdapter;
	}

	public void setmRecentAdapter(RecentChatAdapter mRecentAdapter) {
		this.mRecentAdapter = mRecentAdapter;
	}

	public int getRecentNum() {
		return recentNum;
	}

	public void setRecentNum(int recentNum) {
		this.recentNum = recentNum;
	}
	public int getnClientewMsgNum() {
		return nClientewMsgNum;
	}
	public void setnClientewMsgNum(int nClientewMsgNum) {
		this.nClientewMsgNum = nClientewMsgNum;
	}
	public int getNewMsgNum() {
		return newMsgNum;
	}
	public void setNewMsgNum(int newMsgNum) {
		this.newMsgNum = newMsgNum;
	}
	
	
}
