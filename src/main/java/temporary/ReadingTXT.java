package temporary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ReadingTXT {
    public void readingFiles() {
        System.out.println(System.getenv("EMP_TXT"));

        try {
            BufferedReader reader = new BufferedReader(new FileReader(System.getenv("EMP_TXT")));
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null) {
                String[] temp = line.split(" ");
                System.out.printf("[%s] %-3d %-40s >> %-45s >> %d\n",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss,SSS")),
                        (count += 1), line, Arrays.toString(temp), temp.length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
