package org.wjq.unit.bo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @Program: unit-test-project
 * @Description: 商品信息
 * @Author: wangjianqiang07
 * @Date: 2022/4/5
 **/
public class Sku {

    public Sku(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Sku() {
    }

    private Long id;

    private String name;

    /**
     * 图片
     */
    private String pic;

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

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
        sb.append("\"skuId\":")
                .append(id);
        sb.append(",\"skuName\":\"")
                .append(name).append('\"');
        sb.append(",\"pic\":\"")
                .append(pic).append('\"');
        sb.append(",\"ctime\":")
                .append(ctime);
        sb.append(",\"utime\":")
                .append(utime);
        sb.append(",\"operator\":\"")
                .append(operator).append('\"');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sku sku = (Sku) o;
        return id.equals(sku.id) && name.equals(sku.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
