<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<#import "/common/common.import.ftl" as common>
<#import "/common/common.list.ftl" as list>
<@common.stylecss />

<link href="${request.contextPath}/static/plugin/jquery.pagination.css" type="text/css" rel="stylesheet"/>
<link href="${request.contextPath}/static/assets/css/css-timer/box.css" type="text/css" rel="stylesheet"/>
<link href="${request.contextPath}/static/toast/jquery.toast.css" type="text/css" rel="stylesheet"/>
<link href="${request.contextPath}/static/select/ui-select.css" type="text/css" rel="stylesheet"/>

<style>
    .guagua{
        width: 90px;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
    }
</style>
<body>

<div class="navbar navbar-default" id="navbar">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="#" class="navbar-brand">
                <small>
                    <i class="icon-leaf"></i>
                    Timer 任务调度平台
                </small>
            </a><!-- /.brand -->
        </div>
        <!-- /.navbar-header -->

        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">


                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="${request.contextPath}/static/assets/avatars/user.jpg"
                             alt="Jason's Photo"/>
								<span class="user-info">
									<small>欢迎光临,</small>
									Jason
								</span>

                        <i class="icon-caret-down"></i>
                    </a>

                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="#">
                                <i class="icon-cog"></i>
                                设置
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                <i class="icon-user"></i>
                                个人资料
                            </a>
                        </li>

                        <li class="divider"></li>

                        <li>
                            <a href="#">
                                <i class="icon-off"></i>
                                退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- /.ace-nav -->
        </div>
        <!-- /.navbar-header -->
    </div>
    <!-- /.container -->
</div>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <div class="sidebar" id="sidebar">
            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'fixed')
                } catch (e) {
                }
            </script>


        <@list.list />            <!-- /.nav-list -->

            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
                   data-icon2="icon-double-angle-right"></i>
            </div>

            <script type="text/javascript">
                try {
                    ace.settings.check('sidebar', 'collapsed')
                } catch (e) {
                }
            </script>
        </div>

        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="#">首页</a>
                    </li>
                    <li class="active">任务列表</li>
                </ul>
                <!-- .breadcrumb -->

                <div class="nav-search" id="nav-search" style="right: 80px">
                    <div class="row">
                    <button class="btn btn-xs btn-primary col-sm-4" type="button"  onclick="clearModal()"><i class="icon-plus align-top bigger-125"></i>新增</button>

                    <form class="form-search col-sm-6">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="icon-search nav-search-icon"></i>
								</span>
                    </form>
                    </div>
                </div>
                <!-- #nav-search -->
            </div>

            <div class="page-content">
                <!-- /.page-header -->

                <div class="row">
                    <!-- Modal -->
                    <div class="modal fade" id="myModal"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content" style="width: 800px">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title" id="myModalLabel">新增任务调度信息</h4>
                                </div>
                                <div class="modal-body" id="dialog">
                                    <input id="jobId" style="display: none;">
                                    <div class="row" style="padding-top: 10px">
                                        <label class="col-md-2 box">应用名</label>
                                        <div class="col-md-4">
                                            <div class="input-icon boxText">
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="应用名" id="appName">
                                            </div>
                                        </div>

                                        <label class="col-md-2 box">任务名</label>
                                        <div class="col-md-4">
                                            <div class="input-icon boxText">
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="任务名" id="jobName">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row" style="padding-top: 10px">
                                        <label class="col-md-2 box">任务描述</label>
                                        <div class="col-md-4">
                                            <div class="input-icon">
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="业务描述" id="description">
                                            </div>
                                        </div>

                                        <label class="col-md-2 box">任务负责人</label>
                                        <div class="col-md-4">
                                            <div class="input-icon">
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="mis号,多个请用逗号隔开" id="jobOwner">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row" style="padding-top: 10px">
                                        <label class="col-md-2 box">执行类名</label>
                                        <div class="col-md-4">
                                            <div class="input-icon" >
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="执行类名" id="className">
                                            </div>
                                        </div>

                                        <label class="col-md-2 box">执行方法名</label>
                                        <div class="col-md-4">
                                            <div class="input-icon">
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="执行方法名" id="methodName">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row" style="padding-top: 10px">
                                        <label class="col-md-2 box">失败报警阈值</label>
                                        <div class="col-md-4">
                                            <div class="input-icon">
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="任务执行连续失败报警阈值" id="retryCount"/>
                                            </div>
                                        </div>

                                        <label class="col-md-2 box">路由策略</label>
                                        <div class="col-md-4">
                                            <div class="input-icon">
                                                <i class="fa fa-check-circle-o"></i>
                                                <select class="form-control" placeholder="任务执行路由策略" id="routeStrategy" onchange="showTargetIp()">
                                                    <option value="0">第一个</option>
                                                    <option value="1">最后一个</option>
                                                    <option value="2">轮询</option>
                                                    <option value="3">随机执行</option>
                                                    <option value="4">一致性HASH</option>
                                                    <option value="5" selected="selected">故障转接</option>
                                                    <option value="6">指定机器,调试时使用</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row" style="padding-top: 10px">
                                        <label class="col-md-2 box">执行参数</label>
                                        <div class="col-md-4">
                                            <div class="input-icon">
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="执行参数,以逗号隔开" id="params">
                                            </div>
                                        </div>

                                        <label class="col-md-2 box">cron表达式</label>
                                        <div class="col-md-4">
                                            <div class="input-icon">
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="cron表达式" id="cron">
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row" style="padding-top: 10px" display="none" id="showIp">
                                        <label class="col-md-2 box">指定IP</label>
                                        <div class="col-md-4">
                                            <div class="input-icon">
                                                <i class="fa fa-check-circle-o"></i>
                                                <input class="form-control" placeholder="指定执行机IP" id="targetIp">
                                            </div>
                                        </div>
                                    </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal" id="close">Close</button>
                                    <button type="button" class="btn btn-primary" onclick="createJob()">Save changes</button>
                                </div>
                            </div>
                                </div>
                        </div>
                    </div>
                        <div class="table-header">
                            定时任务列表
                        </div>

                        <div class="table-responsive">
                            <div id="sample-table-2_wrapper" class="dataTables_wrapper" role="grid">

                                <table id="sample-table-2"
                                       class="table table-striped table-bordered table-hover dataTable"
                                       aria-describedby="sample-table-2_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="display:none; width: 265px;"
                                            aria-label="Domain: activate to sort column ascending">任务ID
                                        </th>
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 185px;"
                                            aria-label="Domain: activate to sort column ascending">任务名称
                                        </th>
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 200px;"
                                            aria-label="Price: activate to sort column ascending">应用名称
                                        </th>
                                        <th class="hidden-480 sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 150px;"
                                            aria-label="Clicks: activate to sort column ascending">执行类名
                                        </th>
                                        <th class="hidden-480 sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 150px;"
                                            aria-label="Clicks: activate to sort column ascending">执行方法
                                        </th>
                                        <th class="hidden-480 sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 100px;"
                                            aria-label="Clicks: activate to sort column ascending">方法参数
                                        </th>
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 180px;"
                                            aria-label="

															Update
														: activate to sort column ascending">
                                            <i class="icon-time bigger-110 hidden-480"></i>
                                            cron表达式
                                        </th>
                                        <th class="hidden-480 sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 100px;"
                                            aria-label="Status: activate to sort column ascending">任务状态
                                        </th>
                                        <th class="sorting_disabled" role="columnheader" rowspan="1" colspan="1"
                                            style="width: 300px;" aria-label=""></th>
                                    </tr>
                                    </thead>
                                <#--开始 ajax-->

                                    <tbody role="alert" aria-live="polite" aria-relevant="all" id="listData">
                                    </tbody>
                                </table>
                                <div class="row">

                                    <div class="col-sm-12">
                                        <div class="dataTables_paginate paging_bootstrap">
                                            <ul class="pagination">
                                                <div id="page1"></div>
                                            </ul>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content -->

    </div>
    <!-- /.main-container-inner -->
</tr>
    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>

</div>
<script src="${request.contextPath}/static/assets/js/jquery-1.10.2.min.js?v=1"></script>
<script src="${request.contextPath}/static/plugin/jquery.pagination-1.2.1.js"></script>
<@common.stylejs />
<script type="text/javascript">
    var jobInfo;
    var loadJobList = function () {
        $("#page1").page({
            remote: {
                url: '/jobPage/',  //请求地址
                callback: function (result, pageIndex) {
                    jobInfo = result.data;
                    var out = "";
                    for (var i = 0; i < result.data.length; i++) {
                        out += "<tr class='odd'> <td class='id' style='display: none'> <a href='#'>"+
                                i+
                                "</a> </td> <td class='hidden-480'>" +
                                result.data[i].jobName +
                                "</td> <td class='hidden-480 '>" +
                                result.data[i].appName +
                                "</td> <td class='hidden-480'>" +
                                result.data[i].className +
                                "</td>  <td class='hidden-480'>" +
                                result.data[i].methodName + "</td> <td class='hidden-480'> <div class='guagua' title='" +
                                result.data[i].params + "'>" +
                                result.data[i].params + "</div> </td> <td class='hidden-480 '> <span class='label label-sm label-warning'>" +
                                result.data[i].cron +
                                "</span> </td> <td class='hidden-480 '> <span class='label label-sm label-warning'>" +
                                result.data[i].status +
                                "</span> </td> " + "<td class=' '> <div class='visible-md visible-lg hidden-sm hidden-xs action-buttons'> <a class='blue' href='javascript:void(0);'> <i class='icon-zoom-in bigger-130'></i> </a> <a class='green' href='javascript:void(0);' onclick='editJob(this)'> <i class='icon-pencil bigger-130'></i> </a> <a class='red' href='javascript:void(0);' onclick='deleteJob(this)'> <i class='icon-trash bigger-130'></i> </a> <button class='btn btn-minier btn-success' style='bottom:4px;' onclick='runOneTime(this)'>立即运行一次</button><label> <input  type='checkbox' class='ace ace-switch ace-switch-3' onchange='changeJobStatus(this)'" +
                                (result.data[i].status ==true? "checked ='true'":"") + "> <span class='lbl'></span> </label> </div> </td>";
                    }
                    $("#listData").html(out);
                },
                pageIndexName: 'pageIndex',
                pageSizeName: 'pageSize',
                totalName: 'total'

            }
        });
    }
    loadJobList();

    var createJob = function () {
        var data = {
            id:$('#jobId').val()?new Number($('#jobId').val().trim()) : null,
            appName:$("#appName").val()? $("#appName").val() : null,
            jobName:$("#jobName").val()? $("#jobName").val() : null ,
            jobOwner:$("#jobOwner").val()? $("#jobOwner").val() : null,
            description:$("#description").val()? $("#description").val() : null,
            className:$("#className").val()? $("#className").val() : null,
            methodName:$("#methodName").val()? $("#methodName").val() : null,
            retryCount:$("#retryCount").val()? $("#retryCount").val() : null,
            routeStrategy:$("#routeStrategy").val()? $("#routeStrategy").val() : null,
            cron:$("#cron").val()? $("#cron").val() : null,
            params:$("#params").val()? $("#params").val() : null,
            targetIp:$("#targetIp").val()? $("#targetIp").val() : null
        };
        var jobId = $('#jobId').val();
        var url = jobId? "/api/modifyJob" : "/api/createJob";
        data =JSON.stringify(data);
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: data,
            dataType: "json",
            success: function (message) {
                if(message.status == 1) {
                    $("#dialog input").val("");
                    $('#close').click();
                    location.reload();
                    showToast('Success', '任务创建成功！');
                } else {
                    showToast('Error', message.message);
                }
            },
            error: function (message) {
                showToast('Error', '网络异常！');
            }
        });
    };
    var editJob = function (obj) {
        $("#showIp").display = 'none';
        $('#myModalLabel').text("修改任务调度信息");
        $('#myModal').modal();
        var tdList = $(obj).parents("tr").find("td");
        var index = tdList[0].innerText.trim();
        $("#jobId").val(jobInfo[index].id);
        $("#jobName").val(jobInfo[index].jobName);
        $("#appName").val(jobInfo[index].appName);
        $("#jobOwner").val(jobInfo[index].jobOwner);
        $("#description").val(jobInfo[index].description);
        $("#className").val(jobInfo[index].className);
        $("#methodName").val(jobInfo[index].methodName);
        $("#retryCount").val(jobInfo[index].retryCount);
        $("#routeStrategy").val(jobInfo[index].routeStrategy);
        $("#params").val(jobInfo[index].params);
        $("#cron").val(jobInfo[index].cron);
        $("#targetIp").val(jobInfo[index].targetIp);
        $("#dialog input").attr("disabled","disabled");
        $("#jobOwner").removeAttr("disabled");
        $("#cron").removeAttr("disabled");
        $("#params").removeAttr("disabled");
        if(jobInfo[index].routeStrategy == "6") {
            $("#targetIp").removeAttr("disabled");
        }
    };

    var deleteJob = function (obj) {
        var tdList = $(obj).parents("tr").find("td");
        var index = tdList[0].innerText.trim();
        var requestUrl = "/api/deleteJob/" + jobInfo[index].id;
        var callback = new function() {
            location.reload();
        };
        sendPostRequest(requestUrl, '', callback);
    };
    var runOneTime = function (obj) {
        var tdList = $(obj).parents("tr").find("td");
        var index = tdList[0].innerText.trim();
        var requestUrl = "/api/triggerJob/" + jobInfo[index].id;
        var callback = new function(message) {
            showToast('Success', '运行成功！');
        };
        sendPostRequest(requestUrl, '', callback);

    }
    
    var clearModal = function () {
        $('#myModalLabel').text("新建任务调度信息");
        $("#dialog input").removeAttr("disabled");
        $("#dialog input").val("");
        $("#targetIp").attr("disabled","disabled");
        $("#targetIp").attr("disabled","disabled");
        $('#myModal').modal();
    };

    var changeJobStatus = function (obj) {
        var tdList = $(obj).parents("tr").find("td");
        var index = tdList[0].innerText.trim();
        var requestUrl;
        if($(obj)[0].checked) {
            requestUrl = "/api/resumeJob/" + jobInfo[index].id;
        } else {
            requestUrl = "/api/pauseJob/" + jobInfo[index].id;
        }
        var callback = function (message) {
            tdList[7].innerHTML = '<span class="label label-sm label-warning">' +
                    $(obj)[0].checked + '</span>';
            var toastText = $(obj)[0].checked ? '任务已恢复！' : '任务已暂停！' ;
            showToast('Success', toastText);

        };
        sendPostRequest(requestUrl, '', callback);
    };

    var showTargetIp = function () {
        var strategy = $("#routeStrategy").val();
        if(strategy == '6') {
            $("#targetIp").removeAttr("disabled");
        } else {
            $("#targetIp").attr("disabled","disabled");
        }
    }

    var sendPostRequest = function (url, data, callback) {
        $.ajax({
            type: "POST",
            url: url,
            contentType: "application/json; charset=utf-8",
            data: data,
            dataType: "json",
            success: function (message){
                if(message.status == 1) {
                    callback(message);
                } else {
                    showToast('Error', message.message);
                }
            },
            error: function (message) {
                showToast('Error', '提交数据失败！');
            }
        });
    };

    var showToast = function (type, content) {
        $.toast({
            heading: type,
            text: content,
            position: 'top-right',
            stack: false
        })
    }
</script>
<script src="${request.contextPath}/static/toast/jquery.toast.js"></script>
<script src="${request.contextPath}/static/select/ui-select.js"></script>
</body>
</html>

