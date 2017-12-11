package com.hrm.mapper.provider;


import com.hrm.entity.Job;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

import static com.hrm.common.HrmConstants.JOB_TABLE;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;

public class JobDynaSqlProvider {

    public String selectWithParam(Map<String, Object> params) {
        String sql = new SQL() {
            {
                SELECT("*");
                FROM(JOB_TABLE);
                if (params.get("job") != null) {
                    Job job = (Job) params.get("job");
                    if (job.getName() != null && !job.getName().equals("")) {
                        WHERE(" name LIKE  CONCAT ('%',#{job.name},'%') ");
                    }
                }
            }
        }.toString();

        if (params.get("pageModel") != null ) {
            sql += " limit #{pageModel.firstLimitParam}, #{pageModel.pageSize} ";
        }

        return sql;
    }

    public String count(Map<String, Object> params) {
        return new SQL() {
            {
                SELECT("count(*)");
                FROM(JOB_TABLE);
                if (params.get("job") != null) {
                    Job job = (Job) params.get("job");
                    if (job.getName() != null && !job.getName().equals("")) {
                        WHERE(" name LIKE  CONCAT ('%',#{job.name},'%') ");
                    }
                }
            }
        }.toString();
    }

    public String addJob(Job job) {
        return new SQL() {
            {
                INSERT_INTO(JOB_TABLE);
                if (job.getRemark()!=null && !job.getRemark().equals("")) {
                    VALUES("remark", "#{remark}");
                }

                if (job.getName() != null && !job.getName().equals("")) {
                    VALUES("name", "#{name}");
                }

            }
        }.toString();
    }

    public String updateJob(Job job) {
        return new SQL(){
            {
                UPDATE(JOB_TABLE);
                if(job.getName() != null){
                    SET(" name = #{name} ");
                }

                if(job.getRemark() != null){
                    SET(" remark = #{remark} ");
                }
                WHERE(" id = #{id} ");
            }
        }.toString();
    }
}