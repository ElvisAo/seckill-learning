package everett.ao.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import everett.ao.seckill.utils.JsonLongSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("order_info")
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoEntity {
    @TableId
    @JsonSerialize(using = JsonLongSerializer.class )
    private Long id;
    private Integer userId;
    private Integer goodsId;
    private Integer deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Float goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;

    @Getter
    public static class Builder {
        private Long id;
        private Integer userId;
        private Integer goodsId;
        private Integer deliveryAddrId;
        private String goodsName;
        private Integer goodsCount;
        private Float goodsPrice;
        private Integer orderChannel;
        private Integer status;
        private Date createDate;
        private Date payDate;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUserId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder setGoodsId(Integer goodsId) {
            this.goodsId = goodsId;
            return this;
        }

        public Builder setDeliveryAddrId(Integer deliveryAddrId) {
            this.deliveryAddrId = deliveryAddrId;
            return this;
        }

        public Builder setGoodsName(String goodsName) {
            this.goodsName = goodsName;
            return this;
        }

        public Builder setGoodsCount(Integer goodsCount) {
            this.goodsCount = goodsCount;
            return this;
        }

        public Builder setGoodsPrice(float goodsPrice) {
            this.goodsPrice = goodsPrice;
            return this;
        }

        public Builder setOrderChannel(Integer orderChannel) {
            this.orderChannel = orderChannel;
            return this;
        }

        public Builder setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public Builder setCreateDate(Date createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder setPayDate(Date payDate) {
            this.payDate = payDate;
            return this;
        }

        public OrderInfoEntity build() {
            return new OrderInfoEntity(this);
        }
    }

    private OrderInfoEntity(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.goodsId = builder.goodsId;
        this.deliveryAddrId = builder.deliveryAddrId;
        this.goodsName = builder.goodsName;
        this.goodsCount = builder.goodsCount;
        this.goodsPrice = builder.goodsPrice;
        this.orderChannel = builder.orderChannel;
        this.status = builder.status;
        this.createDate = builder.createDate;
        this.payDate = builder.payDate;
    }
}
