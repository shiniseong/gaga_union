package com.gaga.bo.service.chatbot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/rest/*")
public class ChatbotController {
	/*
	public ChatbotController() {
		System.out.println("hello!!");
	}


	private String secretKey = "VGZDTVBiZXBBRXhBbWFsdnRManVkV3RVRlJQdmtLbFg=";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SignatureGenerator signatureGenerator;

	@CrossOrigin
	@RequestMapping("chatbot")
	public ResponseEntity<String> chat(@org.springframework.web.bind.annotation.RequestBody String requestBody) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest()
				.getSession();
		Boolean hasReceivedWelcomeMessage = (Boolean) session.getAttribute("hasReceivedWelcomeMessage");
		if (hasReceivedWelcomeMessage == null) {
			hasReceivedWelcomeMessage = false;
			session.setAttribute("hasReceivedWelcomeMessage", false);
		}

		JSONObject json = new JSONObject(requestBody);
		String eventType = json.optString("event", "");
		String inputText = "";

		boolean isGptMode = false;
		if (json.has("isGptMode")) {
			isGptMode = json.getBoolean("isGptMode");
		}

		String chatbotMessage = "";

		JSONObject responseObject = new JSONObject();

		if (isGptMode) {
			try {
				// GPT 엔드포인트를 호출
				String gptApiUrl = "http://localhost:8080/api/gpt";
				JSONObject requestBodyJson = new JSONObject();
				requestBodyJson.put("prompt", inputText);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
				HttpEntity<String> request = new HttpEntity<>(requestBodyJson.toString(), headers);
				ResponseEntity<String> response = restTemplate.postForEntity(gptApiUrl, request, String.class);
				String gptResponse = response.getBody();
				JSONObject gptResponseJson = new JSONObject(gptResponse);
				JSONArray choices = gptResponseJson.getJSONArray("choices");
				String gptText = choices.getJSONObject(0).getString("text");
				System.out.println(gptText);
				chatbotMessage = gptText;
				responseObject = createResponseMessage(chatbotMessage);
			} catch (Exception e) {
				e.printStackTrace();
				chatbotMessage = "GPT API 호출 중 에러가 발생.";
				responseObject = createResponseMessage(chatbotMessage);
			}
		} else {
			if (eventType.equals("open")) {
				System.out.println("클라이언트에서 서버로 메시지가 도착했습니다:" + requestBody);
				sendMessageToNaverChatbot(requestBody);
				// 웰컴메시지 이벤트처리
				HttpHeaders headers = new HttpHeaders();
			    headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			    return new ResponseEntity<>(responseObject.toString(), headers, HttpStatus.OK);
				} else if (eventType.equals("send")) {
					// 정상 채팅응답
					inputText = json.getString("inputText");
					try {
						String apiURL = "https://a939u9nmlj.apigw.ntruss.com/custom/v1/9745/0d08472a858f3cb1994fd4f8eb5ead1fd4ccc4827bd38ed0ba1b2f37a997dee0";

						URL url = new URL(apiURL);

						String message = getReqMessage(inputText);
						System.out.println("##" + message);
						System.out.println(inputText + "##왓이즈인풋텍슽");
						String encodeBase64String = signatureGenerator.generateSignature(message, secretKey);

						HttpURLConnection con = (HttpURLConnection) url.openConnection();
						con.setRequestMethod("POST");
						con.setRequestProperty("Content-Type", "application/json;UTF-8");
						con.setRequestProperty("X-NCP-CHATBOT_SIGNATURE", encodeBase64String);

						// post request
						con.setDoOutput(true);
						DataOutputStream wr = new DataOutputStream(con.getOutputStream());

						wr.write(message.getBytes("UTF-8"));
						wr.flush();
						wr.close();
						int responseCode = con.getResponseCode();
						BufferedReader br;

						if (responseCode == 200) { 
							System.out.println(con.getResponseMessage());

							BufferedReader in = new BufferedReader(
									new InputStreamReader(con.getInputStream(), "UTF-8"));
							String decodedString;
							while ((decodedString = in.readLine()) != null) {
								JSONObject chatbotJson = new JSONObject(decodedString);
								JSONArray bubbles = chatbotJson.getJSONArray("bubbles");
								JSONObject firstBubble = bubbles.getJSONObject(0);
								JSONObject data = firstBubble.getJSONObject("data");
								chatbotMessage = data.getString("description");
								responseObject = createResponseMessage(chatbotMessage);
							}
							in.close();

						} else {
							chatbotMessage = con.getResponseMessage();
							responseObject = createResponseMessage(chatbotMessage);
						}
					} catch (Exception e) {
						System.out.println(e);
					}

				}

			}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

		return new ResponseEntity<>(responseObject.toString(), headers, HttpStatus.OK);
		}
		
	

	public static String getReqMessage(String voiceMessage) {

		String requestBody = "";

		try {

			JSONObject obj = new JSONObject();

			long timestamp = new Date().getTime();

			System.out.println("##" + timestamp);

			obj.put("version", "v2");
			obj.put("userId", "UserUserSuperUser");
			obj.put("timestamp", timestamp);

			JSONObject bubbles_obj = new JSONObject();

			bubbles_obj.put("type", "text");

			JSONObject data_obj = new JSONObject();
			data_obj.put("description", voiceMessage);

			bubbles_obj.put("type", "text");
			bubbles_obj.put("data", data_obj);

			JSONArray bubbles_array = new JSONArray();
			bubbles_array.put(bubbles_obj);

			obj.put("bubbles", bubbles_array);
			if (voiceMessage == null || voiceMessage.isEmpty()) {
				obj.put("event", "open"); // 웰컴메시지
			} else {
				obj.put("event", "send"); // 사용자가 보낸 메시지의 경우
			}
			System.out.println("251��° �޴� obj" + obj);
			requestBody = obj.toString();

		} catch (Exception e) {
			System.out.println("## Exception : " + e);
		}
		System.out.println("203리퀘스트바디는 무엇?" + requestBody);
		return requestBody;
	}

	private static JSONObject createResponseMessage(String chatbotMessage) {
		// 응답 메시지를 담을 JSON 객체를 생성합니다.
		JSONObject response = new JSONObject();

		response.put("version", "v2");
		response.put("userId", "U47b00b58c90f8e47428af8b7bddc1231heo2");
		response.put("timestamp", System.currentTimeMillis());
		response.put("event", "send");

		//응답 메시지를 담을 버블 객체를 생성하고, 데이터를 채워넣습니다.
		JSONObject bubble = new JSONObject();
		bubble.put("type", "text");

		JSONObject data = new JSONObject();
		data.put("description", chatbotMessage);

		bubble.put("data", data);

		// 버블 객체 응답메시지에 추가
		JSONArray bubbles = new JSONArray();
		bubbles.put(bubble);

		response.put("bubbles", bubbles);
		System.out.println("230 리스폰스? " + response);
		return response;
	}
	
	public JSONObject sendMessageToNaverChatbot(String requestBody) {
	    JSONObject responseObject = null;
	    try {
	        String apiUrl = "https://a939u9nmlj.apigw.ntruss.com/custom/v1/9745/0d08472a858f3cb1994fd4f8eb5ead1fd4ccc4827bd38ed0ba1b2f37a997dee0";
	        String signature = signatureGenerator.generateSignature(requestBody, secretKey);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "application/json; charset=utf-8");
	        headers.add("X-NCP-CHATBOT_SIGNATURE", signature);
	        
	        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

	        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST,
	                requestEntity, String.class);

	        if (response.getStatusCode() == HttpStatus.OK) {
	            String responseBody = response.getBody();
	            System.out.println("네이버 챗봇 응답 :" + responseBody);
	            responseObject = new JSONObject(responseBody);
	        } else {
	            System.out.println("네이버 챗봇 응답 에러 :" + response.getStatusCode());
	            responseObject = new JSONObject();
	            responseObject.put("error", "챗봇에서 응답하지 않습니다. " + response.getStatusCode());
	        }
	    } catch (Exception e) {
	        System.out.println("네이버 챗봇 요청 실패: " + e.getMessage());
	        responseObject = new JSONObject();
	        responseObject.put("error", "네이버 챗봇 서버 요청 실패: " + e.getMessage());
	    }
	    return responseObject;
	}
	*/	
}
