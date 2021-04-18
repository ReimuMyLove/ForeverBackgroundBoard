package com.ouran.config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.jfinal.json.JFinalJson;

public class Test {
	public static void main(String[] args) {
		String str = "如果有来生 ? 要做一棵树\r\n\r\n站成永恒\r\n\r\n没有悲欢的姿势\r\n\r\n一半在尘土里安详\r\n\\r\\n一半在风里飞扬\\r\\n\\r\\n一半洒落荫凉\\r\\n\\r\\n一半沐浴阳光\\r\\n\\r\\n非常沉默 ? 非常骄傲\\r\\n\\r\\n从不依靠 ? 从不寻找\\r\\n\\r\\n如果有来生 ? 要化成一阵风\\r\\n\\r\\n一瞬间也能成为永恒\\r\\n\\r\\n没有善感的情怀\\r\\n\\r\\n没有多情的眼睛\\r\\n\\r\\n一半在雨里洒脱\\r\\n\\r\\n一半在春光里旅行\\r\\n\\r\\n寂寞了 ? 孤自去远行\\r\\n\\r\\n把淡淡的思念统带走\\r\\n\\r\\n从不思念 ? 从不爱恋\\r\\n\\r\\n如果有来生 ? 要做一只鸟\\r\\n\\r\\n飞越永恒 ? 没有迷途的苦恼\\r\\n\\r\\n东方有火红的希望 ? 南方有温暖的巢床\\r\\n\\r\\n向西逐退残阳 ? 向北唤醒芬芳\\r\\n\\r\\n如果有来生\\r\\n\\r\\n希望每次相遇\\r\\n\\r\\n都能化为永恒\\r\\n\\r\\n作者：清风自来\\r\\n链接：https://www.jianshu.com/p/73945c8c31ea\\r\\n";
		try {
			OutputStream out1 = new FileOutputStream("d:1.txt");
			byte[] br = str.getBytes();
			out1.write(br);
			System.out.println("接收文章成功");
			out1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		System.out.print(str);
		String json="{\"tuwendate\":[{\"isoriginal\":0,\"collectnum\":0,\"typeid\":3,\"id\":17,\"pic\":null,\"writer\":\"木心\",\"time\":\"2020-11-25 15:28:22\",\"text\":\"记得早先少年时\\r\\nRemember the old days\\r\\n大家诚诚恳恳\\r\\nwhen everyone was sincere\\r\\n说一句 是一句\\r\\none's word is one's bond\\r\\n清早上火车站\\r\\nThe dawn of the train station\\r\\n长街黑暗无行人\\r\\nthe dark street with no sign of pedestrian\\r\\n卖豆浆的小店冒着热气\\r\\nand the steam from the soy-milk stall\\r\\n从前的日色变得慢\\r\\nThe nightfalls used to come late in the past\\r\\n车，马，邮件都慢\\r\\nEverything was slow, the bike, the horse, and the post\\r\\n一生只够爱一个人\\r\\na lifetime was all but enough to be in love with one\\r\\n从前的锁也好看\\r\\nThe old-school lock was beautiful\\r\\n钥匙精美有样子\\r\\nand the key exquisite looking\\r\\n你锁了 人家就懂了\\r\\nonce locked, the others understood\\t\",\"tag\":null,\"title\":\"从前慢\",\"userid\":2,\"likes\":0},{\"isoriginal\":0,\"collectnum\":3,\"typeid\":3,\"id\":13,\"pic\":null,\"writer\":\"木心\",\"time\":\"2020-11-25 10:40:51\",\"text\":\"从前日子过的慢，一生只够爱一人\",\"tag\":null,\"title\":\"从前慢\",\"userid\":2,\"likes\":0},{\"isoriginal\":0,\"collectnum\":0,\"typeid\":3,\"id\":14,\"pic\":null,\"writer\":\"木心\",\"time\":\"2020-11-25 10:44:35\",\"text\":\"从前日子过的慢，一生只够爱一人\",\"tag\":null,\"title\":\"从前慢\",\"userid\":2,\"likes\":0},{\"isoriginal\":0,\"collectnum\":0,\"typeid\":3,\"id\":15,\"pic\":null,\"writer\":\"三毛\",\"time\":\"2020-11-25 10:48:23\",\"text\":\"如果有来生 ? 要做一棵树  站成永恒  没有悲欢的姿势  一半在尘土里安详  一半在风里飞扬  一半洒落荫凉  一半沐浴阳光  非常沉默 ? 非常骄傲  从不依靠 ? 从不寻找  如果有来生 ? 要化成一阵风  一瞬间也能成为永恒  没有善感的情怀  没有多情的眼睛  一半在雨里洒脱  一半在春光里旅行  寂寞了 ? 孤自去远行  把淡淡的思念统带走  从不思念 ? 从不爱恋  如果有来生 ? 要做一只鸟  飞越永恒 ? 没有迷途的苦恼  东方有火红的希望 ? 南方有温暖的巢床  向西逐退残阳 ? 向北唤醒芬芳  如果有来生  希望每次相遇  都能化为永恒\",\"tag\":null,\"title\":\"一棵开花的树\",\"userid\":2,\"likes\":0},{\"isoriginal\":0,\"collectnum\":0,\"typeid\":3,\"id\":16,\"pic\":null,\"writer\":\"木心\",\"time\":\"2020-11-25 11:07:36\",\"text\":\"如果有来生 ? 要做一棵树\\r\\n\\r\\n站成永恒\\r\\n\\r\\n没有悲欢的姿势\\r\\n\\r\\n一半在尘土里安详\\r\\n\\r\\n一半在风里飞扬\\r\\n\\r\\n一半洒落荫凉\\r\\n\\r\\n一半沐浴阳光\\r\\n\\r\\n非常沉默 ? 非常骄傲\\r\\n\\r\\n从不依靠 ? 从不寻找\\r\\n\\r\\n如果有来生 ? 要化成一阵风\\r\\n\\r\\n一瞬间也能成为永恒\\r\\n\\r\\n没有善感的情怀\\r\\n\\r\\n没有多情的眼睛\\r\\n\\r\\n一半在雨里洒脱\\r\\n\\r\\n一半在春光里旅行\\r\\n\\r\\n寂寞了 ? 孤自去远行\\r\\n\\r\\n把淡淡的思念统带走\\r\\n\\r\\n从不思念 ? 从不爱恋\\r\\n\\r\\n如果有来生 ? 要做一只鸟\\r\\n\\r\\n飞越永恒 ? 没有迷途的苦恼\\r\\n\\r\\n东方有火红的希望 ? 南方有温暖的巢床\\r\\n\\r\\n向西逐退残阳 ? 向北唤醒芬芳\\r\\n\\r\\n如果有来生\\r\\n\\r\\n希望每次相遇\\r\\n\\r\\n都能化为永恒\\r\\n\\r\\n作者：清风自来\\r\\n链接：https://www.jianshu.com/p/73945c8c31ea\",\"tag\":null,\"title\":\"从前慢\",\"userid\":2,\"likes\":0}]}";
		
	}
}
