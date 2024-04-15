package eu.europa.ec.digit.search.improveperformance;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NumberService {

    private static final int SAMPLE_SIZE = 100_000;
    private Random random = new Random();

    public Integer findSmallestDuplicate(List<Integer> data) {

        List<Integer> duplicates = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {

            for (int j = i + 1; j < data.size(); j++) {

                if (data.get(i).equals(data.get(j))) {

                    log.info("found duplicate {}", data.get(j));
                    duplicates.add(data.get(j));
                }
            }
        }

        return duplicates.stream().sorted().findFirst().orElse(null);

    }

    public Integer findSmallestDuplicateImproved(List<Integer> data) {

        boolean[] duplicates = new boolean[SAMPLE_SIZE];

        int smallestDuplicate = -1;
        int size = data.size();
        for (int i = 0; i < size; ++i) {

            int value = data.get(i);
            if (!duplicates[value]) {
                duplicates[value] = true;
            }
            else {
                log.info("found duplicate {}", value);

                if (smallestDuplicate < 0) {
                    smallestDuplicate = value;
                }
                else {
                    smallestDuplicate = Math.min(smallestDuplicate, value);
                }
            }
        }

        return smallestDuplicate < 0 ? null : smallestDuplicate;

/*
        long start = System.currentTimeMillis();
        int[] array = new int[data.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = data.get(i);
        }
        Arrays.sort(array);
        log.info("Sorting {}", System.currentTimeMillis() - start);

        start = System.currentTimeMillis();

        boolean found = false;
        int smallestDuplicate = Integer.MAX_VALUE;
        // Protects reporting duplicate twice
        boolean equal = false;
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i - 1]) {
                equal = true;
            }
            else if (equal){
                equal = false;
                found = true;
                log.info("found duplicate {}", array[i - 1]);
                smallestDuplicate = Math.min(smallestDuplicate, array[i - 1]);
            }
        }

        // Handle duplicate sequence at the end
        if (equal) {
            found = true;
            log.info("found duplicate {}", array[array.length - 1]);
            smallestDuplicate = Math.min(smallestDuplicate, array[array.length - 1]);
        }

        log.info("Finding {}", System.currentTimeMillis() - start);

        return found ? smallestDuplicate : null;
*/

    }

    public List<Integer> generateData() {

        List<Integer> data = IntStream.range(0, SAMPLE_SIZE).boxed().collect(toList());
        
        data.add(data.get(random.nextInt(data.size())));
        log.info("first duplicate number is: {}", data.get(data.size() - 1));
        data.add(data.get(random.nextInt(data.size())));
        log.info("second duplicate number is: {}", data.get(data.size() - 1));
        Collections.shuffle(data);

        return data;
    }
}
