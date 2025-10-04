import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MyApiController {

    @GetMapping("/get")
    public String getMethod() {
        return "GET method called!";
    }

    @PostMapping("/post")
    public String postMethod(@RequestBody String data) {
        return "POST method called with data: " + data;
    }

    @PutMapping("/put")
    public String putMethod(@RequestBody String data) {
        return "PUT method called with data: " + data;
    }

    @DeleteMapping("/delete")
    public String deleteMethod() {
        return "DELETE method called!";
    }
}
