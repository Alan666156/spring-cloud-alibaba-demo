package com.fuhx;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.fuhx.elk.CommodityDTO;
import com.fuhx.elk.CommodityService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.*;

@Slf4j
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ProductApplication.class)
public class ElasticsearchApplicationTests {

    @Autowired
    private CommodityService commodityService;
    @Autowired
    public ElasticsearchRestTemplate elasticsearchRestTemplate;
//    @Autowired
//    public ElasticsearchOperations elasticsearchOperations;
    @Test
    public void contextLoads() {
        System.out.println(commodityService.count());
    }

    @Test
    public void testInsert() {
        Random random = new Random();
        log.info("elasticsearch添加数据测试");
        CommodityDTO commodity = new CommodityDTO();
        for (int i = 0; i < 50; i++) {
            commodity.setSkuCode(String.valueOf(1501001 + random.nextInt(100)));
            commodity.setName("原味切片面包（"+ i +"1片装）");
            commodity.setCategory("101");
            commodity.setCategoryName("零食");
            commodity.setPrice(32);
            commodity.setBrand("良品铺子");
            commodity.setStock(60);
            commodity.setStatus("0");
//            log.info("elasticsearch添加数据测试：{}", JSON.toJSONString(commodity));
            commodityService.save(commodity);
        }
        for (int i = 1; i <= 20; i++) {
            commodity = new CommodityDTO();
            commodity.setSkuCode(String.valueOf(15010090 + random.nextInt(100)));
            commodity.setName("元气吐司850g（"+ i +"片装）");
            commodity.setCategoryName("零食");
            commodity.setCategory("101");
            commodity.setPrice(25);
            commodity.setBrand("百草味");
            commodity.setStock(50);
            commodity.setStatus("0");
            commodityService.save(commodity);
        }



    }

    @Test
    public void testDelete() {
        CommodityDTO commodity = new CommodityDTO();
        commodity.setSkuCode("15010151");
        commodityService.delete(commodity);
    }

    @Test
    public void testGetAll() {
        Iterable<CommodityDTO> iterable = commodityService.getAll();
        iterable.forEach(e->System.out.println(e.toString()));
    }

    @Test
    public void testGetByName() {
        List<CommodityDTO> list = commodityService.getByName("面包");
        System.out.println(list);
    }

    @Test
    public void testPage() {
        Page<CommodityDTO> page = commodityService.pageQuery(0, 10, "切片");
        System.out.println(page.getTotalPages());
        System.out.println(page.getNumber());
        System.out.println(page.getContent());
    }

    @Test
    public void testInsert2() {
        Random random = new Random();
        List<String> list = Arrays.asList("苹果","香蕉","殷桃","香梨","车厘子","橘子","芒果","苹果礼盒装","芒果10斤装","车厘子5KG礼盒","车厘子2KG特惠装");
        List<String> ccc = Arrays.asList("iphone12","小米1","华为","oppo","OPPO Reno","OPPO Find X","iphone11","iphone8 plus","Mate30","Mate40","小米remix");
        for (int i = 0; i < 10; i++) {
            int temp = random.nextInt(100);
            CommodityDTO commodity = new CommodityDTO();
            commodity.setSkuCode(String.valueOf(202104021 + temp));
            commodity.setName(list.get(i));
            commodity.setCategory("102");
            commodity.setCategoryName("水果");
            commodity.setPrice(11 + i);
            commodity.setBrand("京东自营");
            commodity.setStock(20);
            commodity.setStatus("0");
            commodity.setCreateTime(DateUtil.offsetDay(new Date(), i));
            IndexQuery indexQuery = new IndexQueryBuilder().withObject(commodity).build();
            String index = elasticsearchRestTemplate.index(indexQuery);
//            String index = elasticsearchRestTemplate.index(indexQuery, elasticsearchOperations.getIndexCoordinatesFor(commodity.getClass()));
        }
    }

    @Test
    public void testQuery() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "吐司"))
                .build();
        //6.x版本查询
        List<CommodityDTO> list = elasticsearchRestTemplate.queryForList(searchQuery, CommodityDTO.class);
        //7.x版本查询
//        SearchHits<CommodityDTO> list = elasticsearchRestTemplate.search(searchQuery, CommodityDTO.class);
        log.info("全文检索结果：{}", list);
    }

    /**
     * 数据分页查询
     */
    @Test
    public void queryMatchPage() {
        //查询字段
        String field = "name";
        //查询值
        String value = "面包";
        MatchQueryBuilder builder = QueryBuilders.matchQuery(field, value);
        NativeSearchQuery searchQuery = new NativeSearchQuery(builder).setPageable(PageRequest.of(0, 5));
        //6.x版本查询
        AggregatedPage<CommodityDTO> page = elasticsearchRestTemplate.queryForPage(searchQuery, CommodityDTO.class);
        //7.x版本查询
//        AggregatedPage<CommodityDTO> page = elasticsearchRestTemplate.queryForPage(searchQuery, CommodityDTO.class,elasticsearchOperations.getIndexCoordinatesFor(CommodityDTO.class));
//        elasticsearchRestTemplate.searchScrollStart(0L, searchQuery, CommodityDTO.class, elasticsearchOperations.getIndexCoordinatesFor(CommodityDTO.class));

        // 总记录数
        long totalElements = page.getTotalElements();
        // 总页数
        int totalPages = page.getTotalPages();
        // 当前页号
        int pageNumber = page.getPageable().getPageNumber();
        // 当前页数据集
        List<CommodityDTO> beanList = page.toList();
        // 当前页数据集
        Set<CommodityDTO> beanSet = page.toSet();
        log.info("分页查询结果：{}", JSON.toJSONString(page));
    }

    /**
     * 判断索引是否存在
     * @return boolean
     */
    public boolean indexExists() {
        return elasticsearchRestTemplate.indexExists(CommodityDTO.class);
    }

    /**
     * 判断索引是否存在
     * @return boolean
     */
    public boolean indexExists(String indexName) {
        return elasticsearchRestTemplate.indexExists(indexName);
    }
}