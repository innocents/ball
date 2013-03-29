package net.dixq.irairabar;

import android.app.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

public class IrairaBarActivity extends Activity {
	

	GameSurfaceView _view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//��ʂ̃^�C���A�E�g�h�~ 
		
		_view = new GameSurfaceView(this);
		setContentView(_view);
		AcSensor.Inst().onCreate(this); // �Z���T�[����
		
		
	}
	

	@Override
	protected void onResume() { // �A�N�e�B�r�e�B�������n�߂鎞�Ă΂��
		super.onResume();
		AcSensor.Inst().onResume();// �J�n���ɃZ���T�[�𓮂����n�߂�
	}

	@Override
	protected void onPause() { // �A�N�e�B�r�e�B�̓������~�܂鎞�Ă΂��
		super.onPause();
		AcSensor.Inst().onPause();// ���f���ɃZ���T�[���~�߂�
	}
	
		

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			_view = new GameSurfaceView(this);
			setContentView(_view); // �����̎��̂�GameSurfaceView����GameMgr
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}	
	} 
}
  
