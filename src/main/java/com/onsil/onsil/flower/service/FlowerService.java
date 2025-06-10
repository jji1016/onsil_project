package com.onsil.onsil.flower.service;

import com.onsil.onsil.flower.dto.FlowerDto;
import org.springframework.stereotype.Service;
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
import java.util.stream.Collectors;

@Service
public class FlowerService {
    private final String serviceKey = "HjWN57F2fgCJmRiid1d76b2X6HS6Jn9E0tTy0VXnq0R7t5u6TeGOR2aQKNsHuy4G4Jhwmbmds67XG1wY2KQHlg==";

    private final Map<Integer, FlowerDto> flowerCache = new HashMap<>();

    @PostConstruct
    public void initCache() {
        for (int dataNo = 1; dataNo <= 400; dataNo++) {
            try {
                FlowerDto dto = getFlowerDetailFromApi(dataNo, true);
                if (dto != null) {
                    flowerCache.put(dto.getDataNo(), dto);
                }
            } catch (Exception e) {
                // 필요 시 에러만 간단히 출력
                System.out.println("dataNo=" + dataNo + " 캐싱 실패: " + e.getMessage());
            }
        }
    }

    public List<FlowerDto> getFlowersByMonth(int month) {
        return flowerCache.values().stream()
                .filter(dto -> dto.getFMonth() != null && dto.getFMonth() == month)
                .collect(Collectors.toList());
    }

    public List<FlowerDto> getRecommendedFlowersByMonth(int month) {
        List<FlowerDto> all = getFlowersByMonth(month);
        int n = Math.min(5, all.size());
        Collections.shuffle(all);
        return all.subList(0, n);
    }

    public List<FlowerDto> getBirthFlowersByMonth(int month) {
        return getFlowersByMonth(month);
    }

    public FlowerDto getFlowerDetailFromApi(Integer dataNo) throws Exception {
        return getFlowerDetailFromApi(dataNo, false);
    }

    private FlowerDto getFlowerDetailFromApi(Integer dataNo, boolean isCache) throws Exception {
        if (!isCache && flowerCache.containsKey(dataNo)) {
            return flowerCache.get(dataNo);
        }

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

        FlowerDto dto = FlowerDto.builder()
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

        return dto;
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
