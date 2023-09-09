package sprint3.review.a;

public class Solution {
    public static int brokenSearch(int[] arr, int k) {
        return findElem(arr, k, 0, arr.length - 1);
        // Your code
        // “ヽ(´▽｀)ノ”
    }

    private static void test() {
        int[] arr = {19, 21, 100, 101, 1, 4, 5, 7, 12};
        assert 6 == brokenSearch(arr, 5);
    }

    private static int findElem(int[] arr, int k, int leftIndex, int rightIndex) {
        if (rightIndex == leftIndex) {
            if (arr[rightIndex] == k) {
                return rightIndex;
            } else return -1;
        }

        if (leftIndex > rightIndex) {
            return -1;
        }

        int midIndex = (leftIndex + rightIndex) / 2;
        if (arr[midIndex] == k) {
            return midIndex;
        }

        int leftResult = findElem(arr, k, leftIndex, midIndex - 1);
        int rightResult = findElem(arr, k, midIndex + 1, rightIndex);
        if (leftResult != -1) {
            return leftResult;
        } else return rightResult;
    }
}
