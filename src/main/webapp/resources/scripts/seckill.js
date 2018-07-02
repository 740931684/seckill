//存放交互逻辑
//JavaScript 模块化

var seckill = {
    //封装秒杀相关ajax的url
    URL: {
        now: function () {
            return '/seckill/time/now';
        }
    },

    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },

    handleSeckill: function (seckillIid,node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
    },

    countdown: function (seckillId,nowTime,startTime,endTime) {
        var seckillBox = $('#seckill-box');
        alert("seckillId=" +seckillId +" nowTime=" + nowTime + " startTime=" +startTime + " endTime=" +endTime);
        if (nowTime > endTime) {
            seckillBox.html('秒杀结束');
        }else if (nowTime < startTime) {
            //秒杀尚未开始
            var killTime = new Date(startTime + 1000);

             seckillBox.countdown(killTime, function (event) {

                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒');

                seckillBox.html(format);
            }).on('finish.countdown',function () {
                 //获取秒杀地址，执行秒杀
                 seckill.handleSeckill();
             });
        } else {

        }
    },


    detail: {
        init: function (params) {

            //手机验证和登录
            var killPhone = $.cookie('killPhone');

            //alert("----t0----killPhone="+killPhone);
            //验证手机号
            if (!seckill.validatePhone(killPhone)) {

                //绑定手机号码
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show: true,              //显示弹出层
                    backdrop: 'static',      //禁止位置关闭
                    keyboard: false          //关闭键盘事件
                });
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    if (seckill.validatePhone(inputPhone)) {
                        //电话写入cookie中  期限是7天
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //刷新页面
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<lable class="label label-danger">手机号错误！</lable>').show(300);
                    }
                });
            }
            //已经登录
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(), {}, function (result) {
                //alert("----result----="+result['success'] + "|" +result['data']);
                if (result && result['success']) {
                   // alert("----result inside----="+result['']);
                    var nowTime = result['data'];
                    //时间判断
                    seckill.countdown(seckillId, nowTime, startTime, endTime);
                } else {
                    console.log('result:' + result);
                }
            });



        }
    }

}
