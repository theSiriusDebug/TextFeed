package TextFeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
class RedisController {
    Jedis jedis = new Jedis("localhost", 6379);
    public void add(String key, String value){
        jedis.set(key, value);
        jedis.close();
    }
    @GetMapping("/add/{value}")
    @ResponseBody
    public String add(@PathVariable String value){
        System.out.println(jedis.dbSize());
        add("text" + jedis.dbSize(), value);
        System.out.println("");
        System.out.println(jedis.dbSize());
        System.out.println(value);
        return "add";
    }

    @GetMapping("/")
    @ResponseBody // Эта аннотация указывает, что метод вернет данные, а не будет искать шаблон
    public String getAll(Model model) {
        Set<String> keys = jedis.keys("text*");
        List<String> values = new ArrayList<>();

        for (String key : keys) {
            String value = jedis.get(key);
            values.add(value);
        }
        model.addAttribute("values", values); // Передаем значения в модель
        return "home";
    }
}