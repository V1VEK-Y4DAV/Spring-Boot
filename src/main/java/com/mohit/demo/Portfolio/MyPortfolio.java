package com.mohit.demo.Portfolio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyPortfolio {

    @GetMapping("/mySelf")

    public String Myself() {
    return """
            <h1>Myself</h1>
            <h3> Hii My Name is Ritik Kumar I am A Final year B.Tech. CSE Student at LPU </h3>
            <ul>
            <li><a target=_blank href="https://github.com/ritik-kumar660">gitHub</li>
            <li><a href="https://github.com/ritik-kumar660">LeetCode</li>
            </ul>
            """;
    }
    @GetMapping("/skills")
    public String Skills(){
        return """
                <h1> Tech Stack</h1>
                <ul>
                <li>Python</li>
                <li>Java</li>
                <li>CPP</li>
                <li>React</li>
                <li>Node</li>
                </ul>
                
                """;
    }
    @GetMapping("/education")
    public String getEducation(){
        return """
                <h1>Education Page</h1>
                """;
    }
    @GetMapping("/projects")
    public String getProjects(){
        return """
                <h1>Projects Page</h1>
                """;
    }
    @GetMapping("/contact")
    public String getContact(){
        return """
                <h1>Contact Page</h1>
                """;
    }





}
