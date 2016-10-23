/**
 * Created by Alina Aleshkina on 8.10.2016.
 */
public class Barray {

    private int k;
    private int catalogAndBranchSize;
    private int[][] catalog;
    private int currentSize;

    public Barray(int size) {
        this.k = calculateK(size);
        this.catalogAndBranchSize = (int)Math.pow(2, k);
        this.catalog = new int[this.catalogAndBranchSize][]; //jagged array
        this.currentSize = 0;
    }

    public Barray() {
        this(0);
    }

    /**
     * Calculates k so
     * input size in the constructor <= 2 ^ (2 * k)
     * @param n - constructor input size
     * @return k
     */
    private static int calculateK(int n) {
        int answer = 0;
        while (Math.pow(4, answer) < n)
            answer++;
        return answer;
    }

    /**
     * Returns an element in a certain position
     * @param index - position of an element to fetch
     * @return element value
     * @throws Exception
     */
    public int get(int index) throws Exception {
        if (index < currentSize)
            return catalog[index / catalogAndBranchSize][index % catalogAndBranchSize];
        else
            throw new Exception("Index is out of range");
    }

    /**
     * Changes an old value to the new one at certain position of the barray
     * @param value - new value of an element at position 'index'
     * @param index - index of element to be changed
     * @throws Exception
     */
    public void set(int value, int index) throws Exception{
        if (index <= currentSize)
            catalog[index / catalogAndBranchSize][index % catalogAndBranchSize] = value;
        else
            throw new Exception("Index is out of range");
    }

    /**
     * Adds new element in a barray
     * @param numberToAdd - value to be added
     * @param indexToAddAt - index where to add the value
     * @throws Exception
     */
    public void add(int numberToAdd, int indexToAddAt) throws Exception {
        if (currentSize >= (int)Math.pow(4, k)) // if catalog is full
            extendBarray();
        if(currentSize % catalogAndBranchSize == 0) // if new branch is needed
            catalog[currentSize / catalogAndBranchSize] = new int[catalogAndBranchSize];
        for (int i = currentSize; i > indexToAddAt; i--) //shift values
            set(this.get(i - 1), i);
        set(numberToAdd, indexToAddAt);
        currentSize++;
    }

    /**
     * Deletes an element from barray and shifts all the elements
     * to the right of deleted element to one position to the left
     * @param deleteIndex - index of the value to be deleted
     * @throws Exception - if value is not found in the barray
     */
    public void delete(int deleteIndex) throws Exception {
        for (int i = deleteIndex; i < currentSize - 1; i++)
            set(get(i + 1), i);
        currentSize--;
        if (currentSize % catalogAndBranchSize == 0) // if last branch becomes empty
            catalog[currentSize / catalogAndBranchSize] = null;
        if (currentSize <= (int)Math.pow(2, 2 * (k - 1))) // if catalog size is too big
            shrinkBarray();
    }

    /**
     * Extends barray when a value is added but no more place left
     * @throws Exception
     */
    private void extendBarray() throws Exception{
        changeBarraySizes(currentSize + 1);
    }

    /**
     * Shrinks array when a number of elements become < 2^(2 * (k - 1))
     * @throws Exception
     */
    private void shrinkBarray() throws Exception{
        changeBarraySizes(currentSize);
    }

    /**
     * Creates new barray with needed size, copies values there from the old barray
     * and replaces old barray with the new one.
     * @param newSize - size required for barray renovation
     * @throws Exception
     */
    private void changeBarraySizes(int newSize) throws Exception {
        Barray newBarray = new Barray(newSize);
        for (int i = 0; i < currentSize; i++) {
            newBarray.add(this.get(i), i);
        }
        updateFields(newBarray.catalog, newBarray.k, newBarray.catalogAndBranchSize, newBarray.currentSize);
    }

    /**
     * Updates the fields of this object to the new values
     * @param catalog - new catalog
     * @param k - new k
     * @param catalogAndBranchSize - new catalogAndBranchSize
     * @param currentSize - new currentSize
     */
    private void updateFields(int[][] catalog, int k, int catalogAndBranchSize, int currentSize) {
        this.catalog = catalog;
        this.k = k;
        this.catalogAndBranchSize = catalogAndBranchSize;
        this.currentSize = currentSize;
    }

    /**
     * Searches for a certain element in a barray
     * @param numberToFind - value to be searched
     * @return index of this number or -1 if the number is not in the barray
     */
    public int search(int numberToFind) throws Exception{
        for (int i = 0; i < currentSize; i++) {
            if (get(i) == numberToFind)
                return i;
        }
        return -1;
    }

    /**
     * Preforms quick sort on the barray
     * @throws Exception
     */
    public void quickSort() throws Exception {
        recQuickSort(0, currentSize - 1);
    }

    /**
     * Recursively calls itself on the parts to the left and to the right
     * of the pivot after partition() is performed. When subarrays' sizes <= 1, it stops.
     * @param left - left boundary of an array
     * @param right - right boundary of an array
     * @throws Exception
     */
    private void recQuickSort(int left, int right) throws Exception{
        if (right - left <= 0) {
            return;
        }
        else {
            int pivot = get(right);
            int partitionPosition = partition(left, right, pivot);
            recQuickSort(left, partitionPosition - 1);
            recQuickSort(partitionPosition + 1, right);
        }
    }

    /**
     * Sorts the element so all of them which are bigger than pivot are placed to the right
     * and others (less than pivot) are placed to the left. Pivot is placed in the correct
     * position in between.
     * @param left - left boundary of an array
     * @param right - right boundary of an array
     * @param pivot - value of chosen pivot
     * @return final pivot position or position where division into subarrays will occur
     * @throws Exception
     */
    private int partition(int left, int right, int pivot) throws Exception {
        int leftPrt = left - 1;
        int rightPrt = right;
        while(true) {
            while (get(++leftPrt) < pivot);
            while (rightPrt > 0 && get(--rightPrt) > pivot);
            if (leftPrt >= rightPrt) {
                break;
            } else {
                // swap right and left
                int temp = get(rightPrt);
                set(get(leftPrt), rightPrt);
                set(temp, leftPrt);
            }
        }
        // swap left and pivot
        int temp = get(leftPrt);
        set(pivot, leftPrt);
        set(temp, right);
        return leftPrt;
    }

    public String asString() throws Exception{
        if (currentSize <= 0)
            return "No elements in this barray";
        String s = "";
        for (int i = 0; i < currentSize; i++) {
            s += get(i);
            if ((i + 1) % catalogAndBranchSize == 0 || i == currentSize - 1) // last element in a branch
                s += "\n";
            else
                s += ", ";
        }
        return s;
    }
}
