import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        String path = "/Users/Belosnejka/IdeaProjects/Projects/netology/Work/HomeWork4.3.1/Games/savegames/";
        GameProgress gameProgress1 = new GameProgress(100,2,60,200);
        GameProgress gameProgress2 = new GameProgress(1,2,100,120);
        GameProgress gameProgress3 = new GameProgress(67,0,85,300);
        saveGame(gameProgress1,path+"save1.dat");
        saveGame(gameProgress2,path+"save2.dat");
        saveGame(gameProgress3,path+"save3.dat");
        List<String> zipFileList = new ArrayList<>();
        zipFileList.add(path+"save1.dat");
        zipFileList.add(path+"save2.dat");
        zipFileList.add(path+"save3.dat");
        zipFile(path+"saves.zip", zipFileList);
        openZipFile(path+"saves.zip",path);
        System.out.println(openProgress(path+"save1.dat"));
        System.out.println(openProgress(path+"save2.dat"));
        System.out.println(openProgress(path+"save3.dat"));
    }

    public static void saveGame(GameProgress gameProgress, String path) {
        try (FileOutputStream fos = new FileOutputStream(path)){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFile(String zipPath, List<String> zipList){
        try {
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
            for (String zipFiles : zipList) {
                FileInputStream fis = new FileInputStream(zipFiles);
                ZipEntry entry = new ZipEntry(zipFiles);
                zos.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        deleteFile(zipList);
    }

    public static void deleteFile(List<String> filelist) {
        for (String file : filelist) {
            new File(file).delete();
        }
    }

    public static void openZipFile(String zipPath, String pathDir){
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath));
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null){
                name = entry.getName();
                FileOutputStream fis = new FileOutputStream(name);
                for (int c = zis.read(); c != -1 ; c = zis.read()) {
                    fis.write(c);
                }
                fis.flush();
                zis.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static GameProgress openProgress(String pathOpenFile) {
        try {
            FileInputStream fis = new FileInputStream(pathOpenFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (GameProgress) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
