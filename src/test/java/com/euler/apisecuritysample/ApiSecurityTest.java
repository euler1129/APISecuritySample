package com.euler.apisecuritysample;

import com.euler.apisecuritysample.auth.model.AuthenticateResult;
import com.euler.apisecuritysample.auth.model.RequestData;
import com.euler.apisecuritysample.auth.tools.GeneralTools;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.*;
import org.springframework.util.DigestUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@RunWith(OrderedRunner.class)
public class ApiSecurityTest {

    private RequestData requestData;

    @Before
    public void init(){
        String appId = GeneralTools.generateAppId();
        String appSecret = GeneralTools.generateAppSecret(appId);
        String nonce = RandomString.make(9);
        String timestamp = String.valueOf(Timestamp.valueOf(LocalDateTime.now()));
        this.requestData = RequestData.builder()
                .appId(appId)
                .nonce(nonce)
                .timestamp(timestamp)
                .build();
        String sign = this.getSign(this.requestData, appSecret);
        this.requestData.setSign(sign);
    }

    private String getSign(RequestData requestData, String appSecret){
        StringBuilder sb = new StringBuilder();
        sb.append("AppId=")
                .append(requestData.getAppId())
                .append(",Nonce=")
                .append(requestData.getNonce())
                .append(",Timestamp=")
                .append(requestData.getTimestamp())
                .append(",AppSecret=")
                .append(appSecret);
        log.info("Client Sign String: {}", sb);
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes(StandardCharsets.UTF_8)).toUpperCase();
    }

    @Order(1)
    @Test
    public void testing_Not_Pass_AppId() throws Exception {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/pub/api/testing").build().toUri();
        MultiValueMap<String, String> headers = new HttpHeaders();
        //headers.add("AppId", this.requestData.getAppId());
        headers.add("Nonce", this.requestData.getNonce());
        headers.add("Timestamp", this.requestData.getTimestamp());
        headers.add("Sign", this.requestData.getSign());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        Assert.assertNotNull(responseEntity);
        String responseEntityBody = responseEntity.getBody();
        Assert.assertNotNull(responseEntityBody);
        log.info("responseEntityBody: {}", responseEntityBody);
        AuthenticateResult authenticateResult = new ObjectMapper().readValue(responseEntityBody, AuthenticateResult.class);
        Assert.assertNotNull(authenticateResult);
        Assert.assertEquals(false, authenticateResult.getPass());
        Assert.assertEquals("请传递正确的请求参数", authenticateResult.getMessage());
    }

    @Order(2)
    @Test
    public void testing_Not_Pass_Nonce() throws Exception {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/pub/api/testing").build().toUri();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("AppId", this.requestData.getAppId());
        //headers.add("Nonce", this.requestData.getNonce());
        headers.add("Timestamp", this.requestData.getTimestamp());
        headers.add("Sign", this.requestData.getSign());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        Assert.assertNotNull(responseEntity);
        String responseEntityBody = responseEntity.getBody();
        Assert.assertNotNull(responseEntityBody);
        log.info("responseEntityBody: {}", responseEntityBody);
        AuthenticateResult authenticateResult = new ObjectMapper().readValue(responseEntityBody, AuthenticateResult.class);
        Assert.assertNotNull(authenticateResult);
        Assert.assertEquals(false, authenticateResult.getPass());
        Assert.assertEquals("请传递正确的请求参数", authenticateResult.getMessage());
    }

    @Order(3)
    @Test
    public void testing_Not_Pass_Timestamp() throws Exception {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/pub/api/testing").build().toUri();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("AppId", this.requestData.getAppId());
        headers.add("Nonce", this.requestData.getNonce());
        //headers.add("Timestamp", this.requestData.getTimestamp());
        headers.add("Sign", this.requestData.getSign());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        Assert.assertNotNull(responseEntity);
        String responseEntityBody = responseEntity.getBody();
        Assert.assertNotNull(responseEntityBody);
        log.info("responseEntityBody: {}", responseEntityBody);
        AuthenticateResult authenticateResult = new ObjectMapper().readValue(responseEntityBody, AuthenticateResult.class);
        Assert.assertNotNull(authenticateResult);
        Assert.assertEquals(false, authenticateResult.getPass());
        Assert.assertEquals("请传递正确的请求参数", authenticateResult.getMessage());
    }

    @Order(4)
    @Test
    public void testing_Not_Pass_Sign() throws Exception {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/pub/api/testing").build().toUri();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("AppId", this.requestData.getAppId());
        headers.add("Nonce", this.requestData.getNonce());
        headers.add("Timestamp", this.requestData.getTimestamp());
        //headers.add("Sign", this.requestData.getSign());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        Assert.assertNotNull(responseEntity);
        String responseEntityBody = responseEntity.getBody();
        Assert.assertNotNull(responseEntityBody);
        log.info("responseEntityBody: {}", responseEntityBody);
        AuthenticateResult authenticateResult = new ObjectMapper().readValue(responseEntityBody, AuthenticateResult.class);
        Assert.assertNotNull(authenticateResult);
        Assert.assertEquals(false, authenticateResult.getPass());
        Assert.assertEquals("请传递正确的请求参数", authenticateResult.getMessage());
    }

    @Order(5)
    @Test
    public void testing_Duplicate_Request() throws Exception {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/pub/api/testing").build().toUri();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("AppId", this.requestData.getAppId());
        headers.add("Nonce", this.requestData.getNonce());
        headers.add("Timestamp", this.requestData.getTimestamp());
        headers.add("Sign", this.requestData.getSign());
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<String> responseEntity1 = restTemplate1.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        Assert.assertNotNull(responseEntity1);
        String responseEntityBody1 = responseEntity1.getBody();
        Assert.assertNotNull(responseEntityBody1);
        log.info("responseEntityBody1: {}", responseEntityBody1);
        Assert.assertEquals("testing", responseEntityBody1);

        RestTemplate restTemplate2 = new RestTemplate();
        ResponseEntity<String> responseEntity2 = restTemplate2.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        Assert.assertNotNull(responseEntity2);
        String responseEntityBody2 = responseEntity2.getBody();
        Assert.assertNotNull(responseEntityBody2);
        log.info("responseEntityBody2: {}", responseEntityBody2);
        AuthenticateResult authenticateResult = new ObjectMapper().readValue(responseEntityBody2, AuthenticateResult.class);
        Assert.assertNotNull(authenticateResult);
        Assert.assertEquals(false, authenticateResult.getPass());
        Assert.assertEquals("请不要重复提交请求", authenticateResult.getMessage());
    }

    @Order(6)
    @Test
    public void testing_Tampered_Signatures() throws Exception {
        String generatedSignature = this.requestData.getSign();
        log.info("Signature before tampering: {}", generatedSignature);
        String tamperedSignature = generatedSignature.toLowerCase();
        log.info("Signature after tampering: {}", tamperedSignature);

        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/pub/api/testing").build().toUri();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("AppId", this.requestData.getAppId());
        headers.add("Nonce", this.requestData.getNonce());
        headers.add("Timestamp", this.requestData.getTimestamp());
        headers.add("Sign", tamperedSignature);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        Assert.assertNotNull(responseEntity);
        String responseEntityBody = responseEntity.getBody();
        Assert.assertNotNull(responseEntityBody);
        log.info("responseEntityBody: {}", responseEntityBody);
        AuthenticateResult authenticateResult = new ObjectMapper().readValue(responseEntityBody, AuthenticateResult.class);
        Assert.assertNotNull(authenticateResult);
        Assert.assertEquals(false, authenticateResult.getPass());
        Assert.assertEquals("签名无效", authenticateResult.getMessage());
    }

}