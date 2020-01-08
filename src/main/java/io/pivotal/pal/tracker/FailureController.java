package io.pivotal.pal.tracker;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/failure")
public class FailureController {
    public static boolean simulateFailure;

    @PostMapping("/set")
    public void set() {
        simulateFailure = true;
    }

    @DeleteMapping("/unset")
    public void unset() {
        simulateFailure = false;
    }
}
