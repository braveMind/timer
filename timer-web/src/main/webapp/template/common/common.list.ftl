<#macro list>
<ul class="nav nav-list">
    <li class="active">
        <a href="index.html">
            <i class="icon-dashboard"></i>
            <span class="menu-text"> 控制台 </span>
        </a>
    </li>

    <li>
        <a href="${request.contextPath}/jobList">
            <i class="icon-text-width"></i>
            <span class="menu-text"> 任务列表 </span>
        </a>
    </li>
    <li>
        <a href="${request.contextPath}/machine">
            <i class="icon-text-width"></i>
            <span class="menu-text"> 服务列表 </span>
        </a>
    </li>
    <li>
        <a  href="${request.contextPath}/diary">
            <i class="icon-text-width"></i>
            <span class="menu-text"> 日志 </span>
        </a>
    </li>



</ul><!-- /.nav-list -->
</#macro>

<#macro tableList>
'<td class=" "> <div class="visible-md visible-lg hidden-sm hidden-xs action-buttons"> <a class="blue" href="#"> <i class="icon-zoom-in bigger-130"></i> </a> <a class="green" href="#"> <i class="icon-pencil bigger-130"></i> </a> <a class="red" href="#"> <i class="icon-trash bigger-130"></i> </a> </div> <div class="visible-xs visible-sm hidden-md hidden-lg"> <div class="inline position-relative"> <button class="btn btn-minier btn-yellow dropdown-toggle"data-toggle="dropdown"> <i class="icon-caret-down icon-only bigger-120"></i> </button> <ul class="dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close"> <li> <a href="#" class="tooltip-info" data-rel="tooltip" title=""data-original-title="View"> <span class="blue"> <i class="icon-zoom-in bigger-120"></i> </span> </a> </li> <li> <a href="#" class="tooltip-success" data-rel="tooltip"title="" data-original-title="Edit"> <span class="green"> <i class="icon-edit bigger-120"></i> </span> </a> </li> <li> <a href="#" class="tooltip-error" data-rel="tooltip"title="" data-original-title="Delete"> <span class="red"> <i class="icon-trash bigger-120"></i> </span> </a> </li> </ul> </div> </div> </td>';

</ul><!-- /.nav-list -->
</#macro>