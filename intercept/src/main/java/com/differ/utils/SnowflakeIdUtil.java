package com.differ.utils;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/1 0:53
 */
public class SnowflakeIdUtil {
    private static final long workerIdBits = 5L;
    private static final long datacenterIdBits = 5L;
    private static final long sequenceBits = 12L;
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private static final long sequenceMask = -1L ^ (-1L << sequenceBits);
    private static final long workerIdShift = sequenceBits;
    private static final long datacenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private static final long twepoch = 1609459200000L;

    private static long workerId = 1;
    private static long datacenterId = 1;
    private static long sequence = 0L;
    private static long lastTimestamp = -1L;

    public static synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < 0) {
            throw new RuntimeException(String.format("Clock moved backwards, refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected static long timeGen() {
        return System.currentTimeMillis();
    }

    public static void setWorkerId(long id) {
        if (id > maxWorkerId || id < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        SnowflakeIdUtil.workerId = id;
    }

    public static void setDatacenterId(long id) {
        if (id > maxDatacenterId || id < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        SnowflakeIdUtil.datacenterId = id;
    }
}
