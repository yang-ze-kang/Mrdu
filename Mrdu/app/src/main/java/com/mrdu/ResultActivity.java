package com.mrdu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrdu.bean.ResultBean;
import com.mrdu.bean.TestID;
import com.mrdu.bean.UserBean;
import com.mrdu.net.IResultUtil;
import com.mrdu.net.MyException;
import com.mrdu.net.impl.ResultUtilSQL;
import com.mrdu.util.MyApplication;
import com.mrdu.view.MyBackTitleBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ResultActivity extends Activity implements OnClickListener {

    UserBean user;
    IResultUtil result;
    Context mContext = this;
    Button bt_dtcfs;
    Button bt_dzsb;
    Button bt_jbfs;
    Button bt_qxmm;
    Button bt_sycs;
    // Button bt_wjdc;
    TextView tvTitle;
    WebView webview;
    double score = 0;
    List<ResultBean> wjdc = new ArrayList<ResultBean>();
    List<ResultBean> jbfs = new ArrayList<ResultBean>();
    List<ResultBean> dtcfs = new ArrayList<ResultBean>();
    int select = 3;
    private MyBackTitleBar myBackTitleBar;


    //  public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        InitData();
        InitView();
        InitEvent();

        result = new ResultUtilSQL(mContext);
        List<ResultBean> resultlist = new ArrayList<ResultBean>();
        try {
            resultlist = result.getResultsById(user.user_id);
        } catch (MyException e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (Exception ee) {
            ee.printStackTrace();

        }

        Comparator<ResultBean> cpt = new Comparator<ResultBean>() {

            @Override
            public int compare(ResultBean o1, ResultBean o2) {
                return o1.id - o2.id;
            }
        };

        for (ResultBean rb : resultlist) {
            switch (rb.tid) {
                case TestID.BECK:
                    wjdc.add(rb);
                    break;
                case TestID.JBFS:
                    jbfs.add(rb);
                    break;
                case TestID.DTCFS:
                    dtcfs.add(rb);

            }
        }
        if (!wjdc.isEmpty()) {
            Collections.sort(wjdc, cpt);
            double s = wjdc.get(wjdc.size() - 1).score;
            if (s <= 10) {
                s = (100 - s);
            } else if (s <= 15 && s > 10) {
                s = (100 - (10 + 4 * (s - 10)));
            } else if (s > 15 && s <= 25) {

                s = (100 - (30 + 2 * (s - 15)));
            } else {
                s = (100 - (50 + 50.0 / (63 - 25) * (s - 25)));
            }
            score += s;
        } else {
            score += 0;
        }
        if (!jbfs.isEmpty()) {
            Collections.sort(jbfs, cpt);
            double s = jbfs.get(jbfs.size() - 1).score;
            s = 100 - (s / 3.5) * 100;
            if (s > 0) {
                score += s;
            } else {
                score += 0;
            }

        } else {
            score += 0;
        }
        if (!dtcfs.isEmpty()) {
            Collections.sort(dtcfs, cpt);
            double s = dtcfs.get(dtcfs.size() - 1).score;
            if (s <= 1) {
                s = 100 - (s * 100);
            } else {
                s = 0;
            }
            score += s;
        } else {
            score += 0;
        }
        score = score / 3;
    }

    private void InitEvent() {
        // 设置点击事件
        bt_dtcfs.setOnClickListener(this);
        bt_dzsb.setOnClickListener(this);
        bt_jbfs.setOnClickListener(this);
        bt_qxmm.setOnClickListener(this);
        bt_sycs.setOnClickListener(this);
    }

    private void InitData() {
        MyApplication app = (MyApplication) ResultActivity.this.getApplication();
        user = app.getUser();
    }

    private void InitView() {
        //初始化标题
        myBackTitleBar = (MyBackTitleBar) findViewById(R.id.my_back_title);
        myBackTitleBar.setTitle("结果");
        webview = (WebView) findViewById(R.id.webview);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("http://118.190.201.76:8888/score/" + String.valueOf(user.user_id) + "/SCORE/");
        bt_dtcfs = (Button) findViewById(R.id.JBFS);
        bt_dzsb = (Button) findViewById(R.id.DZSB);
        bt_jbfs = (Button) findViewById(R.id.DTCFS);
        bt_qxmm = (Button) findViewById(R.id.HBQCS);
        bt_sycs = (Button) findViewById(R.id.STROOP);
        tvTitle = (TextView) findViewById(R.id.tv_test_type);
        tvTitle.setText("测试结果");
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//            case R.id.personal:
//                startActivity(new Intent(mContext, PersonalActivity.class));
//                return true;
//        }
//        return true;
//    }

    public void onClick(View v) {
        String re = 0 + "";
        switch (v.getId()) {
            case R.id.JBFS:
                webview.loadUrl("http://118.190.201.76:8888/score/" + String.valueOf(user.user_id) + "/JBFS/");
                tvTitle.setText("渐变范式");
                if (!jbfs.isEmpty()) {
                    double s = jbfs.get(jbfs.size() - 1).score;
                    s = 100 - (s / 3.5) * 100;
                    s = 21.5;
                    if (s <= 100 && s > 75) {
                        re = s + "  你很健康！";
                    } else if (s <= 75 && s > 60) {
                        re = s + "  你有轻度的情绪不良，请注意调解！";
                    } else if (s <= 60 && s > 15) {
                        re = s + "  你有轻度的抑郁倾向，请即时去看心理医生！";

                    } else if (s < 15) {
                        re = s + "  你有严重的抑郁症，必须看心理医生接受治疗！";
                    } else {
                        s = 0;
                        re = s + "  你有严重的抑郁症，必须看心理医生接受治疗！";
                    }

                }
                //   textviewscore.setText(re + "");
                break;
            case R.id.DZSB:
                webview.loadUrl("http://118.190.201.76:8888/score/" + String.valueOf(user.user_id) + "/HBQCS/");
                tvTitle.setText("短暂识别");
                break;
            case R.id.DTCFS:
                tvTitle.setText("点探测范式");
                webview.loadUrl("http://118.190.201.76:8888/score/" + String.valueOf(user.user_id) + "/DTCFS/");
                if (!dtcfs.isEmpty()) {
                    // String re;
                    double s = dtcfs.get(dtcfs.size() - 1).score;
                    if (s <= 1) {
                        s = 100 - (s * 100);

                        if (s <= 100 && s >= 90) {
                            re = s + "  你很健康！";
                        } else if (s < 90 && s >= 70) {
                            re = s + "  你有轻度的情绪不良，请注意调解！";
                        } else if (s < 70 && s >= 50) {
                            re = s + "  你有轻度的抑郁倾向，请即时去看心理医生！";
                        } else {
                            re = s + "  你有严重的抑郁症，必须看心理医生接受治疗！";
                        }

                    } else {
                        re = 0 + "  你有严重的抑郁症，必须看心理医生接受治疗！";
                    }
                    //     textviewscore.setText(re);
                }
                break;
            case R.id.HBQCS:
                tvTitle.setText("宏表情测试");
                webview.loadUrl("http://118.190.201.76:8888/score/" + String.valueOf(user.user_id) + "/HBQCS/");
                break;
            case R.id.STROOP:
                tvTitle.setText("Stroop范式");
                webview.loadUrl("http://118.190.201.76:8888/score/" + String.valueOf(user.user_id) + "/STROOP/");
                break;
//            case R.id.button6:
//
//                texttitle.setText("量表测试情况");
//                webview.loadUrl("file:///android_asset/echarts7.html");
//                if (!wjdc.isEmpty()) {
//                    // String re;
//                    re = "您通过了三项量表测试，其中汉密尔顿量表得分" + re;
//                    double s = wjdc.get(wjdc.size() - 1).score;
//                    if (s <= 10) {
//                        re = (100 - s) + "  你很健康！";
//                    } else if (s <= 15 && s > 10) {
//                        re = (100 - (10 + 4 * (s - 10))) + "  你有轻度的情绪不良，请注意调解！";
//                    } else if (s > 15 && s <= 25) {
//
//                        re = (100 - (30 + 2 * (s - 15))) + "  你有轻度的抑郁倾向，请即时去看心理医生！";
//                    } else {
//                        re = (100 - (50 + 50.0 / (63 - 25) * (s - 25))) + "  你有严重的抑郁症，必须看心理医生接受治疗！";
//                    }
//                    re = "您通过了三项量表测试，其中汉密尔顿量表得分" + "43,你有轻度的情绪不良，请注意调解！";
//                    textviewscore.setText(re);
//                } else {
//                    textviewscore.setText(re);
//                }
//                break;
            //case R.user_id.imageView2:
            //	textviewscore.setText(test_score+"");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
