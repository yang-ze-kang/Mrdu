package com.mrdu;

import android.location.LocationListener;
import android.os.Bundle;
import android.app.Activity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.mrdu.bean.JsonBean;
import com.mrdu.util.GetJsonDataUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PerfectInforActivity extends Activity implements View.OnClickListener {

    //控件
    private EditText etName;
    private EditText etPhone;
    private EditText etOccupation;
    private TextView tvSex;
    private TextView tvBirth;
    private TextView tvLocal;
    private Button btOk;

    ArrayList<JsonBean> optionsItem1 = new ArrayList<>();
    ArrayList<ArrayList<String>> optionsItem2 = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<String>>> optionsItem3 = new ArrayList<>();

    //信息
    private String inforName;
    private String inforSex;
    private String inforOccupation;
    private String inforProvince;
    private String inforCity;
    private String inforArea;
    private String inforPhone;
    private int inforBirthYear;
    private int inforBirthMonth;
    private int inforBirthDay;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_infor);
        initView();
        initData();
    }

    private void initData() {
        String jsonData = new GetJsonDataUtil().getJson(this, "province.json");
        ArrayList<JsonBean> jsonBean = parseData(jsonData);
        optionsItem1 = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {
            ArrayList<String> cityList = new ArrayList<>();
            ArrayList<ArrayList<String>> provinceAreaList = new ArrayList<>();
            for (int j = 0; j < jsonBean.get(i).getCityList().size(); j++) {
                String cityName = jsonBean.get(i).getCityList().get(j).getName();
                cityList.add(cityName);
                ArrayList<String> cityAreaList = new ArrayList<>();
                if (jsonBean.get(i).getCityList().get(j).getArea() == null ||
                        jsonBean.get(i).getCityList().get(j).getArea().size() == 0) {
                    cityAreaList.add("");
                } else {
                    cityAreaList.addAll(jsonBean.get(i).getCityList().get(j).getArea());
                }
                provinceAreaList.add(cityAreaList);
            }
            optionsItem2.add(cityList);
            optionsItem3.add(provinceAreaList);
        }

    }

    //初始化控件
    private void initView() {
        etName = (EditText) findViewById(R.id.et_name);
        etPhone = (EditText) findViewById(R.id.et_phone);
        tvSex = (TextView) findViewById(R.id.tv_sex);
        tvBirth = (TextView) findViewById(R.id.tv_birth);
        etOccupation = (EditText) findViewById(R.id.et_occupation);
        tvLocal = (TextView) findViewById(R.id.tv_local);
        btOk = (Button) findViewById(R.id.bt_ok);
        etName.setOnClickListener(this);
        etPhone.setOnClickListener(this);
        tvSex.setOnClickListener(this);
        tvBirth.setOnClickListener(this);
        etOccupation.setOnClickListener(this);
        tvLocal.setOnClickListener(this);
        btOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sex:
                showSexPickerView();
                break;
            case R.id.tv_birth:
                showTimePickerView();
                break;
            case R.id.tv_local:
                showLocalPickerView();
                break;
            case R.id.bt_ok:
                postInformation();
                break;
        }
    }

    //提交个人信息
    private void postInformation() {
        inforName = String.valueOf(etName.getText());
        inforOccupation = String.valueOf(etOccupation.getText());
        inforPhone = String.valueOf(etPhone.getText());
        JSONObject infor = new JSONObject();
        try {
            infor.put("name", inforName);
            infor.put("sex", inforSex);

            JSONObject birth = new JSONObject();
            birth.put("year", inforBirthYear);
            birth.put("month", inforBirthMonth);
            birth.put("day", inforBirthDay);
            infor.put("birthday", birth);

            JSONObject local = new JSONObject();
            local.put("province", inforProvince);
            local.put("city", inforCity);
            local.put("area", inforArea);

            infor.put("local", local);
            infor.put("occupation", inforOccupation);
            infor.put("phone", inforPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("PerfectInforActivity", "post:" + infor);

//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(infor));
//        Request request = new Request.Builder()
//                .url()
//                .post(requestBody)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
    }

    //选择所在地
    private void showLocalPickerView() {


        OptionsPickerView pvPickerView = new OptionsPickerBuilder(PerfectInforActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                inforProvince = optionsItem1.get(options1).getPickerViewText();
                inforCity = optionsItem2.get(options1).get(options2);
                inforArea = optionsItem3.get(options1).get(options2).get(options3);
                tvLocal.setText(inforProvince + inforCity + inforArea);
            }
        })
                .setTitleText("选择所在地")
                .setTitleSize(20)
                .build();
        pvPickerView.setPicker(optionsItem1, optionsItem2, optionsItem3);
        pvPickerView.show();

    }

    //选择性别
    private void showSexPickerView() {
        final ArrayList<String> sexItems = new ArrayList<>();
        sexItems.add("男");
        sexItems.add("女");
        OptionsPickerView pvOptions = new OptionsPickerBuilder(PerfectInforActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tvSex.setText(sexItems.get(options1));
                inforSex = sexItems.get(options1);
            }
        }).setTitleText("性别")
                .setTitleSize(18)
                .build();
        pvOptions.setPicker(sexItems);
        pvOptions.show();
    }

    //选择日期
    private void showTimePickerView() {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        Date date = new Date();
        int year = Integer.parseInt(String.valueOf(DateFormat.format("yyyy", date)));
        int month = Integer.parseInt(String.valueOf(DateFormat.format("MM", date)));
        int day = Integer.parseInt(String.valueOf(DateFormat.format("dd", date)));
        startDate.set(year - 120, month, day);
        endDate.set(year, month, day);
        TimePickerView pvTime = new TimePickerBuilder(PerfectInforActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (judgeBirthday(date)) {
                    tvBirth.setText(DateFormat.format("yyyy年MM月dd日", date));
                    inforBirthYear = Integer.parseInt(String.valueOf(DateFormat.format("yyyy", date)));
                    inforBirthMonth = Integer.parseInt(String.valueOf(DateFormat.format("MM", date)));
                    inforBirthDay = Integer.parseInt(String.valueOf(DateFormat.format("dd", date)));
                } else {
                    showText("请输入您的真实生日");
                }

            }
        })
                .setTitleText("生日")
                .setRangDate(startDate, endDate)
                .build();
        pvTime.show();
    }

    private boolean judgeBirthday(Date birth) {
        Date date = new Date();
        long nowDate = date.getTime();
        String year = (String) DateFormat.format("yyyy", birth);
        Log.v("PerfectInforActivity", "birth year:" + year);
        if (Integer.parseInt(year) >= Integer.parseInt((String) DateFormat.format("yyyy", nowDate))) {
            return false;
        } else {
            return true;
        }

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    private void showText(String text) {
        Toast.makeText(PerfectInforActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
