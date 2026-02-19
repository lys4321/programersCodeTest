package CreateStarAtintersec;

import java.util.*;
        import java.lang.*;
        import java.io.*;
        import java.util.stream.Collectors;


class CreateStarAtintersec {
    public String[] solution(int[][] line) {
        long[] xDist = {Long.MAX_VALUE, Long.MIN_VALUE};
        long[] yDist = {Long.MAX_VALUE, Long.MIN_VALUE};
        int[][] refined = new int[line.length][];

        int idx = 0;
        for(int i = 0; i < line.length; i++){
            int[] stdr = line[i];

            for(int j = i+1; j < line.length; j++){
                if(refined[j] != null) continue;
                int[] target = line[j];
                long cramer_main = (long)stdr[0] * (long)target[1] - (long)stdr[1] * (long)target[0];

                if(cramer_main == 0){ continue; }
                else{
                    long cramer_sub_x = (long)stdr[2] * (long)target[1] - (long)stdr[1] * (long)target[2];
                    long cramer_sub_y = (long)stdr[2] * (long)target[0] - (long)stdr[0] * (long)target[2];

                    if(cramer_sub_x % cramer_main != 0 || cramer_sub_y % cramer_main != 0) continue;

                    xDist[0] = Math.min(xDist[0], (cramer_sub_x / cramer_main));
                    xDist[1] = Math.max(xDist[1], (cramer_sub_x / cramer_main));

                    yDist[0] = Math.min(yDist[0], (cramer_sub_y / cramer_main));
                    yDist[1] = Math.max(yDist[1], (cramer_sub_y / cramer_main));

                    refined[idx] = (new int[]{(int)(cramer_sub_x / cramer_main), (int)(cramer_sub_y / cramer_main)});
                    idx++;
                }
            }
        }

        Arrays.sort(refined, (obj1, obj2) -> {
            if(obj1 == null) return 1;
            if(obj2 == null) return -1;

            if(obj1[1] == obj2[1]){
                return Integer.compare(obj1[0], obj2[0]);
            }

            return Integer.compare(obj2[1], obj1[1]);
        });

        int xLen = (int)(xDist[1] - xDist[0]) + 1;
        int yLen = (int)(yDist[1] - yDist[0]) + 1;
        String[] starPointArr = new String[yLen];

        for(int i = 0; i < starPointArr.length; i++){
            starPointArr[i] = ".".repeat(xLen);
        }

        for(int i = 0; i < refined.length; i++){
            if(refined[i] == null) break;
            int xp = Math.abs(refined[i][0] - (int)(xDist[1]));
            int yp = Math.abs(refined[i][1] - (int)(yDist[1]));

            StringBuilder sb = new StringBuilder(starPointArr[yp]);
            sb.setCharAt(xp, '*');

            starPointArr[yp] = sb.toString();
        }

        String[] answer = starPointArr;
        return answer;
    }

    public static void main(String[] args) {
        int[][] line = {
                {2, -1, 4},
                {-2, -1, 4},
                {0, -1, 1},
                {5, -8, -12},
                {5, 8, 12}
        };

        CreateStarAtintersec sa = new CreateStarAtintersec();

        for(String s : sa.solution(line)){
            System.out.println(s);
        }
    }
}