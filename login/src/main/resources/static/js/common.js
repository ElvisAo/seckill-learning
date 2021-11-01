function showLoading() {
    let index = layer.msg(
        "处理中...",
        {
            icon: 16,
            shade: [0.5, '#f5f5f5'],
            scrollbar: false,
            offset: '0px',
            time: 10000,
        }
    );
    return index;
}

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