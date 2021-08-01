package com.gy.hospital.utils;

import com.gy.hospital.R;

public class Constants {

    public enum Identity {

        Doctor(2), Patient(1), Admin(3), Good_Doctor(4);

        public int identity;

        Identity(int identity) {
            this.identity = identity;
        }


    }

    public static final String _USER = "_USER";

    public static final int _AS_DOCTOR = 2;
    public static final int _AS_USER = 1;
    public static final int _AS_ADMIN = 3;
    public static final int _AS_GOOD_DOCTOR = 4;

    public final static Integer[] FUNCTION = {R.string.func_info, R.string.func_guahao, R.string.func_fankui, R.string.func_gonggao, R.string.func_jiuzheng, R.string.func_huifu};

    public final static Integer[] FUNCTION_ICON = {R.drawable.function_info_account_black_36dp, R.drawable.function_guahao_black_36dp, R.drawable.function_fankui_black_36dp, R.drawable.function_gonggao_black_36dp, R.drawable.function_jiuzheng_black_36dp, R.drawable.function_huifu_black_36dp};

    public final static String ROBOT_URL = "http://www.tuling123.com/openapi/api?key=057c818d3b1b430faa4ee25b638a5b7c";

    public final static int[] DEPARTMENT_ICON = {R.drawable.r0, R.drawable.r1, R.drawable.r2, R.drawable.r3, R.drawable.r4, R.drawable.r5, R.drawable.r6, R.drawable.r7, R.drawable.r8, R.drawable.r9,};

    public final static String DEFAULT_HOSPITAL_NAME = "rhU6999T";

    public final static String UPDATEUSERPASSINNOTLOGIN = "https://api2.bmob.cn/1/updateUserPassword/";

    public final static String UPDATEUSERPASSINNOTLOGINBYPASS2 = "https://api2.bmob.cn/1/users/";


}
