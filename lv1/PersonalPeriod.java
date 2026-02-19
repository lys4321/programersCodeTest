import java.util.*;

class PersonalPeriod {
    public int[] solution(String today, String[] terms, String[] privacies) {
        List<Integer> trash = new ArrayList<>();

        // terms 전처리
        Map<String, Integer> validTerm = new HashMap<>();
        for(String t : terms){
            int space = t.indexOf(' ');
            validTerm.put(t.substring(0, space), Integer.parseInt(t.substring(space + 1)));
        }

        // privacies를 기준으로 순혼
        for(int i = 0; i < privacies.length; i++){
            int first = privacies[i].indexOf('.');
            int second = privacies[i].indexOf('.', first + 1);
            int space = privacies[i].indexOf(' ');
            String select = privacies[i].substring(space + 1);

            int conDay =
                    Integer.parseInt(privacies[i].substring(0, first)) * 12 * 28 +
                            Integer.parseInt(privacies[i].substring(first + 1, second)) * 28 +
                            validTerm.get(select) * 28 +
                            Integer.parseInt(privacies[i].substring(second + 1, space)) - 1;

            int conToday =
                    Integer.parseInt(today.substring(0, first)) * 12 * 28 +
                            Integer.parseInt(today.substring(first + 1, second)) * 28 +
                            Integer.parseInt(today.substring(second + 1, space)) - 1;

            if(conDay <= conToday){
                trash.add(i + 1);
            }

        }

        int[] answer = trash.stream().mapToInt(Integer::intValue).toArray();
        return answer;
    }

    public static void main(String[] args) {
        String today = "2022.05.19";
        String[] terms = {"A 6", "B 12", "C 3"};
        String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};

        PersonalPeriod p = new PersonalPeriod();

        System.out.println("결과 : " + Arrays.toString(p.solution(today, terms, privacies)));

    }
}