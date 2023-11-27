/* 작업자 우지원 */



package com.care.bedu.membership.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.care.bedu.membership.service.MembershipService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {
	
	@Autowired
	MembershipService service;
	
	@RequestMapping("/test")
	public String test(String imp_key, String imp_secret){
		String token = getToken(imp_key, imp_secret);
		return doCancel(token);
	}

	public String doCancel( String accessToken){
		ObjectMapper objectMapper = new ObjectMapper();

		Map<String, Object> token = new HashMap<>();

		try {
			Map<String,Object> resultMap = objectMapper.readValue(accessToken, Map.class);
			token = (Map<String, Object>) resultMap.get("response");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String baseUrl = "https://api.iamport.kr/payments/cancel";

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization","Bearer " + token.get("access_token"));

		Map<String, String> reqData = new HashMap();

		reqData.put("imp_uid","57008833");
		reqData.put("merchant_uid", "57008833");

        String requestBody;

        try {
            requestBody = objectMapper.writeValueAsString(reqData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert data to JSON.", e);
        }

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);	

		RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, requestEntity, String.class);

		return response.getBody();
	}

	public String getToken(String imp_key, String imp_secret){
		String baseUrl = "https://api.iamport.kr/users/getToken";

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> reqData = new HashMap();

		reqData.put("imp_key",imp_key);
		reqData.put("imp_secret", imp_secret);

		ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;

        try {
            requestBody = objectMapper.writeValueAsString(reqData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert data to JSON.", e);
        }

		HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

		RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, requestEntity, String.class);

		return response.getBody();
	}

	/* 구독자 저장 */
	@RequestMapping("/getSubscribe")
	public ResponseEntity<Object> getSubscribe(String nickname,String type){
		
		if(service.getSubscribe(nickname,type) == 1) {
			return ResponseEntity.ok().build();
		} else{
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
	}
	
	/* 구독 정보 조회 */
	@RequestMapping("/getSubInfo")
	public ResponseEntity<Object> getSubInfo(String nickname){
		
		HashMap<String, Object> map = new HashMap<>();
		
		map = service.getSubInfo(nickname);
		
		return new ResponseEntity<Object>(map,HttpStatus.OK);
	}
	
	
}
