package org.wjq.unit.bo;

import java.time.LocalDateTime;

/**
 * @Program: unit-test-project
 * @Description: 城市
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
public class City {

    public City(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public City() {
    }

    /**
     * 城市id
     */
    private Long id;

    /**
     * 城市名
     */
    private String name;

    private LocalDateTime ctime;

    private LocalDateTime utime;

    private String operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCtime() {
        return ctime;
    }

    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    public LocalDateTime getUtime() {
        return utime;
    }

    public void setUtime(LocalDateTime utime) {
        this.utime = utime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"cityId\":")
                .append(id);
        sb.append(",\"cityName\":\"")
                .append(name).append('\"');
        sb.append(",\"ctime\":")
                .append(ctime);
        sb.append(",\"utime\":")
                .append(utime);
        sb.append(",\"operator\":\"")
                .append(operator).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
