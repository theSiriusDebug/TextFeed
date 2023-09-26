package TextFeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/getAll")
    public Map<String, String> getAllValues() {
        Map<Object, Object> allEntries = redisTemplate.opsForHash().entries("myHash");
        // Convert the keys and values to strings
        Map<String, String> stringEntries = new HashMap<>();
        for (Map.Entry<Object, Object> entry : allEntries.entrySet()) {
            stringEntries.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return stringEntries;
    }

    @PostMapping("/add")
    public void addValue(@RequestBody String value) {
        String key = generateKey(); // Генерируем ключ
        redisTemplate.opsForHash().put("myHash", key, value); // Добавляем значение с сгенерированным ключом
    }

    private String generateKey() {
        Map<Object, Object> allEntries = redisTemplate.opsForHash().entries("myHash");
        int sum = 0;
        for (Object value : allEntries.values()) {
            sum += Integer.parseInt(value.toString());
        }
        return String.valueOf(sum);
    }
}
