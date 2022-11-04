package com.itheima;

import com.alibaba.fastjson2.JSON;
import com.itheima.pojo.AnimeInfo;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringcloudEs02ApplicationTests {

    @Autowired
    private RestHighLevelClient rhlc;

    @Test
    void contextLoads() throws IOException {

        IndexRequest indexRequest = new IndexRequest();
        AnimeInfo animeInfo = new AnimeInfo();
        animeInfo.setName("刀剑神域");
        animeInfo.setTime(2022);
        indexRequest.index("animes").id("1").source(JSON.toJSONString(animeInfo), XContentType.JSON);
        rhlc.index(indexRequest, RequestOptions.DEFAULT);


    }

}
