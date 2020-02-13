import org.junit.Test;

import java.text.NumberFormat;

/**
 * //TODO 添加类功能描述
 *
 * @author: liquan_pgz@qq.com
 * @date: 2018-10-16 14:28
 * @Version: 1.0
 */
public class Client {

    @Test
    public void tess1() {
        System.out.println(Const.RAND_CONST);
    }

    @Test
    public void test2() {
        int i = 80;
        String s = String.valueOf(i < 100 ? 90 : 100);
        String s1 = String.valueOf(i < 100 ? 90 : 100.0);
        String s2 = String.valueOf(i < 100 ? 90 : "100.0");
        System.out.println(s);
        System.out.println(s1);
        System.out.println(90 == 90.0);
//        int in = i < 100 ? 90 : 100.0;
    }

    @Test
    public void test3() {
        calPrice(3244, 23);
        calPrice(324);
    }

    @Test
    public void test4() {
        int count = 0;
        for (int i=0;i<10;i++) {
            count = ++count;
        }
        System.out.println("count = " + count);
    }

    @Test
    public void test5() {
    }

    private void calPrice(int price, int discount) {
        float knockdownPrice = price * discount;
        System.out.println("简单折扣后的价格是：" + formatCurrency(knockdownPrice));
    }

    private void calPrice(int price, int... discounts) {
        float knockdownPrice = price;
        for (int discount : discounts) {
            knockdownPrice = knockdownPrice * discount / 100;
        }
        System.out.println("复杂折扣后的价格是：" + formatCurrency(knockdownPrice));
    }

    private String formatCurrency(float price) {
        return NumberFormat.getCurrencyInstance().format(price / 100);
    }

}
