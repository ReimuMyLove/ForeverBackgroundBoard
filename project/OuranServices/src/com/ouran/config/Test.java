package com.ouran.config;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.jfinal.json.JFinalJson;

public class Test {
	public static void main(String[] args) {
		String str = "��������� ? Ҫ��һ����\r\n\r\nվ������\r\n\r\nû�б���������\r\n\r\nһ���ڳ����ﰲ��\r\n\\r\\nһ���ڷ������\\r\\n\\r\\nһ����������\\r\\n\\r\\nһ����ԡ����\\r\\n\\r\\n�ǳ���Ĭ ? �ǳ�����\\r\\n\\r\\n�Ӳ����� ? �Ӳ�Ѱ��\\r\\n\\r\\n��������� ? Ҫ����һ���\\r\\n\\r\\nһ˲��Ҳ�ܳ�Ϊ����\\r\\n\\r\\nû���Ƹе��黳\\r\\n\\r\\nû�ж�����۾�\\r\\n\\r\\nһ������������\\r\\n\\r\\nһ���ڴ���������\\r\\n\\r\\n��į�� ? ����ȥԶ��\\r\\n\\r\\n�ѵ�����˼��ͳ����\\r\\n\\r\\n�Ӳ�˼�� ? �Ӳ�����\\r\\n\\r\\n��������� ? Ҫ��һֻ��\\r\\n\\r\\n��Խ���� ? û����;�Ŀ���\\r\\n\\r\\n�����л���ϣ�� ? �Ϸ�����ů�ĳ���\\r\\n\\r\\n�������˲��� ? �򱱻��ѷҷ�\\r\\n\\r\\n���������\\r\\n\\r\\nϣ��ÿ������\\r\\n\\r\\n���ܻ�Ϊ����\\r\\n\\r\\n���ߣ��������\\r\\n���ӣ�https://www.jianshu.com/p/73945c8c31ea\\r\\n";
		try {
			OutputStream out1 = new FileOutputStream("d:1.txt");
			byte[] br = str.getBytes();
			out1.write(br);
			System.out.println("�������³ɹ�");
			out1.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		System.out.print(str);
		String json="{\"tuwendate\":[{\"isoriginal\":0,\"collectnum\":0,\"typeid\":3,\"id\":17,\"pic\":null,\"writer\":\"ľ��\",\"time\":\"2020-11-25 15:28:22\",\"text\":\"�ǵ���������ʱ\\r\\nRemember the old days\\r\\n��ҳϳϿҿ�\\r\\nwhen everyone was sincere\\r\\n˵һ�� ��һ��\\r\\none's word is one's bond\\r\\n�����ϻ�վ\\r\\nThe dawn of the train station\\r\\n���ֺڰ�������\\r\\nthe dark street with no sign of pedestrian\\r\\n��������С��ð������\\r\\nand the steam from the soy-milk stall\\r\\n��ǰ����ɫ�����\\r\\nThe nightfalls used to come late in the past\\r\\n�������ʼ�����\\r\\nEverything was slow, the bike, the horse, and the post\\r\\nһ��ֻ����һ����\\r\\na lifetime was all but enough to be in love with one\\r\\n��ǰ����Ҳ�ÿ�\\r\\nThe old-school lock was beautiful\\r\\nԿ�׾���������\\r\\nand the key exquisite looking\\r\\n������ �˼ҾͶ���\\r\\nonce locked, the others understood\\t\",\"tag\":null,\"title\":\"��ǰ��\",\"userid\":2,\"likes\":0},{\"isoriginal\":0,\"collectnum\":3,\"typeid\":3,\"id\":13,\"pic\":null,\"writer\":\"ľ��\",\"time\":\"2020-11-25 10:40:51\",\"text\":\"��ǰ���ӹ�������һ��ֻ����һ��\",\"tag\":null,\"title\":\"��ǰ��\",\"userid\":2,\"likes\":0},{\"isoriginal\":0,\"collectnum\":0,\"typeid\":3,\"id\":14,\"pic\":null,\"writer\":\"ľ��\",\"time\":\"2020-11-25 10:44:35\",\"text\":\"��ǰ���ӹ�������һ��ֻ����һ��\",\"tag\":null,\"title\":\"��ǰ��\",\"userid\":2,\"likes\":0},{\"isoriginal\":0,\"collectnum\":0,\"typeid\":3,\"id\":15,\"pic\":null,\"writer\":\"��ë\",\"time\":\"2020-11-25 10:48:23\",\"text\":\"��������� ? Ҫ��һ����  վ������  û�б���������  һ���ڳ����ﰲ��  һ���ڷ������  һ����������  һ����ԡ����  �ǳ���Ĭ ? �ǳ�����  �Ӳ����� ? �Ӳ�Ѱ��  ��������� ? Ҫ����һ���  һ˲��Ҳ�ܳ�Ϊ����  û���Ƹе��黳  û�ж�����۾�  һ������������  һ���ڴ���������  ��į�� ? ����ȥԶ��  �ѵ�����˼��ͳ����  �Ӳ�˼�� ? �Ӳ�����  ��������� ? Ҫ��һֻ��  ��Խ���� ? û����;�Ŀ���  �����л���ϣ�� ? �Ϸ�����ů�ĳ���  �������˲��� ? �򱱻��ѷҷ�  ���������  ϣ��ÿ������  ���ܻ�Ϊ����\",\"tag\":null,\"title\":\"һ�ÿ�������\",\"userid\":2,\"likes\":0},{\"isoriginal\":0,\"collectnum\":0,\"typeid\":3,\"id\":16,\"pic\":null,\"writer\":\"ľ��\",\"time\":\"2020-11-25 11:07:36\",\"text\":\"��������� ? Ҫ��һ����\\r\\n\\r\\nվ������\\r\\n\\r\\nû�б���������\\r\\n\\r\\nһ���ڳ����ﰲ��\\r\\n\\r\\nһ���ڷ������\\r\\n\\r\\nһ����������\\r\\n\\r\\nһ����ԡ����\\r\\n\\r\\n�ǳ���Ĭ ? �ǳ�����\\r\\n\\r\\n�Ӳ����� ? �Ӳ�Ѱ��\\r\\n\\r\\n��������� ? Ҫ����һ���\\r\\n\\r\\nһ˲��Ҳ�ܳ�Ϊ����\\r\\n\\r\\nû���Ƹе��黳\\r\\n\\r\\nû�ж�����۾�\\r\\n\\r\\nһ������������\\r\\n\\r\\nһ���ڴ���������\\r\\n\\r\\n��į�� ? ����ȥԶ��\\r\\n\\r\\n�ѵ�����˼��ͳ����\\r\\n\\r\\n�Ӳ�˼�� ? �Ӳ�����\\r\\n\\r\\n��������� ? Ҫ��һֻ��\\r\\n\\r\\n��Խ���� ? û����;�Ŀ���\\r\\n\\r\\n�����л���ϣ�� ? �Ϸ�����ů�ĳ���\\r\\n\\r\\n�������˲��� ? �򱱻��ѷҷ�\\r\\n\\r\\n���������\\r\\n\\r\\nϣ��ÿ������\\r\\n\\r\\n���ܻ�Ϊ����\\r\\n\\r\\n���ߣ��������\\r\\n���ӣ�https://www.jianshu.com/p/73945c8c31ea\",\"tag\":null,\"title\":\"��ǰ��\",\"userid\":2,\"likes\":0}]}";
		
	}
}
