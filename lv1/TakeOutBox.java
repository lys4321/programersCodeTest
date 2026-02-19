class TakeOutBox {
    public int solution(int n, int w, int num) {
        int answer = 0;
        int total_row = ((n + w - 1) / w) - 1; // 최상단의 row 좌표 구함
        int num_row = (num - 1) / w; // 목표의 row 좌표(개수) 구함
        int num_col = (num - 1) % w; // 목표의 col 좌표(개수) 구함

        // 정방향, 순방향 조정
        num_col = (num_row % 2 == 0) ? num_col : (w - 1) - num_col;

        for(int r=num_row; r<=total_row; r++){
            int boxCnt = Math.min(w, n - r * w);
            int lastRowCol = (r % 2 == 0) ? boxCnt - 1 : w - boxCnt;

            if(r % 2 == 0){
                if(lastRowCol >= num_col) answer++;
            }else {
                if(lastRowCol <= num_col) answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int n = 22;
        int w = 6;
        int num = 8;
        TakeOutBox tob = new TakeOutBox();
        System.out.println("결과 : " + tob.solution(n, w, num));
    }
}