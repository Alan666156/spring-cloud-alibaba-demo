package com.fuhx.elk;

import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityRepository commodityRepository;


    @Override
    public long count() {
        return commodityRepository.count();
    }

    @Override
    public CommodityDTO save(CommodityDTO commodity) {
        return commodityRepository.save(commodity);
    }

    @Override
    public void delete(CommodityDTO commodity) {
        commodityRepository.delete(commodity);
//        commodityRepository.deleteById(commodity.getSkuId());
    }

    @Override
    public Iterable<CommodityDTO> getAll() {
        return commodityRepository.findAll();
    }

    @Override
    public List<CommodityDTO> getByName(String name) {
        List<CommodityDTO> list = new ArrayList<>();
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", name);
        Iterable<CommodityDTO> iterable = commodityRepository.search(matchQueryBuilder);
        iterable.forEach(e->list.add(e));
        return list;
    }

    @Override
    public Page<CommodityDTO> pageQuery(Integer pageNo, Integer pageSize, String kw) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("name", kw))
                .withPageable(PageRequest.of(pageNo, pageSize))
                .build();
        return commodityRepository.search(searchQuery);
    }


}