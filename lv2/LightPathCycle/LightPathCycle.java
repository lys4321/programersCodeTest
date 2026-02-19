package LightPathCycle;

import java.util.*;
class LightPathCycle {
    public int[] solution(String[] grid) {
        int gly = grid.length;
        int glx = grid[0].length();
        int al = 4;

        boolean[][][] direct = new boolean[gly][glx][al];
        int[] arrowY = { -1, 0, 1, 0 };
        int[] arrowX = { 0, 1, 0, -1 };

        List<Integer> cntList = new ArrayList<>();

        for(int y = 0; y < gly; y++){
            for(int x = 0; x < glx; x++){
                for(int i = 0; i < al; i++){
                    if (direct[y][x][i]) continue;

                    int cy = y;
                    int cx = x;
                    int ci = i;
                    int cnt = 0;

                    while(!direct[cy][cx][ci]){
                        direct[cy][cx][ci] = true;

                        char w = grid[cy].charAt(cx);
                        int weight =
                                (w == 'L') ? 3 :
                                        (w == 'R') ? 1 : 0;
                        ci = (ci + weight + al) % al;
                        cy = (cy + arrowY[ci] + gly) % gly;
                        cx = (cx + arrowX[ci] + glx) % glx;
                        cnt += 1;
                    }
                    if(cnt > 0){
                        cntList.add(cnt);
                    }
                }
            }
        }


        int[] answer = cntList.stream().sorted().mapToInt(Integer::intValue).toArray();
        return answer;
    }

    public static void main(String[] args) {
        String[] grid = {"SL", "LR"};

        LightPathCycle lpc = new LightPathCycle();

        System.out.println("결과 : " + Arrays.toString(lpc.solution(grid)));

    }
}
