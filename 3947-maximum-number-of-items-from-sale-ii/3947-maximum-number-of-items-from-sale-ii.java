
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;
        int maxFactor = 0;
        int minPrice = Integer.MAX_VALUE;

        
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
            minPrice = Math.min(minPrice, item[1]);
        }

       
        int[] freq = new int[maxFactor + 1];

        for (int[] item : items) {
            freq[item[0]]++;
        }

       
        for (int i = 1; i <= maxFactor; i++) {
            for (int j = 2 * i; j <= maxFactor; j += i) {
                freq[i] += freq[j];
            }
        }

       
        TreeMap<Integer, Long> map = new TreeMap<>();

        for (int[] item : items) {
            int factor = item[0];
            int price = item[1];

            int gain = freq[factor] - 1;

            if (gain > 0 && price < 2L * minPrice) {
                map.put(price,
                    map.getOrDefault(price, 0L) + gain);
            }
        }

       
        long remaining = budget;
        long answer = 0;

        for (Map.Entry<Integer, Long> entry : map.entrySet()) {

            int price = entry.getKey();
            long count = entry.getValue();

            long take = Math.min(count, remaining / price);

            answer += take * 2;
            remaining -= take * price;
        }

       
        answer += remaining / minPrice;

        return (int) answer;
    }
}
