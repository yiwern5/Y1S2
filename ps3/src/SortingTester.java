import java.util.Random;
public class SortingTester {
    public static boolean checkSort(ISort sorter, int size) {
        // TODO: implement this
        KeyValuePair[] testArray = new KeyValuePair[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            testArray[i] = new KeyValuePair(rand.nextInt(100) + 1, rand.nextInt(100));
        }
        sorter.sort(testArray);
        //sorter sort array by key, so key should be in ascending order
        for (int i = 0; i < size - 1; i++) {
            if (testArray[i].getKey() > testArray[i + 1].getKey()) {
                return false;
            }
        }
        return true;
    }
    public static boolean isStable(ISort sorter, int size) {
        // TODO: implement this
        KeyValuePair[] testArray = new KeyValuePair[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            testArray[i] = new KeyValuePair(rand.nextInt(30) + 1, i + 1);
        }
        sorter.sort(testArray);
        //equal key (element) should not change position after sort
        for (int i = 0; i < size - 1; i++) {
            if (testArray[i].getKey() == testArray[i + 1].getKey()) {
                if (testArray[i].getValue() > testArray[i + 1].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
    }
}