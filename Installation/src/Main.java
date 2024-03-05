import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static StringBuilder logSb = new StringBuilder();
    public static void main(String[] args) {
        List<File> dirsLists = new ArrayList<>();
        String path = "/Users/Belosnejka/IdeaProjects/Projects/netology/Work/HomeWork4.3.1/Games/";
        dirsLists.add(new File(path,"src"));
        dirsLists.add(new File(path,"res"));
        dirsLists.add(new File(path,"savegames"));
        dirsLists.add(new File(path,"temp"));
        dirsLists.add(new File(path,"src/main"));
        dirsLists.add(new File(path,"src/test"));
        dirsLists.add(new File(path,"res/drawable"));
        dirsLists.add(new File(path,"res/vectors"));
        dirsLists.add(new File(path,"res/icons"));

        List<File> filesLists = new ArrayList<>();
        filesLists.add(new File(path,"src/main/Main.java"));
        filesLists.add(new File(path,"src/main/Utils.java"));
        filesLists.add(new File(path,"temp/temp.txt"));

        for (File dir : dirsLists) {
            if (dir.mkdirs())
                log(dir.getName(), dir.getAbsolutePath(), true);
            else
                log(dir.getName(), null, false);
        }

        for (File file : filesLists) {
            try {
                if (file.createNewFile())
                    log(file.getName(), file.getAbsolutePath(), true);
                else
                    log(file.getName(), null, false);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try (FileWriter writer = new FileWriter(path + "temp/temp.txt",true)) {
            writer.write(logSb.toString());
            writer.append("\n");
            writer.flush();
        } catch (IOException e) {
            System.out.printf(e.getMessage());
        }
    }

    public static void log(String dirOrFileName, String dirOrFilePath, Boolean create) {
        if (create)
            logSb.append(LocalDateTime.now() + " " + dirOrFileName + " has been created on path: " + dirOrFilePath + "\n");
        else
            logSb.append(LocalDateTime.now() + " " + dirOrFileName + " has not created!" + "\n");
    }
}