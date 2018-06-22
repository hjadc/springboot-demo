package com.huju.lol.controller;

import com.huju.lol.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by v_huju on 2018/6/21.
 */
@RestController
@RequestMapping("/users")
public class HelloWorldController {

    static Map<Long,User> userMap = Collections.synchronizedMap(new HashMap<>());

    @RequestMapping("/hello")
    public String index(){
        return "Spring Boot Hello World!";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user){
        user.setId(1l);
        user.setName("王老五");
        user.setAge(28);
        System.out.println(user.toString());
        userMap.put(user.getId(), user);

        return "success";
    }


    // http://localhost:8080/users/?page=1&size=10
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<User> getUserList(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size){
        System.out.println("page=" + page + " ,size=" + size);
        List<User> users = new ArrayList<>(userMap.values());
        return users;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        return userMap.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateUser(@PathVariable Long id, @ModelAttribute User user){
        User user1 = userMap.get(id);
        user1.setName(user.getName());
        user1.setAge(user.getAge());

        userMap.put(id, user1);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        userMap.remove(id);

        return "success";
    }
}
