package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int findTotalCount(int cid, String rname);

    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    Route findOne(int rid);


}
