package com.example.praise.project.util;

import java.util.UUID;

/**
 * Created by Ay on 2017/9/16.
 */
public class UuidUtil {


    public static String generateUUID(){

        return UUID.randomUUID().toString().replace("-", "").toString();

    }

}
