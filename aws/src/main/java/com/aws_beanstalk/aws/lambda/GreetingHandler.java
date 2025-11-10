package com.aws_beanstalk.aws.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class GreetingHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        try {
            String username = "Anonymous";
            if (event.getBody() != null) {
                Map<String, String> input = objectMapper.readValue(event.getBody(), Map.class);
                if (input != null) {
                    username = input.getOrDefault("username", "Anonymous");
                }
            }

            String responseBody = "Hello " + username;

            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpStatus.OK.value())
                    .withHeaders(Map.of("Content-Type", "application/json"))
                    .withBody("{\"message\":\"" + responseBody + "\"}");

        } catch (Exception e) {  // Catching Exception is safer here (JsonProcessingException)
            context.getLogger().log("Error : " + e.getMessage());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withBody("{\"error\":\"Internal Server Error\"}");
        }
    }
}
