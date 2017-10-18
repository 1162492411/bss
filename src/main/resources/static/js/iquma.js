//------------------------- 公共函数 ---------------------------//

var rootPath = "http://localhost:8080";

//公共函数--返回用户主页的list的路径--用于用户主页的tutorials/questions/articles/codes/ntfs
function getUserSecPath(uid,sec) {
    return rootPath + "/user/" + uid + "/" + sec + "s";
}

//公共函数--返回拼接后的带版块的路径--用于相关标签中标签等超链接的地址
function getSecsPath() {
    return rootPath + "/" + $("#sec").val() + "s";
}

//公共函数--返回拼接后的带版块的路径--用于主贴列表的每条主贴的超链接的地址
function getSecPath() {
    return rootPath + "/" + $("#sec").val();
}

//公共函数--返回拼接后的带标签的路径--用于相关标签中标签等超链接的地址
function getTagPath() {
    var path = getSecsPath();
    var tag = $("#tag").val();
    if (!checkData(tag)) path = path + "/" + tag;
    return path;
}

//公共函数--返回拼接后的带类别的路径--用于主贴列表中分页
function getTypesPath() {
    var path = getTagPath(),typePath = $("#type").val();
    if(!checkData(typePath)) return path + "/" + typePath;
    else return path;
}

//公共函数--返回拼接后的收藏路径--用于用户主页的收藏页面
function getCollectionsPath(uid) {
    return rootPath + '/user/' + uid + '/collections';
}

//公共函数--格式化时间
function formateTime(time) {
    if (time != null && time != "null" && time != " ") {
        var d1 = new Date(time);
        var now = new Date().getTime();
        var seconds = (now - d1 + 14 * 60 * 60 * 1000) / 1000 / 60; //取得两次相差的分钟数
        if (seconds < 60) return parseInt(seconds) + "分钟前";
        else var hours = seconds / 60;
        if (hours < 24) return parseInt(hours) + "小时前";
        else var days = hours / 24;
        if (days < 30) return parseInt(days) + "天前";
        else var months = days / 30;
        if (months < 12) return parseInt(months) + "个月前";
        else return parseInt(months / 12) + "年前";
    } else return " ";
}

//公共函数--检测数据是否为空
//TODO:此处待完善
function checkData(data) {
     return data == '' || data == '[]' || !data || data == 0 || data == "{}";
}

//公共函数--检测指定id的元素是否存在
function checkElement(id) {
    return $("#" + id).length > 0;
}

//公共函数--设置数据为空提示
function setEmptyDataTip(id) {
    $("#" + id).append("<div style='text-align:center'>查找的内容不存在</div>");
}

//生成对象，返回字符串形式
//fields ：属性数组; values : 值数组; check : 是否检查值为空
function constructor(fields,values,check){
    var obj = new Object();
    for(var i=0; i < fields.length; i++){
        if(!check || (check && !checkData(values[i])))
            obj[fields[i]] = values[i];
    }
    return JSON.stringify(obj);
}


/**
 * 初始化分页
 * @param currentPage 当前页码
 * @param totalPage 总页码
 * @param id 分页的ID
 * @param path 每个按钮的路径中的公共部分
 */
function initPag(currentPage, totalPage, id, path) {
    var c = parseInt(currentPage);
    var t = parseInt(totalPage);
    var prevString = "";
    var suffString = "";
    if (t <= 1) { //总页数过少时移除分页
        $("#" + id).remove();
        return;
    }
    //处理"上一页"
    if (c > 1) $("#" + id).append("<li id='pagLast' ><a href='" + path + "/" + (c - 1) + "'> << </a></li>");
    //添加当前页码前的页数
    if (c > 3) {
        prevString += "<li><a>...</a></li>";
        for (var i = c - 3; i < c; i++) prevString += "<li><a href='" + path + "/" + i + "'>" + i + "</a></li>";
        $("#" + id).append(prevString);
    } else {
        for (var i = 1; i < c; i++) prevString += "<li><a href='" + path + "/" + i + "'>" + i + "</a></li>";
        $("#" + id).append(prevString);
    }
    //添加当前页码
    $("#" + id).append("<li id='pagCurrent' class='active'><span>" + c + "</span></li>");
    //添加当前页码后的页数
    if (c < t - 3) {
        for (var j = c + 1; j <= c + 3; j++) suffString += "<li><a href='" + path + "/" + j + "'>" + j + "</a></li>";
        suffString += "<li><a>...</a></li>";
    } else
        for (var j = c + 1; j <= t; j++) suffString += "<li><a href='" + path + "/" + j + "'>" + j + "</a></li>";
    $("#" + id).append(suffString);
    //处理"下一页"
    if (c < t) $("#" + id).append("<li id='pagNext' ><a href='" + path + "/" + (c + 1) + "'> >> </a></li>");
}

//------------------- 编辑器页面 ------------------------//

//初始化标签下拉框
function initTagSelection() {
    var levels = ["firstLevel","secondLevel","thirdLevel","fourthLevel","fifthLevel","sixthLevel","seventhLevel","eighthLevel","ninthLevel","tenthLevel"];
    $.ajax({
        type: 'GET',
        url: '/api/getFirstTags',
        async : false,
        success: function(data) {
            initSelections(levels[0],JSON.parse(data));
        }
    });
    initChildSelections(levels,0);
}

//为选择框添加数据
function initSelections(id,data) {
    if(!checkData(data)){
        if(!checkElement(id))
            $("#tagPanel").append("<select id='" + id + "'></select>");
        var $selection = $("#" + id);
        var content = "<option value='0'>请选择类别</option>";
        for(var i = 0; i < data.length; i++)
            content += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
        $selection.append(content);
    }
}

//为选择框添加子框数据
function initChildSelections(levels,current) {
     $("#" + levels[current]).change(function () {
         $("#tid").attr("value",$("#" + levels[current] + " option:selected").val());
         $.ajax({
             type: 'GET',
             url: '/api/getChildrenTags/' + $("#" + levels[current] + " option:selected").val(),
             async : false,
             success: function(data) {
                 resetChildSelections(levels,current + 1);
                 initSelections(levels[current + 1],JSON.parse(data));
                 initChildSelections(levels,current + 1);
             }
         });
     });
}

//删除选择框的所有子框
function resetChildSelections(levels,start){
    for(var i = start; i < levels.length; i++){
        if(checkElement(levels[i]))
            $("#" + levels[i]).remove();
    }
}

//编辑器提交
function editorSubmit(type) {
    var editor = new wangEditor('contentDiv');
    editor.create();
    $("#subBtn").click(function() {
        var d = {
            "title": $("#title").val(),
            "tid": parseInt($("#tid").val()),
            "aid": $("#aid").val(),
            "addTime": new Date(),
            "reTime": new Date(),
            "content": filterEnter(filterXSS(editor.$txt.html()))
        };
        if (type == 'upload' && $("#attid").val() != " ")
            d.attid = parseInt($("#attid").val());
        console.log("此时attid的长度为" + $("#attid").length + "，值是" + $("#attid").val());
        if (d.title == "" || d.tid == 0 || d.aid == "" || d.content == "<p><br></p>" || ($("#attid").length > 0 && $("#attid").val() == "")) {
            alert("存在未填写字段，请填写后再提交！");
            return;
        }
        $.ajax({
            type: 'PUT',
            url: '/user/' + type,
            data: JSON.stringify(d),
            contentType: 'application/json',
            success: function(data) {
                if (data == true) {
                    alert("成功发表！");
                }
            },
            error: function() {
                alert("发布失败，请稍后重试！");
            }
        });
    });
}

//过滤回车符
function filterEnter(content) {
    var string = content;
    try {
        string = string.replace(/\n/g, "<br />");
    } catch (e) {
        alert(e.message);
    }
    return string;
}

//--------------------------主贴列表页面---------------------------//
//初始化标签导航栏
function initTagNav() {
        var navHeader = "<div class='sub-head hidden-xs'>";
        var navAllTag = "<span class='tag-nav__item btn-all-tag'><a href='" + rootPath + "/tags' class='text-secondNav'>热门标签</a></span>";
        var navOuter = "<div class='tag-container-outer'>";
        var navAll = "<span class='tag-nav__item mr20 '><a class='text-secondNav' href='" + rootPath + "/tags'>全部</a></span>";
        var navContainer = "<div class='tag-container'>";
        var tags = ["Java","Android","Html","SQL"];
        for(var i=0; i< tags.length;i++){
            navContainer += "<span class='tag-nav__item mr20'><a class='text-secondNav' href='" + getSecsPath() + "/" + tags[i] + "'>" + tags[i] + "</a></span>";
        }
        var navSuffix = "</div></div></div>";
        $("#main").before(navHeader + navAllTag + navOuter + navAll + navContainer + navSuffix);
}

function getTopicPath() {
    var path = rootPath;
    var sec = $("#condition_sec").val();
    if (sec != " ") path += "/" + sec;
    var sort = $("#condition_sort").val();
    path += "/" + $("#condition_id").val();
    if (sort != " ") path += "/" + sort;
    else path += "/default";
    return path;
}

//初始化列表页面相关状态
function initType() {
    //处理主贴分类方式
    var path = getTagPath();
    $("#nav_new_a").attr("href", path + "/new/1");
    $("#nav_hottest_a").attr("href", path + "/hottest/1");
    $("#nav_unanswered_a").attr("href", path + "/unanswered/1");
    //处理页面类型
    if ($("#type").val() != "") { //如果是按未回答和最热筛选
        $("#nav_new").removeAttr("class");
        $("#nav_" + $("#type").val()).attr("class", "active");
    }
}

//加载主题信息--批量处理信息
function initTopics(topics) {
    if (checkData(topics)) setEmptyDataTip("topic-list");
    else
        for (var i = 0; i < topics.length; i++) initSimpleTopic(topics[i]);
}

//加载主题信息--批量调用
function initSimpleTopic(topic) {
    var temp = "<section class='stream-list__item'>";
    temp += "<div class='qa-rank'><div class='votes hidden-xs'>" + topic.rateCount + "<small>评分</small></div>";

    if (topic.replyCount > 0) temp += "<div class='answers answered'>" + topic.replyCount + "<small>回复</small></div>";
    else temp += "<div class='answers'>" + topic.replyCount + "<small>回复</small></div>";

    var viewCount = topic.viewCount;
    if (viewCount >= 0 && viewCount <= 99) temp += "<div class='views hidden-xs viewsword0to99'>" + topic.viewCount + "<small>浏览</small></div></div><div class='summary'><ul class='author list-inline'><li>";
    else if (viewCount > 99 && viewCount < 999) temp += "<div class='views hidden-xs viewsword100to999'>" + topic.viewCount + "<small>浏览</small></div></div><div class='summary'><ul class='author list-inline'><li>";
    else temp += "<div class='views hidden-xs viewswordgreater999'>" + topic.viewCount + "<small>浏览</small></div></div><div class='summary'><ul class='author list-inline'><li>";

    temp += "<a href='" + rootPath + "/user/" + topic.aid + "/home'>" + topic.user.name + "</a>";

    temp += "<span class='split'></span> <span>" + formateTime(topic.reTime) + "被回复</span></li></ul>";

    if (topic.isBlock == 1)
        temp += "<img src='" + rootPath + "/static/image/blocked.png' />";
    temp += "<h2 class='title'><a href='" + getSecPath() + "/" +  topic.id + "'>" + topic.title + "</a></h2>";

    temp += "<ul class='taglist--inline ib'><li class='tagPopup'><a class='tag tag-sm' href='" + getSecsPath() + "/" + topic.tag.name + "'>" + topic.tag.name + "</a></li></ul></div></section>";

    $("#topic-list").append(temp);

}

//批量显示服务器端获取的标签
function initTags(tags) {
    var path = getSecsPath();
    var str = "";
    for (var i = 0; i < tags.length; i++)
       str += "<li class='tagPopup'><a class='tag' href='" + path + "/" + tags[i] + "'>" + tags[i] + "</a></li>";
    $("#tag-list").append(str);
}

//------------------------- 主贴页面 ---------------------------//

//初始化主贴相关信息
function initTopicInfo() {
    handleTopicSec(); //处理顶部主贴类型文字
    handleTopicStatus(); //处理被关闭的主贴
    handleTopicButtons();//初始化主贴操作按钮
    handleFavoriteStatus(); //用户登录时查询收藏状态
    initReplySort();//初始化回复排序类型
    initTopicEditor();//初始化主贴回复框
    initRateInfo();//用户登录时初始化投票信息
}

//检测主贴相关状态之处理主贴类型
function handleTopicSec() {
    var topicType = $('#condition_sec').val();
    if ('tutorial' == topicType) $("#topicText").text('教');
    else if ('question' == topicType) $("#topicText").text('问');
    else if ('article' == topicType) $("#topicText").text('文');
    else $("#topicText").text('码');
}

//检测主贴相关状态之处理被关闭的主贴
function handleTopicStatus() {
    if ($('#condition_isBlock').val() == "false" && $('#blockButton').length > 0)
        $('#blockButton').removeAttr('disabled');
    else if ($('#condition_isBlock').val() == "true")
        $('#blockTip').attr('type', 'text');
}

//初始化主贴相关状态之初始化主贴操作按钮
function handleTopicButtons() {
    var $div = $("#topicUl"),id=$('#condition_id').val(),sec=$('#condition_sec').val();
    if(checkElement('hasTopicUpdatePermission'))
        $div.append("</li><a href='" + rootPath + "/" + sec + "/" + id + "/update'><Button class='btn btn-primary'>编辑</Button></a></li>");
    if(checkElement("hasTopicBlockPermission"))
        $div.append("<li><Button class='btn btn-primary' id='blockButton' onclick='blockTopic('" + sec + "')' disabled>关闭</Button></li>");
    if(checkElement("hasTopicDeletePermission"))
        $div.append("<li><Button class='btn btn-danger' id='topicDelBtn' onclick='deleteTopic('" + sec + "')'>删除</Button></li>");
    if(!checkData($('#condition_uid').val()))
        $div.append("<li><Button class='btn btn-primary' id='topicFavBtn' onclick='favoriteTopic('" + sec + "')' disabled>收藏 </Button></li>");
}

//检测主贴相关状态之查询收藏状态
function handleFavoriteStatus() {
    var condition = {
        "obid": Number($("#condition_id").val())
    };
    if ($('#condition_uid').val() != "") {
        $.ajax({
            type: 'POST',
            url: '/api/getIsFavorite',
            data: JSON.stringify(condition),
            contentType: 'application/json',
            async: false,
            success: function(data) {
                if (data == false)
                    $('#topicFavBtn').removeAttr('disabled');
            },
            error: function() {
                alert("查看收藏状态失败！");
            }
        });
    }
}

//初始化主贴的附件
function initAtt(attachment) {
    if(attachment.id != 0 && attachment.id != "null"){
        var content = "<input type='hidden' id='attSize' value='" + attachment.size + "'/><span class='glyphicon glyphicon-file'><a href='" + rootPath + "/file/download/" + attachment.id + "' id='attHref'>" + attachment.name + "</a></span>";
        $("#topic-content").append(content);
        var bytes = parseInt($("#attSize").val());
        var k = 1024;
        var sizes = ['B', 'KB', 'MB', 'GB'];
        var i = Math.floor(Math.log(bytes) / Math.log(k));
        var size = (bytes / Math.pow(k, i));
        $("#attHref").append("&emsp;&emsp;大小:" + parseFloat(size).toFixed(2) + sizes[i]);
    }
}

//初始化回复排序方式
function initReplySort() {
    var sort = $("#condition_sort").val(),
        sec = $("#condition_sec").val(),
        id = $("#condition_id").val(),
        $DivA = $("#defaultSort"),
        $DivB = $("#timeSort");
    if(sort == "default"){
        $DivA.attr("class","btn btn-default btn-xs active");
        $DivB.attr("href",rootPath + "/" + sec + "/" + id + "/time");
    }
    else{
        $DivB.attr("class","btn btn-default btn-xs active");
        $DivA.attr("href",rootPath + "/" + sec + "/" + id);
    }
}

//初始化主贴页面的回复框
function initTopicEditor() {
    var $div = $("#repliesDiv");
    if($('#condition_isBlock').val() == 'true') $div.after("<div class='alert alert-warning' role='alert'>该主贴已被关闭，禁止添加新回复</div>");//话题被关闭时显示提示信息
    else{//若话题未被关闭
        if(checkData($('#condition_uid').val()))//用户未登录时显示登录提示
            $div.after("<a class='btn Button--blue' href=" + rootPath  + "/user/login style='position:relative;top:10%'>登录</a>后回复");
        else{//用户登录时初始化富文本框
            $div.after(" <div class='panel panel-default'><div name='content' id='replyContent' style='height:20%'></div><Button class='btn btn-primary' name='content' style='float:right' onclick='addReply()'>评论</Button></div>");
            var editor = new wangEditor('replyContent');
            editor.create();
        }
    }
}

//查询投票信息
function initRateInfo() {
    var d = {
        "opid": $('#condition_id').val()
    };
    if ($('#condition_uid').val() != "") {
        $.ajax({
            type: 'POST',
            url: '/api/getTopicRateInfo',
            contentType: 'application/json',
            data: JSON.stringify(d),
            success: function(data) {
                console.log("查询到用户对该贴的投票信息是" + data);
                if ("like" == data) {
                    $("#like-0").css("background-color", "#A4D3EE");
                    $("#like-0").attr("disabled", "disabled");
                    $("#hate-0").attr("disabled", "disabled");
                } else if ("hate" == data) {
                    $("#hate-0").css("background-color", "#A4D3EE");
                    $("#hate-0").attr("disabled", "disabled");
                    $("#hate-0").attr("disabled", "disabled");
                }
            },
            error: function() {
                alert('发送请求失败！');
            }
        });
    }
}

//处理回复
function handleBestReply() {
    //处理回复
    $('article[id^=replyArticle-]').each(function() {
        var id = $(this).attr("id").substring(13);
        //对于被关闭的回复，隐藏它的内容
        if ($('#condition-replyStatus-' + id).val() == "true") $('#replyContent-' + id).html("该回复已被关闭");
        //对于未被关闭的回复,在关闭回复按钮存在的情况下，启用该按钮
        else if ($('#condition-replyStatus-' + id).val() == "false" && $('#blockReplyButton-' + id).length > 0) $('#blockReplyButton-' + id).removeAttr("disabled");
    });
}

//赞同主贴
function likeTopic(topicType, id) {
    if ($('#condition_uid').val() != "") {
        var da = {
            "id": parseInt(id),
            "aid": $("#condition_aid").val(),
            "sec": topicType
        };
        $.ajax({
            type: 'POST',
            url: '/' + topicType + '/' + id + '/like',
            contentType: 'application/json',
            data: JSON.stringify(da),
            success: function(data) {
                if (data == true) {
                    $("#like-0").css("background-color", "#A4D3EE");
                    $("#like-0").attr("disabled", "disabled");
                    $("#hate-0").attr("disabled", "disabled");
                } else
                    alert('投票失败，请稍后重试！');
            },
            error: function() {
                alert('发送请求失败！');
            }
        });
    }
}

//反对主贴
function hateTopic(topicType, id) {
    if ($('#condition_uid').val() != "") {
        var da = {
            "id": parseInt(id),
            "aid": $("#condition_aid").val(),
            "sec": topicType
        };
        $.ajax({
            type: 'POST',
            url: '/' + topicType + '/' + id + '/hate',
            data: JSON.stringify(da),
            contentType: 'application/json',
            success: function(data) {
                if (data == true) {
                    $("#hate-0").css("background-color", "#A4D3EE");
                    $("#like-0").attr("disabled", "disabled");
                    $("#hate-0").attr("disabled", "disabled");
                } else
                    alert('投票失败，请稍后重试！');
            },
            error: function() {
                alert('发送请求失败！');
            }
        });
    }
}

//删除主贴
function deleteTopic(topicType) {
    var d = {
        "id": $("#condition_id").val(),
        "aid": $("#condition_aid").val(),
        "title": $("#condition_title").val(),
        "sec": topicType
    };
    $.ajax({
        type: 'delete',
        url: '/' + topicType + '/' + $("#condition_id").val(),
        data: JSON.stringify(d),
        contentType: 'application/json',
        success: function(data) {
            if (data == true) {
                alert("删除成功");
            } else {
                alert("未能成功删除");
            }
            location.reload();
        },
        error: function() {
            alert("提交删除请求失败");
        }
    });
}

//更新教程
function updateTopic() {
    var editor = new wangEditor('contentDiv');
    editor.create();
    $("#updateButton").click(function() {
        var d = {
            "id": parseInt($("#id").val()),
            "aid": $("#aid").val(),
            "title": $("#title").val(),
            "content": filterEnter(filterXSS(editor.$txt.html())),
            "sec": $("#sec").val()
        };
        $.ajax({
            type: 'PUT',
            url: '/' + $("#sec").val() + '/' + $("#id").val() + '/update',
            data: JSON.stringify(d),
            contentType: 'application/json',
            success: function(data) {
                if (data == true) {
                    alert("成功修改该贴");
                } else {
                    alert("未能修改该贴");
                }
            },
            error: function() {
                alert("发送更新主贴请求失败！");
            }
        });
    });

}

//收藏主贴
function favoriteTopic(topicType) {
    var d = {
        "id": parseInt($("#condition_id").val()),
        "aid": $("#condition_aid").val(),
        "title": $("#condition_title").val(),
        "sec": topicType
    };
    $.ajax({
        type: 'POST',
        url: '/' + topicType + '/' + $('#condition_id').val() + '/favorite',
        data: JSON.stringify(d),
        contentType: 'application/json', //设置请求头信息
        success: function(data) {
            if (data == true) {
                alert("成功收藏该贴");
                location.reload();
            } else {
                alert("未能收藏该帖");
            }
        },
        error: function() {
            alert("发送收藏请求失败！");

        }
    });
}

//关闭话题
function blockTopic(resultType) {
    var d = {
        "id": $("#condition_id").val(),
        "aid": $("#condition_aid").val(),
        "title": $("#condition_title").val(),
        "sec": resultType
    };
    $.ajax({
        type: 'POST',
        url: '/' + resultType + '/' + $("#condition_id").val() + '/block',
        data: JSON.stringify(d),
        contentType: 'application/json',
        success: function(data) {
            if (data == true) {
                alert('成功关闭该话题');
            } else {
                alert('未能关闭该话题');
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

//---------------------- 回复部分 -----------------------------------------//

//加载最佳回复
function initBestReply(reply, bestId) {
    if (reply.id == bestId) {
        var temp = "<article class='clearfix widget-answers__item accepted' id='replyArticle-" + reply.id + "'><div class='post-col'><div class='widget-vote'>";
        if ($("#hasReplyLikePermission").val() == 'true')
            temp += "<button class='like' id='like-" + reply.id + "' onclick='likeReply('" + reply.id + "','" + reply.uid + "')'></button>";
        temp += "<span class='count'>" + reply.rateCount + "</span>";
        if ($("#hasReplyHatePermission").val() == 'true')
            temp += "<button class='hate' id='hate-" + reply.id + "' onclick='hateReply('" + reply.id + "','" + reply.uid + "')'></button>";
        temp += "</div><div class='text-center accepted-check cancel-cursor-pointer mt15' style='white-space:nowrap'> <span style='color: #3e7ac2;font-weight: 800;font-size:13px'>已采纳</span></div></div><div class='post-offset'><div class='answer fmt' id='replyContent-" + reply.id + "'>" + reply.content + "</div><div class='row answer__info--row'><div class='post-opt col-md-8 col-sm-8 col-xs-10'><ul class='list-inline mb0' id='replyUl-" + reply.id + "'><li><a id='reply-" + reply.id + "-time'>" + formateTime(reply.addTime) + "</a></li>";
        if ($("#hasReplyDeletePermission").val() == 'true')
            temp += "<li><Button class='btn btn-danger' onclick='deleteReply('" + reply.id + "','" + reply.uid + "')'>删除</Button></li>";
        if ($("#hasReplyBlockPermission").val() == 'true')
            temp += "<li><Button class='btn btn-primary' id='blockReplyButton-" + reply.id + "' onclick='blockReply('" + reply.id + "','" + reply.uid + "')' disabled>关闭</Button></li>";
        if ($("#hasReplyAdoptPermission").val() == 'true')
            temp += "<li><Button class='btn btn-primary' onclick='adoptReply('" + reply.id + "','" + reply.uid + "')'>采纳</Button></li>";
        temp += "</ul></div>";
        //右侧用户信息
        temp += "<div class='col-md-2 col-sm-2 col-xs-2 answer__info--author-avatar'><a class='mr10' href='" + rootPath + "/user/" + reply.uid + "/home'><img class='avatar-32' src='" + reply.user.avatar + "' alt='用户头像'></a></div>" + "<div class='col-md-2 col-sm-2 hidden-xs answer__info--author'><div class='answer__info--author-warp'><a class='answer__info--author-name' href='" + rootPath + "/user/" + reply.uid + "/home'>" + reply.user.name + "</a><span class='answer__info--author-rank'>" + reply.user.prestige + "声望</span></div></div>";
        temp += "</div></div></article>";
        $("#replies").append(temp);
    } else initSingleReply(reply);
}

//批量加载回复
function initReplies(replies, bestId) {
    if (replies.length > 0) {
        initBestReply(replies[0], bestId);
        for (var i = 1; i < replies.length; i++)
            initSingleReply(replies[i]);
    }
}

//批量加载回复之普通回复
function initSingleReply(reply) {
    var temp = "<article class='clearfix widget-answers__item' id='replyArticle-" + reply.id + "'><div class='post-col'><div class='widget-vote'>";
    if ($("#hasReplyLikePermission").val() == 'true')
        temp += "<button class='like' id='like-" + reply.id + "' onclick='likeReply('" + reply.id + "','" + reply.uid + "')'></button>";
    temp += "<span class='count'>" + reply.rateCount + "</span>";
    if ($("#hasReplyHatePermission").val() == 'true')
        temp += "<button class='hate' id='hate-" + reply.id + "' onclick='hateReply('" + reply.id + "','" + reply.uid + "')'></button>";
    temp += "</div><div class='text-center accepted-check cancel-cursor-pointer mt15' style='white-space:nowrap'></div></div><div class='post-offset'><div class='answer fmt' id='replyContent-" + reply.id + "'>" + reply.content + "</div><div class='row answer__info--row'><div class='post-opt col-md-8 col-sm-8 col-xs-10'><ul class='list-inline mb0' id='replyUl-" + reply.id + "'><li><a id='reply-" + reply.id + "-time'>" + formateTime(reply.addTime) + "</a></li>";
    if ($("#hasReplyDeletePermission").val() == 'true')
        temp += "<li><Button class='btn btn-danger' onclick='deleteReply('" + reply.id + "','" + reply.uid + "')'>删除</Button></li>";
    if ($("#hasReplyBlockPermission").val() == 'true')
        temp += "<li><Button class='btn btn-primary' id='blockReplyButton-" + reply.id + "' onclick='blockReply('" + reply.id + "','" + reply.uid + "')' disabled>关闭</Button></li>";
    if ($("#hasReplyAdoptPermission").val() == 'true')
        temp += "<li><Button class='btn btn-primary' onclick='adoptReply('" + reply.id + "','" + reply.uid + "')'>采纳</Button></li>";
    temp += "</ul></div>";
    //右侧用户信息
    temp += "<div class='col-md-2 col-sm-2 col-xs-2 answer__info--author-avatar'><a class='mr10' href='" + rootPath + "/user/" + reply.uid + "/home'><img class='avatar-32' src='" + reply.user.avatar + "' alt='用户头像'></a></div>" + "<div class='col-md-2 col-sm-2 hidden-xs answer__info--author'><div class='answer__info--author-warp'><a class='answer__info--author-name' href='" + rootPath + "/user/" + reply.uid + "/home'>" + reply.user.name + "</a><span class='answer__info--author-rank'>" + reply.user.prestige + "声望</span></div></div>";
    temp += "</div></div></article>";
    console.log("完成bestReply的拼接");
    $("#replies").append(temp);
}

//发表回复
function addReply() {
    var d = {
        "tid": Number($('#condition_id').val()),
        "title": $('#condition_title').val(),
        "uid": $('#condition_uid').val(),
        "content": $('#replyContent').val(),
        "addTime": new Date()
    };
    $.ajax({
        type: 'POST',
        url: '/reply',
        data: JSON.stringify(d),
        contentType: 'application/json',
        success: function(data) {
            if (data == true) {
                alert('成功发表回复');
                location.reload();
            } else {
                alert('未能发表回复，请稍后重试' + data);
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

//删除回复
function deleteReply(id, uid) {
    var d = {
        "id": Number(id),
        "tid": Number($('#condition_id').val()),
        "title": $("#condition_title").val(),
        "uid": uid
    };
    $.ajax({
        type: 'DELETE',
        url: '/reply',
        data: JSON.stringify(d),
        contentType: 'application/json',
        success: function(data) {
            if (data == true) {
                alert('成功删除回复');
                location.reload();
            } else {
                alert('未能删除回复，请稍后重试');
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

//关闭回复
function blockReply(id, uid) {
    var d = {
        "id": Number(id),
        "tid": Number($('#condition_id').val()),
        "title": $("#condition_title").val(),
        "uid": uid
    };
    $.ajax({
        type: 'POST',
        url: '/reply/block',
        data: JSON.stringify(d),
        contentType: 'application/json',
        success: function(data) {
            if (data == true) {
                alert('成功关闭回复');
                location.reload();
            } else {
                alert('未能关闭回复，请稍后重试');
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

//采纳回复
function adoptReply(id, uid) {
    var d = {
        "id": Number(id),
        "tid": Number($('#condition_id').val()),
        "title": $("#condition_title").val(),
        "uid": uid
    };
    $.ajax({
        type: 'POST',
        url: '/reply/adopt',
        data: JSON.stringify(d),
        contentType: 'application/json',
        success: function(data) {
            if (data == true) {
                alert('成功采纳回复');
                location.reload();
            } else {
                alert('未能采纳回复，请稍后重试');
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

//赞同回复
function likeReply(id, uid) {
    if ($('#condition_uid').val() != "") {
        var da = {
            "id": parseInt(id),
            "uid": uid
        };
        var d = {
            "opid": id
        };
        $.ajax({
            type: 'POST',
            url: '/api/getReplyRateInfo',
            contentType: 'application/json',
            data: JSON.stringify(d),
            success: function(data) {
                if (data != 'like' && data != 'hate') {
                    $.ajax({
                        type: 'POST',
                        url: '/reply/like',
                        data: JSON.stringify(da),
                        contentType: 'application/json',
                        success: function(data) {
                            if (data == true) {
                                $("#like-" + id).css("background-color", "#A4D3EE");
                                $("#like-" + id).attr("disabled", "disabled");
                                $("#hate-" + id).attr("disabled", "disabled");
                            } else
                                alert('投票失败，请稍后重试！');
                        },
                        error: function() {
                            alert('发送请求失败！');
                        }
                    });
                } else if (data == 'like') {
                    $("#like-" + id).attr("disabled", "disabled");
                    $("#hate-" + id).attr("disabled", "disabled");
                    $("#like-" + id).css("background-color", "#A4D3EE");
                } else if (data == 'hate') {
                    $("#like-" + id).attr("disabled", "disabled");
                    $("#hate-" + id).attr("disabled", "disabled");
                    $("#hate-" + id).css("background-color", "#A4D3EE");
                }
                alert("已投票，请勿重复投票");
            },
            error: function() {
                alert('发送请求失败！');
            }
        });
    }
}

//反对回复
function hateReply(id, uid) {
    if ($('#condition_uid').val() != "") {
        var da = {
            "id": parseInt(id),
            "uid": uid,
            "tid": parseInt($("#condition_id").val())
        };
        var d = {
            "opid": id
        };
        $.ajax({
            type: 'POST',
            url: '/api/getReplyRateInfo',
            contentType: 'application/json',
            data: JSON.stringify(d),
            success: function(data) {
                if (data != 'like' && data != 'hate') {
                    $.ajax({
                        type: 'POST',
                        url: '/reply/hate',
                        data: JSON.stringify(da),
                        contentType: 'application/json',
                        success: function(data) {
                            if (data == true) {
                                $("#hate-" + id).attr("disabled", "disabled");
                                $("#like-" + id).attr("disabled", "disabled");
                                $("#hate-" + id).css("background-color", "#A4D3EE");
                            } else
                                alert('投票失败，请稍后重试！');
                        },
                        error: function() {
                            alert('发送请求失败！');
                        }
                    });
                } else if (data == 'like') {
                    $("#like-" + id).attr("disabled", "disabled");
                    $("#hate-" + id).attr("disabled", "disabled");
                    $("#like-" + id).css("background-color", "#A4D3EE");
                } else if (data == 'hate') {
                    $("#hate-" + id).attr("disabled", "disabled");
                    $("#like-" + id).attr("disabled", "disabled");
                    $("#hate-" + id).css("background-color", "#A4D3EE");
                }
                alert("已投票，请勿重复投票");
            },
            error: function() {
                alert('发送请求失败！');
            }
        });
    }
}

//--------------------- 用户主页  -------------------------//

//加载回复信息--用户回答页面--批量加载
function initAnswers(answers) {
    if (checkData(answers))
        setEmptyDataTip("Profile-answers");
    else
        for (var i = 0; i < answers.length; i++)
            initAnswer(answers[i]);
}

//加载回复信息--用户回答页面--被调用
function initAnswer(answer) {
    var temp = "<div class='List-item'><div class='ContentItem'><h2 class='ContentItem-title'><a href='" + rootPath + "/tutorial/" + answer.tid + "'>" + answer.title + "</a></h2><div class='ContentItem-content is-collapsed'><span class='RichText CopyrightRichText-richText'>" + answer.content + "</span><a class='Button ContentItem-more Button--plain' type='button' href='" + rootPath + "/tutorial/" + answer.tid + "'>查看</a></div></div></div>";
    $("#Profile-answers").append(temp);
}

//加载收藏信息--用户收藏页面--批量加载
function initCollections(collections) {
    if (collections.length == 0)
        $("#Profile-collections").append("<div style='text-align:center'>查找的内容不存在</div>");
    else
        for (var i = 0; i < collections.length; i++)
            initCollection(collections[i]);
}

//加载收藏信息--用户收藏页面--被调用
function initCollection(collection) {
    var temp = "<div class='List-item'><div class='ContentItem'><h2 class='ContentItem-title'><a href='" + rootPath + "/" + collection.topic.sec + "/" + collection.obid + "'>" + collection.topic.title + "</a></h2><div class='ContentItem-content is-collapsed'><span class='RichText CopyrightRichText-richText'>" + collection.topic.content + "</span><a class='Button ContentItem-more Button--plain' type='button' href='" + rootPath + "/" + collection.topic.sec + "/" + collection.obid + "'>查看</a><p style='font-weight: 600'>收藏于" + new Date(collection.favTime).toLocaleString() + "</p></div></div></div>";
    $("#Profile-collections").append(temp);
}

//加载话题列表信息--用户列表页面--批量加载
function initLists(lists) {
    if (checkData(lists)) {
         setEmptyDataTip("Profile-" + $("#condition-topicType").val() + "s");
    } else {
        for (var i = 0; i < lists.length; i++) {
            initList(lists[i]);
        }
    }
}

//加载话题列表信息--用户列表页面--被调用
function initList(list) {
    var temp = "<div class='List-item'><div class='ContentItem'><h2 class='ContentItem-title'><a href='" + rootPath + "/" + list.sec + "/" + list.id + "'>" + list.title + "</a></h2><div class='ContentItem-content is-collapsed'><span class='RichText CopyrightRichText-richText'>" + list.content + "</span><a class='Button ContentItem-more Button--plain' type='button' href='" + rootPath + "/" + list.sec + "/" + list.id + "'>查看</a></div></div></div>";
    $("#Profile-" + $("#condition-topicType").val() + "s").append(temp);
}

//加载用户状态
function initProCard(status) {
    if(checkElement("blockUserButton")){
        if(status)  $("#blockUserButton").text("解封");
        else $("#blockUserButton").text("封禁");
    }
}

//加载用户主页中的主贴分类框
function initProHeaders(id, cid) {
    var temp = "<div class='ProfileMain-header'><ul role='tablist' class='Tabs ProfileMain-tabs'>";
    temp += "<li role='tab' class='Tabs-item'><a class='Tabs-link' id='Tabs-tutorials' href='" + rootPath + "/user/" + id + "/tutorials'>教程</a></li>";
    temp += "<li role='tab' class='Tabs-item'><a class='Tabs-link' id='Tabs-answers' href='" + rootPath + "/user/" + id + "/answers'>回答</a></li>";
    temp += "<li role='tab' class='Tabs-item'><a class='Tabs-link' id='Tabs-questions' href='" + rootPath + "/user/" + id + "/questions'>提问</a></li>";
    temp += "<li role='tab' class='Tabs-item'><a class='Tabs-link' id='Tabs-articles' href='" + rootPath + "/user/" + id + "/articles'>文章</a></li>";
    temp += "<li role='tab' class='Tabs-item'><a class='Tabs-link' id='Tabs-codes' href='" + rootPath + "/user/" + id + "/codes'>代码</a></li>";
    temp += "<li role='tab' class='Tabs-item'><a class='Tabs-link' id='Tabs-collections' href='" + rootPath + "/user/" + id + "/collections'>收藏</a></li>";
    temp += "</ul></div>";

    $("#Profile-" + cid + "s").before(temp);
    $("#Tabs-" + cid + "s").attr("class", "Tabs-link is-active");
}

//加载用户通知中心--批量加载
function initNtfs(ntfs, id) {
    for (var i = 0; i < ntfs.length; i++)
        initNtf(ntfs[i], id);
}

//加载用户通知中心--被调用
function initNtf(ntf, id) {
    var temp = "<div class='List-item'><div class='ContentItem'><div class='ContentItem-content is-collapsed'><span class='RichText CopyrightRichText-richText'>" + ntf.content + "</span><br />" + formateTime(ntf.ntftime) + "  ";
    if (ntf.isnew == true)
        temp += "<a class='Button Button--blue' type='button' onclick='readNtf(" + id + "," + ntf.id + ")'>标记为已读</a>";
    else
        temp += "<a class='Button Button--plain' type='button'>已读</a>";
    temp += "</div></div></div>";
    $("#Profile-ntfs").append(temp);
}

//将信息标记为已读
function readNtf(uid, id) {
    $.ajax({
        type: 'PUT',
        url: '/user/' + uid + '/ntfs/' + id,
        contentType: 'application/json',
        success: function(data) {
            if (data == true) {
                location.reload();
            } else if (data == false) {
                alert('未能标记，请稍后重试');
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

//用户修改密码
function updateAccount(uid) {
    if ($('#newPass').val() != null && $('#newPass').val().split(' ').join('') != '' && $('#newPass').val() == $('#confirmPass').val()) {
        $.ajax({
            type: 'PUT',
            url: '/user/' + uid + '/account',
            contentType: 'application/json',
            data: JSON.stringify({
                "pass" : $("#oldPass").val(),
                "email" : $("#newPass").val()
            }),
            success: function(data) {
                if (data == true) {
                    alert("您已成功修改密码!");
                    location.reload();
                } else if (data == false) {
                    alert("未能修改密码，请稍后重试!");
                }
            },
            error: function() {
                alert('发送请求失败！');
            }
        });
    } else {
        alert("密码不符合要求，请重新确认");
        window.location.reload();
    }
}

//用户上传头像
function uploadAvatar() {
    var formData = new FormData();
    formData.append('file', $('#avatarFile')[0].files[0]);
    $.ajax({
        url: '/file/upload/avatar',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function(data) {
            if (data == "error") {
                $("#uploadAvatarButton").attr("class", "btn btn-error");
                $("#uploadAvatarButton").attr("value", "上传失败");
            } else {
                $("#uploadAvatarButton").attr("class", "btn btn-success");
                $("#uploadAvatarButton").attr("value", "上传成功");
                $("#avatarPanel").append("<input type='hidden' id='avatar' value=' " + data + "'>");
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}


//用户修改信息
function updateProfile() {
    var fields = ["id","name","description","email","avatar"];
    var values = [$("#userid").val(),$("#name").val(),$("#description").val(),$("#email").val(), $("#avatar").val()];
    var data = constructor(fields,values,false);
    if(!checkData(data))
    $.ajax({
        url: '/user/' + $("#id").val() + '/profile',
        type: 'PUT',
        contentType: 'application/json',
        data: data,
        success: function(data) {
            if (data == true) {
                alert('成功修改个人信息');
            } else {
                alert('未能修改个人信息！请稍后重试');
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

//用户上传文件
function uploadFile() {
    var formData = new FormData();
    formData.append('file', $('#codeFile')[0].files[0]);
    $("#uploadBtn").attr("value", "上传中...");
    $.ajax({
        url: '/file/upload/file',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function(data) {
            if (data == "error") {
                $("#uploadBtn").attr("class", "btn btn-danger");
                $("#uploadBtn").attr("value", "上传失败");
            } else {
                $("#uploadBtn").attr("class", "btn btn-success");
                $("#uploadBtn").attr("value", "上传成功");
                $("#attid").attr("value", data);
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

//扩展：用户上传头像实时预览
jQuery.fn.extend({
    uploadPreview: function(opts) {
        var _self = this,
            _this = $(this);
        opts = jQuery.extend({
            Img: "ImgPr",
            Width: 100,
            Height: 100,
            // ImgType: ["gif", "jpeg", "jpg", "bmp", "png"],
            Callback: function() {}
        }, opts || {});
        _self.getObjectURL = function(file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
        _this.change(function() {
            if (this.value) {
                try {
                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                } catch (e) {
                    var src = "";
                    var obj = $("#" + opts.Img);
                    var div = obj.parent("div")[0];
                    _self.select();
                    if (top != self) {
                        window.parent.document.body.focus()
                    } else {
                        _self.blur()
                    }
                    src = document.selection.createRange().text;
                    document.selection.empty();
                    obj.hide();
                    obj.parent("div").css({
                        'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
                        'width': opts.Width + 'px',
                        'height': opts.Height + 'px'
                    });
                    div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src;
                }
                opts.Callback()
            }
        })
    }
});

function blockUser(uid) {
    $.ajax({
        url: '/user/' + uid + "/block",
        type: 'POST',
        success: function(data) {
            if (data == true) {
                alert("封禁成功~");
            } else {
                alert("封禁失败~");
            }
            location.reload();
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

function deleteUser(uid) {
    $.ajax({
        url: '/user/' + uid + "/delete",
        type: 'POST',
        success: function(data) {
            if (data == true) {
                alert("删除成功~");
            } else {
                alert("删除失败~");
            }
        },
        error: function() {
            alert('发送请求失败！');
        }
    });
}

//添加学生账户
function addUser(type) {
    var d = {
        "id": $("#id").val(),
        "name": $("#name").val(),
        "email": $("#email").val()
    };
    $.ajax({
        type: 'post',
        url: '/user/add' + type,
        data: JSON.stringify(d),
        contentType: 'application/json',
        success: function(data) {
            if (data == true) {
                alert("成功添加账户");
            } else {
                alert("未能添加账户");
            }
            location.reload();
        },
        error: function() {
            alert("提交请求失败");
        }
    });
}
