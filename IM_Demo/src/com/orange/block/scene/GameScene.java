package com.orange.block.scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.view.KeyEvent;

import com.orange.content.SceneBundle;
import com.orange.entity.IEntity;
import com.orange.entity.modifier.ColorModifier;
import com.orange.entity.modifier.DelayModifier;
import com.orange.entity.modifier.IEntityModifier.IEntityModifierListener;
import com.orange.entity.modifier.LoopEntityModifier;
import com.orange.entity.modifier.MoveYModifier;
import com.orange.entity.modifier.ScaleModifier;
import com.orange.entity.modifier.SequenceEntityModifier;
import com.orange.entity.primitive.DrawMode;
import com.orange.entity.primitive.Mesh;
import com.orange.entity.scene.Scene;
import com.orange.entity.sprite.AnimatedSprite;
import com.orange.entity.text.Text;
import com.orange.res.FontRes;
import com.orange.util.color.Color;
import com.orange.util.modifier.IModifier;
import com.orange.block.control.TimerTool;
import com.orange.block.entity.Block;
import com.orange.block.entity.FailGroup;
import com.orange.block.entity.SuccGroup;
import com.orange.block.res.Res;
import com.orange.block.util.ConstantUtil;
import com.orange.block.util.LogUtil;

/**
 * ��Ϸ����
 * 
 * @author lch
 * 
 */
public class GameScene extends Scene {
	/** ��Ŀ� **/
	private float blockWidth = 0;
	/** ��ĸ� **/
	private float blockHight = 0;
	/** ����װ��ǰ�������ɵ�δ��ɾ����block **/
	private List<Block[]> blockList;
	/** ��ǰ���ɵĿ������ **/
	private int linesLength = 5;
	/** ��Ϸ״̬ **/
	private int gameStatus = ConstantUtil.GAME_START;
	/** �ƶ����� **/
	private int moveNum = 0;
	/** �ɹ����� **/
	private SuccGroup mSuccGroup;
	/** ʧ�ܽ��� **/
	private FailGroup mFailGroup;
	/** ��ʱ������ **/
	private TimerTool mTimerTool;

	/** ��ʾ��ʱ��Text **/
	private Text timerText;
	
	/**��Ϸ��ʾ��**/
	private AnimatedSprite game_tip;

	// ����Z��������
	private static final int pZIndex_middle = 2;
	private static final int pZIndex_top = 3;

	public Text getTimerText() {
		return timerText;
	}

	public TimerTool getmTimerTool() {
		return mTimerTool;
	}

	@Override
	public void onSceneCreate(SceneBundle bundle) {
		super.onSceneCreate(bundle);
		// ��ͷ����ʾ����4*4�Ŀ飬�����þ�ͷ����ķ�֮һ��Ϊ���
		blockWidth = this.getCameraWidth() / 4;
		blockHight = this.getCameraHeight() / 4;

		this.blockList = new ArrayList<Block[]>();
		mTimerTool = new TimerTool(this);
		initView();
	}

	private void initView() {
		// ��ʼ��blocks
		initBlocks();

		timerText = new Text(getCameraCenterX(), 10,
				FontRes.getFont(ConstantUtil.FONT_NAME_TIMER), "00.000\"",
				"00:00.000\"".length(), getVertexBufferObjectManager());
		this.attachChild(timerText);
		timerText.setCentrePositionX(getCameraCenterX());
		timerText.setZIndex(pZIndex_top);

		mSuccGroup = new SuccGroup(getCameraWidth(), getCameraHeight(), this);
		mSuccGroup.setZIndex(pZIndex_middle);
		mSuccGroup.setVisible(false);
		this.attachChild(mSuccGroup);

		mFailGroup = new FailGroup(getCameraWidth(), getCameraHeight(), this);
		this.attachChild(mFailGroup);
		mFailGroup.setZIndex(pZIndex_middle);
		
		game_tip = new AnimatedSprite(0, 0, Res.GAME_TIP, getVertexBufferObjectManager());
		game_tip.setCentrePositionX(this.getCameraCenterX());
		game_tip.setBottomPositionY(this.getCameraBottomY()-60);
		this.attachChild(game_tip);
		game_tip.setZIndex(pZIndex_top);
		
	}

	/**
	 * ��ʼ��blocks
	 */
	private void initBlocks() {
		Random mRandom = new Random();

		int blackIndex = 0;
		// ��ʼblocks,�ȴ���4*5���ʹ��ʱ����һ��������
		for (int row = 0; row < linesLength; row++) {// ��
			// һ��blocks
			Block[] rowBolcks = new Block[4];
			// ���һ���ڿ�����λ��
			blackIndex = mRandom.nextInt(4);
			for (int column = 0; column < 4; column++) {// ��
				rowBolcks[column] = new Block(this, row, column, blockWidth,
						blockHight, blackIndex, getVertexBufferObjectManager());
				this.attachChild(rowBolcks[column]);
			}
			blockList.add(rowBolcks);
		}
	}

	/**
	 * ����ʱ��������Ϸ���
	 */
	public void resetGame() {
		gameStatus = ConstantUtil.GAME_START;
		linesLength = 5;
		moveNum = 0;
		mSuccGroup.showItems(false);
		timerText.setText("00.000\"");
		timerText.setVisible(true);
		mTimerTool.resetTimer();
		mSuccGroup.setVisible(false);
		game_tip.setVisible(true);
		deletBlocks();
		initBlocks();
		// ��Z��������һ�����²�˳��
		this.sortChildren();
	}

	/**
	 * ��������µ�һ��
	 */
	private void createNewRowBolcks() {
		// ������Ļû�õĲ����Ƴ���
		if (blockList.size() > 5) {
			Block[] rowBolcks = blockList.get(0);
			for (Block block : rowBolcks) {
				block.detachSelf();
			}
			blockList.remove(0);
		}

		// Ŀǰ������һ�п��Y����
		float topBlocksY = blockList.get(blockList.size() - 1)[0].getY();

		// һ�п�
		Block[] rowBolcks = new Block[4];
		int blackIndex = new Random().nextInt(4);
		for (int column = 0; column < 4; column++) {
			// �´��� Block
			rowBolcks[column] = new Block(this, column, blockWidth, blockHight,
					blackIndex, getVertexBufferObjectManager());
			// ����Y����
			rowBolcks[column].setBottomPositionY(topBlocksY - 1);
			// �����Ѿ��ǵڼ���
			rowBolcks[column].setRow(linesLength);
			this.attachChild(rowBolcks[column]);
		}
		blockList.add(rowBolcks);

		// ��Z��������һ�����²�˳��
		this.sortChildren();

		// ��ǰ���ɵĿ����������
		linesLength++;
	}

	/**
	 * ���һ���ƶ�����Ϸʤ��
	 * 
	 * @param pBlock
	 */
	private void gameSucc(Block pBlock) {
		gameStatus = ConstantUtil.GAME_OVER;
		// ���һ���ƶ�����Ϸʤ��
		moveDown(pBlock, 0);
		// ֹͣ��ʱ
		mTimerTool.stop();
		// ��ʾ��Ϸʤ������
		mSuccGroup.showItems(true);
		// ������ʾʱ���Text
		timerText.setVisible(false);
	}

	/**
	 * �������󵯳���ɫ��ʧ�ܽ���,��Ϸʧ��
	 */
	private void gameFail() {
		gameStatus = ConstantUtil.GAME_OVER;
		// ��Ϸʧ�ܣ�ֹͣ��ʱ
		mTimerTool.stop();
		// ������ʾʱ���Text
		timerText.setVisible(false);
	}

	/**
	 * ��Ϸ�쵽����ʱ��ʼ������ɫ��ʤ������
	 */
	private void showSuccGroup() {
		// Ŀǰ������һ�п��Y����
		float topBlocksY = blockList.get(blockList.size() - 1)[0].getY();
		mSuccGroup.setBottomPositionY(topBlocksY);
		mSuccGroup.setVisible(true);
	}

	/**
	 * �Ƴ�ʣ�µ�block�����blockList
	 */
	private void deletBlocks() {
		for (Block[] rowBlocks : blockList) {
			for (Block block : rowBlocks) {
				this.detachChild(block);
			}
		}

		blockList.clear();
	}

	/**
	 * �����Blockʱ���е��߼�����
	 * 
	 * @param pBlock
	 *            �������block
	 */
	public void touchBlock(Block pBlock) {

		if (gameStatus == ConstantUtil.GAME_START) {

			if (pBlock.getRow() == moveNum + 2) {// ��ʾ���ڵײ��������ĵ���������
				// �ж��ǲ��ǵ���˸õ���ĺڿ����һ������ǣ�����Ҳ�ж�������ȷ����ˣ�������Ӧ�ƶ�
				upBlockTouch(pBlock);
			} else if (pBlock.getRow() == moveNum + 1) {// ��ʾ���ڵײ��������ĵ����ڶ���
				if (pBlock.getColorType() == ConstantUtil.COLOR_BLACK) {
					if (linesLength == moveNum + 2) {
						// ��Ϸʤ��
						gameSucc(pBlock);
					} else {
						// ����blocks����
						moveDown(pBlock, 1);
					}

				} else if (pBlock.getColorType() == ConstantUtil.COLOR_WHITE) {
					// ����˰׿飬��Ϸʧ��
					gameFail();
					// ʧ��ʱpBlock��һ������Ч��
					LoopEntityModifier loop = failAction();
					// ����Ч��
					pBlock.registerEntityModifier(loop);
				}

			}

		}

	}

	/**
	 * ʧ��ʱpBlock��һ������Ч��
	 * @return
	 */
	private LoopEntityModifier failAction() {
		SequenceEntityModifier sequence = new SequenceEntityModifier(
				new ColorModifier(0.1f, Color.RED, Color.WHITE),
				new DelayModifier(0.07f), new ColorModifier(0.1f,
						Color.WHITE, Color.RED));
		LoopEntityModifier loop = new LoopEntityModifier(sequence,
				3, new IEntityModifierListener() {

					@Override
					public void onModifierStarted(
							IModifier<IEntity> pModifier,
							IEntity pItem) {
					}
					@Override
					public void onModifierFinished(
							IModifier<IEntity> pModifier,
							IEntity pItem) {
						//Ч��������ϣ���ʾ��Ϸʧ�ܽ���
						mFailGroup.showView();
					}
				});
		return loop;
	}

	/**
	 * �ж��ǲ��ǵ���˸õ���ĺڿ����һ������ǣ�����Ҳ�ж�������ȷ����ˣ�������Ӧ�ƶ�
	 * 
	 * @param pBlock
	 *            ��������Ŀ�
	 */
	private void upBlockTouch(Block pBlock) {
		int touchColumn = pBlock.getColumn();
		for (Block[] blocks : blockList) {
			for (Block block : blocks) {
				if (block.getRow() == moveNum + 1
						&& block.getColorType() == ConstantUtil.COLOR_BLACK) {
					if (block.getColumn() == touchColumn) {
						// ����blocks����
						moveDown(block, 1);
					}
					return;
				}
			}

		}
	}

	/**
	 * ��ȷ����õ���ĺڿ飬������һ�еĿ飬���������ƶ��������µ�һ���飬�ı�ڿ���ɫ
	 * @param pBlock
	 * @param stepNum
	 *һ��Ϊ1������������ɫ�ɹ���������һ��Ϊ0
	 */
	private void moveDown(Block pBlock, int stepNum) {

		if(moveNum == 0){
			// ��ʼ��ʱ
			mTimerTool.start();
			// ������ʾ����
			game_tip.setVisible(false);
		}
		

		if (moveNum < ConstantUtil.LINES_LEN) {
			// ��������µ�һ��
			createNewRowBolcks();
		} else if (moveNum == ConstantUtil.LINES_LEN) {
			// ��ʼ��ʾ��ɫʤ�����棬����ʤ��������û��ʤ��
			showSuccGroup();
		}

		// ������ĺڿ���
		pBlock.setColor(0.63f, 0.63f, 0.63f);
		pBlock.registerEntityModifier(new ScaleModifier(0.1f, 0.5f, 1.0f));

		// �ƶ�������1
		moveNum++;
		// ��Ҫ�ƶ��ľ���
		float moveDistance = this.getCameraHeight() - blockHight * stepNum
				- pBlock.getY();
		for (Block[] rowBlocks : blockList) {
			for (Block block : rowBlocks) {
				// ����������block�����ƶ�ָ������
				moveToY(block, moveDistance);
			}
		}

		// �쵽ʤ��������ɫʤ������ʱ��ʤ����������ƶ�
		if (mSuccGroup.isVisible()) {
			moveToY(mSuccGroup, moveDistance);
		}
	}

	/**
	 * ��Y�᷽���ϣ��ɵ�ǰλ���ƶ�ָ���ľ���
	 * 
	 * @param entity
	 *            Ҫ�ƶ���ʵ��
	 * @param moveDistance
	 *            ��Ҫ�ƶ��ľ���
	 */
	private void moveToY(IEntity entity, float moveDistance) {
		float pFromY = entity.getY();
		float pToY = pFromY + moveDistance;
		entity.registerEntityModifier(new MoveYModifier(0.1f, pFromY, pToY));
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.getActivity().finish();
			return true;
		}
		return false;
	}
	
	@Override
	public void onSceneResume() {
		super.onSceneResume();
		this.setIgnoreUpdate(false);
	}
	
	@Override
	public void onScenePause() {
		super.onScenePause();
		this.setIgnoreUpdate(true);
	}

	@Override
	public void onSceneDestroy() {
		super.onSceneDestroy();

		LogUtil.d("onSceneDestroy");
	}

	// ================================================================
	/**
	 * ��ȡ��ͷ�� �ұ� x ����
	 */
	public float getCameraRightX() {
		return this.getCameraWidth();
	}

	/**
	 * ��ȡ��ͷ�� �е� x ����
	 * 
	 * @return
	 */
	public float getCameraCenterX() {
		return this.getCameraWidth() * 0.5f;
	}

	/**
	 * ��ȡ��ͷ�ײ�Y����
	 * 
	 * @return
	 */
	public float getCameraBottomY() {
		return this.getCameraHeight();
	}

	/**
	 * ��ȡ��ͷ����Y����
	 * 
	 * @return
	 */
	public float getCameraCenterY() {
		return this.getCameraHeight() * 0.5f;
	}

}
