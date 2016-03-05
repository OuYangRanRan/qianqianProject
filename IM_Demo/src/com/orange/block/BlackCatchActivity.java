package com.orange.block;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import com.orange.engine.camera.ZoomCamera;
import com.orange.engine.options.PixelPerfectEngineOptions;
import com.orange.engine.options.PixelPerfectMode;
import com.orange.engine.options.ScreenOrientation;
import com.orange.res.FontRes;
import com.orange.res.RegionRes;
import com.orange.ui.activity.GameActivity;
import com.orange.block.res.Res;
import com.orange.block.scene.GameScene;
import com.orange.block.util.ConstantUtil;
import com.orange.block.util.LogUtil;

/**
 * �����Activity ��
 * @author lch<OGEngine@orangegame.cn>
 * @
 */
public class BlackCatchActivity extends GameActivity {
	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);

	}

	@Override
	protected PixelPerfectEngineOptions onCreatePixelPerfectEngineOptions() {
		PixelPerfectEngineOptions pixelPerfectEngineOptions = new PixelPerfectEngineOptions(
				this, ZoomCamera.class);
		pixelPerfectEngineOptions
				.setScreenOrientation(ScreenOrientation.PORTRAIT_FIXED); // ��������
		pixelPerfectEngineOptions
				.setPixelPerfectMode(PixelPerfectMode.CHANGE_HEIGHT);// ����ģʽ,��������Ϊ�����ֿ�Ȳ��䣬�ı�ߡ�
		pixelPerfectEngineOptions.setDesiredSize(ConstantUtil.DESIRED_SIZE);// �ο��ߴ�
		return pixelPerfectEngineOptions;
	}
	@Override
	protected void onLoadResources() {
		// ������س�ʼ����Դ��
		LogUtil.d("��ʼ������Դ...");
		RegionRes.loadTexturesFromAssets(Res.ALL_XML);
		FontRes.loadFont(128, 128,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 40, true,
				Color.RED, ConstantUtil.FONT_NAME_TIMER);
		
		FontRes.loadFont(256, 512,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 50, true,
				Color.BLACK, ConstantUtil.FONT_NAME_RESULT);

	}
	@Override
	protected void onLoadComplete() {
		// ������Դ��ɺ�
		LogUtil.d("������Դ���...");
		this.startScene(GameScene.class);// ������Ϸ����
	}
	@Override
	protected void onPause() {
		super.onPause();
		this.getEngine().stop();
	}
	
	@Override
	protected synchronized void onResume() {
		super.onResume();
		this.getEngine().start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		android.os.Process.killProcess(android.os.Process.myPid());
	}
}
