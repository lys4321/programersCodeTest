import java.util.*;

class ReceiveReportResults {
    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = {};

        Map<String, Set<String>> reportHis = new HashMap<>(); // 이력 저장소
        Map<String, Integer> cntMap = new HashMap<>(); // 카운팅 저장소

        for(String id : id_list){
            reportHis.put(id, new HashSet<>());
        }

        for(String r : report){
            String[] sArr = r.split(" ");
            String cause = sArr[0];
            String effect = sArr[1];

            if(reportHis.get(cause).add(effect)){
                cntMap.put(effect, cntMap.getOrDefault(effect, 0) + 1);
            }
        }

        List<Integer> answerList = new ArrayList<>();
        for(String id : id_list){
            int f_report_cnt = 0;
            Set<String> resportHisSet = reportHis.get(id);
            if(resportHisSet != null){
                for(String target : resportHisSet){
                    if(cntMap.getOrDefault(target, 0) >= k){
                        f_report_cnt++;
                    }
                }
                answerList.add(f_report_cnt);
            }
        }


        answer = answerList.stream().mapToInt(Integer::intValue).toArray();

        return answer;
    }

    public static void main(String[] args) {
        String[] id_list = {"muzi", "frodo", "apeach", "neo"};
        String[] report = {"muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi"};
        int k = 2;

        ReceiveReportResults rrr = new ReceiveReportResults();

        System.out.println("결과 : " + Arrays.toString(rrr.solution(id_list, report, k)));
    }
}