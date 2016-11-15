<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/header.jsp"%>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading">
                <h1>${seckill.name}</h1>
            </div>
            <div class="panel-body">
                <h2 class="text-danger">
                    <!--glyphicon glyphicon-time-->
                    <span class=""></span>
                    <!--glyphicon-->
                    <span class="" id="seckill-box"></span>
                </h2>
            </div>
        </div>
    </div>

    <div class="modal fade" id="killPhoneModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title text-center">
                        <!--glyphicon glyphicon-phone-->
                        <span class="">秒杀电话:</span>
                    </h3>
                </div>
                <div class="modal-body">
                    <span class="row">
                        <div class="col-xs-8 col-xs-offset-2">
                            <input type="text" name="killPhone" id="killPhoneKey"
                                   placeholder="请输入手机号" class="form-control"/>
                        </div>
                    </span>
                </div>
                <div class="modal-footer">
                    <!--glyphicon-->
                    <span id="killPhoneMessage" class=""></span>
                    <button type="button" id="killPhoneBtn" class="btn btn-success">
                        <!--glyphicon glyphicon-phone-->
                        <span class="">提交</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</body>
<%--<script src="http://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.min.js"></script>--%>
<script src="/resource/script/jquery.countdown.min.js"></script>
<script src="/resource/script/seckill.js" type="application/javascript"></script>
<script type="application/javascript">
    $(function () {
        seckill.detail.init({
            seckillId : ${seckill.seckillId},
            startTime : ${seckill.startTime.time},//毫秒
            endTime : ${seckill.endTime.time}
        });
    });
</script>
</html>