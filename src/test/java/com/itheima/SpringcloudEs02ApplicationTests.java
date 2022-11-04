package com.itheima;

import com.alibaba.fastjson2.JSON;
import com.itheima.dao.AnimeInfoDao;
import com.itheima.pojo.Anime;
import com.itheima.pojo.AnimeInfo;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class SpringcloudEs02ApplicationTests {

    @Autowired
    private RestHighLevelClient rhlc;

    @Autowired
    private AnimeInfoDao animeInfoDao;



    @Test
    void testDeleteIndex() throws IOException {

        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("animes");
        rhlc.delete(deleteRequest,RequestOptions.DEFAULT);

    }


    @Test
    void testBulkInsert() throws IOException {

        BulkRequest bulkRequest = new BulkRequest();

        List<AnimeInfo> animeInfos = animeInfoDao.selectList(null);

        animeInfos.forEach((item)->{
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index("animes").id(item.getId().toString()).source(JSON.toJSONString(item),XContentType.JSON);
            bulkRequest.add(indexRequest);
        });

        rhlc.bulk(bulkRequest,RequestOptions.DEFAULT);

    }

    @Test
    void testSearch() throws IOException {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("animes");
        SearchResponse search = rhlc.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println();
        System.out.println();
        System.out.println();
        search.getHits().forEach(System.out::println);


    }

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
        Anime animeInfo = new Anime();
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
