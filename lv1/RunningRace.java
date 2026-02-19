import java.util.*;

class RunningRace {
    public String[] solution(String[] players, String[] callings) {
        String[] tPlayers = players.clone();
        Map<String, Integer> rank = new HashMap<>();

        for(int i = 0; i < tPlayers.length; i++){
            rank.put(tPlayers[i], i);
        }

        for(String c : callings){
            int curr = rank.get(c);
            int prev = curr - 1;

            rank.put(c, prev);
            rank.put(tPlayers[prev], curr);

            tPlayers[curr] = tPlayers[curr - 1];
            tPlayers[prev] = c;
        }

        return tPlayers;
    }

    public static void main(String[] args) {
        String[] players = {"mumu", "soe", "poe", "kai", "mine"};
        String[] callings = {"kai", "kai", "mine", "mine"};

        RunningRace r = new RunningRace();

        System.out.println("결과 : " + Arrays.toString(r.solution(players, callings)));
    }
}