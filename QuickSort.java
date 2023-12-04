import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class QuickSort {
    public static void main(String[] args) {
        String fileName = "./dados500_mil.txt";

        long startTime = System.currentTimeMillis();

        try {
            int[] array = readArrayFromFile(fileName);

            System.out.println("Array antes do QuickSort:");
            printArray(array);

            quickSort(array, 0, array.length - 1);

            System.out.println("\nArray após o QuickSort:");
            printArray(array);
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo de execução: " + (endTime - startTime) + " milissegundos");
    }

    static int[] readArrayFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        // Ajuste o delimitador do scanner para reconhecer números entre colchetes e vírgulas
        scanner.useDelimiter("[\\[\\],\\s]+");

        ArrayList<Integer> list = new ArrayList<>();
        while (scanner.hasNextInt()) {
            list.add(scanner.nextInt());
        }

        scanner.close();

        // Converte ArrayList para array de inteiros
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }

        return array;
    }

    static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);

            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;

                // troca array[i] e array[j]
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        // troca array[i + 1] e array[high] (ou seja, o pivô)
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }

    static void printArray(int[] array) {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
