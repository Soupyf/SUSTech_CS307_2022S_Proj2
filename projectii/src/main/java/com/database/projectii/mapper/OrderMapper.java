package com.database.projectii.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.database.projectii.model.Order;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {

    @Select("select * from (select product_model, sum(quantity) as sum from orders " +
        "group by product_model)sub where sub.sum = (select sum(quantity) as max_sum from orders " +
        "group by product_model order by max_sum desc limit 1)")
    List<Map<String, Object>> selectFavoriteProductModel();

    @Select("select supply_center, " +
        "       round((sum(surplus_quantity) * 1.0) / (count(distinct (supply_center, product_model)) * 1.0), " +
        "             1) as avg " +
        "from inventories " +
        "group by supply_center " +
        "order by supply_center")
    List<Map<String, Object>> selectAvgStockByCenter();

}
