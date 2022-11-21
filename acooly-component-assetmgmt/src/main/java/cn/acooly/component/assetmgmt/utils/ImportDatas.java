/**
 * acooly-components-business-5.2
 * <p>
 * Copyright 2014 Acooly.cn, Inc. All rights reserved.
 *
 * @author zhangpu
 * @date 2022-11-21 15:00
 */
package cn.acooly.component.assetmgmt.utils;

import com.acooly.core.utils.Dates;
import com.acooly.core.utils.Money;
import com.acooly.core.utils.Strings;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * @author zhangpu
 * @date 2022-11-21 15:00
 */
@Slf4j
public class ImportDatas {

    public static Date toDate(String dateString) {
        try {
            return Dates.parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDate(List<String> row, int i) {
        return toDate(toSafe(row, i));
    }

    public static String toSafe(List<String> row, int i) {
        if (row.size() <= i) {
            return null;
        }
        return Strings.trimToEmpty(row.get(i));
    }

    public static Money toMoney(List<String> row, int i) {
        String m = toSafe(row, i);
        m = Strings.remove(m, "*");
        return Money.amout(m);
    }

}
