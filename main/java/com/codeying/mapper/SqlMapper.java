package com.codeying.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface SqlMapper extends BaseMapper {

    @Select("select id,${field} from tb_${tbname} order by id desc")
    List<LinkedHashMap<String, String>> getDict(@Param("tbname") String tbname,@Param("field") String field);

    //sql语句执行查询图表
    @Select("${sql}")
    List<Dto> exec(String sql);

    //自定义查询
    @Select("${sql}")
    List<LinkedHashMap<String, String>> getQueryMap(String sql);

    public static class Echart {
        String name;
        String value;
        List<Dto> dtos = new ArrayList<>();
        String type;

        List<String> names = new ArrayList<>();
        List<Double> values = new ArrayList<>();

        public Echart init() {
            for (Dto dto : dtos) {
                names.add(dto.getName());
                values.add(dto.getValue());
            }
            return this;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<String> getNames() {
            return names;
        }

        public List<Double> getValues() {
            return values;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Dto> getDtos() {
            return dtos;
        }

        public void setDtos(List<Dto> dtos) {
            this.dtos = dtos;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class Dto {
        String name;
        Double value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getValue() {
            if(value == null){
                return 0.0;
            }
            return ((int)(value*100) / 100.0);
        }

        public void setValue(Double value) {
            this.value = value;
        }
    }

}
