import java.util.List;

public class Utils {
    public static void printMenu(List<String> options, boolean hasExitOption){
        System.out.println("MENU");
        int optionIndex = 1;
        for (String option : options) {
            System.out.println(optionIndex + " - " + option + ";");
            optionIndex++;
        }
        if(hasExitOption){
            System.out.println("0 - Sair");
        }
    }
}
