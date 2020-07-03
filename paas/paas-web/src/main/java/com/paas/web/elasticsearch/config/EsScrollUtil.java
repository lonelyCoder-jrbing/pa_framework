package com.paas.web.elasticsearch.config;

import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * create by sumerian on 2020/7/3
 * <p>
 * desc:es使用邮标进行查询
 **/
public class EsScrollUtil {
    public static List<SearchHit> scrollSearchAll(RestHighLevelClient client, Long scrollTimeOut, SearchRequest request) {
        Scroll scroll = new Scroll(TimeValue.timeValueMillis(scrollTimeOut));
        request.scroll(scroll);
        List<SearchHit> searchHits  = new ArrayList<>();
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            String scrollId = response.getScrollId();
            System.out.println("scrollId:" + scrollId);
            SearchHit[] hits = response.getHits().getHits();
            while (hits != null && hits.length != 0) {
                for (SearchHit hit : hits) {
                    searchHits.add(hit);
                }
                SearchScrollRequest request1 = new SearchScrollRequest(scrollId);
                request1.scroll(scroll);
                SearchResponse response1 = client.searchScroll(request1, RequestOptions.DEFAULT);
                String scrollId1 = response1.getScrollId();
                System.out.println("id" + scrollId1);
                hits = response1.getHits().getHits();

            }
            ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
            clearScrollRequest.addScrollId(scrollId);
            client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchHits;
    }


}
