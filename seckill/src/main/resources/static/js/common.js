function countDown() {
    if($("#startRemains") == undefined) return;
    let startRemains = $("#startRemains").val();
    let startTimeOut;
    if (startRemains > 0) {
        // 还没开始
        $("#buyButtom").attr("disabled", true);
        startTimeOut = setTimeout(function () {
            $("#countDown").text(startRemains - 1);
            $("#startRemains").val(startRemains - 1);
            countDown();
        }, 1000);
    } else {
        // 正在进行中
        $("#buyButtom").attr("disabled", false);
        if (startTimeOut) clearTimeout(startTimeOut);
        $("#seckillTip").html("秒杀进行中");
    }
}

function endCountDown() {
    if($("#endRemains") == undefined) return;
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
    }
}

countDown();
endCountDown();
$("#login").click(function (e) {
    e.preventDefault();
    login();
});

function login() {
    let salt = "1a2b3c4d";
    let inputPass = $("#password").val();
    let mobile = $("#mobile").val();
    let s = "" + salt.charAt(0) + salt.charAt(2) + salt.charAt(7) + inputPass + salt.charAt(6) + salt.charAt(5) + salt.charAt(1);
    $.ajax({
        async: false,
        url: "/login/do_login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            mobile: mobile,
            password: md5(s)
        }),
        success: function (d) {
            console.log("code:" + d.code + " msg:" + d.msg);
            if (d.code == 0) {
                window.location.href = "/goods/to_list";
            }
        },
        error: function (e) {
            console.log(e);
        }
    })
}