package com.mrdu.net.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.mrdu.bean.ForumBean;
import com.mrdu.helper.MyDatabase;
import com.mrdu.net.IForumUtil;
import com.mrdu.net.MyException;

public class ForumUtilSQL implements IForumUtil {
	private MyDatabase db;

	public ForumUtilSQL(Context context) {
		try {
			db = new MyDatabase(context);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public List<ForumBean> getAll() throws MyException {
		SQLiteDatabase wdb = db.getReadableDatabase();
		List<ForumBean> list = new ArrayList<ForumBean>();
		Cursor cs;
		try {
			cs = wdb.query(false, "forumbean", null, "toid is  null", null,
					null, null, null, null, null);
		} catch (Exception e) {
			throw new MyException("獲取帖子列表失败");
		}
		while (cs.moveToNext()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ForumBean forumBean;
			try {
				forumBean = new ForumBean(cs.getInt(0), cs.getInt(1),
						cs.getString(2), cs.getString(3), cs.getString(4),
						cs.getInt(5), sdf.parse(cs.getString(6)), 0);
				list.add(forumBean);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return list;
	}

	@Override
	public List<ForumBean> getById(int id) throws MyException {
		// TODO Auto-generated method stub
		SQLiteDatabase wdb = db.getReadableDatabase();
		List<ForumBean> list = new ArrayList<ForumBean>();
		Cursor cs;
		try {
			cs = wdb.query(false, "forumbean", null, "toid = " + id, null,
					null, null, null, null, null);
		} catch (Exception e) {
			throw new MyException("獲取帖子列表失败");
		}
		while (cs.moveToNext()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			cs.getString(6);
			ForumBean forumBean;
			try {
				forumBean = new ForumBean(cs.getInt(0), cs.getInt(1),
						cs.getString(2), cs.getString(3), cs.getString(4),
						cs.getInt(5), sdf.parse(cs.getString(6)), 0);
				list.add(forumBean);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public void put(ForumBean fb) throws MyException {
		// TODO Auto-generated method stub
		SQLiteDatabase wdb = db.getWritableDatabase();
		try {
			wdb.execSQL(
					"insert into post(uid,title,text,toid,postdate) values(?,?,?,?,datetime())",
					new Object[] { fb.uid, fb.title, fb.text,
							fb.to == 0 ? null : fb.to });
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("發帖失败");
		}
	}

	@Override
	public void deleteById(int id) throws MyException {
		// TODO Auto-generated method stub
		SQLiteDatabase wdb = db.getWritableDatabase();
		try {
			wdb.execSQL("update  post set isdeleted = 2 where id = ?",
					new Object[] { id });
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("删帖失败");
		}
	}

	@Override
	public void update(ForumBean fb) throws MyException {
		// TODO Auto-generated method stub
		SQLiteDatabase wdb = db.getWritableDatabase();
		try {
			wdb.execSQL("update  post set title = ? , text = ?  where id = ?",
					new Object[] { fb.title, fb.text, fb.id });
		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("修改失败");
		}
		try {
			newsAdd();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void newsAdd() throws MyException {
		try {
			SQLiteDatabase wdb = db.getWritableDatabase();
			wdb.execSQL(
					"insert into news(title,text,postdate) values(?,?,datetime())",
					new Object[] {
							"抑郁症能治好吗",
							" 「经常有朋友问我：抑郁症能治好吗？」\r\n"
									+ "\r\n"
									+ "「我回答说能啊，很多抑郁症患者都治好了呀。」\r\n"
									+ "\r\n"
									+ "其实，我知道，这种回答并不准确，问题没这么简单。但由于平时时间有限，不能长篇大论，也只能这样应付了。\r\n"
									+ "\r\n"
									+ "但是我心里一直担心会有人误解，所以今天忙里偷闲，和大家分享一下：\r\n"
									+ "\r\n"
									+ "▼\r\n"
									+ "\r\n"
									+ "一、怎样算“治好”？\r\n"
									+ "\r\n"
									+ "要回答“抑郁症能治好吗？”这个问题，首先得知道，什么样算“治好”。\r\n"
									+ "\r\n"
									+ "对医生来说，只要抑郁症状消失，社会功能和生活质量恢复，并持续两周以上，就算“临床治愈”了，如果持续半年以上便称为“痊愈”。我们认为这两种情况已经可以算“治好”了----即使他还在服药，即使他将来可能还会复发。\r\n"
									+ "\r\n"
									+ "可能很多人并不认同此观点，他们觉得没停药就不能算“治好”，不能保证将来不再复发也不能算“治好”。\r\n"
									+ "\r\n"
									+ "可见，医生和患者之间的理解可能有较大差异，导致沟通上的误解。\r\n"
									+ "\r\n"
									+ "所以后来我再回答这个问题的时候，常常会尽量说全面一些，我说：抑郁症能治好，但很多患者可能需要维持治疗，预防复发。\r\n"
									+ "\r\n"
									+ "▼\r\n"
									+ "\r\n"
									+ "二、医生并不能保证所有抑郁症都治好\r\n"
									+ "\r\n"
									+ "其实上面的回答仍然不全面，因为医生并不能保证治好所有的抑郁症患者。\r\n"
									+ "\r\n"
									+ "既然如此，那还何必去看医生呢？\r\n"
									+ "\r\n"
									+ "道理很简单，因为看医生的患者最终能治好的概率更高。\r\n"
									+ "\r\n"
									+ "据统计，经过规范治疗，可以缩短抑郁症的病程，减轻抑郁症状，大约60%-70%的患者服用第一种抗抑郁药后就会有明显缓解，再加上后续的调整治疗，总体有效率能达到百分之八九十。这肯定比在家坚持“自我调节”的有效率高很多。\r\n"
									+ "\r\n"
									+ "千万不要因为某个人宣称通过某种“自我调节”技术而战胜了抑郁症，你就也跟着去“自我调节”。\r\n"
									+ "\r\n"
									+ "就像不能因为韩寒退学了还能成功，你就也跟着退学一样。\r\n"
									+ "\r\n"
									+ "他能成功可能有他的独特因素，咱们没有人家的“基因”，就不要妄想复制他们的成就了。\r\n"
									+ "\r\n"
									+ "想治好抑郁症，对大多数人来说，到正规医院诊治，接受规范治疗，是最正确的选择。\r\n"
									+ "\r\n"
									+ "▼\r\n"
									+ "\r\n"
									+ "总结一下：\r\n"
									+ "\r\n"
									+ "抑郁症能“治好”，但不能保证“治好”，规范治疗能提高治愈率。那些听医生话，接受规范治疗的患者，“治好”的机会更大。就像那些听老师话，认真学习的学生，考上大学的机会更大一样。\r\n"
									+ "\r\n"
									+ "当然，这里的医生是指合格的精神科医生或心理医生，不包括某些骗子医院的广告医生。 " });
			wdb.execSQL(
					"insert into news(title,text,postdate) values(?,?,datetime())",
					new Object[] {
							"你有多久没睡好觉了？",
							" 说起来，我们的社会越来越进步，烦恼应该越来越少才对。\r\n"
									+ "\r\n"
									+ "事实却恰恰相反！\r\n"
									+ "\r\n"
									+ "令人烦恼的事似乎越来越多了，失眠便是其中之一。\r\n"
									+ "\r\n"
									+ "对于失眠，人们的认识出现了两个极端：一些人长期失眠也不去看医生，怕医生开的安眠药会害了他，比如导致成瘾、依赖，出现严重副反应等。\r\n"
									+ "\r\n"
									+ "这当然是很大的误区，我们以后会详谈。今天重点讨论的是另一个极端。\r\n"
									+ "\r\n"
									+ "这些人过分关注睡眠质量，一旦失眠就大惊小怪。\r\n"
									+ "\r\n"
									+ "甚至有些人！\r\n"
									+ "\r\n"
									+ "只要一两个晚上睡不着，就像世界末日来临了一样，急得像热锅上的蚂蚁，想着：这样下去可怎么得了，身体不就垮了吗？\r\n"
									+ "\r\n"
									+ "然后自己脑补了今后的景象：脸色晦暗、黑眼圈、没精神，学习成绩下降，完不成工作，高血压、糖尿病、癌症.....不得了，肯定活不长了。\r\n"
									+ "\r\n"
									+ "然后就一把鼻涕一把泪。\r\n"
									+ "\r\n"
									+ "向医生哭诉着自己如何被失眠所折磨，真是人不像人鬼不像鬼了，逼着医生给他开“安眠药”。\r\n"
									+ "\r\n"
									+ "其实啊，真的大可不必！\r\n"
									+ "\r\n"
									+ "短期失眠对身体健康影响不大，长期失眠才有明确的危害，而且这危害也是可防可治的。\r\n"
									+ "\r\n"
									+ "我们每个人都有过失眠，只要你不理他，自然就好了。\r\n"
									+ "\r\n"
									+ "比如！\r\n"
									+ "\r\n"
									+ "明天要考试、要参加面试，那今晚睡不着就是常见的现象，这不算异常，考完试就能睡着了，通常不需要治疗。\r\n"
									+ "\r\n"
									+ "要知道，睡眠可是人的本能，不需要你付出努力自然就会发生。\r\n"
									+ "\r\n"
									+ "这和工作学习不一样。\r\n"
									+ "\r\n"
									+ "努力工作业绩会提高，“努力”睡觉却只会适得其反，导致失眠。因为你越努力，越是在违反睡眠的自然规律。\r\n"
									+ "\r\n"
									+ "所谓“世间本无事，庸人自扰之”。\r\n"
									+ "\r\n"
									+ "我常在想，像猫啊、狗啊之类的动物，它们头脑比较简单，不会知道失眠的危害，倒也很少失眠，至少我还没听说过谁家的猫狗被失眠所困扰。\r\n"
									+ "\r\n"
									+ "这或许能给我们一些启示。\r\n"
									+ "\r\n"
									+ "对很多人来说，“担心失眠”本身就是导致长期失眠的一个重要原因。你越是担心失眠，就越是会失眠。\r\n"
									+ "\r\n"
									+ "你不担心失眠，失眠也就懒得理你了。\r\n"
									+ "\r\n"
									+ "所以，想战胜失眠，首先得不怕失眠，这是必要的心理准备。\r\n"
									+ "\r\n"
									+ "不要一失眠就觉得！\r\n"
									+ "\r\n"
									+ "诶呀，天要塌下来了，就急着用安眠药。当然了，我们也不要排斥安眠药，关键看是否真的“需要”。\r\n"
									+ "\r\n" + "有人会说，失眠太可怕了，怎么可能不怕呢？ " });
			wdb.execSQL(
					"insert into news(title,text,postdate) values(?,?,datetime())",
					new Object[] {
							"产后抑郁症的十个误区",
							"误区一：产后抑郁是很正常的――所有的新妈妈都会感到疲惫和抑郁.正解：新妈妈经常会感到疲劳和力不从心。她们或许会经历一段叫做“宝宝综合症”的心路历程。有这种综合症的妇女会感到疲累，没有精力。但是，产后抑郁症是一种情感更强烈的，持续时间更长的心理障碍。有产后抑郁症的妈妈或许会不想和自己的宝宝玩耍。她或许会感到难以集中精神，不能给宝宝足够的温暖和爱护。她会因此而感到内疚。　\r\n"
									+ "\r\n"
									+ "　　误区二：如果你在分娩之后，没有立即患上产后抑郁症，那么，你就不会再患上它了。正解：产后抑郁症会在分娩后的1年内随时发作。\r\n"
									+ "\r\n"
									+ "　　误区三：产后抑郁会不药而愈。正解：“宝宝综合症”会大概持续4个星期，并自自动痊愈。但产后抑郁和其他疾病一样，不经过治疗几乎是不能痊愈的。但好消息是，有很多办法能治愈这个病。\r\n"
									+ "\r\n"
									+ "　　误区四：患有产后抑郁的女性都会有虐儿倾向正解：产后抑郁跟产后精神病不同。产后精神病患者会对生命造成威胁，她们可能会自虐，或者虐儿。如果你感到有这种心理倾向，那么就立刻向家人和医生寻求帮助。\r\n"
									+ "\r\n"
									+ "　　误区五：产后抑郁症患者都会看起来很抑郁，停止照顾自己。正解：你不能单从一个人的外表就看出她是否是产后抑郁症的患者。产后抑郁症的患者或许看起来与常人无异。她会努力使自己看起来很光鲜，并努力地化好妆之类的。通过对外表做修饰来转移她内心的痛苦。\r\n"
									+ "\r\n"
									+ "　　误区六：有产后抑郁的妈妈都不会是好妈妈正解：产后抑郁不会是任何女性变成失职的妈妈。\r\n"
									+ "\r\n"
									+ "　　误区七：一定是做错了什么事，才会患上产后抑郁。正解：患上产后抑郁症，不是任何人的错。没有什么能预防患上这种抑郁症。\r\n"
									+ "\r\n"
									+ "　　误区八：补足睡眠，就能从产后抑郁中康复正解：尽管补充睡眠对产后抑郁的患者来说很重要，但是，单单睡眠不能治愈产后抑郁。\r\n"
									+ "\r\n"
									+ "　　误区九：当妇女进入哺乳期时，她们不能服用抗抑郁药正解：调查发现，孩子从母乳中吃到抗抑郁药的可能性很少。当产后抑郁患者需要服用抗抑郁药的时候，她的医生会小心翼翼选择最能帮助她的药，同时不会对宝宝造成伤害的药。\r\n"
									+ "\r\n"
									+ "　　误区十：孕妇或者曾经有分娩经验的妇女不会感到抑郁。正解：怀孕或者有分娩经验并不能保证妇女不会患上抑郁。换而言之，怀孕不会帮助妇女抵抗抑郁，而事实上，正在怀孕的妇女更可能会感到压抑。" });

		} catch (Exception e) {
			e.printStackTrace();
			throw new MyException("发布失败");
		}
	}
}
