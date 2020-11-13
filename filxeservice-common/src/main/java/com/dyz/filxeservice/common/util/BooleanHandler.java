package com.dyz.filxeservice.common.util;

import com.dyz.filxeservice.common.constant.ServiceConstant;

import java.util.Objects;

public class BooleanHandler {

    public static String convertToString(Boolean b) {
        if(Objects.isNull(b)) {
            return null;
        }
        if(b) {
            return ServiceConstant.BOOLEAN_TRUE;
        }
        return ServiceConstant.BOOLEAN_FALSE;
    }
}
