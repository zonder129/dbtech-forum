package park.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import park.server.model.Status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import park.server.services.ForumService;
import park.server.services.PostService;
import park.server.services.ThreadService;
import park.server.services.UserService;

@RestController
@RequestMapping("/api/service")
public class ServiceController {
    private UserService userService;

    private ForumService forumService;

    private PostService postService;

    private ThreadService threadService;

    public ServiceController(UserService userService, ForumService forumService, PostService postService, ThreadService threadService) {
        this.userService = userService;
        this.forumService = forumService;
        this.postService = postService;
        this.threadService = threadService;
    }

    @PostMapping("/clear")
    public ResponseEntity clear() {
        userService.deleteAll();
        forumService.deleteAll();
        postService.deleteAll();
        threadService.deleteAll();
        forumService.deleteAllVisitors();
        return ResponseEntity.status(200).body(null);
    }

    @GetMapping("/status")
    public ResponseEntity<Status> status() {
        Status dbStatus = new Status(forumService.amount(), postService.amount(), threadService.amount(), userService.amount());
        return ResponseEntity.status(200).body(dbStatus);
    }

}
