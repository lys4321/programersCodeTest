package PerfectCrime;

import java.util.*;

class PerfectCrime {
    public int solution(int[][] info, int n, int m) {
        final int INF = 1000000000;
        int[] hstr = new int[m];

        Arrays.fill(hstr, INF);
        hstr[0] = 0;

        for(int[] inf : info){
            int weightA = inf[0];
            int weightB = inf[1];
            int[] next = new int[m];

            Arrays.fill(next, INF);

            for(int i = 0; i < m; i++){
                if(hstr[i] == INF) continue;

                int nextA = hstr[i] + weightA;
                if(nextA < n){
                    next[i] = Math.min(next[i], nextA);
                }

                int nextB = i + weightB;
                if(nextB < m){
                    next[nextB] = Math.min(next[nextB], hstr[i]);
                }
            }
            hstr = next;
        }

        int answer = INF;

        for(int h : hstr){
            answer = Math.min(h, answer);
        }

        return answer == INF ? -1 : answer;
    }

    public static void main(String[] args) {
        int[][] info = {{1, 2}, {2, 3}, {2, 1}};
        int n = 4;
        int m = 4;

        PerfectCrime pc = new PerfectCrime();

        System.out.println("결과 : " + pc.solution(info, n, m));
    }
}