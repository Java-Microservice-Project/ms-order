package com.example.ms_order.client.decoder;

import az.atl.orderms.exception.CustomFeignException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.web.ErrorResponse;

import static az.atl.orderms.model.enums.ErrorMessage.CLIENT_ERROR;


public class CustomErrorDecoder implements ErrorDecoder {
    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {
        var objectMapper = new ObjectMapper();
        var errorResponse = objectMapper.readValue(response.body().asInputStream(), ErrorResponse.class);

        return new CustomFeignException(CLIENT_ERROR.getMessage(), errorResponse.getBody().getStatus());
    }
}