/**
 * Created by yu on 2017/1/15.
 */
var socket = null;
var isAuth = false;
var userNick = null;
var userCount = 0;
$(function () {
    $("#menuModal").modal('show');
    var height = $(window).height();
    $('#content').css("height", height - $('#top').height() - $('#opt').height() - 40);

    $('#loginBtn').click(function () {
        userLogin();
    });

    $('#faceBtn').qqFace({
        id: 'facebox',
        assign: 'mess',
        path: 'arclist/'	//表情存放的路径
    });

    $('#sendBtn').click(function () {
        var mess = $("#mess").val().trim();
        if (mess) {
            sendMess(mess);
            $("#mess").val('');
        }
    }).keyup(function (e) {
        var keyCode = e.which || e.keyCode;
        if (keyCode == 13) {
            $("#sendBtn").click();
        }
    });
});

function sendMess(mess) {
    send(true, "{'code':10086,'message':'" + mess + "'}");
};

function heartbeat() {
    //发送pong消息响应
    send(isAuth, "{'code':10016}");
}

function userLogin() {
    if (!userNick) {
        userNick = $('#nick').val().trim();
    }
    if (userNick) {
        if (!window.WebSocket) {
            window.WebSocket = window.MozWebSocket;
        }
        if (window.WebSocket) {
            window.socket = new WebSocket("ws://chatroom.lixifan.cn/websocket");
            window.socket.onmessage = function (event) {
                var data = eval("(" + event.data + ")");
                console.log("onmessage data: " + JSON.stringify(data));
                switch (data.code) {
                    case 20001:
                        console.log("system message: " + JSON.stringify(data));
                        sysUserCount(data);
                        break;
                    case 20002:
                        console.log("system message: " + JSON.stringify(data));
                        doLoginSuccess(data);
                        break;
                    case 20003:
                        console.log("system message: " + JSON.stringify(data));
                        logSystem(data);
                        break;
                    case 20004:
                        console.log("broadcast message: " + JSON.stringify(data));
                        broadcastInvake(data);
                        break;
                }
            };
            window.socket.onclose = function (event) {
                console.log("connection close!!!");
                closeInvake(event);
            };
            window.socket.onopen = function (event) {
                console.log("connection success!!");
                setInterval("heartbeat()", 50 * 1000);
                openInvake(event);
            };
        } else {
            alert("您的浏览器不支持WebSocket！！！");
        }
    } else {
        1
        $('#tipMsg').text("请输入昵称");
        $('#tipModal').modal('show');
    }
}

function send(auth, mess) {
    if (!window.socket) {
        return;
    }
    if (socket.readyState == WebSocket.OPEN || auth) {
        console.log("send: " + mess);
        window.socket.send(mess);
    } else {
        $('#tipMsg').text("连接没有成功，请重新登录");
        $('#tipModal').modal('show');
    }
}
;

function openInvake(event) {
    var obj = {};
    obj.code = 10000;
    obj.nickName = $('#nick').val().trim();
    send(true, JSON.stringify(obj));
}
;


function closeInvake(event) {
    window.socket = null;
    window.isAuth = false;
    window.userCount = 0;
    $('#tipMsg').text("登录失败，网络连接异常");
    $('#tipModal').modal('show');
}
;

/**
 * 处理系统消息
 * @param data
 */
function doLoginSuccess(data) {
    console.log("auth result: " + data.auth);
    auth = data.auth;
    if (auth) {
        $("#menuModal").modal('hide');
        $('#chatWin').show();
        $('#content').scrollTop($('#content')[0].scrollHeight);
    }
};

/**
 * 处理系统消息
 * @param data
 */
function sysUserCount(data) {
    console.log("current user: " + data.count);
    userCount = data.count;
    $('#userCount').text(userCount);


    var content;
    if (data.action == 1) {
        content = "欢迎" + data.userNickName + "来到聊天室";
    } else {
        content = data.userNickName + "已退出聊天室";
    }
    var html = '</div><div class="item" style="text-align:center;color: gray;">' + content + '</div>';
    $("#content").append(html);
    $('#content').scrollTop($('#content')[0].scrollHeight);
};

/**
 * 处理系统消息
 * @param data
 */
function logSystem(data) {
    console.log("system message: " + data.extend.mess);
};

/**
 * 处理广播消息
 * @param data
 */
function broadcastInvake(data) {
    var mess = data.message;
    var nick = data.nickName;
    var uid = data.channelId;
    var time = data.time;
    mess = replace_em(mess);
    var html = '<div class="title">' + nick + '&nbsp;(' + uid + ') &nbsp;' + time + '</div><div class="item">' + mess + '</div>';
    $("#content").append(html);
    $('#content').scrollTop($('#content')[0].scrollHeight);

}
;

function erorInvake(data) {

}
;

//查看结果
function replace_em(str) {
    str = str.replace(/\</g, '&lt;');
    str = str.replace(/\>/g, '&gt;');
    str = str.replace(/\n/g, '<br/>');
    str = str.replace(/\[em_([0-9]*)\]/g, '<img src="arclist/$1.gif" border="0" />');
    return str;
};