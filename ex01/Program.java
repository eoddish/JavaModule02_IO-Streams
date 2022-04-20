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

        } catch (Exception e) {
            System.out.println(e);
        }


        Collections.sort(dictionary, Collections.reverseOrder());
        Vector<Integer> vectorA = new Vector<Integer>(dictionary.size());
        Vector<Integer> vectorB = new Vector<Integer>(dictionary.size());
        /*
        for (int i = 0; i < textA.size(); i++) {
            for (int j = 0; j < dictionary.size(); j++) {
                if (textA.get(i).equals(dictionary.get(j))) {
                    vectorA.set(j, vectorA.get(j) + 1);
                }
            }
        }
        for (int i = 0; i < textB.size(); i++) {
            for (int j = 0; j < dictionary.size(); j++) {
                if (textB.get(i).equals(dictionary.get(j))) {
                    vectorB.set(j, vectorB.get(j) + 1);
                }
            }
        }
        */
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

        for(int i = 0; i< dictionary.size(); i++) {
            System.out.print(dictionary.get(i) + " ");
        }
        System.out.println();
        for(int i = 0; i < vectorA.size(); i++) {
            System.out.print(vectorA.get(i) + " ");
        }
        System.out.println();

        for(int i = 0; i < vectorB.size(); i++) {
            System.out.print(vectorB.get(i) + " ");
        }
        System.out.println();
    }
}