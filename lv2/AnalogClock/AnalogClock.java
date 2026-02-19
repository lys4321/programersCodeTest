package AnalogClock;
import java.math.BigDecimal;
import java.math.RoundingMode;

class AnalogClock {
    final BigDecimal[] weight = {
            new BigDecimal("1")
                    .divide(new BigDecimal("120"), 20, RoundingMode.HALF_UP)  // 초당 시침 이동 각도 : 0
            ,new BigDecimal("0.5")     // 분당 시침 이동 각도 : 1
            ,new BigDecimal("0.1")     // 초당 분침 이동 각도 : 2
            ,new BigDecimal("6")       // 초당 초침 및 분당 분침 이동 각도 : 3
    };

    public void changeWeight(int h1, int m1, int s1, BigDecimal[] arr){
        arr[0] = new BigDecimal(h1)
                .remainder(new BigDecimal("12"))
                .multiply(new BigDecimal("30"))
                .add(
                        new BigDecimal(m1)
                                .multiply(weight[1])
                                .add(
                                        new BigDecimal(s1).multiply(weight[0])
                                ));  // 시침 각도값(360도 조정 전) 계산
        arr[1] = new BigDecimal(m1)
                .multiply(weight[3])
                .add(
                        new BigDecimal(s1).multiply(weight[2])
                );  // 분침 각도값(360도 조정 전) 계산
        arr[2] = new BigDecimal(s1).multiply(weight[3]);   // 초침 각도값(360도 조정 전) 계산
    }


    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        int answer = 0;

        int[] clock = { // 주어진 시각을 초로 변환
                h1 * 3600 + m1 * 60 + s1   // 시작 시간 초변환
                ,h2 * 3600 + m2 * 60 + s2   // 종료 시간 초변환
        };

        // 각도 선언필요
        BigDecimal[] angle = new BigDecimal[3];
        changeWeight(h1, m1, s1, angle);

        BigDecimal[] prev = new BigDecimal[3];
        BigDecimal[] curr = new BigDecimal[3];

        if(
                angle[2].compareTo(angle[1]) == 0 ||
                        angle[2].compareTo(angle[0]) == 0
        ){
            answer += 1;
        }

        for(int i = 1; i <= clock[1] - clock[0]; i++){
            prev[0] = angle[0];
            prev[1] = angle[1];
            prev[2] = angle[2];

            // 초 변경에 따른 각도 계산
            int currSecond = clock[0] + i;
            int ch = currSecond / 3600;
            int cm = (currSecond / 60) % 60;
            int cs = currSecond % 60;

            changeWeight(ch, cm, cs, angle);

            curr[0] = prev[0].compareTo(angle[0]) > 0 ? angle[0].add(new BigDecimal("360")) : angle[0];
            curr[1] = prev[1].compareTo(angle[1]) > 0 ? angle[1].add(new BigDecimal("360")) : angle[1];
            curr[2] = prev[2].compareTo(angle[2]) > 0 ? angle[2].add(new BigDecimal("360")) : angle[2];

            boolean minuteState =
                    ((prev[2].subtract(prev[1])).signum() < 0 &&
                            (curr[2].subtract(curr[1])).signum() >= 0);

            boolean hourState =
                    ((prev[2].subtract(prev[0])).signum() < 0 &&
                            (curr[2].subtract(curr[0])).signum() >= 0);

            if(minuteState && hourState){
                if(curr[0].compareTo(curr[1]) == 0){
                    answer += 1;
                }
                else{
                    answer += 2;
                }
            }
            else if(minuteState || hourState){
                answer += 1;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int h1 = 0;
        int m1 = 5;
        int s1 = 30;
        int h2 = 0;
        int m2 = 7;
        int s2 = 0;

        AnalogClock clock = new AnalogClock();

        System.out.println("결과 : " + clock.solution(h1, m1, s1, h2, m2, s2));

    }
}