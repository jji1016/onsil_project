package com.onsil.onsil.flower.service;

import com.onsil.onsil.flower.dto.FlowerDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
public class FlowerService {
    @Value("${api.flower.service-key}")
    private String serviceKey;

    public FlowerDto getFlowerDetailFromApi(Integer dataNo) throws Exception {
        String urlStr = "https://apis.data.go.kr/1390804/NihhsTodayFlowerInfo01/selectTodayFlowerView01"
                + "?ServiceKey=" + URLEncoder.encode(serviceKey, "UTF-8")
                + "&dataNo=" + dataNo;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        Scanner sc = new Scanner(conn.getInputStream(), StandardCharsets.UTF_8);
        StringBuilder xml = new StringBuilder();
        while (sc.hasNext()) xml.append(sc.nextLine());
        sc.close();

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml.toString())));
        doc.getDocumentElement().normalize();

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
                .fileName2(getTagValue("fileName2", e))
                .fileName3(getTagValue("fileName3", e))
                .imgUrl1(getTagValue("imgUrl1", e))
                .imgUrl2(getTagValue("imgUrl2", e))
                .imgUrl3(getTagValue("imgUrl3", e))
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
