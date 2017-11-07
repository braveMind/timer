<!DOCTYPE html>
<html lang="en">
<#import "/common/common.import.ftl" as common>
<#import "/common/common.list.ftl" as list>
<@common.stylecss />
<link href="${request.contextPath}/static/plugin/jquery.pagination.css" type="text/css" rel="stylesheet"/>
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
                    <li class="active">控制台</li>
                </ul>
                <!-- .breadcrumb -->

                <div class="nav-search" id="nav-search">
                    <form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input"
                                           id="nav-search-input" autocomplete="off"/>
									<i class="icon-search nav-search-icon"></i>
								</span>
                    </form>
                </div>
                <!-- #nav-search -->
            </div>

            <div class="page-content">
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">

                        <div class="table-header">
                            任务执行日志列表
                        </div>

                        <div class="table-responsive">
                            <div id="sample-table-2_wrapper" class="dataTables_wrapper" role="grid">

                                <table id="sample-table-2"
                                       class="table table-striped table-bordered table-hover dataTable"
                                       aria-describedby="sample-table-2_info">
                                    <thead>
                                    <tr role="row">
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="display: none; width: 265px;"
                                            aria-label="Domain: activate to sort column ascending">日志ID
                                        </th>
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 265px;"
                                            aria-label="Domain: activate to sort column ascending">应用名称
                                        </th>
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 185px;"
                                            aria-label="Price: activate to sort column ascending">任务名称
                                        </th>
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 185px;"
                                            aria-label="Price: activate to sort column ascending">IP地址
                                        </th>
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 295px;"
                                            aria-label="

															Update
														: activate to sort column ascending">
                                            <i class="icon-time bigger-110 hidden-480"></i>
                                            开始时间
                                        </th>
                                        <th class="sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 295px;"
                                            aria-label="

															Update
														: activate to sort column ascending">
                                            <i class="icon-time bigger-110 hidden-480"></i>
                                            结束时间
                                        </th>
                                        <th class="hidden-480 sorting" role="columnheader" tabindex="0"
                                            aria-controls="sample-table-2" rowspan="1" colspan="1" style="width: 254px;"
                                            aria-label="Status: activate to sort column ascending" >执行结果
                                        </th>
                                    </tr>
                                    </thead>


                                    <tbody role="alert" aria-live="polite" aria-relevant="all" id="listData">
                                    </tbody>
                                </table>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="dataTables_info" id="sample-table-2_info">
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="dataTables_paginate paging_bootstrap">
                                            <div id="page1"></div>
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
    <div class="tooltip top in" style="top: 100px; left: 993px; display: none;" id="toolTip"><div class="tooltip-inner">search engines : 24.5%</div></div>

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div>
<!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->
<@common.stylejs />
<script type="text/javascript">
    var jobList;
    $("#page1").page({
        remote: {
            url: '/diaryList/',  //请求地址
            callback: function (result, pageIndex) {
                var out = "";
                jobList = result.data;
                for(var i = 0; i < result.data.length; i++) {
                    out += "<tr class='odd'> <td class=' ' style='display: none;'>" +
                            i +
                            "</td><td class=' '>" +
                            result.data[i].appName +
                            "</td> <td class=' '>" +
                            result.data[i].jobName +
                            "</td> <td class=' '>" +
                            result.data[i].address +
                            "</td> <td class='hidden-480 '>" +
                            result.data[i].startTime +
                            "</td> <td class=' '>" +
                            result.data[i].endTime +
                            "</span> </td> <td class='hidden-480 '> <span class='label label-sm label-warning'>" +
                            "<div data-toggle='tooltip' data-delay= 'show: 100' data-placement='bottom' title='" +
                            result.data[i].info +
                            "'>" +
                            result.data[i].result +"</div>" +
                            "</span> </td></tr>"
                }
                $("#listData").html(out);
            },
            pageIndexName: 'pageIndex',
            pageSizeName: 'pageSize',
            totalName: 'total'

        }
    });

</script>

</body>
</html>

