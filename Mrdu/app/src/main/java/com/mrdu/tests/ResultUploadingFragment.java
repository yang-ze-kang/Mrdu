package com.mrdu.tests;

import com.mrdu.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResultUploadingFragment extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// [1]通过打气筒把一个布局转换成view对象
		View view = inflater.inflate(R.layout.fragment_waitresult, container,false);
		return view;
	}
}
