import java.util.Stack;
public class Lcs {
    public static int getMax(int i, int j, int k){
        int max1 = Math.max(i,j);
        return Math.max(max1, k);
    }
    public static void computeLCS(String a, String b, String c){
        int[][][] number = new int[a.length()+1][b.length()+1][c.length()+1];
        int[][][] choice = new int[a.length()+1][b.length()+1][c.length()+1];
        //Choice 1, add letter, go diagonal (use sub-problem i-1,j-1, k-1); Choice 2,  go up (use sub-problem i,j-1);
        //Choice 3, go left (use sub-problem i-1,j); Choice 4, go back (use sub-problem i,j,k-1).
        //initialize arrays to mark exit spot (First digit repersents x axis, second repersents y axis, and third repersents z axis. uses a base 100 number system)
        for (int k = 0; k < c.length()+1; k++) {
            for (int i = 0; i < a.length()+1; i++) {
                for (int j = 0; j < b.length()+1; j++) {
                    if (k == 0 || i == 0 || j == 0) {
                        choice[i][j][k] = ((i+1) * 10000) + ((j+1) * 100) + (k+1);
                    }
} }
}
        //compute length of LCS and store choices
        for (int k = 1; k < c.length()+1; k++){
            for (int i = 1; i < a.length()+1; i++){
                for (int j = 1; j < b.length()+1; j++){
                    if(a.charAt(i-1) == b.charAt(j-1) && b.charAt(j-1) == c.charAt(k-1)){
                        number[i][j][k] = number[i-1][j-1][k-1] + 1;
                        choice[i][j][k] = 1;
                    } else {
                        int max = getMax(number[i-1][j][k], number[i][j-1][k], number[i][j][k-1]);
                        if (number[i-1][j][k] == max) { //one row up is largest sub-problem
                            number[i][j][k] = number[i-1][j][k];
                            choice[i][j][k] = 2;
                        } else if (number[i][j-1][k] == max) { //one row left is largest sub-problem
                            number[i][j][k] = number[i][j-1][k];
                            choice[i][j][k] = 3;
                        } else if (number[i][j][k-1] == max) { //one row back is largest sub-problem
                            number[i][j][k] = number[i][j][k-1];
                            choice[i][j][k] = 4;
                        }
} }
} }
        //Trace back the moves done to the LCS
        Stack<Integer> choiceList = new Stack<>();
        choiceList.push(choice[a.length()][b.length()][c.length()]);
        int i = a.length(), j = b.length(), k = c.length();
        int next = -1;
        //break when exit spot is recorded
        while (next < 10000){
            int current = choiceList.peek();
            if (current == 1) {
                next = choice[i-1][j-1][k-1];
                choiceList.push(next);
                i--;
                j--;
                k--;
            } else if (current == 2) {
                next = choice[i-1][j][k];
                choiceList.push(next);
                i--;
            } else if (current == 3) {
                next = choice[i][j-1][k];
                choiceList.push(next);
                j--;
            } else if (current == 4) {
                next = choice[i][j][k-1];
                choiceList.push(next);
                k--;
            } else if (current == 0) {
                next = choice[i-1][j-1][k-1];
                choiceList.push(next);
} }
        //retreive exit location and extract x y and z axes
        int exitLocation = choiceList.pop();
        int exitZ = exitLocation % 100;
int exitY = (exitLocation/100) % 100;
        int exitX = (exitLocation/10000) % 100;
        //display length of LCS
        System.out.println("LCS\nlength: "+number[a.length()][b.length()][c.length()]);
        //create and display the subsequence
        if (0 < number[a.length()][b.length()][c.length()]) {
            StringBuilder subsqnce = new StringBuilder();
            int x = exitX, y = exitY, z = exitZ;
            while (!choiceList.isEmpty()){
                int current = choiceList.pop();
                if (current == 1) {
                    subsqnce.append(a.charAt(x-1));
                    x++;
                    y++;
                    z++;
                } else if (current == 2) {
                    x++;
}
                //just in case we want to get the letters from a diff word
                //else if (current == 3) {
                //y++;
                //} else if (current == 4) {
//z++;
//}
}
            System.out.println("subsequence: "+subsqnce);
        }
}
    public static void main(String[] args){
        computeLCS(args[0],args[1],args[2]);
} }