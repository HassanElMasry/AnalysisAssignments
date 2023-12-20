import java.util.HashMap;
import java.util.Map;

public class App {

    static class res {
        String doneX;
        String doneY;
        double score;
        public res(String doneX, String doneY, double score){
            this.doneX = doneX;
            this.doneY = doneY;
            this.score = score;
            double threshold = 1e-10;
            if (Math.abs(this.score) < threshold) {
                this.score = 0.0;
            }
        }   
    }

    public static res allignment(String x, String y, Map< Character, Map<Character, Double>> matrix) {
        double[][] temp = new double[x.length() + 1][y.length() + 1];
        for (int j = 1; j <= y.length(); j++) {
            temp[0][j] = temp[0][j - 1] + matrix.get('-').get(y.charAt(j - 1));
        }
        for (int i = 1; i <= x.length(); i++) {
            temp[i][0] = temp[i - 1][0] + matrix.get(x.charAt(i - 1)).get('-');
        }
        for(int i = 1; i <= x.length(); i++){
            for(int j = 1; j <= y.length(); j++){
                double match = temp[i-1][j-1] + matrix.get(x.charAt(i-1)).get(y.charAt(j-1));
                double delete = temp[i-1][j] + matrix.get(x.charAt(i-1)).get('-');
                double insert = temp[i][j-1] + matrix.get('-').get(y.charAt(j-1));
                temp[i][j] = Math.max(match, Math.max(delete, insert));
            }
        }
        String doneX = "";
        String doneY = "";
        int i = x.length();
        int j = y.length();
        while(i > 0 || j > 0){
            if(i > 0 && j > 0 && temp[i][j] == temp[i - 1][j - 1] + matrix.get(x.charAt(i-1)).get(y.charAt(j-1))){
                doneX = x.charAt(i-1) + doneX;
                doneY = y.charAt(j-1) + doneY;
                i--;
                j--;
            }
            else if(i > 0 && temp[i][j] == temp[i - 1][j] + matrix.get(x.charAt(i-1)).get('-')){
                doneX = x.charAt(i-1) + doneX;
                doneY = '-' + doneY;
                i--;
            }
            else{
                doneX = '-' + doneX;
                doneY = y.charAt(j-1) + doneY;
                j--;
            }
        }
        return new res(doneX, doneY, temp[x.length()][y.length()]);
    }
    public static void main(String[] args) throws Exception {
        String x = "TCCCAGTTATGTCAGGGGACACGAGCATGCAGAGAC";
        String y = "AATTGCCGCCGTCGTTTTCAGCAGTTATGTCAGATC";
        //String x = "ATGCC";
        //String y = "TACGCA";
        Map< Character, Map<Character, Double>> matrix = new HashMap<>();
        matrix.put('A', new HashMap<>(Map.of('A', 1.0, 'G', -0.8, 'T', -0.2, 'C', -2.3, '-', -0.6)));
        matrix.put('G', new HashMap<>(Map.of('A', -0.8, 'G', 1.0, 'T', -1.1, 'C', -0.7, '-', -1.5)));
        matrix.put('T', new HashMap<>(Map.of('A', -0.2, 'G', -1.1, 'T', 1.0, 'C', -0.5, '-', -0.9)));
        matrix.put('C', new HashMap<>(Map.of('A', -2.3, 'G', -0.7, 'T', -0.5, 'C', 1.0, '-', -1.0)));
        matrix.put('-', new HashMap<>(Map.of('A', -0.6, 'G', -1.5, 'T', -0.9, 'C', -1.0, '-', Double.NaN)));
        res result = allignment(x, y, matrix);
        System.out.println(result.doneX);
        System.out.println(result.doneY);
        System.out.println(result.score);
    }
}
