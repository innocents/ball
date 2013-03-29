package net.dixq.irairabar;


public class Vec {
	
	public float _x, _y;
	
	Vec(){
		_x = _y = 0;
	}
	
	Vec( float x, float y ){
		_x = x;
		_y = y;
	}
	
	//角度を取得
	float getAngle(){
		return (float)Math.atan2(_y, _x);
	}

	//大きさを取得
	float getLength(){
		return (float)Math.sqrt( _x*_x + _y*_y );
	}
	
	//引数より大きさが大きい場合は引数の値にする
	void setLengthCap( float maxLength ){
		float len = getLength();
		if( maxLength == 0 ){
			return;	//0���h�~
		}
		if( len > maxLength ){//maxLengthより大きければ大きさをmaxLengthにする
			float rate =len/maxLength;
			_x /= rate;
			_y /= rate;
		}
	}
	
	// vec方向へrate率ほどブレンドする
	void blend( Vec vec, float rate ){
		float w = vec._x - _x;
		float h = vec._y - _y;
		_x += w*rate;
		_y += h*rate;
	}
	
}
