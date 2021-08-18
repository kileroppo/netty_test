package com.example.demo0713.lammbda.demo3;

import com.google.common.base.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class practGuava {

    public static void main(String[] args) {
        testGaCache();
        testLambda();
    }


    static void testGaCache(){

        LoadingCache<Integer, BigDecimal> cache  =   CacheBuilder.newBuilder().maximumSize(10)
                .refreshAfterWrite(10, TimeUnit.MILLISECONDS)
                .softValues()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<Integer, BigDecimal>() {
                    @Override
                    public BigDecimal load(Integer integer) throws Exception {
                        return new BigDecimal(integer);
                    }
                });

        cache.put(1, BigDecimal.valueOf(412341));
        cache.put(2, BigDecimal.valueOf(3413241));
        cache.put(3, BigDecimal.valueOf(8878));
        cache.put(4, BigDecimal.valueOf(1341243));
        cache.put(5, BigDecimal.valueOf(2342));
        cache.put(6, BigDecimal.valueOf(56));
        cache.put(7, BigDecimal.valueOf(565));
        cache.put(8, BigDecimal.valueOf(565));
        cache.put(9, BigDecimal.valueOf(56));
        cache.put(10, BigDecimal.valueOf(5));
        cache.put(11, BigDecimal.valueOf(8787.00000));

        cache.asMap().entrySet().stream()
                .forEach(x -> {
//                    System.out.println("key:"+ x.getKey() + "value:"+x.getValue() );
        });

        // 1 为某个字符串添加特定的连接符
        List<String> strings = Lists.newArrayList("1", "2","", "3", "4");
        String join = Joiner.on("--lzp--").skipNulls().join(strings);
//        System.out.printf("changed str:"+ join);
        // 2 对字符串进行分割
        String testStr1 = "1,2,,3,4,5";
        Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();
//        splitter.split(testStr1).forEach(x-> System.out.println(x));
//        CharMatcher.forPredicate(testStr1).replaceFrom("","3");
        List<String> listStr = Lists.newArrayList("1", "2", "3");
        List<String> listStr2 = Lists.newArrayList("d", "ddd", "hghghjh");
        List<Integer> collect = listStr.stream().map(Integer::parseInt).collect(Collectors.toList());

        String ddd = Iterables.find(listStr2, new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String s) {
                return s.startsWith("d");
            }
        });

        listStr2.stream().findFirst().filter(
                input ->input.startsWith("d")
        ).get();

    }

    static void testLambda(){
        Stopwatch started = Stopwatch.createStarted();
        List<Apple> appleList = Lists.newArrayList(
                new Apple(1, "red"),
                new Apple(2, "red"),
                new Apple(3, "green"),
                new Apple(4, "green"));

            // 把集合变成map
        Map<Integer, Apple> collect = appleList.stream().collect(Collectors.toMap(Apple::getId, apple -> apple));

        appleList.parallelStream().collect(Collectors.toMap(Apple::getId, Apple->Apple));
        // 以apple中颜色排序
        Map<String, List<Apple>> collect1 = appleList.stream().collect(Collectors.groupingBy(Apple::getColor));
        appleList.stream().collect(Collectors.groupingBy(Apple::getColor));

        System.out.println(started.elapsed(TimeUnit.MILLISECONDS));
    }

}
