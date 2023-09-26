import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RedisConsoleApp {

	public static void main(String[] args) {
		SpringApplication.run(RedisConsoleApp.class, args);
	}
}

@Component
class RedisExample implements CommandLineRunner {

	private final RedisTemplate<String, String> redisTemplate;

	@Autowired
	public RedisExample(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void run(String... args) {
		// Пример работы с Redis
		String key = "myKey";
		String value = "Hello, Redis!";

		// Сохраняем значение в Redis
		redisTemplate.opsForValue().set(key, value);

		// Получаем значение из Redis
		String retrievedValue = redisTemplate.opsForValue().get(key);

		System.out.println("Значение из Redis: " + retrievedValue);
	}
}
