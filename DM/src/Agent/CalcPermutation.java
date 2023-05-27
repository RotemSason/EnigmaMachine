package Agent;

import java.util.ArrayList;
import java.util.List;

public class CalcPermutation {

    private List<int[]> lst;

    public CalcPermutation() {
        lst = new ArrayList<>();
    }

    void createArr(int a[], int n)
    {

        int[]anew=new int[n];
        for (int i = 0; i < n; i++) {
            anew[i] = a[i];
        }
        lst.add(anew);
    }
    void heapPermutation(int a[], int size, int n) {
        if (size == 1)
            createArr(a,n);
        for (int i = 0; i < size; i++) {
            heapPermutation(a, size - 1, n);

            if (size % 2 == 1) {
                int temp = a[0];
                a[0] = a[size - 1];
                a[size - 1] = temp;
            } else {
                int temp = a[i];
                a[i] = a[size - 1];
                a[size - 1] = temp;
            }
        }
    }

    public List<int[]> getLst() {
        return lst;
    }
}
