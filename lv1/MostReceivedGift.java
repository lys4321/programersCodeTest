import java.util.*;
import java.util.stream.*;

class MostReceivedGift {
    public Map<String, Map<String, Map<String, Integer>>> settingMapStructure(String[] friends){
        return
                Arrays.stream(friends)
                        .collect(Collectors.toMap(
                                friend_out -> friend_out,
                                friend_out -> Arrays.stream(friends)
                                        .filter(friend_in -> !friend_in.equals(friend_out))
                                        .collect(Collectors.toMap(
                                                friend_in -> friend_in,
                                                friend_in -> {
                                                    Map<String, Integer> tradeMap = new HashMap<>();
                                                    tradeMap.put("sendCnt", 0);
                                                    tradeMap.put("catchCnt", 0);
                                                    return tradeMap;
                                                }
                                        ))));
    }

    public int solution(String[] friends, String[] gifts) {
        int answer = 0;

        Map<String, Map<String, Map<String, Integer>>> calMap =
                settingMapStructure(friends);

        Map<String, Integer> scoreMap = new HashMap<>();

        // 이름으로 순환
        for(String f : friends){
            Map<String, Map<String, Integer>> target = calMap.get(f);
            int totalScore = 0;
            for(String g : gifts){
                String[] s = g.split(" ");
                String sender = s[0];
                String catcher = s[1];

                if(sender.equals(f)){
                    Map<String, Integer> setMap = target.get(catcher);
                    setMap.put("sendCnt", setMap.get("sendCnt") + 1);
                    totalScore += 1;
                }
                else if(catcher.equals(f)){
                    Map<String, Integer> setMap = target.get(sender);
                    setMap.put("catchCnt", setMap.get("catchCnt") + 1);
                    totalScore -= 1;
                }
            }
            scoreMap.put(f, totalScore);
        }

        Map<String, Map<String, Map<String, Integer>>> nextCalMap =
                settingMapStructure(friends);

        for(String outf : friends){
            Map<String, Map<String, Integer>> pMap = calMap.get(outf);
            int prevScore = scoreMap.get(outf);
            int nextCatchCnt = 0;

            for(String inf : friends){
                if(!outf.equals(inf)){
                    int sendCnt = pMap.get(inf).get("sendCnt");
                    int catchCnt = pMap.get(inf).get("catchCnt");

                    if(sendCnt > catchCnt){
                        nextCatchCnt += 1;
                    }
                    else if(sendCnt == catchCnt){
                        int compareScore = scoreMap.get(inf);
                        if(prevScore > compareScore) nextCatchCnt += 1;
                    }
                }
            }
            if(answer < nextCatchCnt) answer = nextCatchCnt;
        }


        return answer;
    }

    public static void main(String args[]){
        String[] friends = {"muzi", "ryan", "frodo", "neo"};
        String[] gifts = {
                "muzi frodo",
                "muzi frodo",
                "ryan muzi",
                "ryan muzi",
                "ryan muzi",
                "frodo muzi",
                "frodo ryan",
                "neo muzi"
        };
        MostReceivedGift mrg = new MostReceivedGift();

        System.out.println("결과 : " + mrg.solution(friends, gifts));
    }
}