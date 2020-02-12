package com.mrdu.tests;

import com.mrdu.bean.QuestionBean;
import com.mrdu.bean.TestBean;
import com.mrdu.bean.TestID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Questionare {
	private List<QuestionBean> questions;
	private List<TestBean> testBeans;
	private int now;
	public int[] answer;//答案选项
	private String[] answerInfo;
	private int tid;


	private void loadQuestion() {
		questions.add(new QuestionBean("1", "我不感到悲伤。", "我感到悲伤。", "我始终悲伤，不能自制。", "我太悲伤或不愉快，不堪忍受。"));
		questions.add(new QuestionBean("2", "我对将来并不失望。", "对未来我感到心灰意冷。", "我感到前景黯淡。", "我觉得将来毫无希望，无法改善。"));
		questions.add(new QuestionBean("3", "我没有感到失败。", "我觉得比一般人失败要多些。", "回首往事，我能看到的是很多次失败。", "我觉得我是一个完全失败的人。"));
		questions.add(new QuestionBean("4", "我从各种事件中得到很多满足。", "我不能从各种事件中感受到乐趣。", "我不能从各种事件中得到真正的满足。", "我对一切事情不满意或感到枯燥无味。 "));
		questions.add(new QuestionBean("5", "我不感到有罪过。", "我在相当的时间里感到有罪过。", "我在大部分时间里觉得有罪。", "我在任何时候都觉得有罪。"));
		questions.add(new QuestionBean("6", "我没有觉得受到惩罚。", "我觉得可能会受到惩罚。", "我预料将受到惩罚。", "我觉得正受到惩罚。"));
		questions.add(new QuestionBean("7", "我对自己并不失望。", "我对自己感到失望。", "我讨厌自己。", "我恨自己。"));
		questions.add(new QuestionBean("8", "我觉得并不比其他人更不好。", "我要批判自己的弱点和错误。", "我在所有的时间里都责备自己的错误。", "我责备自己把所有的事情都弄坏了。"));
		questions.add(new QuestionBean("9", "我没有任何想弄死自己的想法。", "我有自杀想法，但我不会去做。", "我想自杀。", "如果有机会我就自杀。"));
		questions.add(new QuestionBean("10", "我哭泣与往常一样。", "我比往常哭得多。", "我一直要哭。", "我过去能哭，但现在要哭也哭不出来。"));
		questions.add(new QuestionBean("11", "和过去相比，我生气并不更多。", "我比往常更容易生气发火。", "我觉得所有的时间都容易生气。", "过去使我生气的事，目前一点也不能使我生气了。"));
		questions.add(new QuestionBean("12", "我对其他人没有失去兴趣。", "和过去相比，我对别人的兴趣减少了。", "我对别人的兴趣大部分失去了。", "我对别人的兴趣已全部丧失了。"));
		questions.add(new QuestionBean("13", "我能像平时一样做出决定。", "我推迟作出决定比过去多了。", "我作决定比以前困难大得多。", "我再也不能作出决定了。"));
		questions.add(new QuestionBean("14", "觉得我的外表看上去并不比过去更差。", "我担心自己看上去显得老了，没有吸引力。", "我觉得我的外貌有些变化，使我难看了。",
				"我相信我看起来很丑陋。"));
		questions.add(new QuestionBean("15", "我工作和以前一样好。", "要着手做事，我目前需额外花些力气。", "无论做什么我必须努力催促自己才行。", "我什么工作也不能做了。"));
		questions.add(new QuestionBean("16", "我睡觉与往常一样好。", "我睡眠不如过去好。", "我比往常早醒1～2小时，难以再睡。", "我比往常早醒几个小时，不能再睡。"));
		questions.add(new QuestionBean("17", "我并不感到比往常更疲乏。", "我比过去更容易感到疲乏无力。", "几乎不管做什么，我都感到疲乏无力。", "我太疲乏无力，不能做任何事情。"));
		questions.add(new QuestionBean("18", "我的食欲和往常一样。", "我的食欲不如过去好。", "我目前的食欲差得多了。", "我一点也没有食欲了。"));
		questions.add(new QuestionBean("19", "最近我的体重并无很大减轻。", "我体重下降2.27千克以上。", "我体重下降5.54千克以上。", "我体重下降7.81千克以上。"));
		questions.add(new QuestionBean("20", "我对健康状况并不比往常更担心。", "我担心身体上的问题，如疼痛、胃不适或便秘。", "我很担心身体问题，想别的事情很难。",
				"我对身体问题如此担忧，以致不能想其他任何事情。"));
		questions.add(new QuestionBean("21", "我没有发现自己对性的兴趣最近有什么变化。", "我对性的兴趣比过去降低了。", "我现在对性的兴趣大大下降。", "我对性的兴趣已经完全丧失。"));
	}

	private void loadGradualForm() {
		questions.add(new QuestionBean("ha1", "3", null, null, null));
		questions.add(new QuestionBean("ha7", "5", null, null, null));
		questions.add(new QuestionBean("ha3", "3", null, null, null));
		questions.add(new QuestionBean("sa5", "1", null, null, null));
		questions.add(new QuestionBean("sa3", "4", null, null, null));
		questions.add(new QuestionBean("sa6", "7", null, null, null));
		questions.add(new QuestionBean("an7", "1", null, null, null));
		questions.add(new QuestionBean("an2", "4", null, null, null));
		questions.add(new QuestionBean("an6", "7", null, null, null));

	}

	private void loadPointDetectForm() {
		questions.add(new QuestionBean("happy", "ha1", "ne1", "sa1",
				null));
		questions.add(new QuestionBean("normal", "ha2", "ne2", "sa2",
				null));
		questions.add(new QuestionBean("sad", "ha3", "ne3", "sa3",
				null));
		questions.add(new QuestionBean("happy", "ha4", "ne4", "sa4",
				null));
		questions.add(new QuestionBean("normal", "ha5", "ne5", "sa5",
				null));
		questions.add(new QuestionBean("sad", "ha6", "ne6", "sa6",
				null));
		questions.add(new QuestionBean("happy", "ha7", "ne7", "sa7",
				null));
		questions.add(new QuestionBean("normal", "ha3", "ne6", "sa1",
				null));
		questions.add(new QuestionBean("sad", "ha1", "ne7", "sa5",
				null));
		
	}

	private void loadHongForm(int i){

	}
	private void loadHongForm() {
		//TODO Auto-generated method stub
		questions.add(new QuestionBean("ha1", "1", null, null,null));
		questions.add(new QuestionBean("ha7", "1", null, null, null));
		questions.add(new QuestionBean("ha3", "1", null, null, null));
		questions.add(new QuestionBean("sa5", "3", null, null, null));
		questions.add(new QuestionBean("sa3", "3", null, null, null));
		questions.add(new QuestionBean("sa6", "3", null, null, null));
		questions.add(new QuestionBean("an7", "3", null, null, null));
		questions.add(new QuestionBean("an2", "3", null, null, null));
		questions.add(new QuestionBean("an6", "3", null, null, null));
	}
	private void loadShortForm(){
		questions.add(new QuestionBean("ha1", "1", null, null,null));
		questions.add(new QuestionBean("ha7", "1", null, null, null));
		questions.add(new QuestionBean("ha3", "1", null, null, null));
		questions.add(new QuestionBean("sa5", "3", null, null, null));
		questions.add(new QuestionBean("sa3", "3", null, null, null));
		questions.add(new QuestionBean("sa6", "3", null, null, null));
		questions.add(new QuestionBean("an7", "3", null, null, null));
		questions.add(new QuestionBean("an2", "3", null, null, null));
		questions.add(new QuestionBean("an6", "3", null, null, null));
	}
	private void loadStroopForm(){
		questions.add(new QuestionBean("ha1", "1", null, null,null));
		questions.add(new QuestionBean("ha7", "1", null, null, null));
		questions.add(new QuestionBean("ha3", "1", null, null, null));
		questions.add(new QuestionBean("sa5", "3", null, null, null));
		questions.add(new QuestionBean("sa3", "3", null, null, null));
		questions.add(new QuestionBean("sa6", "3", null, null, null));
		questions.add(new QuestionBean("an7", "3", null, null, null));
		questions.add(new QuestionBean("an2", "3", null, null, null));
		questions.add(new QuestionBean("an6", "3", null, null, null));
	}
	public Questionare(int tid) {
		this.tid = tid;
		questions = new ArrayList<QuestionBean>();
		switch (tid) {
		case TestID.BECK:
			loadQuestion();
			//System.out.println("Spider");
			break;
		case TestID.JBFS:
			loadGradualForm();
			//System.out.println("OH");
			break;
		case TestID.DTCFS:
			loadPointDetectForm();
			//System.out.println("WRONG");
			break;
		case TestID.HBQCS:
			loadHongForm();
			//System.out.println("HEY");
			break;
		case TestID.DZSB:
			loadShortForm();
			break;
		case TestID.STROOP:
			loadStroopForm();
			break;
		}
		Collections.shuffle(questions);
		now = -1;
		answer = new int[questions.size()];
	}
	
	public void doAnswer(int ans) {
		answer[now] = ans;
	}


	/*public void doAnswer(int ans,String ansinfo) {
		answer[now] = ans;
		answerInfo[now]= ansinfo;
	}*/

	public boolean next() {
		now += 1;
		if (now >= questions.size()) {
			return false;
		}
		return true;
	}

	public boolean last() {
		now --;
		if (now <0) {
			now++;
			return false;
		}
		return true;
	}
	

	public QuestionBean getQuestion() {
		if (now >= questions.size()) {
			return null;
		}
		return questions.get(now);
	}

	public int getNow() {
		return now;
	}

	public void setNow(int now) {
		this.now = now;
	}

	public int getNumOfQuestion(){
		return questions.size();
	}
	public int[] getAnswer() {
		return answer;
	}
	public String getAnwserInfo(){
		switch (answer[now]){
			case 1:
				return "A";
			case 2:
				return "B";
			case 3:
				return "C";
			case 4:
				return "D";
		}
		return "";
	}

	public double getScore() {
		double total = 0;
		switch (tid) {
		case TestID.BECK:
			for (int i = 0; i < answer.length; i++) {
				total += answer[i];
			}
			break;
		case TestID.JBFS:
			for (int i = 0; i < answer.length; i++) {
				total += Math.abs(answer[i] - Integer.parseInt(questions.get(i).answer1));
			}
			total /= questions.size();
			break;
		case TestID.HBQCS:
 
			for (int i = 0; i < answer.length; i++) {
				if(answer[i]==Integer.parseInt(questions.get(i).answer1))
				total += 5;
			}
			break;
		case TestID.DZSB:
			 
			for (int i = 0; i < answer.length; i++) {
				if(answer[i]==Integer.parseInt(questions.get(i).answer1))
				total += 5;
			}
			break;	
		case TestID.STROOP:
			 
			for (int i = 0; i < answer.length; i++) {
				if(answer[i]==Integer.parseInt(questions.get(i).answer1))
				total += 5;
			}
			break;	
		case TestID.DTCFS:
			int count_happy = 0;
			int count_normal = 0;
			int count_sad = 0;
			double total_happy = 0;
			double total_normal = 0;
			double total_sad = 0;
			for (int i = 0; i < answer.length; i++) {
				if (questions.get(i).question.equals("happy")) {
					count_happy++;
					total_happy += answer[i];
				} else if (questions.get(i).question.equals("normal")) {
					count_normal++;
					total_normal += answer[i];
				} else {
					count_sad++;
					total_sad += answer[i];
				}
			}
			double avg_sad = total_sad / count_sad;
			double avg_happy = total_happy / count_happy;
			double avg_normal = total_normal / count_normal;
			if (avg_sad > avg_happy) {
				total = 0;
			} else {
				total = (avg_happy - avg_sad) / ((avg_sad + avg_happy + avg_normal) / 3);
			}
			break;
		}
		return total;
	}
}
