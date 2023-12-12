package com.euler.apisecuritysample.auth.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 描述: Twitter的分布式自增ID雪花算法snowflake (Java版)
 **/
public class IdUtils {
    /**
     * 起始的时间戳
     */
    private final static long START_TIMESTAMP = 1609231152008L;

    /**
     * 每一部分占用的位数 生成id的长度=(26+2+2)/2+1=16 个字符
     */
    private final static long SEQUENCE_BIT = 12; //序列号占用的位数
    private final static long DATA_CENTER_BIT = 5;//数据中心占用的位数
    private final static long MACHINE_BIT = 5;   //机器标识占用的位数

    /**
     * 每一部分的最大值
     */
    private final static long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);
    private final static long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private final static long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private final static long MACHINE_LEFT = SEQUENCE_BIT;
    private final static long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private final static long TIMESTAMP_LEFT = DATA_CENTER_LEFT + DATA_CENTER_BIT;

    private static long dataCenterId = 0;  //数据中心
    private static long machineId = 0;     //机器标识
    private static long sequence = 0L; //序列号
    private static long lastTimesTamp = -1L;//上一次时间戳

    public IdUtils(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        IdUtils.dataCenterId = dataCenterId;
        IdUtils.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     */
    public static synchronized long nextId() {
        long currTimestamp = getNewTimestamp();
        if (currTimestamp < lastTimesTamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }

        if (currTimestamp == lastTimesTamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currTimestamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimesTamp = currTimestamp;

        return (currTimestamp - START_TIMESTAMP) << TIMESTAMP_LEFT //时间戳部分
                | dataCenterId << DATA_CENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    private static long getNextMill() {
        long mill = getNewTimestamp();
        while (mill <= lastTimesTamp) {
            mill = getNewTimestamp();
        }
        return mill;
    }

    private static long getNewTimestamp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) throws ParseException {
        System.out.println("当前日期转：" + System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String customDate = "1970-01-02 00:00:00";
        Date dt = simpleDateFormat.parse(customDate);
        System.out.println(customDate + " 转long：" + dt.getTime());
        long id = IdUtils.nextId();
        System.out.println("生成id：" + id + "  长度：" + String.valueOf(id).length());
        System.out.println("UUID：" + UUID.randomUUID());
    }
}

