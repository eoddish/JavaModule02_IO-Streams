import java.util.*;
import java.io.*;
public class Program {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Map<String, Integer[]> formats = new TreeMap<String,Integer[]>();
        File sigFile = new File("signatures.txt");

        try {
            FileInputStream fin = new FileInputStream(sigFile);
            int i;
            while (true) {
                String key = new String();
                String sigs = new String();
                while ((i = fin.read()) != -1 && ((char) i != ',')) {
                    key += (char) i;
                }
                if ( i == -1)
                    break;
                while ((i = fin.read()) != -1 && ((char) i != '\n')) {
                    sigs += (char)i;
                }
                List<Integer> sig = new ArrayList<Integer>();
                Scanner sc = new Scanner(sigs);
                while (sc.hasNextInt(16)) {
                    sig.add(sc.nextInt(16));
                }
                Integer [] s = new Integer[sig.size()];
                formats.put(key,sig.toArray(s));
                sc.close();
                if (i == -1)
                    break;
            }
            fin.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        File output = new File("result.txt");
        if (output.exists())
            output.delete();

        while (!scanner.hasNext("42")) {
            List<Integer> signature = new ArrayList<Integer>();
            String path = scanner.next();
            File input = new File(path);
            try {
                FileInputStream in = new FileInputStream(input);
                FileOutputStream out = new FileOutputStream(output, true);
                int i = 0;
                int j = 0;
                while ((i = in.read()) != -1 && j < 12) {
                    signature.add(i);
                    j++;
                }
                in.close();

                int k = 0;
                int processed = 0;
                for (Map.Entry<String, Integer[]> entry : formats.entrySet()) {
                    Integer[] format = new Integer[entry.getValue().length];
                    if (Arrays.equals(entry.getValue(), signature.subList(0, entry.getValue().length).toArray(format))) {
                        out.write((entry.getKey() + "\n").getBytes());
                        System.out.println("PROCESSED");
                        processed = 1;
                        break;
                    }
                }
                if (processed == 0)
                    System.out.println("UNDEFINED");

                out.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        scanner.close();
    }
}