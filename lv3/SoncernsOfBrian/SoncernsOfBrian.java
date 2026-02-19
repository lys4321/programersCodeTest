package SoncernsOfBrian;

import java.util.*;
import java.util.stream.Collectors;

class SoncernsOfBrian {
    public String solution(String sentence) {
        // 공백 포함 시 실패
        if(sentence.isEmpty()) return "invalid";
        if(sentence.contains(" ")) return "invalid";
        // 인덱스 탐색 필요
        // 그 중에 중복 일어날 시 튕기기 해야함
        // 또한 문자 붙을 경우?? 그런 것도 체크해야함
        int LEN = sentence.length();
        Map<Character, Integer[]> locMap = new LinkedHashMap<>();

        // 문자열 길이까지 순환
        for(int i = 0; i < LEN; i++) {
            char target = sentence.charAt(i);
            // 이미 넣었던 소문자면 무시
            if(locMap.containsKey(target)) continue;
            // 소문자일 때만 실행되게
            if(Character.isLowerCase(target)){
                List<Integer> temp = new ArrayList<>();
                int first = i;
                int next = sentence.indexOf(target, first + 1);
                int end = sentence.lastIndexOf(target);

                temp.add(first);
                // 소문자의 개수가 2개인 경우
                if(end > first){
                    // 다음 소문자의 인덱스와 처음 소문자의 인덱스 차이 구함
                    // 규칙 1의 경우라면 2일 것이며, 규칙2의 경우엔 2 이상
                    int dist = next - first;
                    // 소문자가 가운데 없이 붙어있을 경우 검사
                    if(dist == 1) return "invalid";

                    int forCheck = first;
                    while(forCheck != end){
                        if((sentence.indexOf(target, forCheck + 1) - forCheck) != dist){
                            return "invalid";
                        }
                        forCheck = sentence.indexOf(target, forCheck + 1);
                    }

                    // 소문자의 인덱스 범위 순환
                    for(int j = first + dist; j <= end; j += dist) {
                        char search = sentence.charAt(j);
                        // 처음 차이 값과 다른 경우 소문자 중복으로 판단
                        if(Character.isUpperCase(search)){
                            return "invalid";
                        }
                        temp.add(j);
                    }
                }
                locMap.put(target, temp.toArray(new Integer[temp.size()]));
            }
        }

        List<String> words = new ArrayList<>();
        List<String> usedUpper = new ArrayList<>();

        int currLoc = 0;
        boolean rule2 = false;
        char rule2Char = ' ';
        int ruleLastIdx = 0;
        try{
            for(char key : locMap.keySet()) {
                Integer[] idxArr = locMap.get(key);
                int IDX_LEN = idxArr.length;
                int first = idxArr[0];
                int end = idxArr[IDX_LEN - 1];

                char currLocChar = sentence.charAt(currLoc);

                // 규칙2로 감싼 규칙 1 완료 후 규칙2 상태도 빠져나가기 위한 부분
                if (rule2 && (rule2Char == currLocChar)) {
                    rule2Char = ' ';
                    rule2 = false;
                    ruleLastIdx = 0;
                    currLoc++;
                }

                // 현재 인덱스 위치가 소문자 시작 부분보다 작으면 그 차이만큼 대문자 추가
                if(currLoc < first){
                    // 규칙2가 실행중이지 않으며
                    // 인덱스 개수가 2개이면 다음 순환에서 규칙 2로 돌아가도록 유도
                    // 아니라면 규칙1로 유도
                    int splitEnd = ((IDX_LEN == 2) && !rule2) ? first : first - 1;
                    String word = sentence.substring(currLoc, splitEnd);

                    if(!word.isEmpty()){
                        words.add(word); // 정답 제출용 리스트에 추가
                        usedUpper.add(word); // 대문자 사용 이력 리스트에 추가
                        currLoc = splitEnd; // 다음 인덱스 조정
                    }
                }

                // 위에서 인덱스가 변경되어 다시 한 번 체크
                currLocChar = sentence.charAt(currLoc);

                if(Character.isUpperCase(currLocChar)){
                    if((end + 1) >= LEN){
                        return "invalid";
                    }

                    if(rule2 && (end > ruleLastIdx)){
                        return "invalid";
                    }

                    StringBuilder sb = new StringBuilder();
                    for(int i = currLoc; i <= end + 1; i++){
                        if(Character.isUpperCase(sentence.charAt(i))){
                            sb.append(sentence.charAt(i));
                        }
                    }

                    if(!(sb.toString().isEmpty())){
                        words.add(sb.toString());
                        usedUpper.add(sb.toString());
                    }

                    currLoc = end + 2;
                }
                else{
                    boolean interrupt = false; // 중간에 소문자 나올 시, 끊어짐 상태 의미
                    int interruptIdx = 0; // 끊어짐 위치

                    if(sentence.indexOf(currLocChar, currLoc + 1) != end) return "invalid";

                    for(int i =  currLoc + 1; i < end; i++) { // 규칙 2에 대한 검사
                        char searchCh = sentence.charAt(i);
                        boolean lowerYn = Character.isLowerCase(searchCh); // 소문자 여부

                        if (lowerYn) {
                            interrupt = true;
                            interruptIdx = i;
                            break;
                        }
                    }
                    // 중간에 소문자 검출될 경우 'ABCDb...'처럼 발견된 이전의 인덱스로 조정
                    int endLoc = interrupt ? interruptIdx - 1 : end;
                    String word = sentence.substring(currLoc + 1, endLoc);

                    if (!word.isEmpty()){
                        words.add(word);
                        usedUpper.add(word);
                    }

                    currLoc = interrupt ? interruptIdx - 1 : end + 1;

                    if (interrupt) {
                        if(rule2) return "invalid";
                        rule2 = true;
                        rule2Char = currLocChar;
                        ruleLastIdx = end;
                    }
                }
            }

            // 위에는 키의 개수만큼 순환하기 때문에 마무리 작업이 필요
            StringBuilder sb = new StringBuilder();
            for(int i = currLoc; i < LEN; i++){
                if(Character.isUpperCase(sentence.charAt(i))){
                    sb.append(sentence.charAt(i));
                }
            }

            if(!sb.toString().isEmpty()){
                words.add(sb.toString()); // 정답 제출용 리스트에 추가
                usedUpper.add(sb.toString()); // 대문자 사용 이력 리스트에 추가
            }
        }catch (Exception e) {
            e.setStackTrace(e.getStackTrace());
            return "invalid";
        }

        String finalResult = String.join(" ", words).trim().replaceAll("\\s+", " ");
        String forLengthComp_words = String.join("", words).trim();
        String forLengthComp_upper = usedUpper.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(""))
                .trim();


        return (forLengthComp_upper.equals(forLengthComp_words)) ? finalResult : "invalid";
    }

    public static void main(String[] args) {
        // 규칙1을 만족한 상태에서 규칙 2로 트러기?
        String[] inputs = {
                "", //=> "invalid"
                "AxAxAxAoBoBoB", //=> "invalid"
                "AxAxAxAoBoBoB", //=> "invalid"
                "aaA", //=> "invalid"
                "Aaa", //=> "invalid"
                "HaEaLaLaOWaOaRaLaD", //=> "invalid"
                "abAba", //=> "invalid"
                "HELLO WORLD", //=> "invalid"
                "xAaAbAaAx", //=> "invalid"
                "AbAaAbAaC", //=> "invalid"
                "AbAaAbAaCa", //=> "invalid"
                "aCaCa", //=> "invalid"
                "a", //=> "invalid"
                "aa", //=> "invalid"
                "aaaA", //=> "invalid"
                "AaAaAa", //=> "invalid"
                "bAaAcAb", //=> "invalid"
                "bAaAcAaBb", //=> "invalid"
                "bTxTxTaTxTbkABaCDk", //=> "invalid"
                "TxTxTxbAb", //=> "invalid"
                "xAaAbAaAx", //=> "invalid"
                "aAAAAAbAAAAa", //=> "AAAA AA AAA"
                "SpIpGpOpNpGJqOqA", //=> "SIGONG JOA"
                "HaEaLaLaObWORLDb", //=> "HELLO WORLD"
                "SpIpGpOpNpGJqOqA", //=> "SIGONG JOA"
                "aIaAM", //=> "I AM"
                "AAAaBaAbBBBBbCcBdBdBdBcCeBfBeGgGGjGjGRvRvRvRvRvR", //=> "AAA B A BBBB C BBBB C BB GG GGG RRRRRR"
                "aHELLOWORLDa", //=> "HELLOWORLD"
                "HaEaLaLObWORLDb", //=> "HELL O WORLD"
                "HaEaLaLaObWORLDb", //=> "HELLO WORLD"
                "aHbEbLbLbOacWdOdRdLdDc", //=> "HELLO WORLD"
                "aGbWbFbDakGnWnLk", //=> "GWFD GWL"
                "HaEaLaLaObWORLDbSpIpGpOpNpGJqOqAdGcWcFcDdeGfWfLeoBBoAAAAxAxAxAA", //=> "HELLO WORLD SIGONG JOA GWFD GWL BB AAA AAAA A"
                "aBcAadDeEdvAvlElmEEEEm", //=> "BA DE A E EEEE"
                "AaABCDEBbB", //=> "AA BCDE BB"
                "aAAAAAAa", //=> "AAAAAA"
                "aAAAAAAabBBBBBb", //=> "AAAAAA BBBBB"
                "AaA", //=> "AA"
                "A", //=> "A"
                "AB", //=> "AB"
                "aHbEbLbLbOacWdOdRdLdDc", //=> "HELLO WORLD"
                "AaAA", //=> "AA A"
                "AaAAa", //=> "A AA"
                "bAaAb", //=> "AA"
                "AaAaBBB", //=> "AAB BB"
                "aAa", //=> "A"
                "aHELLOaAbWORLDb", //=> "HELLO A WORLD"


                "aABbCa",  // A BC
                "HxIIFaYOUabPcAcScSbTHISdTESTdeCASEeYOUfCgAgNfPhAhShSiAiQUIZjANDjAkRkEVlElRlYSmMmAmRmTHyI",
                "HaEaLaLaObWORLDb", //HELLO WORLD
                "HaEaLaLaObWORLDbSpIpGpOpNpGJqOqAdGcWcFcDdeGfWfLeoBBoAAAAxAxAxAA", //HELLO WORLD SIGONG J O A GWFD GWL BB AAA AAAA A
                "SpIpGpOpNpGJqOqA", //SIGONG JOA
                "aIaAM", //I AM
                "AAAaBaAbBBBBbCcBdBdBdBcCeBfBeGgGGjGjGRvRvRvRvRvR", //AAA B A BBBB C BBBB C BB GG G G G RRRRRR
                "aHELLOWORLDa", //HELLOWORLD
                "HaEaLaLObWORLDb", //HELL O WORLD
                "HaEaLaLaObWORLDb", //HELLO WORLD
                "AA", //AA
                "XcXbXcX", //X XX X
                "AAAAxAxAA", //AAAA A AA
                "AaAaAcA", //A A AA
                "aAaAABBB", //A AABBB
                "AaAaAbAbAbA", //A A AAAA
                "AaBcBcBaBdBgBdeFFeBBBBsBStS", //A BBB BB FF BBB BB SS
                "AaAaAcA", //A A AA
                "HaEaLaLaObWORLDb", //HELLO WORLD
                "SpIpGpOpNpGJqOqA", //SIGONG JOA 또는 SIGONG J O A
                "aIaAM", //I AM
                "AAAaBaAbBBBBbCcBdBdBdBcCeBfBeGgGGjGjGRvRvRvRvRvR", //AAA B A BBBB C BBBB C BB GG G G G RRRRRR 또는 AA ABA BBBB C BBBB C BB GG GGG RRRRRR
                "aHELLOWORLDa", //HELLOWORLD
                "HaEaLaLObWORLDb", //HELL O WORLD
                "HaEaLaLaObWORLDb", //HELLO WORLD

                "aHbEbLbLbOacWdOdRdLdDc", //HELLO WORLD
                "aHbEbLbLbOacWdOdRdLdDc", //HELLO WORLD


                "xAaAbAaAx", // invalid
                "bAaAcAb",
                "aABCaa",  // invalid
                "aABCaDa", // invalid
                "HaEaLaLaOWaOaRaLaD", //invalid
                "abAba", //invalid
                "HELLO WORLD", //invalid
                "xAaAbAaAx", //invalid
                "aAAAbBBBb", //invalid
                "AaAaAbAbAb", //invalid
                "aHbEbLbLbacWdOdRdLdDc", //invalid
                "AaBcBcBaBdBgBdeFFeBBBBsBScS", //invalid
                "AababB", //invalid
                "AabCbaB", //invalid
                "AabCbCbCaB", //invalid
                "aBasD", //invalid
                "HaEaLaLaOWaOaRaLaD", //invalid
                "abAba", //invalid
                "HELLO WORLD", //invalid
                "xAaAbAaAx", //invalid
                "AababB", //invalid
                "aHbEbLbLbacWdOdRdLdDc", //invalid
                "HELLOWbObRbLb", //invalid
                "HELLOWbObRbLb", //invalid
                "aHbEbLbLbacWdOdRdLdDc", //invalid
                "AaaA", //invalid
                "aaA", //invalid
                "Aaa", //invalid

                "AxAxAxAoBoBoB", //invalid
                "AxAxAxAoBoBoB", //invalid
                "aAaAaAABBB", //invalid
                "AaAaAaAbAbAbA", //invalid
                "AaCbUaCbCbCB", //invalid
                "AbAaAbAaCa", //invalid
                "AbAaAbAaC", //invalid
                "AbAaAbAaCa", //invalid
                "AbAaAbAaCa", //invalid
                "AbAaAbAaC", //invalid
        };


        SoncernsOfBrian solution = new SoncernsOfBrian();
        for(String STR : inputs)
            System.out.println(solution.solution(STR));
    }
}