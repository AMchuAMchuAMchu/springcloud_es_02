package com.itheima;

import com.alibaba.fastjson2.JSON;
import com.itheima.pojo.AnimeInfo;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringcloudEs02ApplicationTests {

    @Autowired
    private RestHighLevelClient rhlc;

    @Test
    void testGetDoc() throws IOException {

        GetRequest getRequest = new GetRequest();
        getRequest.index("animes").id("1");
        GetResponse documentFields = rhlc.get(getRequest, RequestOptions.DEFAULT);
        System.out.println();
        System.out.println();
        System.out.println();
//        System.out.println(documentFields);
        System.out.println(documentFields.getSourceAsString());

    }

    @Test
    void testCreateIndexAndDoc() throws IOException {

        IndexRequest indexRequest = new IndexRequest();
        AnimeInfo animeInfo = new AnimeInfo();
        animeInfo.setName("刀剑神域");
        animeInfo.setTime(2022);
        indexRequest.index("animes").id("1").source(JSON.toJSONString(animeInfo), XContentType.JSON);
        rhlc.index(indexRequest, RequestOptions.DEFAULT);


    }


    @AfterEach
    void close() throws IOException {
        rhlc.close();
    }

}
