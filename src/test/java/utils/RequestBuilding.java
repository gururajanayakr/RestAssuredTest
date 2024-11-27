package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class RequestBuilding {

    public static RequestSpecification requestSpecification;

    public RequestSpecification buildRequest() throws IOException {
        if(requestSpecification == null) {
            FileOutputStream fileOutputStream = new FileOutputStream(System.getProperty("user.dir")+"/logs/reportLogs.txt");
            PrintStream log = new PrintStream(fileOutputStream);
            requestSpecification = new RequestSpecBuilder().setBaseUri(PropertyData.getProperty("baseUrl"))
                    .addQueryParam("key","qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return requestSpecification;
        }
        return requestSpecification;
    }
}
