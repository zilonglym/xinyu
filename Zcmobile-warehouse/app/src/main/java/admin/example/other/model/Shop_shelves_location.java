package admin.example.other.model;

/**
 * Created by admin on 2017/7/24.
 */

public class Shop_shelves_location {

    public int shop_id; //商品id
    public String hj; //货架
    public String wz;  //位置
    public String bw; //板位
    public String cs; //层数
    public String name; //商品名称
    public String shop;//商家名
    public String type; //商品型号
    public String num; //数量
    public String date_cr; //最后更新时间

    @Override
    public String toString() {
        return "Shop_shelves_location{" +
                "shop_id=" + shop_id +
                ", hj='" + hj + '\'' +
                ", wz='" + wz + '\'' +
                ", bw='" + bw + '\'' +
                ", cs='" + cs + '\'' +
                ", name='" + name + '\'' +
                ", shop='" + shop + '\'' +
                ", type='" + type + '\'' +
                ", num='" + num + '\'' +
                ", date_cr='" + date_cr + '\'' +
                '}';
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getHj() {
        return hj;
    }

    public void setHj(String hj) {
        this.hj = hj;
    }

    public String getWz() {
        return wz;
    }

    public void setWz(String wz) {
        this.wz = wz;
    }

    public String getBw() {
        return bw;
    }

    public void setBw(String bw) {
        this.bw = bw;
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate_cr() {
        return date_cr;
    }

    public void setDate_cr(String date_cr) {
        date_cr = date_cr;
    }
}
