import java.nio.file.*;
import java.util.*;
import java.io.*;
public class Program {
    public static void main(String args[]) {

        int len = "--current-folder=".length();
        String check = args[0].substring(0, len);
        if (!check.equals("--current-folder=")) {
            System.out.println("Error: Wrong argument");
            System.exit(-1);
        }

        String start = args[0].substring(len, args[0].length());
        Path path = Paths.get(start);
        if (!path.toFile().isDirectory()) {
            System.out.println("Error: Path is not a directory!");
            System.exit(-1);
        }

        System.setProperty("user.dir",path.toString());
        System.out.println(start.toString());

        Scanner scanner = new Scanner(System.in);
        String command;
        while (!(command = scanner.next()).equals("exit")) {

            if (command.equals("ls")) {
                File directory = new File(path.toString());
                String[] pathNames = directory.list();
                for ( String pathName : pathNames ) {
                    Path filePath = path.resolve(pathName);
                    File file = new File(filePath.toString());
                    System.out.println(pathName + " " + file.length() + " " + "KB");
                }
            } else if (command.equals("cd")) {
                path = path.resolve(scanner.next());
                try {
                    path = Paths.get(path.toFile().getCanonicalPath());
                } catch (Exception e) {
                    System.out.println(e);
                }
                System.setProperty("user.dir",path.toString());
                System.out.println(path.toString());
            } else if (command.equals("mv")) {
                Path source = path.resolve(scanner.next());
                Path dest = path.resolve(scanner.next());
                try {
                    source = Paths.get(source.toFile().getCanonicalPath());
                    dest = Paths.get(dest.toFile().getCanonicalPath());
                    if (dest.toFile().isDirectory()) {
                        Files.move(source, Paths.get(dest.toString() + "/" + source.toFile().getName()));
                    } else
                        Files.move(source, dest);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        scanner.close();
    }
}