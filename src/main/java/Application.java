import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * The application is an executable point
 * Mentorship program's topic: Multithreading
 *
 * @author Oleksandr Deineka
 * @version 1.0
 * @since 2020-02-23
 */
public class Application {

    public static void main(String[] args) {
        WorkerService worker1 = new WorkerService();
        WorkerService worker2 = new WorkerService();
        WorkerService worker3 = new WorkerService();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService);

        List<Future<Integer>> futures = new ArrayList<Future<Integer>>();
        futures.add(executorCompletionService.submit(worker1));
        futures.add(executorCompletionService.submit(worker2));
        futures.add(executorCompletionService.submit(worker3));

        for (int i = 0; i < futures.size(); i++) {
            try {
                Integer result = executorCompletionService.take().get();
                System.out.println("Result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
