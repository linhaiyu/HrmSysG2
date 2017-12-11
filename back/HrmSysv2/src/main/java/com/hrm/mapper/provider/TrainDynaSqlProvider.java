package com.hrm.mapper.provider;


import com.hrm.entity.Train;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static com.hrm.common.HrmConstants.TRAIN_TABLE;

public class TrainDynaSqlProvider {

    public String selectWithParams(Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(TRAIN_TABLE);
                if (params.get("train") != null) {
                    Train train = (Train) params.get("train");
                    if (train.getName() != null && !train.getName().equals("")) {
                        WHERE(" title LIKE CONCAT ('%', #{train.name}, '%') ");
                    }

                    if (train.getRemark() != null && !train.getRemark().equals("")) {
                        WHERE(" remark LIKE CONCAT ('%', #{train.remark}, '%') ");
                    }

                    if(train.getTeacher() != null && train.getTeacher().getId() != null && train.getTeacher().getId() != 0){
                        WHERE(" employee_id = #{train.teacher.id} ");
                    }
                }
            }
        }.toString();

        if (params.get("pageModel") != null) {
            sql += " limit #{pageModel.firstLimitParam} , #{pageModel.pageSize}  ";
        }

        return sql;
    }

    public String count(Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM(TRAIN_TABLE);
                if (params.get("train") != null) {
                    Train train = (Train) params.get("train");

                    if (train.getRemark() != null && !train.getRemark().equals("")) {
                        WHERE(" remark LIKE CONCAT ('%', #{train.remark}, '%') ");
                    }

                    if (train.getName() != null && !train.getName().equals("")) {
                        WHERE(" title LIKE CONCAT ('%', #{train.name}, '%') ");
                    }
                }
            }
        }.toString();
    }

    public String insertTrain(Train train){
        return new SQL() {
            {
                INSERT_INTO(TRAIN_TABLE);

                if (train.getName() != null && !train.getName().equals("")) {
                    VALUES("title", "#{name}");
                }

                if (train.getRemark() != null && !train.getRemark().equals("")) {
                    VALUES("remark", "#{remark}");
                }

                if (train.getTeacher()!= null) {
                    VALUES("employee_id", "#{teacher.id}");
                }
            }
        }.toString();
    }

    public String updateTrain(Train train) {
        return new SQL() {
            {
                UPDATE(TRAIN_TABLE);

                if (train.getName() != null && !train.getName().equals("")) {
                    SET("title = #{name}");
                }

                if (train.getRemark() != null && !train.getRemark().equals("")) {
                    SET("remark = #{remark}");
                }

                if(train.getTeacher() != null && train.getTeacher().getId() != null && train.getTeacher().getId() != 0){
                    SET(" employee_id = #{teacher.id} ");
                }

                WHERE(" id = #{id} ");
            }
        }.toString();
    }
}
