# Quant Network Solution

### Step 0: (Make the program run)

1. I began by checking dependencies in POM and since there were rabbitmq and mongodb I created `docker-compose.yaml`
   and `create_dir.sh` to prepare the environment to start the Application. (files are available at project's root)
    1. chmod +x create_dir.sh
    2. ./create_dir.sh
    3. docker-compose -f docker-compose.yaml up -d
    4. docker ps -a
2. I started the `SpringBootTemplateApplication` and got Exception at
   startup: `A default binder has been requested, but there is no binder available` [Error1].
    1. By looking at `DefaultBinderFactory` class in the `doGetBinder` method it is clear that `binderConfigurations` is
       empty.
    2. I added breakpoints to observe wiring + debug=true in application.yml
        1. By searching about the issue I had to provide some implementation(configs) and I found
           the `spring-cloud-stream-binder-rabbit` dependency is missing.
        2. I added `spring-cloud-stream-binder-rabbit` with version corresponding to `spring-cloud-stream`.
        3. And to make them unified I added ${version.spring-cloud-stream} properties.
3. I ran the Application after above modifications and ran successfully, But when I tried either of endpoints(Thanks to
   Template Service.postman_collection.json) I got Exception [Error2] (Query failed with error code 13 and error
   message) Not surprised, since I have not set proper credentials(username, password).
4. I changed mongodb properties in application.yml to be connected. But got another Exception(Authentication
   failed) [Error3]
5. After I searched about the above Exception,I found out that I had to add `authentication-database: admin` to tell
   where authentication db is(similar to this -> mongodb://USER:PASSWORD@localhost/DB?authSource=admin), which fixed the
   problem.

### Step 1: (Test out its functionality)

I started simple functional tests by importing `Template Service.postman_collection.json` to postman.

1. POST templates
    1. Validation -> templateField cannot be null in TemplateService: ``` {
       "service": "Template Service",
       "errorCode": 42,
       "timestamp": 1650745800161,
       "message": "Failed processing the request: The request is missing the required 'templateField' field.",
       "details": "uri=/templates"
       }```
    2. If you send request with `How are you?` value you get `Always peachy!` in return
    3. If you send 5 duplicate values, returns 42
    4. As commented in `postStuff` method except validation and above conditions, this method sends data to queue and
       then saves in database.
2. GET templates
    1. Throws exception if the value not found otherwise you get exact same value with ID(retrieves from database).
    2. Throws exception if there are more than that value in database(unhandled exception -> 500)

### Step 2: (Create Unit tests)

Some unit tests provided.

### Step 3: (What the program does)

Chat-bot / Gateway

### Step 4: (Curiosity)

1. Why I am able to post duplicate values and get `returned non unique result` Exception? (depending on business
   requirements we two options)
    1. Prevent duplication(POST)
    2. Return one of values eg:first, last (GET)
2. Why we have exposed all endpoints in management section in `application.yml` and there is no `actuator`
   dependency? `.../actuator/health -> 404` to enable the actuator, first we should add dependency(I commented out), and
   next we have to expose as we need.

[Error1]: ./docs/error1.txt "Full Stack Trace Error1"

[Error2]: ./docs/error2.txt "Query failed with error code 13 and error message"

[Error3]: ./docs/error2.txt "Authentication failed"
