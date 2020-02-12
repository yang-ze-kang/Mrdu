package com.mrdu.bean;

import java.io.Serializable;

public class TestResultBean implements Serializable {

    private int user_id;//测试用户
    public int test_id;//测试类型ID
    public String test_type = null;//测试类型
    /*测试类型：
            JBFS    渐变范式
            DTCFS   点探测范式
            BECK    贝克抑郁量表
            HBQCS   宏表情测试
            STROOP  Stroop测试
            DZSB    短暂识别
    * */
    public double test_score;//测试总分数
    public String user_option = null;//用户选项1234分别代表ABCD 以|分隔
    public String picture_id = null;//测试图片id,以|分隔
    public String reaction_time = null;//用户每道题反应毫秒数,以|分隔

    public TestResultBean(String test_type) {
        this.test_type = test_type;
    }

    public TestResultBean(TestResultBean testResult) {
        this.user_id = testResult.user_id;
        this.test_id = testResult.test_id;
        this.test_type = testResult.test_type;
        this.test_score = testResult.test_score;
        this.picture_id = testResult.picture_id;
        this.user_option = testResult.user_option;
        this.reaction_time = testResult.reaction_time;
    }


    //设置测试结果
    public void setResult(TestBean result) {
        user_id = result.getUser_id();//用户ID
        test_id = result.getTest_id();//测试ID
        test_score = result.getTotalScore();//总分
        setUser_option(result.answers);//用户选项
        if (result.getTest_id() != TestID.BECK) {
            setReaction_time(result.time);//反应时间
            setPicture_id(result.getPictureIds());//测试图片ID
        }
    }


    /*
     * 测试结果格式化
     * */
    //设置用户每道题反应的毫秒数
    public void setReaction_time(long time[]) {
        reaction_time = "";
        for (int i = 0; i < time.length - 1; i++)
            reaction_time += String.valueOf(time[i]) + '|';
        reaction_time += String.valueOf(time[time.length - 1]);
    }

    //设置用户选择答案
    public void setUser_option(int x[]) {
        user_option = "";
        for (int i = 0; i < x.length - 1; i++)
            user_option += String.valueOf(x[i]) + '|';
        user_option += String.valueOf(x[x.length - 1]);
    }

    //设置图片id
    public void setPicture_id(int x[]) {
        picture_id = "";
        for (int i = 0; i < x.length - 1; i++)
            picture_id += String.valueOf(x[i]) + '|';
        picture_id += String.valueOf(x[x.length - 1]);
    }
}
