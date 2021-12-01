package lewisCode.app;

import java.util.Random;

public class CreditCardCreator {
    public static Random random = new Random();
    //customer account number generator
    public String customerNumberGenerator(){
        long num = random.nextInt((999999999 - 100000000) + 1) + 100000000;
        String format = String.format("%d",num);
        int checkDigit = random.nextInt((9-1) + 1) + 1;
        String formatted = String.format("%d",checkDigit);
        String MII = "400000";
        String luhn = MII + format;
        String luhnAlgo ;
//      ...................implementing luhn algorithm in building digit checker
        int sum = 0;
        int[] arrayNum = new int[luhn.length()];
        for (int i = luhn.length()-1; i >= 0; i--) {
            arrayNum[i] = Integer.parseInt(luhn.substring(i, i + 1));
            if (i % 2 == 0) {
                arrayNum[i] *= 2;
            }
            sum += arrayNum[i] > 9 ? arrayNum[i] - 9 : arrayNum[i];
        }
        if((sum + checkDigit) % 10 == 0){
            luhnAlgo = luhn + formatted;
        }else {
            int lastDigitChecker = ((10 - (sum%10))%10);
            luhnAlgo = luhn + lastDigitChecker;
        }
        return luhnAlgo;
    }
    //customer pin generator
    public String customerPinGenerator(){
        int pin = random.nextInt((9999 - 1000) + 1) + 1000;
        return String.format("%d",pin);
    }

}
