package Core;

public class Test {
    public static void main(String[] args) {
        Date d = new Date("2021-05-06");
        System.out.println(d);

        int diff = Date.getDifference(new Date("2021-06-09"), new Date("2021-06-13"));
        System.out.println(diff);
    }
}
