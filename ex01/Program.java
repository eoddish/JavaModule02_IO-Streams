import java.util.*;
import java.io.*;
public class Program {
    public static void main(String[] args) {
        String pathA = args[0];
        String pathB = args[1];
        List<String> textA = new ArrayList<String>();
        List<String> textB = new ArrayList<String>();
        List<String> dictionary = new ArrayList<String>();

        File fileA = new File(pathA);
        File fileB = new File(pathB);

        try {
            String currentLine;
            BufferedReader readerA = new BufferedReader(new FileReader(fileA));
            while ((currentLine = readerA.readLine()) != null) {
                Scanner wordScanner = new Scanner(currentLine);
                while (wordScanner.hasNext()) {
                    String word = wordScanner.next();
                    if (!dictionary.contains(word))
                        dictionary.add(word);
                    textA.add(word);
                }
                wordScanner.close();
            }
            readerA.close();
            BufferedReader readerB = new BufferedReader(new FileReader(fileB));
            while ((currentLine = readerB.readLine()) != null) {
                Scanner wordScanner = new Scanner(currentLine);
                while (wordScanner.hasNext()) {
                    String word = wordScanner.next();
                    if (!dictionary.contains(word))
                        dictionary.add(word);
                    textB.add(word);
                }
                wordScanner.close();
            }
            readerB.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        Collections.sort(dictionary);
        File output = new File("dictionary.txt");
        if (output.exists())
            output.delete();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(output));
            for (int i = 0; i < dictionary.size(); i++) {
                writer.write(dictionary.get(i), 0, dictionary.get(i).length());
                if (i < dictionary.size() - 1)
                    writer.write(", ", 0, 2);
                else
                    writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        Vector<Integer> vectorA = new Vector<Integer>(dictionary.size());
        Vector<Integer> vectorB = new Vector<Integer>(dictionary.size());

        for (int i = 0; i < dictionary.size(); i++) {
            vectorA.add(0);
            vectorB.add(0);
        }
        for (int i = 0; i < dictionary.size(); i++) {
            vectorA.set(i, Collections.frequency(textA, dictionary.get(i)));
        }
        for (int i = 0; i < dictionary.size(); i++) {
            vectorB.set(i, Collections.frequency(textB, dictionary.get(i)));
        }

        double numerator = 0;
        for (int i = 0; i < dictionary.size(); i++)
            numerator += vectorA.get(i) * vectorB.get(i);

        double denominator = 0;
        double denom1 = 0;
        double denom2 = 0;
        for (int i = 0; i < dictionary.size(); i++) {
            denom1 += vectorA.get(i) * vectorA.get(i);
            denom2 += vectorB.get(i) * vectorB.get(i);
        }
        denominator = Math.sqrt(denom1) * Math.sqrt(denom2);
        double similarity = numerator / denominator;
        System.out.println("Similarity = " + (double)((int)(similarity * 100)) / 100 );
    }
}