package com.onsil.onsil.flower.init;

import com.onsil.onsil.entity.Product;
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
import java.util.*;

@Component
@RequiredArgsConstructor
public class FlowerApiToDbLoader {
    private final FlowerRepository flowerRepository;
    private final String serviceKey = "HjWN57F2fgCJmRiid1d76b2X6HS6Jn9E0tTy0VXnq0R7t5u6TeGOR2aQKNsHuy4G4Jhwmbmds67XG1wY2KQHlg==";

    @PostConstruct
    public void loadFromApi() {
        if (flowerRepository.count() > 0) return;

        Map<Integer, List<Product>> monthMap = new HashMap<>();
        for (int dataNo = 1; dataNo <= 366; dataNo++) {
            try {
                Product product = getFlowerFromApi(dataNo);
                if (isValidProduct(product)) {
                    addToMonthMap(monthMap, product);
                }
            } catch (Exception e) { /* 에러 로깅 */ }
            sleep(90000);
        }
        saveProducts(monthMap);
    }

    private Product getFlowerFromApi(int dataNo) throws Exception {
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
        String resultCode = (codeList.getLength() == 0) ? "없음" : codeList.item(0).getTextContent().trim();
        if (codeList.getLength() == 0 || !"1".equals(resultCode)) {
            return null;
        }
        NodeList items = doc.getElementsByTagName("result");
        if (items.getLength() == 0) return null;
        Element e = (Element) items.item(0);

        String flowerName = getTagValue("flowNm", e);
        // 이미지 경로를 dataNo와 꽃이름 조합으로 생성
        String imagePath = null;
        if (flowerName != null) {
            imagePath = String.format("%05d_%s.png", dataNo, flowerName);
        }

        return Product.builder()
                .dataNo(safeParseInt(getTagValue("dataNo", e))) // Integer 사용으로 변경
                .fMonth(safeParseInt(getTagValue("fMonth", e)))
                .flowerName(flowerName)
                .flowLang(getTagValue("flowLang", e))
                .flowerInfo(getTagValue("fContent", e))
                .fUse(getTagValue("fUse", e))
                .fGrow(getTagValue("fGrow", e))
                .fType(getTagValue("fType", e))
                .price(13500)
                .image(imagePath)
                .build();
    }

    // Null-safe 파싱 메서드(Integer) - safeParseInteger 제거됨
    private Integer safeParseInt(String value) {
        try {
            return value != null ? Integer.parseInt(value.trim()) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isValidProduct(Product product) {
        return product != null
                && product.getFMonth() != null
                && product.getFlowerName() != null;
    }

    private void addToMonthMap(Map<Integer, List<Product>> map, Product product) {
        int month = product.getFMonth();
        map.putIfAbsent(month, new ArrayList<>());
        if (map.get(month).size() < 6) {
            map.get(month).add(product);
        }
    }

    private void saveProducts(Map<Integer, List<Product>> monthMap) {
        monthMap.values().stream()
                .flatMap(List::stream)
                .forEach(flowerRepository::save);
    }

    private void sleep(Integer millis) {
        try { Thread.sleep(millis); }
        catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
    }

    // XML 태그 값 추출 메서드
    private String getTagValue(String tag, Element e) {
        NodeList nl = e.getElementsByTagName(tag);
        if (nl.getLength() == 0) return null;
        Node n = nl.item(0).getFirstChild();
        return n != null ? n.getNodeValue().trim() : null;
    }
}
