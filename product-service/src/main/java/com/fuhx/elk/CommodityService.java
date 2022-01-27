package com.fuhx.elk;

import org.springframework.data.domain.Page;

import java.util.List;

public interface CommodityService {

    long count();

    CommodityDTO save(CommodityDTO commodity);

    void delete(CommodityDTO commodity);

    Iterable<CommodityDTO> getAll();

    List<CommodityDTO> getByName(String name);

    Page<CommodityDTO> pageQuery(Integer pageNo, Integer pageSize, String kw);

}