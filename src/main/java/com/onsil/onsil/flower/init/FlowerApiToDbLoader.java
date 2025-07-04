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

    // 꽃 번호에 대응하는 영어 파일명 Map (예시)
    // 실제로는 외부 파일, DB, 또는 코드 상에 전체 목록을 관리해야 함
    private static final Map<Integer, String> DATA_NO_TO_ENG_FILE = new HashMap<>();
    static {
        // 예시: 1번 꽃은 "00101_hyangnamu.jpg", 2번 꽃은 "00102_rose.jpg" 등
        DATA_NO_TO_ENG_FILE.put(153, "00153_gladiolus.jpg");
        DATA_NO_TO_ENG_FILE.put(154, "00154_rose.jpg");
        // ... 전체 꽃 번호와 영어 파일명을 모두 추가해야 함
    }

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
            } catch (Exception e) {
                // 에러 로깅
            }
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
        String flowerNameKor = getTagValue("flowNm", e); // 한글 이름

        // 이미 영어 파일명을 관리하고 있다면, 그 값을 가져와서 사용
        String imagePath = DATA_NO_TO_ENG_FILE.get(dataNo); // 예: "00153_gladiolus.jpg"
        // 만약 Map을 쓰지 않고, 외부에서 영어 파일명을 받아오는 구조라면 그 값을 imagePath에 할당

        return Product.builder()
                .dataNo(safeParseInt(getTagValue("dataNo", e)))
                .fMonth(safeParseInt(getTagValue("fMonth", e)))
                .flowerName(flowerNameKor)
                .flowLang(getTagValue("flowLang", e))
                .flowerInfo(getTagValue("fContent", e))
                .fUse(getTagValue("fUse", e))
                .fGrow(getTagValue("fGrow", e))
                .fType(getTagValue("fType", e))
                .price(13500)
                .image(imagePath)
                .build();
    }

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

    private String getTagValue(String tag, Element e) {
        NodeList nl = e.getElementsByTagName(tag);
        if (nl.getLength() == 0) return null;
        Node n = nl.item(0).getFirstChild();
        return n != null ? n.getNodeValue().trim() : null;
    }
}
