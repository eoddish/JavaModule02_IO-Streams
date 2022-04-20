import java.util.*;
import java.io.*;
public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Integer[]> formats = new TreeMap<String,Integer[]>();

        /*nteger[] png = {0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
        Integer[] gif = {0x47, 0x49, 0x46, 0x38, 0x37, 0x61,};
        Integer[] jpeg = {0xFF, 0xD8, 0xFF, 0xE0, 0x00, 0x10, 0x4A, 0x46, 0x49, 0x46, 0x00, 0x01};
        Integer[] bmp = {0x42, 0x4D};
        Integer[] tiff = {0x4D, 0x4D, 0x00, 0x2A};
        Integer[] psd = {0x38, 0x42, 0x50, 0x53};
        Integer[] pdf = {0x25, 0x50, 0x44, 0x46, 0x2D};
        Integer[] iso = {0x43, 0x44, 0x30, 0x30, 0x31};
        Integer[] mp4 = {0x66, 0x74, 0x79, 0x70, 0x69, 0x73, 0x6F, 0x6D};
        Integer[] mp3 = {0x49, 0x44, 0x33};

        formats.put("PNG", png);
        formats.put("GIF", gif);
        formats.put("JPEG", jpeg);
        formats.put("BMP", bmp);
        formats.put("TIFF", tiff);
        formats.put("PSD", psd);
        formats.put("PDF", pdf);
        formats.put("ISO", iso);
        formats.put("MP4", mp4);
        formats.put("MP3", mp3);

         */

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
                while ((i = fin.read()) != -1 && ((char) i != '\n')) {
                    sigs += (char)i;
                }


                Scanner sc = new Scanner(sigs);
                List<Integer> sig = new ArrayList<Integer>();
                while (sc.hasNextInt()) {
                    sig.add(sc.nextInt());
                }
                sc.close();
                Integer [] s = new Integer[sig.size()];
                formats.put(key,sig.toArray(s));

                System.out.println("*");
                if (i == -1)
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        for (Map.Entry<String, Integer[]> entry1 : formats.entrySet()) {
            System.out.print(entry1.getKey() + " ");
            Integer[] ab= entry1.getValue();
            for( Integer abb : ab)
                System.out.print(abb + " ");
            System.out.println();
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
                /*
                Integer[] format1 = new Integer[signature.size()];
                for(Integer b : signature.toArray(format1)) {
                    System.out.print(b + " ");
                }
                System.out.println();

                 */
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

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        scanner.close();
    }
}