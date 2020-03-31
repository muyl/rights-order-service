import com.cc.cmbc.order.common.util.DateUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.util.StopWatch;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 拓仲 on 2020/3/16
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
//        System.out.println(!DateUtil.isAfterToday("2020-03-15"));
//        System.out.println(!DateUtil.isBeforeToday("2020-03-15"));
        Random random = new Random(100);
        System.out.println(random.nextInt());

        for (int i = 0; i < 100; i++) {
            System.out.println(Test.buildVoucherSuffix14("200323"));
    }
        StopWatch stopWatch = new StopWatch("aaa");
        for (int i = 0; i < 10; i++) {
            stopWatch.start("bbb"+i);
            // 创建自动start的计时器
            Thread.sleep(1000L);
            stopWatch.stop();
        }
        System.out.println(stopWatch.prettyPrint());


    }

    private static String buildVoucherSuffix14(String date) {
        char[] bases = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D'};
        //中间六位
        List<Character> chars = date.chars()
                .mapToObj(e -> (char) e).collect(Collectors.toList());
        Collections.shuffle(chars);
        String mid6 = chars.stream()
                .map(Object::toString).collect(Collectors.joining());
        //后八位
        String suffix8 = RandomStringUtils.random(5, bases)
                + RandomStringUtils.randomAlphabetic(7);
        return mid6 + suffix8;
    }


}
