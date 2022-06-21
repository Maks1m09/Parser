package by.tms;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/parser")
public class Controller {

    @GetMapping("/doParser1")
    public @ResponseBody

    StringBuffer doParser() {
        return doNothing();
    }

    private StringBuffer doNothing() {
        Pattern pattern = Pattern.compile("<DIV>(.*)", Pattern.MULTILINE);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.getForEntity("https://football.kulichki.net/england/", String.class);
        Matcher matcher = (pattern.matcher(
                Objects.requireNonNull(entity.getBody()).
                        replace("<DIV class=title_r>\n", "<DIV class=title_r>").
                        replaceAll("<DIV class=title_r><A.*>\n\n", "<DIV>")));

        StringBuffer s = new StringBuffer();
        while (matcher.find()){
            s.append(matcher.group(1)+"\n");
        }
        System.out.println(s);
        return s;
    }
}

