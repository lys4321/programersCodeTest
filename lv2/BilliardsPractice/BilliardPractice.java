package BilliardsPractice;

import java.util.*;

class BilliardPractice {
    public int[] solution(int m, int n, int startX, int startY, int[][] balls) {
        // 같은 입사각을가진다 라는 말은
        // 이등변 삼각형을 가진다는 의미이며,
        // 축을 대칭하면 여러 선계산이 단일 선계산이 되므로 한번에 처리가 가능하다.
        List<Integer> resultList = new ArrayList<>();

        for(int[] ball : balls){
            int[][] fcd = {
                    {ball[0], 2 * n - ball[1]} , // 상(y)
                    {2 * m - ball[0], ball[1]} , // 우(x)
                    {ball[0], -1 * ball[1]} , // 하(y)
                    {-1 * ball[0], ball[1]}   // 좌(x)
            };

            int dist = -1;
            int idx = 0;
            for(int[] coor : fcd){
                if(
                        (idx == 0 && startX == ball[0] && startY < ball[1]) ||
                                (idx == 1 && startY == ball[1] && startX < ball[0]) ||
                                (idx == 2 && startX == ball[0] && startY > ball[1]) ||
                                (idx == 3 && startY == ball[1] && startX > ball[0])
                ){
                    idx += 1;
                    continue;
                }

                int calVal =
                        (startX - coor[0]) * (startX - coor[0]) +
                                (startY - coor[1]) * (startY - coor[1]);

                if(dist == -1 || dist > calVal){
                    dist = calVal;
                }

                idx += 1;
            }

            resultList.add(dist);
        }

        int[] answer = resultList.stream().mapToInt(Integer::intValue).toArray();
        return answer;
    }

    public static void main(String[] args) {
        int m = 10;
        int n = 10;
        int startX = 3;
        int startY = 7;
        int[][] balls = {{7, 7}, {2, 7}, {7, 3}};

        BilliardPractice bp = new BilliardPractice();

        System.out.println("결과 : " + Arrays.toString(bp.solution(m, n, startX, startY, balls)));
    }
}