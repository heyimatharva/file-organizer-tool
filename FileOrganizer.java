import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileOrganizer {

    public static void main(String[] args) {
        // Replace with your directory path
        String directoryPath = "D:/docs/atharva";
        
        File directory = new File(directoryPath);
        
        if (!directory.isDirectory()) {
            System.out.println("The provided path is not a directory.");
            return;
        }
        
        File[] files = directory.listFiles();
        
        if (files == null || files.length == 0) {
            System.out.println("The directory is empty or an I/O error occurred.");
            return;
        }
        
        for (File file : files) {
            if (file.isFile()) {
                String extension = getFileExtension(file);
                
                if (extension.isEmpty()) {
                    continue;
                }
                
                File subDirectory = new File(directoryPath + File.separator + extension);
                
                if (!subDirectory.exists()) {
                    subDirectory.mkdir();
                }
                
                File newFileLocation = new File(subDirectory.getPath() + File.separator + file.getName());
                
                try {
                    Files.move(file.toPath(), newFileLocation.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Moved " + file.getName() + " to " + subDirectory.getPath());
                } catch (IOException e) {
                    System.err.println("Failed to move " + file.getName() + ": " + e.getMessage());
                }
            }
        }
        
        System.out.println("File segregation completed.");
    }
    
    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        
        if (lastIndexOf == -1) {
            return ""; // empty extension
        }
        
        return name.substring(lastIndexOf + 1);
    }
}
