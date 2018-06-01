package com.jun.timer;

/**
 * @author jun
 * @Date 18/4/17 .
 * @des:
 */
public class QuickSort {
    public static void quickSort(int[] arr){
    qsort(arr, 0, arr.length-1);
}

    private static void qsort(int[] arr, int low, int high) {
        if(low<high){
            int pivot=partition(arr, low, high);        //将数组分为两部分
            qsort(arr,low,pivot);
            qsort(arr,pivot,high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot=arr[low];
        while (low<high){
            while (low<high && arr[high]>=pivot) --high;
            arr[low]=arr[high];             //交换比枢轴小的记录到左端
            while (low<high && arr[low]<=pivot) ++low;
            arr[high] = arr[low];
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(QuickSort.class.getClassLoader().getResource(""));
    }

}
