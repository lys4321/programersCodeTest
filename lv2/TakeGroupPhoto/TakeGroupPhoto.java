package TakeGroupPhoto;
// 굳이 data를 전처리하여 사용할 필요없음
// 이 솔루션 함수 하나만으로 처리할 필요가 없음
// 반복문만을 고집하여 순환을 처리할 필요가 없음
// 순환을 하려면 반복문만이 유일한 정답이 아님

// -----------------------

// ""으로부터 시작하여 모든 경우의 수를 순환
// 순환은 재귀함수를 이용
//     |- AB
//     |- AC
//     |- AD
// A---
//     |- AE
//.  ....
//     |- BA
//     |- BC
//     |- BD
// B---
//     |- BE
// 의 구조처럼 이용
// => 저 많은 경우의 수도 ""으로부터시작

class TakeGroupPhoto {
    char[] frd = {'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};
    boolean[] line;
    int answer;

    public int solution(int n, String[] data) {
        String orderBy = "";
        line = new boolean[8];
        answer = 0;
        ordering(orderBy, data);
        return answer;
    }

    public void ordering(String curr, String[] data){
        if(curr.length() == 8){
            if(check(curr, data)) answer++;
            return;
        }

        for(int i = 0; i < 8; i++){
            if(!line[i]){
                line[i] = true;
                ordering(curr + frd[i], data);
                line[i] = false;
            }
        }
    }

    public boolean check(String curr, String[] data){
        for(String d : data){
            int res = curr.indexOf(d.charAt(0));
            int req = curr.indexOf(d.charAt(2));
            char oper = d.charAt(3);
            int len = d.charAt(4) - '0';

            int dist = Math.abs(res - req) - 1;

            if(oper == '>'){
                if(len >= dist) return false;
            }
            else if(oper == '<'){
                if(len <= dist) return false;
            }
            else if(oper == '='){
                if(dist != len) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 2;
        String[] data = {"N~F=0", "R~T>2"};

        TakeGroupPhoto gp = new TakeGroupPhoto();

        System.out.println("결과 : " + gp.solution(n, data));
    }
}