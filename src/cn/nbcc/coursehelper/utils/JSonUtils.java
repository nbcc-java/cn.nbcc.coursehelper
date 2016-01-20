package cn.nbcc.coursehelper.utils;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;

public class JSonUtils {
   public static void main(String[] args) {
       /**
        * �� Array ������ Json ��
        */
       String[] str = { "Jack", "Tom", "90", "true" };
       JSONArray json = JSONArray.fromObject(str);
       System.err.println(json);

       /**
        * �������飬ע�����ֺͲ���ֵ
        */
       Object[] o = { "����", "�Ϻ�", 89, true, 90.87 };
       json = JSONArray.fromObject(o);
       System.err.println(json);

       /**
        * ʹ�ü�����
        */
       List<String> list = new ArrayList<String>();
       list.add("Jack");
       list.add("Rose");
       json = JSONArray.fromObject(list);
       System.err.println(json);

       /**
        * ʹ�� set ��
        */
       Set<Object> set = new HashSet<Object>();
       set.add("Hello");
       set.add(true);
       set.add(99);
       json = JSONArray.fromObject(set);
       System.err.println(json);
   }
}
