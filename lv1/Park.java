import java.util.Arrays;
import java.util.Collections;
class Park {
    public int solution(int[] mats, String[][] park) {
        int answer = -1;

        // 매트 길이 긴 순으로 내림차순 정렬
        int mal = mats.length; //matArrLength
        Integer[] transMats = Arrays.stream(mats).boxed().toArray(Integer[]::new);
        Arrays.sort(transMats, Collections.reverseOrder());

        int yl = park.length; // y len
        int xl = park[0].length; // x len

        for(Integer v : transMats){
            if(v == null){
                continue; // null 검사
            }

            int val = v; // Integer -> int

            if(val > yl || val > xl) continue;

            for(int y = 0; y + val <= yl; y++){
                for(int x = 0; x + val <= xl; x++){
                    boolean sign = true;
                    for(int ystart = y; ystart < (y + val) && sign; ystart++){
                        for(int xstart = x; xstart < (x + val); xstart++){
                            if(!park[ystart][xstart].equals("-1")){
                                sign = false;
                                break;
                            }
                        }
                    }
                    if(sign){
                        return val;
                    }
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] mats = {5, 3, 2};
        String[][] park = {
                {"A", "A", "-1", "B", "B", "B", "B", "-1"},
                {"A", "A", "-1", "B", "B", "B", "B", "-1"},
                {"-1", "-1", "-1", "-1", "-1", "-1", "-1", "-1"},
                {"D", "D", "-1", "-1", "-1", "-1", "E", "-1"},
                {"D", "D", "-1", "-1", "-1", "-1", "-1", "F"},
                {"D", "D", "-1", "-1", "-1", "-1", "E", "-1"}
        };

        Park p = new Park();

        System.out.println("결과 : " + p.solution(mats, park));
    }
}