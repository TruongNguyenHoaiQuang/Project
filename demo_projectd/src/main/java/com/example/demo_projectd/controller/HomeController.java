package com.example.demo_projectd.controller;

import com.example.demo_projectd.model.UserProject;
import com.example.demo_projectd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class HomeController {

    String name = "";
    @Autowired
    UserService userService;

    @RequestMapping(value = {"/", "", "/home"}, method = RequestMethod.GET)
    public String homepage(){
        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/homeSigned", method = RequestMethod.POST)
    public String homeSigned(){
        return "home_signed";
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public String signUp(Model model){
        model.addAttribute("userproject", new UserProject());
        return "register";
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("userproject")UserProject userProject, Model model){

        userService.addUser(userProject);
        model.addAttribute("userproject", new UserProject());
            return "login";
    }

    @RequestMapping (value = "/detectImage", method = RequestMethod.POST)
    public String detect(Model model) {

        model.addAttribute("nameImage", name);

        return "detect_show";
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String upload(@RequestParam("imageFile") MultipartFile imageFile, Model model) throws IOException {

        saveImage(imageFile);
        String folder = "F:/2018-2019/Q1-DSA/demo_projectd/src/main/resources/static/imageDownload/";

        try {
            userService.detectImage(name);
        }catch (Exception e){
            e.printStackTrace();
        }

        model.addAttribute("nameImage", name);

        return "upload_page";
    }

    public void saveImage(MultipartFile imageFile) throws IOException {
        String folder = "F:/2018-2019/Q1-DSA/demo_projectd/src/main/resources/static/imageDownload/";

        byte[] bytes = imageFile.getBytes();
        name = imageFile.getOriginalFilename();
        Path path = Paths.get(folder + name);
        Files.write(path, bytes);

    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "test";
    }


}
