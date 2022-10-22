import java.time.LocalDateTime;
import java.util.List;

public class Utils {
    public static void printMenu(List<String> options, boolean hasExitOption) {
        System.out.println("MENU");
        int optionIndex = 1;
        for (String option : options) {
            System.out.println(optionIndex + " - " + option + ";");
            optionIndex++;
        }
        if (hasExitOption) {
            System.out.println("0 - Sair");
        }
    }

    public static long createUniqueKey(String content) {
        content += System.currentTimeMillis();
        return Long.valueOf(content.hashCode());
    }

    public static void printRequest(String name, Long uniqueKey, LocalDateTime localDate) {
        String date = localDate.toString();
        String request = String.format(
                "Request:\n{\n    'name': '%s';\n    'uniqueKey': %d;\n    'processDate': '%s'\n}",
                name, uniqueKey, date);
        System.out.println(request);
    }
}
