package Tiling;

import java.util.*;

class Tiling {
    final int MOD = 1000000007;
    int[] memoArr; // 이런 방식 처음 사용, 효율성 증진을 위해 사용한다던데

    public int settingParttern(int size){
        if(size == 0) return 1;
        if(memoArr[size] != -1) return memoArr[size];

        long sum = 0;

        for(int i = 4; i <= size; i = i + 2){
            sum = (sum + (2 * (long)settingParttern(size - i))) % MOD;
        }

        sum = (sum + (3 * (long)settingParttern(size - 2))) % MOD;

        memoArr[size] = (int)(sum % MOD);

        return memoArr[size];
    }

    public int solution(int n) {
        if(n % 2 != 0) return 0;
        int answer = 0;
        memoArr = new int[n + 1];

        Arrays.fill(memoArr, -1);

        answer = settingParttern(n);
        return answer;
    }

    public static void main(String[] args) {
        int n = 4;
        Tiling tiling = new Tiling();
        System.out.println(tiling.solution(n));
    }
}