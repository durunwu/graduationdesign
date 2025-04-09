
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/static/jquery-3.2.1.min.js">
    // window.onunload(alert("jin7"))
    window.onunload = function() {
        alert("jin7");
    };
</script>
<html>
<head>
    <title>会员卡列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/table/bootstrap-table.min.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/table/bootstrap-table.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/table/locale/bootstrap-table-zh-CN.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/sweetalert/sweetalert.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/sweetalert/sweetalert.min.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/date/bootstrap-datetimepicker.min.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date/Moment.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/date/bootstrap-datetimepicker.min.js"></script>

    <script>
        $(function () {
            $('#table').bootstrapTable({
                url:'${pageContext.request.contextPath}/subject/subjectReservationQuery',
                method:'post',
                contentType:"application/x-www-form-urlencoded",
                columns:[
                    { field:'subId',title:'课程编号',sortable: true},
                    { field:'subname',title:'课程名称',sortable: true},
                    { field:'sellingPrice',title:'课程售价',sortable: true},
                    { field:'subjectContainNum',title:'人数上限',sortable: true},
                    { field:'alreadyReservationNum',title:'已预约人数',sortable: true},
                    {
                        field:'xx',title:'预约操作',
                        formatter : function(value, row, index) {
                            return "<a href='javascript:upd1("+row.subId+")' class='glyphicon glyphicon-ok'></a>";
                            // return "<a title='删除' href='javascript:del1("
                            //     + row.subId + ")'><span class='glyphicon glyphicon-trash'></span></a> | <a href='javascript:upd1("+row.subId+")' class='glyphicon glyphicon-pencil'></a>";
                        }

                    }
                ],
                queryParamsType:'',
                queryParams:queryParams,
                height:360,
                pageList:[5,10,15],
                pageNumber:1,
                pageSize:5,
                pagination:true,
                sidePagination:'server',

            })
        });
        //删除
        function del1(id){

            swal({
                    title: "确定删除吗？",
                    text: "您将无法恢复！",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "确定删除！",
                    cancelButtonText: "取消删除！",
                    closeOnConfirm: false,
                    closeOnCancel: false
                },
                function (isConfirm) {
                    if (isConfirm) {
                        var opt=$('#table').bootstrapTable('getOptions');
                        var subjectid=$('#cardid').val();

                        $.post('${pageContext.request.contextPath}/subject/del',{'subId':id,"pageSize":opt.pageSize,"pageNumber":opt.pageNumber,"subname":subjectid},function(data){
                            //重新给table绑定数据
                            $("#table").bootstrapTable("load",data) ;
                        }) ;
                        swal("删除！", "删除成功",
                            "success");

                    } else {
                        swal("取消！", "您已取消删除)",
                            "error");
                    }
                });
        }

        //获取当前的条件个页面页数即使更新值
        function queryParams(afds){
            var i={
                "pageSize":afds.pageSize,
                "pageNumber":afds.pageNumber,
                "id":$('#cardid').val(),
            };
            return i;
        }
        //查询
        function search(){
            var opt=$('#table').bootstrapTable('getOptions');
            var subjectid=$('#cardid').val();

            $.post("${pageContext.request.contextPath}/subject/subjectReservationQuery",{"pageSize":opt.pageSize,"pageNumber":opt.pageNumber,"subname":subjectid},function (releset) {
                $("#table").bootstrapTable('load',releset) ;
            })
        }

        function save(){
            if(!validateAdd()){
                return;
            }
            //接收数据
            var opt=$('#table').bootstrapTable('getOptions');
            var subjectid=$('#cardid').val();
            var name=$("#name").val();
            var money = $("#money").val();
            $("#myModal").modal("hide") ;
            $.post("${pageContext.request.contextPath}/subject/count",{"subname":name},function (releset) {
                $("#table").bootstrapTable('load',releset) ;
                if(releset<1){
                    $.post('${pageContext.request.contextPath}/subject/add',{'subname':name,'sellingPrice':money},function(data){
                        $("#table").bootstrapTable("load",data) ;
                        $.post("${pageContext.request.contextPath}/subject/query",{"pageSize":opt.pageSize,"pageNumber":opt.pageNumber,"subname":subjectid},function (releset) {
                            $("#table").bootstrapTable('load',releset) ;
                        })
                        swal("添加！", "添加成功",
                            "success");
                    }) ;
                }else if(releset>0){
                    swal("失败！", "已有该课程，请重新输入！",
                        "error");
                    $.post("${pageContext.request.contextPath}/subject/query",{"pageSize":opt.pageSize,"pageNumber":opt.pageNumber,"subname":subjectid},function (releset) {
                        $("#table").bootstrapTable('load',releset) ;
                    })
                }
            })
        }

        function validateAdd() {
            $("#name").parent().find("span").remove();
            $("#money").parent().find("span").remove();


            var name = $("#name").val().trim();
            if(name == null || name == ""){
                $("#name").parent().append("<span style='color:red'>请输入课程名称</span>");
                return false;
            }

            var money = $("#money").val().trim();
            if(money == null || money == ""){
                $("#money").parent().append("<span style='color:red'>请输入费用</span>");
                return false;
            }


            if(!(/^[0-9,.]*$/.test(money))){
                $("#money").parent().append("<span style='color:red'>费用只能为正整数或小数</span>");
                return false;
            }

            return true;
        }
        function upd1(id){
            $("#myModal2").modal("show");
            $('#id').val(id);
            $.post('${pageContext.request.contextPath}/subject/getReservationSubjectById',{'subId':id},function(data){
                $("#table").bootstrapTable("load",data) ;
                $("#xgname").val(data.subname);
                $("#xgmoney").val(data.sellingPrice);
            }) ;

        }
        <%--function upd1(id){--%>
        <%--    $("#myModal2").modal("show");--%>
        <%--    $('#id').val(id);--%>
        <%--    $.post('${pageContext.request.contextPath}/subject/cha',{'subId':id},function(data){--%>
        <%--        $("#table").bootstrapTable("load",data) ;--%>
        <%--        $("#xgname").val(data.subname);--%>
        <%--        $("#xgmoney").val(data.sellingPrice);--%>
        <%--    }) ;--%>

        <%--}--%>
        function kong() {

            $("#name").val("");
            $("#money").val("");
        }
        function upd(){
            if(!validateUpd()){
                return;
            }
            var opt=$('#table').bootstrapTable('getOptions');
            var subjectid=$('#cardid').val();
            var id =  $('#id').val();
            var name = $("#xgname").val();
            var money= $("#xgmoney").val();
            $("#myModal2").modal("hide") ;
            $.post('${pageContext.request.contextPath}/subject/upd',{'subId':id,'subname':name,'sellingPrice':money},function(data){
                $.post("${pageContext.request.contextPath}/subject/query",{"pageSize":opt.pageSize,"pageNumber":opt.pageNumber,"subname":subjectid},function (releset) {
                    $("#table").bootstrapTable('load',releset) ;
                })
                $("#table").bootstrapTable("load",data) ;
                swal("修改！", "修改成功",
                    "success");
            }) ;
        }

        <%--function saveReservation(){--%>
        <%--    if(!validateUpd()){--%>
        <%--        return;--%>
        <%--    }--%>
        <%--    var opt=$('#table').bootstrapTable('getOptions');--%>
        <%--    var subjectid=$('#cardid').val();--%>
        <%--    var id =  $('#id').val();--%>
        <%--    var name = $("#xgname").val();--%>
        <%--    var money= $("#xgmoney").val();--%>
        <%--    var start_time= $("#xgstart_time").val();--%>
        <%--    var end_time= $("#xgend_time").val();--%>
        <%--    $("#myModal2").modal("hide") ;--%>
        <%--    $.post('${pageContext.request.contextPath}/subject/saveReservation',{'subId':id,'subname':name,'sellingPrice':money,'reservation_start_time':start_time,'reservation_end_time':end_time},function(data){--%>
        <%--        $.post("${pageContext.request.contextPath}/subject/subjectReservationQuery",{"pageSize":opt.pageSize,"pageNumber":opt.pageNumber,"subname":subjectid},function (releset) {--%>
        <%--            $("#table").bootstrapTable('load',releset) ;--%>
        <%--        })--%>
        <%--        $("#table").bootstrapTable("load",data) ;--%>
        <%--        swal("保存！", "保存成功",--%>
        <%--            "success");--%>
        <%--    },"json").contentType("application/json"); ;--%>
        <%--}--%>
        function saveReservation(){
            if(!validateUpd()){
                return;
            }
            var opt=$('#table').bootstrapTable('getOptions');
            var subjectid=$('#cardid').val();
            var id =  $('#id').val();
            var name = $("#xgname").val();
            var money= $("#xgmoney").val();
            var start_time = $("#start_time").val();
            var end_time = $("#end_time").val();

            // 校验 start_time 必须小于 end_time
            if (start_time && end_time) {
                var startDate = new Date(start_time);
                var endDate = new Date(end_time);
                // 校验 startDate 是否有效（不能为空）
                if (isNaN(startDate.getTime())) {
                    swal("错误", "开始时间格式不正确，请输入有效的日期时间", "error");
                    return;
                }
                // 校验 endDate 是否有效（不能为空）
                if (isNaN(endDate.getTime())) {
                    swal("错误", "结束时间格式不正确，请输入有效的日期时间", "error");
                    return;
                }
                if (startDate >= endDate) {
                    swal("错误", "开始时间必须早于结束时间", "error");
                    return;
                }
            } else {
                // 如果 start_time 或 end_time 为空
                if (!start_time) {
                    swal("错误", "开始时间不能为空", "error");
                }
                if (!end_time) {
                    swal("错误", "结束时间不能为空", "error");
                }
                return;
            }

            // 将日期时间转换为时间戳
            var start_timestamp = new Date(start_time).getTime();
            var end_timestamp = new Date(end_time).getTime();
            $("#myModal2").modal("hide");
            var dataToSend = {'subId':id,'subName':name,'sellingPrice':money,'reservationStartTime':start_timestamp,'reservationEndTime':end_timestamp};
            $.ajax({
                type: "POST",
                url: '${pageContext.request.contextPath}/subject/saveReservation',
                contentType: "application/json",
                data: JSON.stringify(dataToSend),
                success: function (data) {
                    // 根据实际需求选择使用 data 还是 releset
                    $.post("${pageContext.request.contextPath}/subject/subjectReservationQuery", {
                        "pageSize": opt.pageSize,
                        "pageNumber": opt.pageNumber,
                        "subname": subjectid
                    }, function (releset) {
                        // 假设使用 releset 数据加载表格
                        $("#table").bootstrapTable('load', releset);
                        swal("保存！", "保存成功", "success");
                    });
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log('jqXHR:', jqXHR);
                    console.log('textStatus:', textStatus);
                    console.log('errorThrown:', errorThrown);
                    let errorMessage;
                    if (jqXHR.responseJSON && jqXHR.responseJSON.message) {
                        errorMessage = jqXHR.responseJSON.message;
                    } else if (jqXHR.status === 404) {
                        swal("执行成功","", "success");
                    } else if (textStatus === 'error' && jqXHR.status === 500) {
                        errorMessage = "服务器内部错误，请稍后重试。建议联系管理员查看服务器日志。";
                    } else if (textStatus === 'timeout') {
                        errorMessage = "请求超时，请稍后重试。";
                    } else {
                        errorMessage = `请求出错：${textStatus}，错误信息：${errorThrown}`;
                    }
                    if (errorMessage = "/subject/saveReservation.jsp") {
                        swal("执行成功","", "success");
                    }else {
                        swal(errorMessage,"", "error");
                    }
                }
            });

        }

        function validateUpd() {
            $("#xgname").parent().find("span").remove();
            $("#xgmoney").parent().find("span").remove();


            var xgname = $("#xgname").val().trim();
            if(xgname == null || xgname == ""){
                $("#xgname").parent().append("<span style='color:red'>请输入课程名称</span>");
                return false;
            }

            var xgmoney = $("#xgmoney").val().trim();
            if(xgmoney == null || xgmoney == ""){
                $("#xgmoney").parent().append("<span style='color:red'>请输入费用</span>");
                return false;
            }


            if(!(/^[0-9,.]*$/.test(xgmoney))){
                $("#xgmoney").parent().append("<span style='color:red'>费用只能为正整数或小数</span>");
                return false;
            }

            return true;
        }


    </script>
</head>
<body>
    <%--    //查询--%>
    <div class="panel panel-default">
    <div class="panel-body">
        <form class="form-inline">
            <div  class="input-group input-daterange">
                <label for="cardid" class="control-label">课程名称:</label>
                <input id="cardid" type="text" class="form-control">
            </div>
            <button onclick="search()" type="button" class="btn btn-default" style="margin-top: 20px" >查询</button>
<%--             <button type="button" class="btn btn-default" onclick="kong()" style="float: right; margin-top: 20px" data-toggle="modal" data-target="#myModal">--%>
<%--                 <span class="glyphicon glyphicon-plus"></span>添加课程</button>--%>
        </form>
    </div>



</div>
    <%--//页面数据展示--%>
    <div>
        <table id="table"></table>
    </div>
    <div class="modal fade" id="myModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">❤增加新课程</h4>
                </div>
                <div class="modal-body">
                    <!-- form开始 -->
                    <form>
                        <div class="form-group">
                            <label for="name" class="col-sm-4 control-label"style="margin-top: 10px">课程名称</label>
                            <div class="col-sm-8">
                                <input type="text"style="margin-top: 10px" class="form-control" id="name" parsley-trigger="change" parsley-required="true" parsley-minlength="4" parsley-type="email" parsley-validation-minlength="1">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="money" class="col-sm-4 control-label"style="margin-top: 10px">费用</label>
                            <div class="col-sm-8">
                                <input type="text"style="margin-top: 10px" class="form-control" id="money" parsley-trigger="change" parsley-required="true" parsley-minlength="4" parsley-type="email" parsley-validation-minlength="1">
                            </div>
                        </div>
                    </form>
                    &nbsp;
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button onclick="save()" id = "add" type="button" class="btn btn-primary">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>




    <div class="modal fade" id="myModal2">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel2">预约课程</h4>
                </div>
                <div class="modal-body">
                    <!-- form开始 -->
                    <form>
                        <input type="hidden" id="id" name="id">
                        <div class="form-group">
                            <label for="xgname" class="col-sm-4 control-label"style="margin-top: 10px">课程名称</label>
                            <div class="col-sm-8">
                                <input type="text"style="margin-top: 10px" class="form-control" id="xgname" parsley-trigger="change" parsley-required="true" parsley-minlength="4" parsley-type="email" parsley-validation-minlength="1">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="xgmoney" class="col-sm-4 control-label"style="margin-top: 10px">课程费用</label>
                            <div class="col-sm-8">
                                <input type="text"style="margin-top: 10px" class="form-control" id="xgmoney" parsley-trigger="change" parsley-required="true" parsley-minlength="4" parsley-type="email" parsley-validation-minlength="1">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="start_time" class="col-sm-4 control-label" style="margin-top: 10px">开始时间</label>
                            <div class="col-sm-8">
                                <input type="datetime-local" style="margin-top: 10px" class="form-control" id="start_time"
                                       parsley-trigger="change" parsley-required="true">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="end_time" class="col-sm-4 control-label" style="margin-top: 10px">结束时间</label>
                            <div class="col-sm-8">
                                <input type="datetime-local" style="margin-top: 10px" class="form-control" id="end_time"
                                       parsley-trigger="change" parsley-required="true">
                            </div>
                        </div>
                    </form>
                    &nbsp;
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button onclick="saveReservation()"  type="button" class="btn btn-primary">保存</button>
                    </div>
                </div>
            </div>
        </div></div>
</body>
</html>
