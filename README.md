# To-Do List in Java and Spark
by [Sandro Alvarez](https://github.com/sandromateo) and [Caleb Stevenson](https://github.com/cgrahams)

version 0.0.0, September 14, 2016

## Description
A to-do list

### Specifications

| Behavior                   | Input              | Output             |
|----------------------------|--------------------|--------------------|
| Make a to-do task          | Make bed           | Make bed           |
| Make a list of to-do tasks | Make bed, walk dog | Make bed, walk dog |

### Routing

| Behavior                               | Path       | HTTP Verb | App.java Example                                                                                                                                                                                                                                                                            | Process                                                                                                                             |
|----------------------------------------|------------|-----------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------|
| Home Page                              | /          | GET       | get("/", (request, response) -> {,Map model = new HashMap();,model.put("template", "templates/index.vtl");,return new ModelAndView(model, layout);,}, new VelocityTemplateEngine());                                                                                                        | User requests page. Server sends HTTP GET request. Spark matches request to "/" route.                                              |
| List All tasks                         | /tasks     | GET       | get("/tasks", (request, response) -> {,Map model = new HashMap();,model.put("tasks", Task.all());,model.put("template", "templates/tasks.vtl");,return new ModelAndView(model, layout);,}, new VelocityTemplateEngine());                                                                   | User requests page. Server collects all tasks, renders template. Velocity loops through tasks and displays them.                    |
| Display specific tasks's details       | /tasks/:id | GET       | get("/tasks/:id", (request, response) -> {,Map model = new HashMap();,Task task = Task.find(Integer.parseInt(request.params(":id")));,model.put("task", task),model.put("template", "templates/task.vtl");,return new ModelAndView(model, layout);,}, new VelocityTemplateEngine());        | User requests to view single instance of task. Server uses task's unique ID to locate it, render page containing details.           |
| Display form                           | /tasks/new | GET       | get("/tasks/new", (request, response) {,Map model = new HashMap();,model.put("template", "templates/task-form.vtl");,return new ModelAndView(model, layout);,}, new VelocityTemplateEngine());                                                                                              | User requests form to create new task. Server returns page with form. *Form action and method must match path/verb of route below.* |
| Create new task when form is submitted | /tasks     | POST      | post("/tasks", (request, response) -> {,Map model = new HashMap();,String description = request.queryParams("description");,Task newTask = new Task(description);,model.put("template", "templates/success.vtl");,return new ModelAndView(model, layout);,}, new VelocityTemplateEngine()); | User submits form. Server grabs attributes from form. Uses them to create new task. Server renders a success page.                  |

## Known Issues

## Technology Used
Java, Spark, JUnit, Gradle

## Legal
Licensed under GNU 3.0

Copyright (c) 2016 _Sandro Alvarez & Caleb Stevenson_ All Rights Reserved.
