package com.orange.block.control;

import com.orange.engine.handler.timer.ITimerCallback;
import com.orange.engine.handler.timer.TimerHandler;
import com.orange.block.scene.GameScene;
import com.orange.block.util.LogUtil;

public class TimerTool {

	private GameScene mGameScene;
	private TimerHandler mTimerHandler;
	/**��ǰ�������ܺ�����**/
	private long millisPass = 0;
	/**�Ƿ��ʱ��**/
	private boolean isCountDowning = false;
	/**���ٺ����ۼ�һ�μ�ʱ**/
	private static long stepMillis = 83;

	public TimerTool(GameScene pGameScene) {
		this.mGameScene = pGameScene;
		initTimerHandler();
	}

	// ��ʼ��TimerHandler,����Ϊÿ��stepMillis * 0.001f��ѭ���ص�onTimePassed����
	private void initTimerHandler() {

		mTimerHandler = new TimerHandler(stepMillis * 0.001f, true,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// �ۼ���������ʱ�䣬���ڽ����ϸ�����ʾ��ǰʱ��
						addMillisPass();
					}
				});

	}

	// ����ʱ��
	public void resetTimer() {
		millisPass = 0;
		isCountDowning = false;
	}
	// �ۼ���������ʱ�䣬���ڽ����ϸ�����ʾ��ǰʱ��
	private void addMillisPass() {
		millisPass += stepMillis;
		mGameScene.getTimerText().setText(millisToTimer(millisPass));
	}
	// ��ȡ��ǰ�������ܺ�����
	public long getMillisPass() {
		return millisPass;
	}
	// �Ѻ���ת��Ϊһ�ַ֡��롢������ı���ʾģʽ�ַ���
	public String millisToTimer(long pMillisPass) {
		String timer = "";
		long min = pMillisPass / 60000;
		long sec = (pMillisPass - min * 60000) / 1000;
		String secStr = sec < 10 ? "0" + sec : String.valueOf(sec);
		long millisec = pMillisPass % 1000;
		String millisecStr = millisec < 100 ? "0" + millisec : String
				.valueOf(millisec);
//		LogUtil.d("pMillisPass--->"+pMillisPass+"   millisecStr--->"+millisecStr);
		if (min > 0) {
			timer += min + ":";
		}
		timer += secStr + "." + millisecStr + "\"";
		return timer;
	}
	// ע��mTimerHandler��ʼ��ʱ
	public void start() {
		if (!isCountDowning) {
			isCountDowning = true;
			mGameScene.registerUpdateHandler(mTimerHandler);
		}
	}
	// ��ע��mTimerHandlerֹͣ��ʱ
	public void stop() {
		mGameScene.unregisterUpdateHandler(mTimerHandler);
	}
}
