package KakaoFriendsColoringBook;

import java.util.Arrays;

class KakaoFriendsColoringBook {
    boolean[][] visited;
    int[] ay = {1, -1, 0, 0}; // 상하
    int[] ax = {0, 0, -1, 1}; // 좌우

    public int searchArea(int cury, int curx, int[][] arr, int y, int x){
        int result = 1;
        visited[cury][curx] = true;

        for(int i = 0; i < 4; i++){
            int ny = cury + ay[i];
            int nx = curx + ax[i];

            if((ny >= 0 && ny < y) && (nx >= 0 && nx < x)){
                if((arr[cury][curx] == arr[ny][nx]) && !visited[ny][nx]){
                    result = result + searchArea(ny, nx, arr, y, x);
                }
            }
        }

        return result;
    }

    public int[] solution(int m, int n, int[][] picture) {
        int[] answer = {0, 0};
        visited = new boolean[m][n];

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(picture[i][j] == 0) continue;
                if(!visited[i][j]){
                    int cal = searchArea(i, j, picture, m, n);
                    answer[0] = answer[0] + 1;
                    answer[1] = answer[1] < cal ? cal : answer[1];
                }
            }
        }


        return answer;
    }

    public static void main(String[] args) {
        int m = 6;
        int n = 4;
        int[][] picture = {
                {1, 1, 1, 0},
                {1, 2, 2, 0},
                {1, 0, 0, 1},
                {0, 0, 0, 1},
                {0, 0, 0, 3},
                {0, 0, 0, 3}
        };

        KakaoFriendsColoringBook obj = new KakaoFriendsColoringBook();

        System.out.println("결과 : " + Arrays.toString(obj.solution(m, n, picture)));

    }
}