// ajax请求操作
var ajaxHtml = (function() {

    var requestData = function(url, data, config, type) {
        console.log(url)
        var def = $.Deferred();
        $.ajax({
            type: type,
            data: data,
            timeout: 15000,
            url: url,
            dataType: 'html',
            crossDomain: true,
            success: function(res) {
                def.resolve(res);
            },
            error: function(xhr,err) {
                console.log(err);
            }
        });
        return def.promise();
    };

    var o = {};
    
    o.get = function(url, data, config) {
        return requestData(url, data, config, 'get');
    };
    o.post = function(url, data, config) {
        return requestData(url, data, config, 'post');
    };
    return o;
})();