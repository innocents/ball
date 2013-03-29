package net.dixq.irairabar;

import java.util.ArrayList;
import java.util.LinkedList;

import net.dixq.irairabar.Barricade.eType;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameMgr {
	
	private enum eStatus{
		NORMAL,
		GAMEOVER,
		GAMECLEAR
	};

	private static final float PI = (float) Math.PI;
	private ArrayList<Barricade> _barrList = new ArrayList<Barricade>();//障害物リスト
	private LinkedList<Task> _taskList = new LinkedList<Task>();// タスクリスト
	private eStatus _status = eStatus.NORMAL;
	private Player _player;

	GameMgr() {
		_barrList.add(new BarricadeSquare(  0,  0,480, 20, null,null));// 画面の4隅に四角を配置
		_barrList.add(new BarricadeSquare(  0,  0, 20,800, null,null));
		_barrList.add(new BarricadeSquare(460,  0, 20,800, null,null));
		_barrList.add(new BarricadeSquare(  0,780,480, 20, null,null));

		_barrList.add(new BarricadeTriangle(  0, 0, 200, new BConf(+PI / 150)));// 回転する三角
		_barrList.add(new BarricadeTriangle(480, 0, 180, new BConf(+PI / 150)));// 

		//_barrList.add(new BarricadeStar(240, 240, 30, 50, new BConf(-PI / 360)));// 回転する星
		//_barrList.add(new BarricadeStar(240, 240, 20,  80, new BConf(+PI / 360)));// 
		
		_barrList.add(new BarricadeSquare(300, 440, 4000, 100, new BConf(-PI/90),null));//回転する四角
		//_barrList.add(new BarricadeSquare(300, 440, 4000, 100, new BConf(-PI / 130),null));
		//_barrList.add(new BarricadeSquare(300, 440, 4000, 100, new BConf(-PI / 190),null));
		//_barrList.add(new BarricadeSquare(0, 440, 2000, 20, new BConf(-PI / 90),null));
		//_barrList.add(new BarricadeSquare(200, 440, -3000, 100, new BConf(-PI / 130),null));
		
		//_barrList.add(new BarricadeSquare(330, 620, 130, 20, null));

		//_barrList.add(new BarricadeSquare(230, 390, 20, 350, null));

		//_barrList.add(new BarricadeSquare(0, 480, 240, 20, new BConf(+PI / 360)));

		//_barrList.add(new BarricadeSquare( 0, 440, 900, 50, new BConf(+PI / 360),null));
		//_barrList.add(new BarricadeSquare(130, 600, 110, 20, new BConf(+PI / 360)));
		//_barrList.add(new BarricadeSquare(185, 600,  55, 20, new BConf(+PI / 360)));
		//_barrList.add(new BarricadeSquare(20, 680,  80, 20, null));

		_barrList.add(new BarricadeSquare(0, 750, 800, 30, new BConf(eType.GOAL),null));//ゴール
		
		for (Barricade bar : _barrList) {
			_taskList.add(bar);	//タスクリストに障害物を追加
		}

		_player = new Player();
		_taskList.add(_player);
		_taskList.add(new FpsController());
	}

	private boolean Collision(){//衝突判定
		Vec vec = new Vec();
		final Circle cir = _player.getPt();//プレイヤーの中心円を取得
		for(Barricade barr : _barrList){//障害物の数だけループ
			Def.eHitCode code = barr.isHit(cir, vec);//接触判定
			switch(code){
			case OUT:
				_status = eStatus.GAMEOVER;//接触した物がアウトならゲームオーバー
				return true;
			case GOAL:
				_status = eStatus.GAMECLEAR;//接触した物がゴールならゲームクリア
				return true;
			}
		}
		return false;
	}
	
	public boolean onUpdate() {
		if( _status != eStatus.NORMAL ){
			return true;
		}
		if( Collision() ){
			return true;
		}
		for (int i = 0; i < _taskList.size(); i++) {
			if (_taskList.get(i).onUpdate() == false) { 
				_taskList.remove(i); 
				i--;
			}
		}
		return true;
	}

	private void drawStatus(Canvas c){
		switch( _status ){
		case GAMEOVER:
			{
				Paint paint = new Paint();
				paint.setTextSize(80);
				paint.setColor(Color.GREEN);
				c.drawText("は？", 150, 400, paint);
			}
			break;
		case GAMECLEAR:
			{
				Paint paint = new Paint();
				paint.setTextSize(120);
				paint.setColor(Color.BLACK);
				c.drawText("当然だ", 80, 400, paint);
			}
			break;
		}
	}
	
	public void onDraw(Canvas c) {
		c.drawColor(Color.WHITE); // ���œh��Ԃ�
		for (Task task : _taskList) {
			task.onDraw(c);// �`��
		}
		drawStatus(c);
	}

}
