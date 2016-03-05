package com.orange.block.entity;

import com.orange.entity.primitive.Rectangle;
import com.orange.input.touch.TouchEvent;
import com.orange.opengl.vbo.VertexBufferObjectManager;
import com.orange.util.color.Color;
import com.orange.block.scene.GameScene;
import com.orange.block.util.ConstantUtil;

/**
 * ������Ԫ��
 * 
 * @author lch
 * 
 */
public class Block extends Rectangle {
	// ��Ϸ����
	private GameScene mGameScene;
	// ��block����ɫ���ͣ���ɫ���Ǻ�ɫ��
	private int colorType;
	// block ���ڵ���
	private int row;
	// block ���ڵ���
	private int column;

	// ======================get&set========================
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public int getColorType() {
		return colorType;
	}
	// =====================================================

	/**
	 * ������1,��ʼ��blocksʱ�õ�
	 * @param pGameScene ��Ϸ����
	 * @param row block���ڵ���
	 * @param column block���ڵ���
	 * @param pWidth block�Ŀ�
	 * @param pHeight block�ĸ�
	 * @param blackIndex ����ȷ���Ƿ��Ǻڿ飬���blackIndex == columnʱ��Ϊ�ڿ�
	 * @param pVertexBufferObjectManager
	 */
	public Block(GameScene pGameScene, int row, int column, float pWidth,
			float pHeight, int blackIndex,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(column * pWidth, (3 - row) * pHeight, pWidth - 1, pHeight - 1,
				pVertexBufferObjectManager);
		this.mGameScene = pGameScene;
		this.row = row;
		this.column = column;
		if (row == 0) {
			// ��һ������Ϊ�ƿ�
			this.setColor(Color.YELLOW);
		} else {
			// ��ʼ��block����ɫ����,�ǰ׿黹�Ǻڿ�?
			initBlockData(column, blackIndex);
		}
		// ���ÿ�����Ӧ�����¼�
		this.setIgnoreTouch(false);
	}

	/**
	 * ������2,����blocksʱ�õ�
	 * @param pGameScene ��Ϸ����
	 * @param column block���ڵ���
	 * @param pWidth block�Ŀ�
	 * @param pHeight block�ĸ�
	 * @param blackIndex ��ȷ���Ƿ��Ǻڿ飬���blackIndex == columnʱ��Ϊ�ڿ�
	 * @param pVertexBufferObjectManager
	 */
	public Block(GameScene pGameScene, int column, float pWidth, float pHeight,
			int blackIndex, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(column * pWidth, 0, pWidth - 1, pHeight - 1,
				pVertexBufferObjectManager);
		this.mGameScene = pGameScene;
		this.column = column;
		// ��ʼ��block����ɫ����,�ǰ׿黹�Ǻڿ�?
		initBlockData(column, blackIndex);
		// ���ÿ�����Ӧ�����¼�
		this.setIgnoreTouch(false);
	}
	
	/**
	 * ��ʼ��block����ɫ����,�ǰ׿黹�Ǻڿ�?
	 * @param column
	 * @param blackIndex
	 */
	private void initBlockData(int column, int blackIndex) {
		if (blackIndex == column) {
			// ����Ϊ�ڿ�
			this.setColor(Color.BLACK);
			this.colorType = ConstantUtil.COLOR_BLACK;
		} else {
			// ����Ϊ�׿�
			this.setColor(Color.WHITE);
			this.colorType = ConstantUtil.COLOR_WHITE;
		}
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// �����¼�����
		if (pSceneTouchEvent.isActionDown()) {
			// �����Blockʱ���е��߼�����
			mGameScene.touchBlock(this);
		}
		return true;
	}
}
