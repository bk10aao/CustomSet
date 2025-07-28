public class Temp {

    public static void main(String[] args) {
        String str = "0, ";
        for(int i = 10; i <= 1000; i+= 10) {
            str += (i + ", ");
        }
        System.out.println(str.substring(0, str.length() - 2));
    }
}
