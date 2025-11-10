package com.aws_beanstalk.aws.eb;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("v1/users")
public class UserController {
    private List<String> listUsername=new ArrayList<>();

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody String username){
        listUsername.add(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(listUsername.toString());
    }
}
