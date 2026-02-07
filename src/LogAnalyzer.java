import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LogAnalyzer {

    public static void main(String[] args) {
        String filePath = "logs.csv";
        Map<String, Integer> levelCountMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] columns = line.split(",");
                String level = columns[1];

                levelCountMap.put(level, levelCountMap.getOrDefault(level, 0) + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 結果表示
        System.out.println("=== ログレベル別件数 ===");
        for (String level : levelCountMap.keySet()) {
            System.out.println(level + ": " + levelCountMap.get(level));
        }

        // CSV出力
        try (FileWriter writer = new FileWriter("level_summary.csv")) {
            writer.write("level,count\n");
            for (String level : levelCountMap.keySet()) {
                writer.write(level + "," + levelCountMap.get(level) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
