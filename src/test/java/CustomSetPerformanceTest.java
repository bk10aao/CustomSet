import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class CustomSetPerformanceTest {
    public static void main(String[] args) {
        // Input sizes to test
        int[] sizes = {1, 10, 50, 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000,
                25000, 50000, 100000, 250000, 500000, 1000000 };
        // Store results
        ArrayList<long[]> results = new ArrayList<>();
        Random random = new Random();

        for (int size : sizes) {
            System.out.println(size);
            // Pre-generate data for consistent testing
            Collection<Integer> collection = generateCollection(size, random);
            CustomSet<Integer> set = new CustomSet<>();
            set.addAll(collection); // Populate set for methods needing data

            // Benchmark each method
            System.out.println("add");
            long addTime = benchmarkAdd(new CustomSet<>(), size, random);
            System.out.println("addAll");

            long addAllTime = benchmarkAddAll(new CustomSet<>(), collection);
            System.out.println("clear");

            long clearTime = benchmarkClear(new CustomSet<>(collection));
            System.out.println("contains");

            long containsTime = benchmarkContains(set, random);
            System.out.println("containsAll");

            long containsAllTime = benchmarkContainsAll(set, collection);
            System.out.println("isEmpty");

            long isEmptyTime = benchmarkIsEmpty(set);
            System.out.println("remove");

            long removeTime = benchmarkRemove(set, random);
            System.out.println("removeAll");

            long removeAllTime = benchmarkRemoveAll(set, collection);
            System.out.println("retainAll");

            long retainAllTime = benchmarkRetainAll(set, collection);
            System.out.println("size");

            long sizeTime = benchmarkSize(set);
            System.out.println("toArray");

            long toArrayTime = benchmarkToArray(set);
            System.out.println("toString");

            long toStringTime = benchmarkToString(set);

            // Store results (in nanoseconds)
            results.add(new long[]{size, addTime, addAllTime, clearTime, containsTime,
                    containsAllTime, isEmptyTime, removeTime, removeAllTime,
                    retainAllTime, sizeTime, toArrayTime, toStringTime});
        }

        // Write results to CSV
        try (FileWriter writer = new FileWriter("V2_Hash_2_performance_data.csv")) {
            writer.write("Size,AddTime,AddAllTime,ClearTime,ContainsTime,ContainsAllTime," +
                    "IsEmptyTime,RemoveTime,RemoveAllTime,RetainAllTime,SizeTime," +
                    "ToArrayTime,ToStringTime\n");
            for (long[] result : results) {
                writer.write(String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",
                        result[0], result[1], result[2], result[3], result[4], result[5],
                        result[6], result[7], result[8], result[9], result[10], result[11],
                        result[12]));
            }
            System.out.println("Performance data written to performance_data.csv");
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }

    private static long benchmarkAdd(CustomSet<Integer> set, int size, Random random) {
        long start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            set.add(random.nextInt());
        }
        return System.nanoTime() - start;
    }

    private static long benchmarkAddAll(CustomSet<Integer> set, Collection<Integer> collection) {
        long start = System.nanoTime();
        set.addAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkClear(CustomSet<Integer> set) {
        long start = System.nanoTime();
        set.clear();
        return System.nanoTime() - start;
    }

    private static long benchmarkContains(CustomSet<Integer> set, Random random) {
        int item = random.nextInt();
        long start = System.nanoTime();
        set.contains(item);
        return System.nanoTime() - start;
    }

    private static long benchmarkContainsAll(CustomSet<Integer> set, Collection<Integer> collection) {
        long start = System.nanoTime();
        set.containsAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkIsEmpty(CustomSet<Integer> set) {
        long start = System.nanoTime();
        set.isEmpty();
        return System.nanoTime() - start;
    }

    private static long benchmarkRemove(CustomSet<Integer> set, Random random) {
        int item = random.nextInt();
        long start = System.nanoTime();
        set.remove(item);
        return System.nanoTime() - start;
    }

    private static long benchmarkRemoveAll(CustomSet<Integer> set, Collection<Integer> collection) {
        long start = System.nanoTime();
        set.removeAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkRetainAll(CustomSet<Integer> set, Collection<Integer> collection) {
        long start = System.nanoTime();
        set.retainAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkSize(CustomSet<Integer> set) {
        long start = System.nanoTime();
        set.size();
        return System.nanoTime() - start;
    }

    private static long benchmarkToArray(CustomSet<Integer> set) {
        long start = System.nanoTime();
        set.toArray();
        return System.nanoTime() - start;
    }

    private static long benchmarkToString(CustomSet<Integer> set) {
        long start = System.nanoTime();
        set.toString();
        return System.nanoTime() - start;
    }

    private static Collection<Integer> generateCollection(int size, Random random) {
        Collection<Integer> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            collection.add(random.nextInt());
        }
        return collection;
    }
}