package com.java.springboot.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.java.springboot.entities.User;
import com.java.springboot.service.CountryService;
import com.java.springboot.service.UserService;
import com.jayway.jsonpath.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api/v1/json/")
@Slf4j
public class JSONController {

    private final static String jsonPath = "src/main/resources/json/";

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @GetMapping("file/users")
    public void  saveUsersToCSVFolder() throws IOException {
        List<User> users = userService.getUsers();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

//        Object to JSON in file
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jsonPath+"Users.json"), users);

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
        log.info(json);
    }

    @GetMapping("read/users")
    public List<User>  readUsersToCSVFolder() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        List<User> users = mapper.readValue(new File(jsonPath+"Users.json"), List.class);
        return users;
    }

    @Operation(summary = "parses a complex json saved locally using gson object parsing.")
    @GetMapping("gson-read/pageinfo-sample")
    public Map<String, String>  readPageInfoSampleToUI() throws IOException {

        Map<String, String> response = new LinkedHashMap<>();
        Path path = Paths.get(jsonPath+"pageinfo-sample.json");
        String content = Files.readString(path, Charset.forName("UTF-8"));

        JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();

        String pageName = jsonObject.getAsJsonObject("pageInfo").get("pageName").getAsString();
        response.put("pageName", pageName);

        JsonArray arr = jsonObject.getAsJsonArray("posts");
        for (int i = 0; i < arr.size(); i++) {
            String post_id = arr.get(i).getAsJsonObject().get("post_id").getAsString();
            String author_name = arr.get(i).getAsJsonObject().get("author_name").getAsString();
            response.put("postId-"+i,post_id);
            response.put("authorName-"+i,author_name);
        }

        return response;
    }

    @Operation(summary = "parses a complex json saved locally using jsonPathparsing.", description = "read more - https://www.baeldung.com/guide-to-jayway-jsonpath ")
    @GetMapping("jsonpath-read/samples")
    public Map<String, String>  readPageInfoSampleUsingJsonPathToUI() throws IOException {

        Map<String, String> response = new LinkedHashMap<>();
        Path path = Paths.get(jsonPath+"pageinfo-sample.json");
        String pageInfo = Files.readString(path, Charset.forName("UTF-8"));

        String pageName = JsonPath.read(pageInfo, "$.pageInfo.pageName");
        response.put("pageName", pageName);
        Integer posts = JsonPath.read(pageInfo, "$.posts.length()");

        for(int i=0; i < posts; i++) {
            String post_id = JsonPath.read(pageInfo, "$.posts[" + i + "].post_id");
            String author_name = JsonPath.read(pageInfo, "$.posts[" + i + "].author_name");
            response.put("postId-"+i,post_id);
            response.put("authorName-"+i,author_name);
        }

        String toolInfo = Files.readString(Paths.get(jsonPath+"toolinfo-sample.json"), Charset.forName("UTF-8"));

        // JSON Paths
        String locationPathZero = "$.tool.jsonpath.creator.location[0]";
        String locationPathTwo = "$.tool.jsonpath.creator.location[2]";
        String jsonpathCreatorNamePath = "$['tool']['jsonpath']['creator']['name']";
        String jsonpathCreatorLocationPath = "$['tool']['jsonpath']['creator']['location'][*]";

        DocumentContext jsonContext = JsonPath.parse(toolInfo);
        String locationPathZeroValue = jsonContext.read(locationPathZero);
        String locationPathTwoValue = jsonContext.read(locationPathTwo);
        String jsonpathCreatorName = jsonContext.read(jsonpathCreatorNamePath);
        List<String> jsonpathCreatorLocations = jsonContext.read(jsonpathCreatorLocationPath);

        response.put(locationPathZero, locationPathZeroValue);
        response.put(locationPathTwo, locationPathTwoValue);
        response.put(jsonpathCreatorNamePath, jsonpathCreatorName);
        response.put(jsonpathCreatorLocationPath, jsonpathCreatorLocations.toString());

        return response;
    }

    @Operation(summary = "parses a complex json saved locally using jsonPath predicates.", description = "read more - https://www.baeldung.com/guide-to-jayway-jsonpath ")
    @GetMapping("jsonpath-read/sample-predicates")
    public Map<String, Object>  readPageInfoSampleUsingJsonPathPredicates() throws IOException {
        Map<String, Object> response = new LinkedHashMap<>();
        String bookInfo = Files.readString(Paths.get(jsonPath+"bookinfo-sample.json"), Charset.forName("UTF-8"));

        response.put("jsonFile", bookInfo);

        Filter expensiveFilter = Filter.filter(Criteria.where("price").gt(20.00));
        List<Map<String, Object>> expensive = JsonPath.parse(bookInfo)
                .read("$['book'][?]", expensiveFilter);
        response.put("predicate1", expensive);

        List<Map<String, Object>> expensive2 = JsonPath.parse(bookInfo)
                .read("$['book'][?(@['price'] > $['price range']['medium'])]");
        response.put("predicate2 - $['book'][?(@['price'] > $['price range']['medium'])]", expensive2);

        Predicate expensivePredicate = new Predicate() {
            public boolean apply(PredicateContext context) {
                String value = context.item(Map.class).get("price").toString();
                return Float.valueOf(value) > 20.00;
            }
        };
        List<Map<String, Object>> expensive3 = JsonPath.parse(bookInfo)
                .read("$['book'][?]", expensivePredicate);
        response.put("predicate3", expensive3);

        return response;
    }

    @Operation(summary = "parses a complex json saved locally using jsonPath predicates.", description = "read more - https://www.baeldung.com/guide-to-jayway-jsonpath ")
    @GetMapping("jsonpath-read/movieinfo-sample")
    public Map<String, Object>  readMovieInfoSampleUsingJsonPath() throws IOException {
        Map<String, Object> response = new LinkedHashMap<>();

        String movieInfo = Files.readString(Paths.get(jsonPath+"movieinfo-sample.json"), Charset.forName("UTF-8"));

        Object dataObject = JsonPath.parse(movieInfo).read("$[?(@.id == 2)]");
        String dataString = dataObject.toString();
        response.put("$[?(@.id == 2)]", dataString);

        List<Map<String, Object>> dataList = JsonPath.parse(movieInfo)
                .read("$[?('Eva Green' in @['starring'])]");
        List<String> titles = dataList.stream().map((d) -> (String)d.get("title")).collect(Collectors.toList());
        response.put("Movies with Eva Green - $[?('Eva Green' in @['starring'])]", titles);

        DocumentContext context = JsonPath.parse(movieInfo);
        int length = context.read("$.length()");
        long revenue = 0;
        for (int i = 0; i < length; i++) {
            revenue += context.read("$[" + i + "]['box office']", Long.class);
        }
        response.put("Total Box Office Revenue", revenue);

        context = JsonPath.parse(movieInfo);
        List<Object> revenueList = context.read("$[*]['box office']");
        Integer[] revenueArray = revenueList.toArray(new Integer[0]);
        Arrays.sort(revenueArray);
        int highestRevenue = revenueArray[revenueArray.length - 1];
        Configuration pathConfiguration =
                Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> pathList = JsonPath.using(pathConfiguration).parse(movieInfo)
                .read("$[?(@['box office'] == " + highestRevenue + ")]");
        response.put("Highest Revenue Path", pathList);

        Map<String, String> dataRecord = context.read(pathList.get(0));
        String title = dataRecord.get("title");
        response.put("Highest Revenue Title", title);

        context = JsonPath.parse(movieInfo);
        List<Map<String, Object>> samMendesMovies = context.read("$[?(@.director == 'Sam Mendes')]");

//        Filter directorSamMendesFilter = Filter.filter(Criteria.where("director")
//                .contains("Sam Mendes"));
//        List<Map<String, Object>> samMendesMovies = JsonPath.parse(movieInfo)
//                .read("$[?]['director']", directorSamMendesFilter);

        List<Object> dateList = new ArrayList<>();
        for (Map<String, Object> item : samMendesMovies) {
            Object date = item.get("release date");
            dateList.add(date);
        }
        Long[] dateArray = dateList.toArray(new Long[0]);
        Arrays.sort(dateArray);

        long latestTime = dateArray[dateArray.length - 1];
        List<Map<String, Object>> finalDataList = context.read("$[?(@['director'] == 'Sam Mendes' && @['release date'] == " + latestTime + ")]");
        title = (String) finalDataList.get(0).get("title");
        response.put("Latest Movie By Sam Mendes - $[?(@['director'] == 'Sam Mendes' && @['release date'] == " + latestTime + ")]", title);
        
        return  response;
    }

}
