$(function () {
    getOrderDetail();
})

/**
 * 获取请求参数
 * @param name
 * @returns {string|null}
 */
function getQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

function getOrderDetail() {
    let orderId = getQueryString("orderId");
    console.log(orderId);
    $.ajax({
        url: "/order/order_detail?orderId=" + orderId,
        type: "GET",
        success: function (data) {
            if (data.code == 0) {
                render(data.data);
            } else {
                console.log(data.msg);
            }
        },
        error: function () {
            console.log("请求错误")
        }
    })
}

function render(data) {
    let orderId = data.id;
    let userId = data.userId;
    let goodsId = data.goodsId;

    $("#goodsName").text(goodsId);
    $("#orderId").text(orderId);
    $("#userId").text(userId);
}