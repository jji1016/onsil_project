package com.onsil.onsil.flower.init;

import com.onsil.onsil.entity.Flower;
import com.onsil.onsil.flower.repository.FlowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class FlowerApiToDbLoader {
    private final FlowerRepository flowerRepository;

    // 본인 서비스키로 교체!
    private final String serviceKey = "8N3SDxEYbYGIkUP0giZCVk4OubROuxfvWO0ryBU9kWnF/pakQ1rkEUZ5+ZAGM0ui56IxZKiu9pmdg1KRowKxkg==";

    @PostConstruct
    public void loadFromApi() {
        if (flowerRepository.count() > 0) {
            System.out.println("FLOWER 테이블에 이미 데이터가 있습니다. 적재 생략.");
            return;
        }
        for (int dataNo = 1; dataNo <= 366; dataNo++) {
            try {
                Flower flower = getFlowerFromApi(dataNo);
                if (flower != null) {
                    flowerRepository.save(flower);
                    System.out.println("dataNo=" + dataNo + " 저장 완료");
                } else {
                    System.out.println("dataNo=" + dataNo + " 데이터 없음/파싱 실패");
                }
            } catch (Exception e) {
                System.out.println("dataNo=" + dataNo + " 저장 실패: " + e.getMessage());
            }
            try {
                Thread.sleep(3000); // 3초 대기 (분당 20건 이하)
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("==== 꽃 데이터 적재 완료 ====");
    }

    private Flower getFlowerFromApi(int dataNo) throws Exception {
        String urlStr = "https://apis.data.go.kr/1390804/NihhsTodayFlowerInfo01/selectTodayFlowerView01"
                + "?serviceKey=" + URLEncoder.encode(serviceKey, "UTF-8")
                + "&dataNo=" + dataNo;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int status = conn.getResponseCode();
        InputStream is = (status == 200) ? conn.getInputStream() : conn.getErrorStream();
        String xml = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        Document doc = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
        doc.getDocumentElement().normalize();

        NodeList codeList = doc.getElementsByTagName("resultCode");
        if (codeList.getLength() == 0 || !"1".equals(codeList.item(0).getTextContent().trim())) return null;

        NodeList items = doc.getElementsByTagName("result");
        if (items.getLength() == 0) return null;

        Element e = (Element) items.item(0);

        return Flower.builder()
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
        return n != null ? n.getNodeValue().trim() : null;
    }

    private Integer parseInt(String s) {
        try { return s == null ? null : Integer.parseInt(s.trim()); }
        catch (Exception ex) { return null; }
    }
}
