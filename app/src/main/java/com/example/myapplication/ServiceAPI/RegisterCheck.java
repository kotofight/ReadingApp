package com.example.myapplication.ServiceAPI;

import android.content.Context;
import android.widget.Toast;

import java.util.Map;

public class RegisterCheck {
    public static boolean isEquel(Map<String,String> map, Context context)
    {
        for(String key:map.keySet())
        {
            if(map.get(key).equals(""))
            {
                Toast.makeText(context,"请输入完整的信息",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if(!map.get("email").contains("qq"))
        {
            Toast.makeText(context,"要QQ邮箱才行哦",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!map.get("password").equals(map.get("passwordAgain")))
        {
            Toast.makeText(context,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (map.get("telephone").length()!=11)
        {
            Toast.makeText(context,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(map.get("emailNumber").equals(""))
        {
            Toast.makeText(context,"请输入验证码",Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(context,"请稍等....",Toast.LENGTH_SHORT).show();
        return true;
    }
}
