package com.onsil.onsil.flower.service;

import com.onsil.onsil.flower.dto.FlowerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FlowerService {
    @Value("${api.flower.service-key}")
    private String serviceKey;

    // 월별 전체 꽃 리스트 (공공데이터포털 API)
    public List<FlowerDto> getFlowersByMonth(int month) throws Exception {
        String urlStr = "https://apis.data.go.kr/1390804/NihhsTodayFlowerInfo01/selectTodayFlowerList01"
                + "?ServiceKey=" + URLEncoder.encode(serviceKey, "UTF-8")
                + "&fMonth=" + month
                + "&numOfRows=30";
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int status = conn.getResponseCode();
        InputStream is = (status == 200) ? conn.getInputStream() : conn.getErrorStream();
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8);
        StringBuilder xml = new StringBuilder();
        while (sc.hasNext()) xml.append(sc.nextLine());
        sc.close();

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml.toString())));
        doc.getDocumentElement().normalize();

        // resultCode/resultMsg 체크
        String resultCode = getTagValue("resultCode", doc.getDocumentElement());
        String resultMsg = getTagValue("resultMsg", doc.getDocumentElement());
        if (!"00".equals(resultCode) && !"1".equals(resultCode)) {
            throw new RuntimeException("공공데이터포털 API 오류: " + resultMsg + " (코드:" + resultCode + ")");
        }

        NodeList items = doc.getElementsByTagName("item");
        List<FlowerDto> list = new ArrayList<>();
        for (int i = 0; i < items.getLength(); i++) {
            Element e = (Element) items.item(i);
            list.add(FlowerDto.builder()
                    .dataNo(parseInt(getTagValue("dataNo", e)))
                    .fMonth(parseInt(getTagValue("fMonth", e)))
                    .fDay(parseInt(getTagValue("fDay", e)))
                    .flowNm(getTagValue("flowNm", e))
                    .fSctNm(getTagValue("fSctNm", e))
                    .fEngNm(getTagValue("fEngNm", e))
                    .flowLang(getTagValue("flowLang", e))
                    .fContent(getTagValue("fContent", e))
                    .fUse(getTagValue("fUse", e))
                    .fGrow(getTagValue("fGrow", e))
                    .fType(getTagValue("fType", e))
                    .fileName1(getTagValue("fileName1", e))
                    .imgUrl1(getTagValue("imgUrl1", e))
                    .publishOrg(getTagValue("publishOrg", e))
                    .build());
        }
        return list;
    }

    // 이 달의 탄생화 (fType에 '탄생화' 포함)
    public List<FlowerDto> getBirthFlowersByMonth(int month) throws Exception {
        return getFlowersByMonth(month).stream()
                .filter(f -> f.getFType() != null && f.getFType().contains("탄생화"))
                .collect(Collectors.toList());
    }

    // 이 달의 꽃 추천 (탄생화 제외)
    public List<FlowerDto> getRecommendedFlowersByMonth(int month) throws Exception {
        return getFlowersByMonth(month).stream()
                .filter(f -> f.getFType() == null || !f.getFType().contains("탄생화"))
                .collect(Collectors.toList());
    }

    // 꽃 상세정보 (dataNo)
    public FlowerDto getFlowerDetailFromApi(Integer dataNo) throws Exception {
        String urlStr = "https://apis.data.go.kr/1390804/NihhsTodayFlowerInfo01/selectTodayFlowerView01"
                + "?ServiceKey=" + URLEncoder.encode(serviceKey, "UTF-8")
                + "&dataNo=" + dataNo;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int status = conn.getResponseCode();
        InputStream is = (status == 200) ? conn.getInputStream() : conn.getErrorStream();
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8);
        StringBuilder xml = new StringBuilder();
        while (sc.hasNext()) xml.append(sc.nextLine());
        sc.close();

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml.toString())));
        doc.getDocumentElement().normalize();

        String resultCode = getTagValue("resultCode", doc.getDocumentElement());
        String resultMsg = getTagValue("resultMsg", doc.getDocumentElement());
        if (!"00".equals(resultCode) && !"1".equals(resultCode)) {
            throw new RuntimeException("공공데이터포털 API 오류: " + resultMsg + " (코드:" + resultCode + ")");
        }

        NodeList items = doc.getElementsByTagName("item");
        if (items.getLength() == 0) return null;

        Element e = (Element) items.item(0);

        return FlowerDto.builder()
                .dataNo(parseInt(getTagValue("dataNo", e)))
                .fMonth(parseInt(getTagValue("fMonth", e)))
                .fDay(parseInt(getTagValue("fDay", e)))
                .flowNm(getTagValue("flowNm", e))
                .fSctNm(getTagValue("fSctNm", e))
                .fEngNm(getTagValue("fEngNm", e))
                .flowLang(getTagValue("flowLang", e))
                .fContent(getTagValue("fContent", e))
                .fUse(getTagValue("fUse", e))
                .fGrow(getTagValue("fGrow", e))
                .fType(getTagValue("fType", e))
                .fileName1(getTagValue("fileName1", e))
                .imgUrl1(getTagValue("imgUrl1", e))
                .publishOrg(getTagValue("publishOrg", e))
                .build();
    }

    private String getTagValue(String tag, Element e) {
        NodeList nl = e.getElementsByTagName(tag);
        if (nl.getLength() == 0) return null;
        Node n = nl.item(0).getFirstChild();
        return n != null ? n.getNodeValue() : null;
    }
    private Integer parseInt(String s) {
        try { return s == null ? null : Integer.parseInt(s); }
        catch (Exception ex) { return null; }
    }
}
