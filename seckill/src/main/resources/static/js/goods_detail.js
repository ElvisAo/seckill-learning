$(function () {
    getDetail();
    $("#buyButtom").click(getSeckillPath);
})

function generateVerifyCode() {

}

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

function getSeckillResult(goodsId) {
    let index;
    $.ajax({
        async: false,
        url: "/seckill/result/" + goodsId,
        type: "GET",
        success: function (data) {
            if (data.code == 0) {
                let result = data.data;
                if (result == 0) {
                    setTimeout(getSeckillResult(goodsId), 50)
                } else if (result == -1) {
                    console.log("秒杀失败");
                    clearTimeout(index);
                } else {
                    clearTimeout(index);
                    console.log("秒杀成功：" + goodsId);
                }
            } else {
                console.log(data.msg);
            }
        },
    })
}


function doSeckill(goodsId, path) {
    $.ajax({
        async: false,
        url: "/seckill/" + path + "/do_seckill",
        type: "POST",
        data: {
            goodsId: goodsId
        },
        success: function (data) {
            if (data.code == 0) {
                getSeckillResult(goodsId);
            } else {
                console.log(data.msg);
            }
        },
    })
}

function getSeckillPath() {
    let goodsId = getQueryString("goodsId");
    let verifyCode = $("#verifyCode").val();
    $("#goodsId").val(goodsId);
    $.ajax({
        async: false,
        url: "/seckill/get_seckillpath",
        type: "GET",
        data: {
            goodsId: goodsId,
            verifyCode: verifyCode
        },
        success: function (data) {
            if (data.code == 0) {
                let path = data.data;
                doSeckill(goodsId, path);
            } else {
                console.log(data.msg);
            }
        },
    })
}

function getDetail() {
    let goodsId = getQueryString("goodsId");
    $("#goodsId").val(goodsId);
    console.log(goodsId);
    $.ajax({
        async: false,
        url: "/goods/to_detail/" + goodsId,
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

function countDown() {
    if ($("#startRemains") == undefined) return;
    let startRemains = $("#startRemains").val();
    let startTimeOut;
    if (startRemains > 0) {
        // 还没开始
        $("#buyButtom").attr("disabled", true);
        startTimeOut = setTimeout(function () {
            $("#seckillTip").text("秒杀倒计时 " + (startRemains - 1));
            $("#startRemains").val(startRemains - 1);
            countDown();
        }, 1000);
    } else {
        // 正在进行中
        $("#buyButtom").attr("disabled", false);
        if (startTimeOut) clearTimeout(startTimeOut);
        $("#seckillTip").html("秒杀进行中");
        $("#verifyCodeImg").show();
        $("#verifyCodeImg").attr("src", "seckill/verifyCode?goodsId=" + $("#goodsId"));
        $("#verifyCode").show();
    }
}

function endCountDown() {
    if ($("#endRemains") == undefined) return;
    let remains = $("#endRemains").val();
    let timeout;
    if (remains > 0) {
        timeout = setTimeout(function () {
            $("#endRemains").val(remains - 1);
            endCountDown();
        }, 1000);
    } else {
        if (timeout) clearTimeout(timeout);
        $("#seckillTip").html("秒杀已结束");
        $("#buyButtom").attr("disabled", true);
        $("#verifyCodeImg").hide();
        $("#verifyCode").hide();
    }
}

function render(data) {
    let goodsVo = data.goodsVo;
    let startRemains = data.startRemains - (8 * 60 * 60);
    let endRemains = data.endRemains - (8 * 60 * 60);
    let seckillStatus = data.seckillStatus;
    let user = data.user;
    console.log("goodsVo=" + goodsVo);
    console.log("startRemains=" + startRemains);
    console.log("endRemains=" + endRemains);
    console.log("gouserodsVo=" + user);
    if (user) {
        $("#userTip").hide();
    }
    $("#goodsName").text(goodsVo.goodsName);
    $("#goodsImg").attr("src", goodsVo.goodsImg);
    $("#startRemains").val(startRemains);
    $("#endRemains").val(endRemains);
    $("#goodsPrice").text(goodsVo.goodsPrice);
    $("#seckillPrice").text(goodsVo.seckillPrice);
    $("#stockCount").text(goodsVo.stockCount);
    $("#goodsId").text(goodsVo.id);
    countDown();
    endCountDown();
}