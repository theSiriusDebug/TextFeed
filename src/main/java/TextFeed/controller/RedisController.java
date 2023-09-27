package TextFeed.controller;

import TextFeed.SpecificException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import redis.clients.jedis.Jedis;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RedisController {
    private final Jedis jedis;
    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    public RedisController() {
        jedis = new Jedis("localhost", 6379);
    }

    @GetMapping("/add")
    public String add(@RequestParam("value") String value) {
        logger.info("user trying to add new element to redis db.");
        if (value != null && !value.isEmpty()) {
            try {
                String key = "text" + jedis.dbSize();
                jedis.set(key, value);
                logger.info("Successfully set key '{}' with value '{}'", key, value);
            } catch (Exception e) {
                logger.error("Error occurred while setting key and value in cache. Value: '{}'", value, e);
                e.printStackTrace();
                return "error";
            }

        } else {
            return "invalidData";
        }

        return "redirect:/";
    }

    @GetMapping("/")
    public String getAll(Model model) {
        try {
            List<String> values = retrieveValuesFromRedis();
            model.addAttribute("values", values);
            logger.info("Retrieved values from Redis: {}", values);
            return "home";
        } catch (Exception e) {
            handleOtherErrors(e);
            return "error";
        }
    }

    private List<String> retrieveValuesFromRedis() {
        Set<String> keys = jedis.keys("text*");
        return keys.stream()
                .map(key -> jedis.get(key))
                .collect(Collectors.toList());
    }

    private void handleSpecificException(SpecificException e) {
        logger.error("Specific error occurred", e);
    }

    private void handleOtherErrors(Exception e) {
        logger.error("Error occurred while retrieving values from Redis", e);
    }
}
