import java.util.concurrent.ConcurrentHashMap;

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
        for (int i = 1; i <= 3; i++) {
            new Thread(new WorkerService(), "Thread-" + i).start();
        }
        WorkerService.getMap().forEach((key, value) -> System.out.println(key + " : " + value));
    }
}
