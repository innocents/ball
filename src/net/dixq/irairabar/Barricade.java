package net.dixq.irairabar;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class Barricade extends Task {
	public enum eType {  //障害物のタイプ
 		OUT, //当たるとアウト
		GOAL //当たるとゴール
	}
	
	protected PointF  _center = new PointF(0,0);		//図形の中心点
	protected PointF  _pt[];							//図形の頂点
	protected Paint   _paint = new Paint();				//ペイント
	protected eType   _type = eType.OUT;				//タイプ
	protected float   _rotaSpeed = 0;					//回転スピード

	//コンストラクタ  type=タイプ　n=頂点　conf=設定情報
	public Barricade(int n, BConf conf){
		if( conf != null ){
			_rotaSpeed 	= conf.speed;	//回転スピード
			_type 		= conf.type;	//物体のタイプ
		}
		switch(_type){
		case OUT:	//接触したらアウトの物
			_paint.setColor(Color.RED);//赤色
			break;
		case GOAL:	//接触したらゴールの物
			_paint.setColor(Color.GREEN);//緑色
			break;
		}
		_pt = new PointF[n];	//頂点配列を作る
		for( int i=0; i<n; i++ ){
			_pt[i] = new PointF();	//頂点を作る
		}
		_paint.setAntiAlias(true);
	}

	//更新する
	public boolean onUpdate(){
		if( _rotaSpeed != 0 ){	//��]����Ȃ�
			DiagramCalcr.RotateDiagram( _pt, _center, _rotaSpeed );	//���_���X�g(_pt)��_center�𒆐S�ɉ�]����
		}
		return true;
	}

	//接触しているかを問う。玉が接触していれば接触した部分をvecに格納し、物体のタイプを返す。接触していなければNOを返す
	public Def.eHitCode isHit( final Circle cir, Vec vec ){
		if( DiagramCalcr.isHit( _pt, cir, vec ) == true ){
			switch(_type){
			case OUT:
				return Def.eHitCode.OUT;
			case GOAL:
				return Def.eHitCode.GOAL;
			}
		}
		return Def.eHitCode.NO;
	}
	
	//描画
	public void onDraw(Canvas c){
		if( _pt.length < 1 ){	//頂点が1未満はありえない
			return;
		}
		Path path = new Path();
		path.moveTo(_pt[0].x, _pt[0].y);	//パスの初期位置
		for( int i=0; i<_pt.length; i++ ){
			path.lineTo(_pt[i].x, _pt[i].y);	//頂点の位置へラインを引いていく
		}
		c.drawPath(path, _paint);	//引いたラインを描画する
	}
}
