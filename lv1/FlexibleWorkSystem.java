class FlexibleWorkSystem {
    public int solution(int[] schedules, int[][] timelogs, int startday){
        int answer = 0;
        // schedules 기준으로 순환
        for(int schedule=0; schedule < schedules.length; schedule++) {
            // 희망 시간 설정
            int hope_time = schedules[schedule] / 100;
            int hope_minute = schedules[schedule] % 100;

            hope_minute += 10; // 먼저 +10
            hope_time = (hope_time + (hope_minute / 60)) % 24; // +10한 후에 목 늘어나면 시간에 +1
            hope_minute = hope_minute % 60; // +10한 후에 나머지 부분만 분으로 설정

            int hope_schedule = (hope_time * 100) + hope_minute;
            int[] access_logs = timelogs[schedule];
            boolean all_complete = true;

            // 대상자 별 출근 이력 순환 및 비교
            for(int i = 0; i < 7; i++) {
                int searchIndex = ((startday - 1 ) + i) % 7;
                // 평일이면서 설정 시간보다 늦게 왔을 경우 체크 감지하여 fasle 변환
                if((searchIndex < 5) && (access_logs[i] > hope_schedule)) {
                    all_complete = false;
                    break;
                }
            }

            if(all_complete) {
                answer++;
            }
        }
        return answer;
    }

    public static void main(String[] args) {
        int[] schedules = {700, 800, 1100};
        int[][] timelogs = {
                {710, 2359, 1050, 700, 650, 631, 659},
                {800, 801, 805, 800, 759, 810, 809},
                {1105, 1001, 1002, 600, 1059, 1001, 1100}
        };
        int startday = 5;

        FlexibleWorkSystem fws = new FlexibleWorkSystem();

        System.out.println("결과 : " + fws.solution(schedules, timelogs, startday));

    }
}