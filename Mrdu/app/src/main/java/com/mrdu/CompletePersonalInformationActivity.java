package com.mrdu;

import com.mrdu.bean.UserBean;
import com.mrdu.net.MyException;
import com.mrdu.net.impl.UserUtilSQL;
import com.mrdu.util.MyApplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CompletePersonalInformationActivity extends Activity implements OnClickListener{
	UserBean user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_completepersonalinformation);
		EditText textname = (EditText)findViewById(R.id.editText1);
		EditText textsex = (EditText)findViewById(R.id.editText2);
		EditText textoccu = (EditText)findViewById(R.id.editText3);
		EditText textemail = (EditText)findViewById(R.id.editText4);
		EditText textphone = (EditText)findViewById(R.id.editText5);
		Button b1 = (Button)findViewById(R.id.JBFS);
		b1.setOnClickListener(this);
		MyApplication app = (MyApplication)CompletePersonalInformationActivity.this.getApplication();
		user=app.getUser();
		//textname.setText(user.nickname);
		textsex.setText(user.sex+"");
		textoccu.setText(user.occupation);
		textemail.setText(user.email);
		textphone.setText(user.phonenum);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.e("测试", "触发点击");
		EditText textname = (EditText)findViewById(R.id.editText1);
		EditText textsex = (EditText)findViewById(R.id.editText2);
		EditText textoccu = (EditText)findViewById(R.id.editText3);
		EditText textemail = (EditText)findViewById(R.id.editText4);
		EditText textphone = (EditText)findViewById(R.id.editText5);
		UserUtilSQL update = new UserUtilSQL(this);
		//user.nickname = textname.getText().toString();
		user.sex = textsex.getText().toString().charAt(0);
		user.occupation = textoccu.getText().toString();
		user.email = textemail.getText().toString();
		user.phonenum = textphone.getText().toString();
		try {
			update.Update(user);
			Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
			finish();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, "更新失败", Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
