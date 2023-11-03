import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static class Stopwatch{
        private final BigDecimal start;
        public Stopwatch() {
            start = BigDecimal.valueOf(System.currentTimeMillis());
        }
        public BigDecimal elapsedTime() {
            BigDecimal now = BigDecimal.valueOf(System.currentTimeMillis());
            BigDecimal temp = now.subtract(start);
            BigDecimal divider = BigDecimal.valueOf(1000.0);
            return temp.divide(divider);
        }
    }
    public static void main(String[] args) throws Exception {
        //Stopwatch watch = new Stopwatch();
        //System.out.println(powerDivNConq(5, 1));
        int [] a = {1,4,2,3,-1,6,7,-2,11,-6};
        int[] ab = mergeSortArray(a,0, a.length - 1);
        for(int i = 0; i < ab.length; i++){
            System.out.print(ab[i] + "   ");
        }
        System.out.println();
        List<int[]> pair = findPairs(a, 5);
        for(int[] p : pair){
            System.out.println(p[0] + "   " + p[1]);
        }
        //System.out.println(binarySearch(ab,22,0, a.length));
        //BigDecimal time = watch.elapsedTime();
        //System.out.println(time);
    }

    public static BigInteger powerStandard(int a, int n){
        BigInteger result = BigInteger.valueOf(1);
        BigInteger bigA = BigInteger.valueOf(a);
        while(n > 0){
            result = result.multiply(bigA);
            n--;
        }
        return result;
    }

    public static BigInteger powerDivNConq(int a, int n){
        BigInteger result = BigInteger.valueOf(1);
        BigInteger bigA = BigInteger.valueOf(a);
        if(n == 0){
            return result;
        }
        else if(n == 1){
            return bigA;
        }
        else{
            if(n % 2 == 0){
                result = powerDivNConq(a, n/2);
                return result.multiply(result);
            }
            else{
                result = powerDivNConq(a, (n-1)/2);
                return result.multiply(result).multiply(bigA);
            }
        }
    }

    public static int[] mergeSortArray(int[] array, int f, int l ){
        // if the array is only 1 element
        if (f == l){
            int [] r = new int[1];
            r[0] = array[f];
            return r;
        }
        // split the array into 2 parts
        int mid = (f + l)/2;
        // sort the left side with recursion
        int[] left = mergeSortArray(array, f, mid);
        // sort the right side with recursion
        int[] right = mergeSortArray(array, mid + 1, l);
        // merge the 2 arrays ma3 ba3d when its done sorting
        int [] r = mergeSorted(left, right);
        return r;
    }

    public static int[] mergeSorted(int [] a, int [] b){
        int[] result = new int[a.length + b.length];
        int rstart = 0;
        int acount = 0;
        int bcount = 0;
        while(acount < a.length && bcount < b.length){
            if(a[acount] <= b[bcount]){
                result[rstart++] = a[acount++];
            }else{
                result[rstart++] = b[bcount++];
            }
        }
        while(acount < a.length){
            result[rstart++] = a[acount++];
        }
        while(bcount < b.length){
            result[rstart++] = b[bcount++];
        }
        return result;
    }

    public static boolean binarySearch(int[] array , int key, int low, int high){
        int mid = low + ((high - low) / 2);
        if(low > high){
            return false;
        }
        if(array[mid] == key){
            return true;
        }else if(array[mid] > key){
            return binarySearch(array, key, low , mid - 1);
        }else{
            return binarySearch(array, key, mid + 1, high);
        }

    }


    public static List<int[]> findPairs(int[] array, int sum){
        array = mergeSortArray(array,0, array.length - 1);
        List<int[]> pairs = new ArrayList<>();
        for(int i = 0; i < array.length; i++){
            int remainder = sum - array[i];
            if(binarySearch(array,remainder,i + 1, array.length - 1)){
                pairs.add(new int[]{array[i], remainder});
            }
        }
        return pairs;
    }

}

