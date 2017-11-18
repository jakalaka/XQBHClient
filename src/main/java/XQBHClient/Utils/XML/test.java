package XQBHClient.Utils.XML;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        Map In=new HashMap();
        Map head=new HashMap();
        Map body =new HashMap();
        head.put("ZDBH_U","ZD0001");
        head.put("ZDJYM_","1233211234567");
        body.put("JNWBZ_",0);
        List list=new ArrayList();
        Map map1=new HashMap();
        map1.put("goosName","我擦");
        map1.put("goosID",1);
        Map map2=new HashMap();
        map2.put("goosName","xxx是");
        map2.put("goosID","2222");
        list.add(map1);
        list.add(map2);
        body.put("FZDLoin1",list);
        In.put("head",head);
        In.put("body",body);
        String jsonString = JSON.toJSONString(In);
        System.out.println(jsonString);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        System.out.println(jsonObject.getObject("head",Map.class).get("ZDBH_U"));
        System.out.println(((Map)((JSONArray)jsonObject.getObject("body",Map.class).get("FZDLoin1")).get(0)).get("goosName"));

    }
}
