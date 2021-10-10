package com.example.demo0713;

import com.example.demo0713.executorpool.DataRecordHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

@SpringBootTest
class Demo0713ApplicationTests implements ApplicationContextAware {


    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
    }

    @Test
    public   void test2() {
        StringBuilder build = new StringBuilder();
        ExecutorService pool = DataRecordHandler.getexecPool();
        Collections.singletonList("1,2,3,4").parallelStream().forEach(
                build::append
        );

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testSpringSourceCode(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        SomeBean someBean= (SomeBean) context.getBean("someBean");
        someBean.doSomething();
    }
}
