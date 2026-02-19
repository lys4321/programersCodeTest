class VideoPlayer {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        String answer = "";

        // 코딩 진행 상황
        // 입력값 전처리
        int tSec = (Integer.parseInt(video_len.split(":")[0]) * 60) + Integer.parseInt(video_len.split(":")[1]);
        int cSec = (Integer.parseInt(pos.split(":")[0]) * 60) + Integer.parseInt(pos.split(":")[1]);
        int osSec = (Integer.parseInt(op_start.split(":")[0]) * 60) + Integer.parseInt(op_start.split(":")[1]);
        int oeSec = (Integer.parseInt(op_end.split(":")[0]) * 60) + Integer.parseInt(op_end.split(":")[1]);

        int weight = 10;
        // commands 배열의 요소만큼 순환
        for(String c : commands){
            int setVal = (cSec >= tSec) ? tSec : cSec; // 비교값 계산
            if(Math.max(osSec, Math.min(setVal, oeSec)) == setVal){
                setVal = oeSec;
            }

            // c 에 따라 분기하여 처리
            if(c.equals("prev")){
                setVal -= weight;

                if(setVal <= 0){
                    cSec = 0;
                }
                else if((setVal >= osSec) && (setVal <= oeSec)){
                    cSec = oeSec; // 비교값이 오프닝 구간에 위치 시 처리 및 비교값 할당
                }
                else{
                    cSec = setVal;
                }
            }
            else if(c.equals("next")){

                setVal += weight; // 비교값 계산
                if(setVal >= tSec){
                    cSec = tSec; // 비교값이 전체 영상 길이 초과 시 처리 및 비교값 할당
                }
                else if((setVal >= osSec) && (setVal <= oeSec)){
                    cSec = oeSec; // 비교값이 오프닝 구간에 위치 시 처리 및 비교값 할당
                }
                else{
                    cSec = setVal; // 비교값 할당
                }
            }
        }

        int calSec = 60;

        // 계산된 최종 재생구간 값 처리
        answer =
                String.format("%02d", cSec / calSec) + ":" + String.format("%02d", cSec % calSec);

        return answer;
    }

    public static void main(String[] args) {
        String video_len = "34:33";
        String pos = "13:00";
        String op_start = "00:55";
        String op_end = "02:55";
        String[] commands = {"next", "prev"};

        VideoPlayer videoPlayer = new VideoPlayer();
        System.out.println("결과 : " + videoPlayer.solution(video_len, pos, op_start, op_end, commands));
    }
}