import java.util.Map;
import java.util.HashMap;

class Bandaging {
    public int solution(int[] bandage, int health, int[][] attacks) {
        int answer = 0;

        int currHealth = health; // 계산에 사용될 체력
        int combo = 0; // 연속 성공 횟수
        int timing = bandage[0];
        int heal = bandage[1];
        int bonus = bandage[2];

        Map<Integer, Integer> attackInfo = new HashMap<>(); // 공격정보 저장용

        int i = 0;
        for(; i <= attacks.length-1; i++){
            attackInfo.put(attacks[i][0], attacks[i][1]);// 공격시간은 유일성을 가져서 이를 키로, 데미지는 값으로 사용
        }

        int lastSec = attacks[i-1][0]; // 붕대감기 최종 시간에 사용될 초

        for(int t = 0; t <= lastSec; t++){
            int inDamage = attackInfo.getOrDefault(t, 0);
            currHealth -= inDamage;

            if(currHealth <= 0){
                currHealth = -1;
                break;
            }

            if(inDamage > 0){
                combo = 0;
                continue;
            }

            currHealth += heal;
            combo += 1;

            if(combo >= timing){
                currHealth += bonus;
                combo = 0;
            }

            if(currHealth >= health) {
                currHealth = health;
            }

        }

        answer = currHealth;
        return answer;
    }

    public static void main(String[] args) {
        int[] bandage = {5, 1, 5};
        int health = 30;
        int[][] attacks = {
                {2, 10},
                {9, 15},
                {10, 5},
                {11, 5}
        };

        Bandaging b = new Bandaging();

        System.out.println("결과 : " + b.solution(bandage, health, attacks));
    }
}