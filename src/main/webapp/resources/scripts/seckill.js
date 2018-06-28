//存放交互逻辑
//JavaScript 模块化

var seckill = {
    //封装秒杀相关ajax的url
    URL: {},

    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },

    detail: {
        init: function (params) {

            //手机验证和登录
            var killPhone = $.cookie('killPhone');
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            alert("----t0----killPhone="+killPhone);
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
                        setAttribute("sessionID",inputPhone);
                        window.location.reload();
                    } else {
                        $('#killPhoneMessage').hide().html('<lable class="label label-danger">手机号错误！</lable>').show(300);
                    }
                });
            }
        }
    }

}
