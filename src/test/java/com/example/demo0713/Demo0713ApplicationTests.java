package com.example.demo0713;

import com.example.demo0713.executorpool.DataRecordHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

@SpringBootTest
class Demo0713ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public   void test2() {
        ExecutorService pool = DataRecordHandler.getexecPool();
        pool.execute(new ThreadSub());
    }

    @Test
    public void testdd() throws InterruptedException {
        ConditionDemo conditionDemo = new ConditionDemo();
        List<Integer> integers = Arrays.asList(3, 5, 6, 7, 8);

        for (int i = 0; i < integers.size(); i++){
            conditionDemo.put(integers.get(i));
        }

    }
}
