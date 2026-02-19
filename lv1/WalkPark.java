import java.util.Arrays;

class WalkPark {
    public int[] solution(String[] park, String[] routes) {
        int pxl = park[0].length();
        int pyl = park.length;
        int[] curLoc = null;
        int rl = routes.length;

        for(int i = 0; i < pyl; i++){
            if(park[i].contains("S")){
                curLoc = new int[]{park[i].indexOf('S') % pxl, i};
                break;
            }
        }

        for(int i = 0; i < rl; i++){
            char d = routes[i].charAt(0);
            int m = Integer.parseInt(routes[i].substring(2));

            int xWeight = (d == 'E') ? 1 : (d == 'W') ? -1 : 0;
            int yWeight = (d == 'S') ? 1 : (d == 'N') ? -1 : 0;

            int tCurX = curLoc[0];
            int tCurY = curLoc[1];
            boolean check = false;

            for(int j = 1; j <= m; j++){
                int tx = curLoc[0] + xWeight * j;
                int ty = curLoc[1] + yWeight * j;

                if(
                        tx < 0
                                ||ty < 0
                                ||tx >= pxl
                                ||ty >= pyl
                                ||park[ty].charAt(tx) == 'X'
                ){
                    check = true;
                    break;
                }

                tCurX = tx;
                tCurY = ty;
            }
            if(!check){
                curLoc[0] = tCurX;
                curLoc[1] = tCurY;
            }
        }

        int[] answer = {curLoc[1], curLoc[0]};
        return answer;
    }

    public static void main(String[] args) {
        String[] park = {"SOO", "OOO", "OOO"};
        String[] routes = {"E 2", "S 2", "W 1"};

        WalkPark wp = new WalkPark();

        System.out.println("결과 : " + Arrays.toString(wp.solution(park, routes)));

    }
}