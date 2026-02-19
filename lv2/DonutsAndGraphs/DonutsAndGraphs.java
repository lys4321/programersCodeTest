package DonutsAndGraphs;

import java.util.*;

class DonutsAndGraphs {
    static private long[] conEdge;
    static private int[] answer;

    public int autoSettingInt(long[] targetArr, long value){
        int conVal = Arrays.binarySearch(targetArr, value);
        return (conVal >= 0) ? conVal : -conVal - 1;
    }

    public void searchingRoute(int to, int first){
        int start = autoSettingInt(conEdge, (long)(to) << 32);
        int end = autoSettingInt(conEdge, (long)(to + 1) << 32);
        int nextLeng = end - start;

        if(nextLeng <= 0){
            answer[2] = answer[2] + 1;
            return;
        }
        else if(nextLeng >= 2){
            answer[3] = answer[3] + 1;
            return;
        }

        int next = (int)(conEdge[start] & 0xFFFFFFFFL);

        if(first == next){
            answer[1] = answer[1] + 1;
            return;
        }

        searchingRoute(next, first);
    }

    public int[] solution(int[][] edges) {
        answer = new int[]{0, 0, 0, 0};
        int data_length = edges.length;
        long[] rvsConEdge = new long[data_length];
        conEdge = new long[data_length];

        for(int i = 0; i < data_length; i++){
            conEdge[i] = ((long)edges[i][0] << 32) | ((long)edges[i][1] & 0xffffffffL);
            rvsConEdge[i] = ((long)edges[i][1] << 32) | ((long)edges[i][0] & 0xffffffffL);
        }

        Arrays.sort(conEdge);
        Arrays.sort(rvsConEdge);

        int max = (int)(conEdge[data_length-1] >>> 32); // 정렬시킨 이유
        int startPoint = -1;
        int startIdx = -1;
        int endIdx = -1;

        for(int i = 1; i <= max; i++){
            long rvs_from = ((long)i) << 32;
            long rvs_to = ((long)(i+1)) << 32;
            int con_rvs_to = autoSettingInt(rvsConEdge, rvs_to);
            int con_rvs_from = autoSettingInt(rvsConEdge, rvs_from);
            int rvs_dist = con_rvs_to - con_rvs_from;

            if(rvs_dist == 0){
                int con_to = autoSettingInt(conEdge, rvs_to);
                int con_from = autoSettingInt(conEdge, rvs_from);
                int dist = con_to - con_from;

                if(dist >= 2){
                    startPoint = i;
                    startIdx = con_from;
                    endIdx = con_to;
                    break;
                }
            }
        }

        // 연결된 곳 저장
        int[] links = new int[endIdx - startIdx];
        int idx = 0;
        for(int i = startIdx; i < endIdx; i++){
            links[idx] = (int)(conEdge[i] & 0xFFFFFFFFL);
            idx++;
        }

        answer[0] = startPoint;

        for(int i = 0; i < links.length; i++){
            searchingRoute(links[i], links[i]);
        }

        return answer;
    }

    public static void main(String[] args) {
        int[][] edges = {
                {4, 11}, {1, 12}, {8, 3}, {12, 7}, {4, 2},
                {7, 11}, {4, 8}, {9, 6}, {10, 11}, {6, 10},
                {3, 5}, {11, 1}, {5, 3}, {11, 9}, {3, 8}
        };

        DonutsAndGraphs obj = new DonutsAndGraphs();

        System.out.println("결과 : " + Arrays.toString(obj.solution(edges)));
    }
}