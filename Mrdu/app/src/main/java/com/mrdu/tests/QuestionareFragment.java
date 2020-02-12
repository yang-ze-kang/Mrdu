package com.mrdu.tests;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mrdu.R;
import com.mrdu.TestActivity;
import com.mrdu.bean.QuestionBean;
import com.mrdu.bean.TestBean;
import com.mrdu.bean.TestID;
import com.mrdu.bean.UserBean;
import com.mrdu.util.MyApplication;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class QuestionareFragment extends Fragment implements OnClickListener {

    private Context mContext;
    private TextView question;
    private Button bt_answer1;
    private Button bt_answer2;
    private Button bt_answer3;
    private Button bt_answer4;
    private Button bt_preQuest;
    private Button bt_nextQuest;
    private TextView tv_ifSelected;
    private TextView tv_ifCanSubmit;

    private UserBean user;
    private TestBean testBean;
    private static int all = 21;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        // [1]通过打气筒把一个布局转换成view对象
        View view = inflater.inflate(R.layout.fragment_questionare, container, false);
        question = (TextView) view.findViewById(R.id.tv_question);
        bt_answer1 = (Button) view.findViewById(R.id.bt_answer1);
        bt_answer2 = (Button) view.findViewById(R.id.bt_answer2);
        bt_answer3 = (Button) view.findViewById(R.id.bt_answer3);
        bt_answer4 = (Button) view.findViewById(R.id.bt_answer4);
        bt_preQuest = (Button) view.findViewById(R.id.JBFS);
        bt_nextQuest = (Button) view.findViewById(R.id.DZSB);
        tv_ifSelected = (TextView) view.findViewById(R.id.note);
        tv_ifCanSubmit = (TextView) view.findViewById(R.id.note1);
        bt_answer1.setOnClickListener(this);
        bt_answer2.setOnClickListener(this);
        bt_answer3.setOnClickListener(this);
        bt_answer4.setOnClickListener(this);
        bt_preQuest.setOnClickListener(this);
        bt_nextQuest.setOnClickListener(this);

        //初始化问卷
        bt_preQuest.setEnabled(false);
        tv_ifCanSubmit.setVisibility(View.INVISIBLE);
        MyApplication app = (MyApplication) mContext.getApplicationContext();
        user = app.getUser();
        testBean = new TestBean(user.user_id, TestID.BECK);
        setQuestion(testBean.getQuestion());
        return view;
    }

    private void setQuestion(QuestionBean q) {
        if (q == null)
            return;
        question.setText("第" + String.valueOf(testBean.getCurrentQuestion() + 1) + "题");
        bt_answer1.setText("A、" + q.answer1);
        bt_answer2.setText("B、" + q.answer2);
        bt_answer3.setText("C、" + q.answer3);
        bt_answer4.setText("D、" + q.answer4);
        //设置上一题是否可选
        if (testBean.getCurrentQuestion() == 0) {
            bt_preQuest.setEnabled(false);
        } else {
            bt_preQuest.setEnabled(true);
        }
        //设置是否已选择
        if (testBean.getAnswer(testBean.getCurrentQuestion()) > 0) {
            tv_ifSelected.setText("已选择 " + testBean.getAnwserInfo());
            tv_ifSelected.setTextColor(Color.parseColor("#4169E1"));
        } else {
            tv_ifSelected.setText("未作答");
            tv_ifSelected.setTextColor(Color.parseColor("#FF0000"));
        }
        //设置下一题按键
        if (testBean.getCurrentQuestion()  == testBean.getNumberOfQuestions() - 1) {
            bt_nextQuest.setText("提交");
            for (int i = 0; i < all; i++) {
                if (testBean.getAnswer(i) == 0) {
                    bt_nextQuest.setEnabled(false);
                    tv_ifCanSubmit.setVisibility(View.VISIBLE);
                    return;
                }
            }
            bt_nextQuest.setEnabled(true);
        } else {
            bt_nextQuest.setText("下一题");
            bt_nextQuest.setEnabled(true);
            tv_ifCanSubmit.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        long s = SystemClock.uptimeMillis();
        Log.v("QuestionareFragmentClickTime:", String.valueOf(s));
        TestActivity activity = (TestActivity) this.getActivity();
        switch (v.getId()) {
            case R.id.bt_answer1:
                testBean.doAnswer(1);
                break;
            case R.id.bt_answer2:
                testBean.doAnswer(2);
                break;
            case R.id.bt_answer3:
                testBean.doAnswer(3);
                break;
            case R.id.bt_answer4:
                testBean.doAnswer(4);
                break;
            case R.id.JBFS: {
                testBean.lastQuestion();
                setQuestion(testBean.getQuestion());
                System.out.println("up:" + testBean.getCurrentQuestion());
            }
            return;
            case R.id.DZSB:
                if (bt_nextQuest.getText() == "提交") {
                    Log.v("QuestionareFragment","test_id"+String.valueOf(testBean.getTest_id()));
                    activity.onAnswer(testBean);
                    return;
                }
                break;
        }
        if (testBean.getCurrentQuestion() < testBean.getNumberOfQuestions() - 1) {
            Log.e("yang", "now question is" + String.valueOf(testBean.getCurrentQuestion()));
            testBean.nextQuestion();//自动下一题
            setQuestion(testBean.getQuestion());
        } else {
            for (int i = 0; i < all; i++) {
                if (testBean.getAnswer(i) == 0) {
                    Log.e("yang", String.valueOf(testBean.getNumberOfQuestions()));
                    Log.e("yang", "some questions has not been answered!");
                    setQuestion(testBean.getQuestion());
                    return;
                }
            }
            testBean.lastQuestion();
            tv_ifSelected.setText("已选择 " + testBean.getAnwserInfo());
            tv_ifSelected.setTextColor(Color.parseColor("#4169E1"));
            bt_nextQuest.setEnabled(true);
            tv_ifCanSubmit.setVisibility(View.INVISIBLE);
            Log.e("yang", "last question number is :" + String.valueOf(testBean.getCurrentQuestion()));
        }
    }
}
