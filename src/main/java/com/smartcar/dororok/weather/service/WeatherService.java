package com.smartcar.dororok.weather.service;

import com.smartcar.dororok.weather.domain.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class WeatherService {

    private final String key = System.getenv("WEATHER_KEY");

    private final GridService gridService;

    private final RestTemplate restTemplate;

    public WeatherService(GridService gridService, RestTemplate restTemplate) {
        this.gridService = gridService;
        this.restTemplate = restTemplate;
    }

    public GetWeatherDto getCurrentWeather(String lat, String lng) {
        LatXLngY grid = gridService.convertGRID_GPS(0,Long.parseLong(lat),Long.parseLong(lng));
        String nx = String.format("%.0f",grid.x);
        String ny= String.format("%.0f",grid.y);
        ApiDateTime dateTime = convertAPITime();

        String date = dateTime.getDate();
        String time = dateTime.getTime();
        //System.out.println("now date = " + date + " time = " + time);

        String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"
                + "?serviceKey=" + key
                + "&pageNo=1"                   // 페이지 번호
                + "&numOfRows=36"              // 페이지 ROWS
                + "&dataType=JSON"             // JSON, XML
                + "&base_date=" + date         // 발표일자
                + "&base_time=" + time         // 발표시각
                + "&nx=" + nx                   // 예보지점 X 좌표
                + "&ny=" + ny ;                 // 예보지점 Y 좌표

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        // HttpEntity에 헤더를 포함시켜 전달
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // HTTP GET 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,  // 수정된 부분: 헤더 포함된 엔티티 전달
                String.class
        );

        // 응답 값
        return getWeatherInfo(responseEntity.getBody());

    }


    private String getNowDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(new Date());
    }

    private String getNowTime() {
        DateFormat dateFormat = new SimpleDateFormat("HHmm");
        return dateFormat.format(new Date());
    }


    private String getPreviousDate(String dateStr) {
        try {
            // 날짜 형식 지정
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

            // 문자열을 Date 객체로 파싱
            Date date = dateFormat.parse(dateStr);

            // Calendar 객체 생성 및 설정
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // 하루 전으로 설정
            calendar.add(Calendar.DAY_OF_MONTH, -1);

            // 결과 날짜 포맷팅
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //API 요청 파라미터에 맞게 날짜와 시간 변경
    private ApiDateTime convertAPITime() {

        ApiDateTime apiDateTime = new ApiDateTime();

        String date = getNowDate();
        String time = getNowTime();

        apiDateTime.setDate(date);
        int convertTime = Integer.parseInt(time);

        if (convertTime < 210 || convertTime >= 2310)  {
            apiDateTime.setDate(getPreviousDate(date));
            apiDateTime.setTime("2300");
        } else if( convertTime >= 210 && convertTime < 510) {
            apiDateTime.setTime("0200");
        } else if( convertTime >= 510 && convertTime < 810) {
            apiDateTime.setTime("0500");
        } else if( convertTime >= 810 && convertTime < 1110) {
            apiDateTime.setTime("0800");
        } else if( convertTime >= 1110 && convertTime < 1410) {
            apiDateTime.setTime("1100");
        } else if( convertTime >= 1410 && convertTime < 1710) {
            apiDateTime.setTime("1400");
        } else if( convertTime >= 1710 && convertTime < 2010) {
            apiDateTime.setTime("1700");
        } else if( convertTime >= 2010 && convertTime < 2310) {
            apiDateTime.setTime("2000");
        }
        return apiDateTime;
    }


    private GetWeatherDto getWeatherInfo(String json) {
        GetWeatherDto dto = new GetWeatherDto();

        Long nowTime = Long.parseLong(getNowTime());

        JSONObject jsonObject = new JSONObject(json);
        JSONObject response = jsonObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray itemArray = items.getJSONArray("item");

        // SKY와 PTY 값을 추출하여 출력
        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            String category = item.getString("category");
            Long fcstTime = Long.parseLong(item.getString("fcstTime"));
            if (category.equals("SKY")) {
                int fcstValue = item.getInt("fcstValue");
                SkyCondition skyCondition = SkyCondition.fromCode(fcstValue);
                dto.setSkyCondition(skyCondition);
            } else if (category.equals("PTY") && (nowTime <= fcstTime)) {
                int fcstValue = item.getInt("fcstValue");
                PrecipitationType precipitationType = PrecipitationType.fromCode(fcstValue);
                dto.setPrecipitationType(precipitationType);
                //System.out.println(fcstTime);
                break;
            }
        }
        return dto;
    }
}
