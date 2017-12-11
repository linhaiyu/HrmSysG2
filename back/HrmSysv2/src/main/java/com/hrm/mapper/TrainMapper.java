package com.hrm.mapper;


import com.hrm.entity.Train;
import com.hrm.mapper.provider.TrainDynaSqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

import static com.hrm.common.HrmConstants.TRAIN_TABLE;

public interface TrainMapper {

    @Select("select * from " + TRAIN_TABLE + " where id = #{id}")
    @Results({
            @Result(column = "createDate", property = "date", javaType = java.util.Date.class),
            @Result(column = "title", property = "name"),
            @Result(column = "employee_id", property = "teacher", one = @One(select = "com.hrm.mapper.EmployeeMapper.selectById", fetchType = FetchType.EAGER))
    })
    Train selectById(Integer id);

    @Delete(" delete from " + TRAIN_TABLE + " where id = #{id}")
    void deleteById(Integer id);

    @Select("select count(*) from " + TRAIN_TABLE + " ")
    int getTotalCount();

    /**
     * 动态SQL
     */
    @SelectProvider(type = TrainDynaSqlProvider.class, method = "selectWithParams")
    @Results({
            @Result(column = "createDate", property = "date", javaType = java.util.Date.class),
            @Result(column = "title", property = "name"),
            @Result(column = "employee_id", property = "teacher", one = @One(select = "com.hrm.mapper.EmployeeMapper.selectById", fetchType = FetchType.EAGER))
    })
    List<Train> selectByPage(Map<String, Object> params);

    @SelectProvider(type = TrainDynaSqlProvider.class, method = "count")
    int count(Map<String, Object> params);

    @SelectProvider(type = TrainDynaSqlProvider.class, method = "insertTrain")
    void save(Train train);

    @SelectProvider(type = TrainDynaSqlProvider.class, method = "updateTrain")
    void update(Train train);
}
