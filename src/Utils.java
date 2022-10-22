import java.util.List;

public class Utils {
    public static void printMenu(List<String> options){
        System.out.println("MENU");
        int optionIndex = 1;
        for (String option : options) {
            System.out.println(optionIndex + " - " + option);
            optionIndex++;
        }
    }
}
