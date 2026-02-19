package PseudoCantorArray;

class PseudoCantorArray {
    public int solution(int n, long l, long r) {
        // 11011 11011 00000 11011 11011
        // 11011
        // 1
        //-----------------------------------
        // 1
        // 11011
        // 11011 11011 00000 11011 11011
        return (int) (recursive(n, r) - recursive(n, l-1));
    }

    public long recursive(int n, long a){
        if(a == 0) return 0;
        if(n == 0) return 1;

        long prevL = (long) Math.pow(5, n - 1);
        long prevCnt = (long) Math.pow(4, n - 1);

        int currSection = (int) ((a - 1) / prevL);
        long remain = (a - 1) % prevL + 1;

        if(currSection < 2){
            return (prevCnt * currSection) + recursive(n - 1, remain);
        }
        else if(currSection == 2){
            return 2 * prevCnt;
        }
        else{
            return (prevCnt * (currSection -1)) + recursive(n - 1, remain);
        }

    }

    public static void main(String[] args) {
        int n = 2;
        int l = 4;
        int r = 17;

        PseudoCantorArray pc = new PseudoCantorArray();

        System.out.println("결과 : " + pc.solution(n, l, r));
    }
}