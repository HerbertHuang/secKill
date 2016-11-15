/**
 * Created by Herbert on 2016/11/13.
 */
var seckill = {
    //
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + "/exposer";
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    handleSeckillkill: function (seckillId, node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>').show();
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            //在毁掉函数中,执行交互逻辑
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    //开始
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log('killUrl:' + killUrl);
                    $('#killBtn').one('click', function () {
                        $(this).addClass('disabled');
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();
                } else {
                    //未开始
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countdown(seckillId, now, start, end);
                }
            } else {
                console.log('result' + result);
            }
        });
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        if (nowTime > endTime) {
            //秒杀结束
            seckillBox.html('秒杀结束');
        } else if (nowTime < startTime) {
            //秒杀未开始
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime, function (event) {
                console.log('start to countdown');
                var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
                seckillBox.html(format);
            }).on('finish.countdown', function () {
                //获取秒杀地址
                seckill.handleSeckillkill(seckillId, seckillBox);
            });
        } else {
            //秒杀开始
            seckill.handleSeckillkill(seckillId, seckillBox);
        }
    },
    validatePhone: function (phone) {
        if (phone && phone.length() == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    detail: {
        //detail init
        init: function (params) {
            //手机验证,计时
            //在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            console.log(killPhone);
            //验证手机号
            if (!seckill.validatePhone(killPhone)) {
                var killPhoneModal = $('#killPhoneModal');
                console.log('killPhoneModal' + killPhoneModal);
                killPhoneModal.modal({
                    show: true
                    //backdrop: 'static',
                    //keyboard: false
                });
                $('#killPhoneKey').attr('disabled', false);
                console.log('end');
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    console.log("inputPhone" + inputPhone);
                    if (seckill.validatePhone(inputPhone)) {
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                });
            }
            //已经登录
            console.log("already logon");
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    console.log('nowTime' + nowTime);
                    console.log('startTime' + startTime);
                    console.log('endTime' + endTime);
                    //时间判断
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                }
            });
        }
    }
}