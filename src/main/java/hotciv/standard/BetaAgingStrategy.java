package hotciv.standard;
import hotciv.framework.*;

public class BetaAgingStrategy implements AgingStrategy {
    @Override
    public int getStrategicAging(int currentAge) {
        if (currentAge < -100) {
            currentAge = currentAge + 100;
            return currentAge;
        } else if (currentAge == -100) {
            return -1;
        } else if (currentAge == -1) {
            return 1;
        } else if (currentAge == 1) {
            return 50;
        } else if (currentAge >= 50 && currentAge < 1750) {
            currentAge = currentAge + 50;
            return currentAge;
        } else if (currentAge >= 1750 && currentAge < 1900) {
            currentAge = currentAge + 25;
            return currentAge;
        } else if (currentAge >= 1900 && currentAge < 1970) {
            currentAge = currentAge + 5;
            return currentAge;
        } else {
            currentAge = currentAge + 1;
            return currentAge;
        }
    }
}
