package com.fuhx.elk;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Elasticsearch商品查询
 * @author fuhx
 */
@Repository
public interface CommodityRepository extends ElasticsearchRepository<CommodityDTO, String> {

}