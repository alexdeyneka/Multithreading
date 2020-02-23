import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class WorkerService implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(WorkerService.class.getName());
    private static BlockingQueue<int[]> queue;

    private static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();

    static {
        queue = new ArrayBlockingQueue<int[]>(3);
        int[] arrayA = {25, 10, 2, 8, 5, 3};
        int[] arrayB = {8, 10, 1, 8, 64, 3};
        int[] arrayC = {65, 10, 2, 0, 5, 15};
        try {
            queue.put(arrayA);
            queue.put(arrayB);
            queue.put(arrayC);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        boolean permission = getPermission();
        if (permission && !queue.isEmpty()) {
            performAction(map, queue.poll());
            finish();
        }
    }

    private synchronized boolean getPermission() {
        LOGGER.info("Thread " + Thread.currentThread().getName() + " is started");
        return true;
    }

    private void performAction(ConcurrentHashMap<String, Integer> map, int[] inputArray) {
        int maxXor = 0;
        for (int i = 0; i < inputArray.length; i++) {
            for (int j = i + 1; j < inputArray.length; j++) {
                maxXor = Math.max(maxXor, inputArray[i] ^ inputArray[j]);
            }
        }
        map.put(Thread.currentThread().getName(), maxXor);
    }

    private synchronized void finish() {
        LOGGER.info("Thread " + Thread.currentThread().getName() + " is finished");
    }

    public static ConcurrentHashMap<String, Integer> getMap() {
        return map;
    }
}
